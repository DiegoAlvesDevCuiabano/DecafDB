package br.com.diegoalves.model;

public class CreateSchemaCommand implements Command {
    private String schemaName;

    public CreateSchemaCommand(String schemaName) {
        this.schemaName = schemaName;
    }

    public CommandType getType() {
        return CommandType.CREATE_SCHEMA;
    }

    public String getSchemaName() {
        return schemaName;
    }
}
