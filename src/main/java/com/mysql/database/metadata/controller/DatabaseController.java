package com.mysql.database.metadata.controller;


import com.mysql.database.metadata.dto.DatabaseData;
import com.mysql.database.metadata.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/data")
public class DatabaseController {
    @Autowired
    private DatabaseService databaseService;

    @GetMapping("/showData")
    public ResponseEntity<DatabaseData> getTableMetadata(){
        ResponseEntity<DatabaseData> response = databaseService.getDbMigrationJsonData();
        return response;
    }


}
