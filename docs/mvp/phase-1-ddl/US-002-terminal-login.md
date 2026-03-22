# US-002: Terminal com Login

## História

**Como** usuário, **quero** abrir o DecafDB e fazer login, **para** acessar o banco.

## Descrição

Quando a aplicação inicia, a primeira coisa que aparece é um pedido de login. O motor lê o arquivo `sys_users.decaf` (da US-001) e valida se as credenciais conferem. Se válido, entra no modo de comandos. Se inválido, pede de novo.

## Fluxo

1. Aplicação inicia
2. Exibe: `DecafDB v0.1`
3. Exibe: `Username: `
4. Usuário digita
5. Exibe: `Password: `
6. Usuário digita
7. Motor lê `sys_users.decaf` e compara
8. Se bater: exibe `Connected as root.` e mostra prompt `decafdb>`
9. Se não bater: exibe `Invalid credentials. Try again.` e volta pro passo 3

## Comportamento do terminal (após login)

- Prompt `decafdb>` fica esperando input
- Usuário digita um comando e aperta Enter
- Comando vai pro Engine (US-003)
- Engine retorna resultado (sucesso ou erro)
- Resultado é exibido
- Prompt aparece de novo
- Loop continua até EXIT

## Critérios de aceite

- Login com root/root funciona e mostra prompt
- Login errado mostra mensagem de erro e pede de novo
- Após login, terminal aceita input em loop
- Terminal exibe resultados do Engine
