# DecafDB — status

> Atualizado em 09/10/2026. A frente principal de trabalho é o Delibera.

O DecafDB é um protótipo local de banco de dados em Java 17, com terminal, parser e estrutura inicial de catálogo. `mvn -q package` passou. Em teste isolado, o terminal autenticou com o usuário de desenvolvimento e aceitou comandos `CREATE SCHEMA` e `CREATE TABLE`, mas não criou a estrutura correspondente no armazenamento. Não há suíte automatizada nem serviço web publicado.

**Situação:** prova de conceito, sem persistência de DDL demonstrada e sem prontidão comercial. Retomar após a homologação prioritária do Delibera; primeiro completar e testar persistência, recuperação e isolamento do armazenamento.
