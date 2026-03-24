package br.com.diegoalves.parser;

import br.com.diegoalves.model.*;

public class Parser {

    public Command operation(String input) {
        String[] tokens = input.trim().split("\\s+");
        if (tokens.length < 3) {
            System.out.println("Sintaxe inválida");
            return null;
        }
        String action = tokens[0].toUpperCase();
        String artifact = tokens[1].toUpperCase();
        if (action.equalsIgnoreCase("CREATE") && artifact.equalsIgnoreCase("SCHEMA")) {
            return new CreateSchemaCommand(tokens[2]);
        } else if (action.equalsIgnoreCase("CREATE") && artifact.equalsIgnoreCase("TABLE")) {
            return new CreateTableCommand(tokens[2]);
        } else if (action.equalsIgnoreCase("UPDATE") && artifact.equalsIgnoreCase("SCHEMA")) {
            return new UpdateSchemaCommand(tokens[2]);
        } else if (action.equalsIgnoreCase("UPDATE") && artifact.equalsIgnoreCase("TABLE")) {
            return new UpdateTableCommand(tokens[2]);
        } else if (action.equalsIgnoreCase("DROP") && artifact.equalsIgnoreCase("SCHEMA")) {
            return new DropSchemaCommand(tokens[2]);
        } else if (action.equalsIgnoreCase("DROP") && artifact.equalsIgnoreCase("TABLE")) {
            return new DropTableCommand(tokens[2]);
        } else {
            System.out.println("Error: invalid action");
            return null;
        }

    }
}
