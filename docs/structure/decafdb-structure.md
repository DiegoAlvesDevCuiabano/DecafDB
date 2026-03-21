# DecafDB: Structure Document (Phase 0)

## 1. Overview

DecafDB is a didactic database written in pure Java. Its purpose is to teach how a database works internally, implementing concept by concept in incremental layers.

The name is a play on words: Java = coffee, DecafDB = decaffeinated version, lightweight, without the heavy complexity of a real database.

## 2. Directory Layout

DecafDB stores everything under a root directory called `data/`. Each schema is a directory. Inside each schema, each table is a directory. Inside each table directory are its files.

```
decafdb/
  data/
    catalog.json
    stats.json
    public/
      schema.json
      alunos/
        alunos.meta
        alunos.decaf
      professores/
        professores.meta
        professores.decaf
    financeiro/
      schema.json
      contas/
        contas.meta
        contas.decaf
```

**Rules:**

- One schema = one directory inside `data/`
- One table = one directory inside the schema
- Every table must have a `.meta` (structure) and a `.decaf` (data) file
- Future files (indexes, per-table statistics) go inside the table directory
- Schema-level elements (views, procedures) go inside the schema directory
- `catalog.json` only knows which schemas exist
- `schema.json` knows which tables exist in that schema
- No duplication: each level manages its own inventory

**Future extensions inside the table directory:**

- `.idx` for index files
- `.stats` for table statistics

## 3. File Formats

### 3.1 catalog.json

Global registry. Only knows schemas.

```json
{
  "schemas": ["public", "financeiro"],
  "default_schema": "public",
  "created_at": "2026-03-21T22:00:00"
}
```

### 3.2 schema.json

Schema metadata. Knows its tables and, in the future, views/procedures.

```json
{
  "schema_name": "public",
  "tables": ["alunos", "professores"],
  "created_at": "2026-03-21T22:00:00"
}
```

### 3.3 .meta (table metadata)

Defines the table structure. Everything the engine needs to know how to read the `.decaf` file.

**Example for table `alunos`:**

```json
{
  "table_name": "alunos",
  "schema": "public",
  "columns": [
    { "name": "id",
      "type": "INTEGER",
      "size": 4,
      "auto_increment": true },
    { "name": "nome",
      "type": "VARCHAR",
      "size": 100 },
    { "name": "idade",
      "type": "INTEGER",
      "size": 4 }
  ],
  "row_size": 121,
  "next_id": 1,
  "record_count": 0,
  "created_at": "2026-03-21T22:00:00"
}
```

### 3.4 Internal Fields vs User Fields

DecafDB separates two concepts:

- `_rowid`: physical address of the record in the file. Managed by the system. It can change when the purge routine compacts the `.decaf` file (removes deleted records and reorganizes the file). The user never sees or manipulates it.

- `id` (auto_increment): logical identifier created by the user. It never changes and is never reused. This is the field the user references in WHERE clauses, relationships, and queries.

They must exist separately because the purge routine reorganizes the file and recalculates physical positions. The logical `id` must remain stable regardless of physical changes.

**All internal fields (invisible to the user):**

- `_rowid` (4 bytes): physical position, managed by the system
- `_deleted` (1 byte): 0 = active, 1 = deleted
- `_deleted_at` (8 bytes): deletion timestamp in milliseconds, 0 if active

**`row_size` calculation:**

```
  1   (_deleted)
+ 8   (_deleted_at)
+ 4   (_rowid)
+ 4   (id)
+ 100 (nome)
+ 4   (idade)
= 121 bytes per record
```

To find record N: `offset = N * row_size`. Direct access, no scanning required.

### 3.5 Supported Types

- `INTEGER`: 4 bytes, supports up to ~2.1 billion
- `REAL`: 8 bytes, Java double, 15 decimal digits of precision
- `VARCHAR(n)`: n bytes, fixed-length, padded with spaces
- `BOOLEAN`: 1 byte, 0 = false, 1 = true

### 3.6 Auto Increment

Columns marked with `auto_increment: true` in the `.meta` file have their values generated automatically by the system. The `next_id` field in `.meta` controls the next value. IDs are never reused, even after deletion.

### 3.7 .decaf (data)

Binary file. Each record is a fixed-length byte sequence in the following order:

```
[_deleted][_deleted_at][_rowid][id][nome][idade]
 1 byte    8 bytes     4 bytes 4b  100b  4b
```

**Record 0 (active, offset 0):**

```
_deleted    = 0 (active)
_deleted_at = 0 (no date)
_rowid      = 1
id          = 42
nome        = "João" + spaces up to 100 bytes
idade       = 20
```

**Record 1 (active, offset 121):**

```
_deleted    = 0 (active)
_deleted_at = 0 (no date)
_rowid      = 2
id          = 43
nome        = "Maria" + spaces up to 100 bytes
idade       = 22
```

**Record 2 (deleted, offset 242):**

```
_deleted    = 1 (deleted)
_deleted_at = 1711035200000 (deletion timestamp)
_rowid      = 3
id          = 44
nome        = "Pedro" + spaces up to 100 bytes
idade       = 19
```

This record is in the trash. The purge routine will permanently remove it after 30 days from the `_deleted_at` date. When that happens, the `.decaf` file is compacted and the `_rowid` values of subsequent records may change. The `id` 44 will never be reused.

### 3.8 Trash (Purge)

Deleted records are not removed immediately. They are marked with `_deleted = 1` and `_deleted_at` is filled with the deletion timestamp.

A purge routine scans the `.decaf` files and permanently removes records older than 30 days since deletion. After purging, the file is compacted and `_rowid` values are recalculated.

### 3.9 stats.json

Consolidated global statistics. The execution engine reads this file to make query plan decisions without scanning the `.decaf` files.

```json
{
  "last_updated": "2026-03-21T23:00:00",
  "tables": {
    "public.alunos": {
      "record_count": 10000,
      "active_records": 9850,
      "deleted_records": 150,
      "columns": {
        "id": {
          "min": 1,
          "max": 10000,
          "cardinality": 10000
        },
        "idade": {
          "min": 15,
          "max": 65,
          "cardinality": 51
        }
      }
    }
  }
}
```

## 4. Architecture Decision Records (ADRs)

**ADR-001: Metadata in JSON**
Human-readable, easy to debug, didactic. Real databases use binary, but for DecafDB clarity is worth more than performance at this layer.

**ADR-002: Fixed-length binary data**
VARCHAR is stored internally as fixed-length padded with spaces. Simple, predictable, offset-calculable. The space waste trade-off becomes teaching material.

**ADR-003: Table as directory**
A table will have more than just data (indexes, statistics, constraints). Everything stays together in the directory. Avoids complicated naming conventions.

**ADR-004: Soft delete with 30-day trash**
DELETE marks `_deleted = 1` and fills `_deleted_at`. A purge routine permanently removes records after 30 days and compacts the file.

**ADR-005: Separation between _rowid and id**
`_rowid` is the physical address of the record, it can change after compaction. `id` with auto_increment is the logical identity, it never changes and is never reused. They exist separately because the purge routine reorganizes the file and recalculates physical positions.

**ADR-006: Distributed catalog by level**
`catalog.json` knows schemas. `schema.json` knows tables. Each level manages its own inventory. No duplication.

**ADR-007: Statistics separated from catalog**
`stats.json` holds statistics. `catalog.json` holds structure. If one gets corrupted, the other survives.

**ADR-008: In-memory counters**
Counters updated on every INSERT/DELETE. Persisted to stats.json periodically. Implementation when DML exists.
