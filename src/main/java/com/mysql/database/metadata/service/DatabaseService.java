package com.mysql.database.metadata.service;

import com.mysql.database.metadata.dto.DatabaseData;
import org.springframework.http.ResponseEntity;

public interface DatabaseService {

    ResponseEntity<DatabaseData> getDbMigrationJsonData();
}
