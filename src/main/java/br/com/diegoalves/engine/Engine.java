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
            default -> System.out.println("Comando inválido");
        }
    }

}
