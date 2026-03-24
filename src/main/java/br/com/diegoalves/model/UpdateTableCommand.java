package br.com.diegoalves.model;

public class UpdateTableCommand implements Command {
    private String tableName;

    public UpdateTableCommand(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public CommandType getType() {
        return CommandType.UPDATE_TABLE;
    }
}
