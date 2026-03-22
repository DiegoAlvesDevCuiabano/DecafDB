# US-005: Executor DDL

## História

**Como** Engine, **preciso** executar os comandos DDL parseados, **para** criar e remover schemas e tabelas no disco.

## Descrição

O Executor DDL recebe o objeto parseado do Engine e faz as operações reais no disco: cria pastas, cria arquivos, atualiza JSONs. É a camada que toca no filesystem.

## Operações

### CREATE SCHEMA

1. Valida: schema não existe no `catalog.json`
2. Cria pasta `data/{nome_schema}/`
3. Cria `data/{nome_schema}/schema.json` com lista de tabelas vazia
4. Adiciona nome do schema no `catalog.json`
5. Retorna: "Schema {nome} created."

### CREATE TABLE

1. Valida: schema existe
2. Valida: tabela não existe naquele schema
3. Calcula `row_size`: 1 (_deleted) + 8 (_deleted_at) + 4 (_rowid) + soma das colunas
4. Cria pasta `data/{schema}/{nome_tabela}/`
5. Cria `.meta` com definição das colunas, row_size, next_id, record_count
6. Cria `.decaf` vazio (0 bytes)
7. Adiciona nome da tabela no `schema.json`
8. Retorna: "Table {nome} created."

### DROP TABLE

1. Valida: tabela existe no `schema.json`
2. Remove pasta `data/{schema}/{nome_tabela}/` e tudo dentro
3. Remove nome da tabela do `schema.json`
4. Retorna: "Table {nome} dropped."

## Validações e erros

- Schema já existe: "Schema {nome} already exists."
- Schema não existe (pra criar tabela): "Schema {nome} does not exist."
- Tabela já existe: "Table {nome} already exists in schema {schema}."
- Tabela não existe (pra drop): "Table {nome} does not exist."
- Tentar dropar tabela de sistema: "Cannot drop system tables."

## Proteções

- Schema `system` não pode ser dropado
- Tabela `sys_users` não pode ser dropada
- Schema `public` não pode ser dropado

## Critérios de aceite

- `CREATE SCHEMA financeiro` cria `data/financeiro/` com `schema.json` e atualiza `catalog.json`
- `CREATE TABLE alunos (id INTEGER AUTO_INCREMENT, nome VARCHAR(100), idade INTEGER)` cria `data/public/alunos/` com `.meta` correto (row_size = 121) e `.decaf` vazio
- `DROP TABLE alunos` remove a pasta e atualiza `schema.json`
- Criar schema/tabela duplicada retorna erro
- Dropar tabela que não existe retorna erro
- Dropar `sys_users` retorna erro de proteção
- Dropar schema `system` retorna erro de proteção
