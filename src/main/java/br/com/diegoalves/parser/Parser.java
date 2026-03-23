package br.com.diegoalves.parser;

import br.com.diegoalves.model.Command;
import br.com.diegoalves.model.CreateSchemaCommand;

public class Parser {

    public Command operation(String input) {
        String[] tokens = input.trim().split("\\s+");
        String action = tokens[0].toUpperCase();
        String artifact = tokens[1].toUpperCase();
        if (action.equalsIgnoreCase("CREATE") && artifact.equalsIgnoreCase("SCHEMA")) {
            return new CreateSchemaCommand(tokens[2]);
        } else {
            System.out.println("Error: invalid action");
            return null;
        }

    }
}
