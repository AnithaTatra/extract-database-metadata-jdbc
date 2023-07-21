package com.mysql.database.metadata.serviceimpl;

import com.mysql.database.metadata.dto.ColumnDataDto;
import com.mysql.database.metadata.dto.DatabaseData;
import com.mysql.database.metadata.dto.TableDbJsonData;
import com.mysql.database.metadata.service.DatabaseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseServiceImpl implements DatabaseService {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    public ResponseEntity<DatabaseData> getDbMigrationJsonData() {

        List<DatabaseData> tables = new ArrayList<>( );

        List<ColumnDataDto> columnList = null;

        List<TableDbJsonData> table = new ArrayList<>( );
         DatabaseData tableObj = null;
        TableDbJsonData table1 = null;

        DatabaseData tableList = new DatabaseData( );
        List<TableDbJsonData> tableDbMigrationDTOS1 = new ArrayList<>( );
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            DatabaseMetaData metaData = connection.getMetaData( );

            // Get all tables in the database
            ResultSet tablesResultSet = metaData.getTables(null, null, null, new String[]{"TABLE"});
            while (tablesResultSet.next( )) {

                String tableName = tablesResultSet.getString("TABLE_NAME");
                table1 = new TableDbJsonData( );
                tableObj = new DatabaseData( );
                System.out.println("Table: " + tableName);
                table1.setName(tableName);
                table.add(table1);
                tableObj.setTableList(table);
                tables.add(tableObj);
                columnList = new ArrayList<>( );

                // Get all columns in the table
                ResultSet columnsResultSet = metaData.getColumns(null, null, tableName, null);
                ResultSet primaryKeyResultSet = metaData.getPrimaryKeys(null, null, tableName);

                while (columnsResultSet.next( )) {
                    ColumnDataDto columns = null;

                    String columnName = columnsResultSet.getString("COLUMN_NAME");
                    String columnType = columnsResultSet.getString("TYPE_NAME");
                    int columnSize = columnsResultSet.getInt("COLUMN_SIZE");
                    boolean isAutoIncrement = columnsResultSet.getBoolean("IS_AUTOINCREMENT");
                    String defaultValue = columnsResultSet.getString("COLUMN_DEF");
                    boolean isNullable = columnsResultSet.getBoolean("NULLABLE");


                    System.out.println("  Column: " + columnName + ", Type: " + columnType + ", Size: " + columnSize);

                    columns = new ColumnDataDto( );
                    columns.setName(columnName);
                    if (isAutoIncrement) {
                        columns.setIsAutoIncrement("true");
                    } else {
                        columns.setIsAutoIncrement("false");

                    }
                    columns.setDefaultValue(defaultValue);
                    if (isNullable) {
                        columns.setIsNull("true");
                    } else {
                        columns.setIsNull("false");
                    }

                    columns.setDatatype(columnType + "(" + columnSize + ")");
                    columnList.add(columns);
                    table1.setColumns(columnList);

                    while (primaryKeyResultSet.next( )) {
                        String primaryKeyConstraintName = primaryKeyResultSet.getString("PK_NAME");
                        if (primaryKeyConstraintName.equalsIgnoreCase("PRIMARY")) {
                            columns.setKeytype("PK");
                        }


                    }


                }

                tableDbMigrationDTOS1.add(table1);

                columnsResultSet.close( );
                primaryKeyResultSet.close( );


            }
            tableList.setTableList(tableDbMigrationDTOS1);
            tablesResultSet.close( );
        } catch (SQLException e) {
            e.printStackTrace( );
        }


        return ResponseEntity.ok(tableList);

    }
}
