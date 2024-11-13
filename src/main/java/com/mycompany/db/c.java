package com.mycompany.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class c {
    public static Connection conectar() {
        Properties props = new Properties();
        Connection conexion = null;
        try {
            // Cargar propiedades desde archivo ubicado en src/main/resources
            InputStream in = c.class.getClassLoader().getResourceAsStream("config.properties");

            if (in == null) {
                System.out.println("No se encontró el archivo de propiedades");
                return null;
            } else {
                System.out.println("Archivo de propiedades cargado correctamente");
            }

            // Cargar las propiedades desde el archivo
            props.load(in);
            in.close();

            // Obtener los valores de conexión
            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            // Verificar que las propiedades se obtuvieron correctamente
            System.out.println("URL: " + url);
            System.out.println("User: " + user);
            System.out.println("Password: " + password);

            // Conectar a la base de datos
            conexion = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión a PostgreSQL exitosa");
        } catch (IOException | SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }
        return conexion;
    }
}
