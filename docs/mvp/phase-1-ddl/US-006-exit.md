# US-006: Comando EXIT

## História

**Como** usuário, **quero** digitar EXIT pra sair do banco, **para** encerrar minha sessão.

## Descrição

O comando EXIT encerra o loop do terminal e fecha a aplicação. É reconhecido diretamente pelo Terminal, antes de chegar no Engine.

## Comportamento

- `EXIT` ou `exit` ou `Exit` (case-insensitive) encerra o loop
- Exibe mensagem de despedida antes de fechar
- Sem confirmação

## Exemplo

```
decafdb> EXIT
Goodbye.
```

## Critérios de aceite

- `EXIT` encerra a aplicação sem erro
- `exit` funciona (case-insensitive)
- Mensagem de despedida é exibida
