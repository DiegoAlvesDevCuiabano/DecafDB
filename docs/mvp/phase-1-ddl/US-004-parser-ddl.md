# US-004: Parser DDL

## História

**Como** Engine, **preciso** interpretar comandos DDL, **para** saber o que o usuário quer fazer.

## Descrição

O Parser recebe a string SQL crua e transforma num objeto estruturado que o Engine entende. Na Fase 1, ele só precisa entender DDL: CREATE SCHEMA, CREATE TABLE e DROP TABLE.

Se não entender o que o usuário digitou, retorna erro claro.

## Comandos suportados

### CREATE SCHEMA

```sql
CREATE SCHEMA financeiro
```

Extrai: tipo do comando (CREATE_SCHEMA), nome do schema.

### CREATE TABLE

```sql
CREATE TABLE alunos (
  id INTEGER AUTO_INCREMENT,
  nome VARCHAR(100),
  idade INTEGER
)
```

Extrai: tipo do comando (CREATE_TABLE), nome da tabela, lista de colunas com nome, tipo, tamanho e flag de auto_increment.

Se não especificar schema, assume `public`.

### DROP TABLE

```sql
DROP TABLE alunos
```

Extrai: tipo do comando (DROP_TABLE), nome da tabela.

## Tipos suportados

| Tipo | Tamanho |
|------|---------|
| INTEGER | 4 bytes (fixo) |
| REAL | 8 bytes (fixo) |
| VARCHAR(n) | n bytes (obrigatório informar) |
| BOOLEAN | 1 byte (fixo) |

VARCHAR sem tamanho deve retornar erro.

## Palavras reservadas

Não podem ser nome de tabela ou coluna:

CREATE, DROP, ALTER, SCHEMA, TABLE, INSERT, SELECT, UPDATE, DELETE, WHERE, FROM, INTO, VALUES, SET, AND, OR, NOT, INTEGER, REAL, VARCHAR, BOOLEAN, AUTO_INCREMENT, EXIT, TRUE, FALSE, NULL

## Erros esperados

- Comando não reconhecido: "Unrecognized command: {input}"
- Sem nome de tabela: "Table name is required"
- Sem colunas: "At least one column is required"
- VARCHAR sem tamanho: "VARCHAR requires a size: VARCHAR(n)"
- Palavra reservada como nome: "{word} is a reserved word and cannot be used as a name"
- Coluna duplicada: "Duplicate column name: {name}"

## Critérios de aceite

- `CREATE SCHEMA financeiro` retorna objeto parseado com tipo CREATE_SCHEMA e nome "financeiro"
- `CREATE TABLE alunos (id INTEGER AUTO_INCREMENT, nome VARCHAR(100), idade INTEGER)` retorna tabela com 3 colunas corretas
- `DROP TABLE alunos` retorna objeto com tipo DROP_TABLE e nome "alunos"
- `XPTO BLAH` retorna erro claro
- `CREATE TABLE SELECT (id INTEGER)` retorna erro de palavra reservada
- `CREATE TABLE alunos (nome VARCHAR)` retorna erro de tamanho faltando
- `CREATE TABLE alunos (id INTEGER, id INTEGER)` retorna erro de coluna duplicada
- Parser é case-insensitive pra keywords (CREATE = create = Create)
