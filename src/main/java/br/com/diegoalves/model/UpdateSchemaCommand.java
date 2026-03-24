package br.com.diegoalves.model;

public class UpdateSchemaCommand implements Command {
    private String schemaName;

    public UpdateSchemaCommand(String schemaName) {
        this.schemaName = schemaName;
    }

    public CommandType getType() {
        return CommandType.UPDATE_SCHEMA;
    }

    public String getSchemaName() {
        return schemaName;
    }
}
