# US-001: Tabela de Sistema sys_users

## História

**Como** sistema, **preciso** de uma tabela de usuários no schema `system`, **para** autenticar quem acessa o banco.

## Descrição

A `sys_users` é a primeira tabela que existe no DecafDB. Ela fica num schema dedicado chamado `system`, separado do `public` onde ficam as tabelas do usuário. Essa tabela usa o mesmo formato `.meta` e `.decaf` de qualquer tabela normal, ou seja, o banco usa a si mesmo pra autenticar.

Essa estrutura já vem pronta com o banco. Não é criada em runtime.

## Estrutura no disco

```
DecafDB/
  data/
    catalog.json              # lista schemas: "system" e "public"
    stats.json                # estatísticas (vazio por agora)
    system/                   # schema de sistema
      schema.json             # lista tabelas: ["sys_users"]
      sys_users/              # pasta da tabela
        sys_users.meta        # definição das colunas
        sys_users.decaf       # registro root/root em binário
    public/                   # schema padrão do usuário
      schema.json             # lista tabelas: [] (vazio)
```

## Definição da tabela

**Schema**: system

**Tabela**: sys_users

**Colunas:**

| Nome | Tipo | Tamanho | Auto Increment |
|------|------|---------|----------------|
| id | INTEGER | 4 bytes | sim |
| username | VARCHAR | 50 bytes | não |
| password | VARCHAR | 50 bytes | não |

**Campos internos (invisíveis pro usuário):**

| Nome | Tamanho | Descrição |
|------|---------|-----------|
| _deleted | 1 byte | 0 = ativo, 1 = deletado |
| _deleted_at | 8 bytes | timestamp da exclusão, 0 se ativo |
| _rowid | 4 bytes | posição física |

**row_size**: 1 + 8 + 4 + 4 + 50 + 50 = **117 bytes**

## Dado inicial

O `.decaf` já vem com um registro:

```
_deleted    = 0
_deleted_at = 0
_rowid      = 1
id          = 1
username    = "root" + espaços até 50 bytes
password    = "root" + espaços até 50 bytes
```

## Critérios de aceite

- Pasta `data/system/sys_users/` existe
- `sys_users.meta` tem as colunas certas e row_size = 117
- `sys_users.decaf` tem exatamente um registro (root/root) legível pelo motor
- `catalog.json` lista "system" e "public"
- `system/schema.json` lista "sys_users"
