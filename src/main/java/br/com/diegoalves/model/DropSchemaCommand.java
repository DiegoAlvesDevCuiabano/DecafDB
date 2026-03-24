package br.com.diegoalves.model;

public class DropSchemaCommand implements Command {
    private String schemaName;

    public DropSchemaCommand(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public CommandType getType() {
        return CommandType.DROP_SCHEMA;
    }
}
