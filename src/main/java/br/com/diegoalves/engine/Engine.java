package br.com.diegoalves.engine;

import br.com.diegoalves.model.Command;
import br.com.diegoalves.parser.Parser;

public class Engine {

    public void start(String input) {
        Parser parser = new Parser();
        Command cmd = parser.operation(input);

        if(cmd == null) {
            return;
        }
        switch (cmd.getType()) {
            case CREATE_SCHEMA -> System.out.println("Schema criado");
            case CREATE_TABLE -> System.out.println("Tabela criada");
            case UPDATE_SCHEMA -> System.out.println("Schema atualizado");
            case UPDATE_TABLE -> System.out.println("Tabela atualizada");
            case DROP_SCHEMA -> System.out.println("Schema excluido");
            case DROP_TABLE -> System.out.println("Tabela excluida");
            default -> System.out.println("Comando inválido");
        }
    }

}
