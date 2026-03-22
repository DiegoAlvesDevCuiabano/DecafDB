# US-003: Classe Engine

## História

**Como** sistema, **preciso** de uma classe central que receba o SQL digitado e orquestre a execução, **para** ter um ponto único de entrada do motor.

## Descrição

O Engine é o coração do DecafDB. Recebe a string SQL do Terminal, manda pro Parser interpretar, e despacha o resultado pro Executor correto. Depois retorna o resultado pro Terminal exibir.

Na Fase 1, só DDL passa por aqui. Mas a estrutura já fica pronta pra receber DML, EXPLAIN e validação de permissão no futuro.

## Fluxo

```
Terminal
  |
  v
Engine.execute(sql)
  |
  v
Parser.parse(sql)
  |
  v
[Objeto do comando parseado]
  |
  v
Executor.execute(comando)
  |
  v
[Resultado]
  |
  v
Terminal exibe resultado
```

## Pontos de extensão (vazios por agora)

- **Validação de perfil**: checar se o usuário logado pode executar o comando. Na Fase 1, sempre permite.
- **Despacho DML**: quando implementar INSERT/SELECT/etc, o Engine roteia pro executor certo.
- **EXPLAIN**: quando implementar, o Engine intercepta e mostra o plano antes de executar.

## Responsabilidades

- Receber string SQL
- Delegar parsing pro Parser
- Delegar execução pro Executor correto
- Tratar erros e retornar mensagens claras
- Nunca acessar disco diretamente (isso é trabalho do Executor)

## Critérios de aceite

- Comando DDL válido passa pelo Engine e retorna sucesso
- SQL inválido retorna mensagem de erro clara
- Engine não acessa disco diretamente
- Engine delega corretamente pro Parser e pro Executor
