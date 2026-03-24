package br.com.diegoalves.model;

public class DropTableCommand implements Command {
    private String tableName;

    public DropTableCommand(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public CommandType getType() {
        return CommandType.DROP_TABLE;
    }
}
