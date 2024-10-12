package com.mycompany.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class c {
    private static final String URL = "jdbc:postgresql://localhost:5432/control";
    private static final String USER = "admin";
    private static final String PASSWORD = "root";

    public static Connection conectar() {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión a PostgreSQL exitosa");
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }
        return conexion;
    }

}
  

    
    

