# Fase 1: DDL - Visão Geral

## Objetivo

Motor mínimo funcional: o usuário abre o terminal, faz login, cria e dropa schemas e tabelas, e os arquivos são gerados e removidos no disco.

Essa é a primeira fase com código Java de verdade. Tudo antes disso (Fase 0) foi só documentação e decisões de design.

## Escopo

- Tabela de sistema pra autenticação (sys_users)
- Terminal interativo com login
- Classe central do motor (Engine)
- Parser SQL só pra comandos DDL
- Executor DDL que cria e remove arquivos e pastas no disco
- Comando EXIT

## O que NÃO entra

INSERT, SELECT, UPDATE, DELETE, índices, estatísticas, transações, multi-usuário, plano de execução.

## User Stories

| ID | Título | Prioridade |
|----|--------|-----------|
| US-001 | Tabela de sistema sys_users | 1 |
| US-002 | Terminal com login | 3 |
| US-003 | Classe Engine | 4 |
| US-004 | Parser DDL | 5 |
| US-005 | Executor DDL | 6 |
| US-006 | Comando EXIT | 2 |

## Ordem de implementação

1. **US-001** (sys_users): montar estrutura base no disco com schema system e registro root/root
2. **US-006** (EXIT): implementar cedo pra sempre ter como sair do loop
3. **US-002** (terminal + login): prompt interativo que lê sys_users e autentica
4. **US-003** (Engine): classe central que recebe SQL e despacha
5. **US-004** (parser DDL): interpretar CREATE SCHEMA, CREATE TABLE, DROP TABLE
6. **US-005** (executor DDL): criar pastas e arquivos no disco

## Ao final da Fase 1, o usuário pode:

1. Iniciar a aplicação
2. Fazer login com root/root
3. Criar um schema novo
4. Criar tabelas com colunas e tipos
5. Dropar tabelas
6. Sair da aplicação

Todas as operações refletem como arquivos e pastas reais dentro de `data/`.
