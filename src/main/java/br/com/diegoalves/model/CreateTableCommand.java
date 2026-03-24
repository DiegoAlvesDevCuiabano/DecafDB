package br.com.diegoalves.model;

public class CreateTableCommand implements Command {
    private String tableName;

    public CreateTableCommand(String tableName) {
        this.tableName = tableName;
    }

    public CommandType getType() {
        return CommandType.CREATE_TABLE;
    }

    public String getTableName() {
        return tableName;
    }

}
