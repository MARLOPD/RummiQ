package com.rummyq.backend.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    public static Connection getConnection() throws Exception {

        String url = System.getenv("RUMMYQ_SUPABASE_URL"); 
        String user = System.getenv("RUMMYQ_SUPABASE_USER");
        String password = System.getenv("RUMMYQ_SUPABASE_PASSWORD");
        url += password;
        return DriverManager.getConnection(url, user, password);
    }
}