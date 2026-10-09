# DecafDB

Experimento didático de um banco de dados construído em Java. O projeto reúne um terminal, um parser de comandos e a estrutura inicial de catálogo e armazenamento para estudar o funcionamento interno de um SGBD.

> **Estado:** prova de conceito local, em desenvolvimento. Compila com Java 17, mas comandos DDL aceitos pelo terminal ainda não demonstraram persistência correta. Não há suíte automatizada nem serviço web. [Status técnico](docs/status.md)

## O que estudar no código

| Parte | Onde fica |
| :--- | :--- |
| Entrada e terminal | [`src/main/java/br/com/diegoalves/terminal/`](src/main/java/br/com/diegoalves/terminal/) |
| Interpretação de comandos | [`parser/`](src/main/java/br/com/diegoalves/parser/) e [`model/`](src/main/java/br/com/diegoalves/model/) |
| Execução | [`engine/`](src/main/java/br/com/diegoalves/engine/) |
| Catálogo e arquivos locais | [`data/`](data/) e [`docs/structure/`](docs/structure/) |

## Compilar e explorar

Pré-requisitos: JDK 17 e Maven. Execute a partir da raiz do repositório:

```powershell
mvn -q package
java -cp target/classes br.com.diegoalves.terminal.Terminal
```

O terminal usa arquivos locais em `data/` e contém dados de desenvolvimento. Use uma cópia descartável do repositório para experimentar. As credenciais presentes no protótipo são apenas de teste; **não use o DecafDB para armazenar dados reais**. Digite `exit` para sair.

## Próximo marco técnico

Implementar e testar a gravação e a recuperação dos comandos de criação de esquema e tabela, seguida de testes automatizados para catálogo, parser e isolamento do armazenamento. O [mapa do MVP](docs/mvp/phase-1-ddl/overview.md) e [`docs/status.md`](docs/status.md) registram o escopo atual.
