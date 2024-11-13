package com.mycompany.views;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Principal extends javax.swing.JPanel {

    private Properties props;
    private JList<String> listProductosAgotados;
    private DefaultListModel<String> listModel;
    private static final int NIVEL_MINIMO = 5;

    public Principal() {
        props = cargarProps(); // Carga propiedades al inicio
        initComponents();
        inicializarPaneles();
        mostrarAlertas(); // Muestra las alertas si las propiedades se cargaron correctamente
    }

    // Método para cargar el archivo de propiedades
    private Properties cargarProps() {
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                return null;
            }
            props.load(input);
        } catch (IOException e) {
        }
        return props;
    }

    private void inicializarPaneles() {
        listModel = new DefaultListModel<>();
        listProductosAgotados = new JList<>(listModel);
        listProductosAgotados.setForeground(Color.RED);

        title.setText("<html>Comienza el día con una visión general de la actividad del taller. Consulta las ventas actuales, "
                + "actualizaciones de inventario y mantén un control sobre "
                + "el estado de los productos. Sigue al tanto de tu negocio y asegura la satisfacción de tus clientes.</html>");
    }

    // Método para inicializar estilos
    private void InitStyles() {
        // Asegurarse de que las propiedades estén cargadas antes de usarlas
        if (props == null) {
            return;
        }
        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        // Si las propiedades son nulas, eso indica que no se cargaron correctamente
        if (url == null || user == null || password == null) {
        } else {
            System.out.println("URL: " + url);
            System.out.println("Usuario: " + user);
            System.out.println("Contraseña: " + password);
        }
        title.setText("<html>Comienza el día con una visión general de la actividad del taller. Consulta las ventas actuales, "
                + "actualizaciones de inventario y mantén un control sobre "
                + "el estado de los productos. Sigue al tanto de tu negocio y asegura la satisfacción de tus clientes.</html>");
    }

    // Restante código de tu clase...
    // Método que consulta los productos agotados en la base de datos
    private void mostrarAlertas() {
        List<String> productosAgotados = obtenerProductosAgotados();

        if (productosAgotados.isEmpty()) {
            // Si no hay productos agotados, muestra un mensaje único
            productosAgotadosList.setListData(new String[]{"No hay productos agotados"});
            productosAgotadosList.setForeground(Color.GREEN);
        } else {
            // Si hay productos agotados, muestra la lista en rojo
            productosAgotadosList.setListData(productosAgotados.toArray(new String[0]));
            productosAgotadosList.setForeground(Color.decode("#A3080A"));
        }
    }

// Método que consulta los productos agotados desde la base de datos
    private List<String> obtenerProductosAgotados() {
        List<String> productos = new ArrayList<>();
        if (props == null) {
            return productos;
        }

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        String consulta = "SELECT producto FROM productos WHERE existencia <= ?";

        try (Connection conexion = DriverManager.getConnection(url, user, password); PreparedStatement stmt = conexion.prepareStatement(consulta)) {
            stmt.setInt(1, NIVEL_MINIMO);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                productos.add(rs.getString("producto"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Productos agotados: " + productos);
        return productos;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        title = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        productosAgotadosList = new javax.swing.JList<>();

        setBackground(new java.awt.Color(255, 255, 255));

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        title.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bg.add(title, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 560, 256));

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        jLabel1.setText("Bajo Stock");
        bg.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 280, -1, -1));

        productosAgotadosList.setFont(new java.awt.Font("Segoe UI Symbol", 0, 18)); // NOI18N
        jScrollPane2.setViewportView(productosAgotadosList);

        bg.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 320, 450, 218));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, 879, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, 582, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bg;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> productosAgotadosList;
    private javax.swing.JLabel title;
    // End of variables declaration//GEN-END:variables
}
