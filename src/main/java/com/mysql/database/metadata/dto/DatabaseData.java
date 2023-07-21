package com.mysql.database.metadata.dto;

import java.util.List;

public class DatabaseData {

    private List<TableDbJsonData> tableList;

    public List<TableDbJsonData> getTableList() {
        return tableList;
    }

    public void setTableList(List<TableDbJsonData> tableList) {
        this.tableList = tableList;
    }
}
