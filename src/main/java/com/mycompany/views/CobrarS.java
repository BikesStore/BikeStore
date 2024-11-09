package com.mycompany.views;

import com.mycompany.db.c;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class CobrarS extends javax.swing.JPanel {

    private DefaultTableModel dtm;
    private Object[] r = new Object[4];
    private String _codigo;
    private String _servicio;
    private String _precio;
    double _subTotal = 0;
    double total = 0;
    Date fechaActual = new Date();
    SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
    String fechaF = fecha.format(fechaActual);

    public CobrarS() {
        initComponents();
        InitStyles();
        dtm = (DefaultTableModel) tablaV.getModel();
    }

    private void InitStyles() {

        title.putClientProperty("FlatLaf.styleClass", "h1");
        title.setForeground(Color.black);
    }

    private void abrirVentanaCobro() {
        JDialog ventanaCobro = new JDialog();
        ventanaCobro.setSize(300, 200);
        ventanaCobro.setLayout(null);
        ventanaCobro.setLocationRelativeTo(this);

        JLabel labelTotal = new JLabel("Total a pagar: $" + total);
        labelTotal.setBounds(30, 20, 200, 30);
        ventanaCobro.add(labelTotal);

        JLabel labelMonto = new JLabel("Monto pagado:");
        labelMonto.setBounds(30, 60, 100, 30);
        ventanaCobro.add(labelMonto);

        JTextField campoMonto = new JTextField();
        campoMonto.setBounds(140, 60, 100, 30);
        ventanaCobro.add(campoMonto);

        JButton btnConfirmar = new JButton("Confirmar");
        btnConfirmar.setBounds(80, 110, 120, 30);
        ventanaCobro.add(btnConfirmar);

        btnConfirmar.addActionListener(e -> {
            try {
                double montoPagado = Double.parseDouble(campoMonto.getText().trim());
                if (montoPagado < total) {
                    JOptionPane.showMessageDialog(ventanaCobro, "El monto ingresado es insuficiente.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    double cambio = montoPagado - total;

                    if (registrarVenta()) {
                        JOptionPane.showMessageDialog(ventanaCobro, "Cobro exitoso. Cambio: $" + cambio);
                        ventanaCobro.dispose();
                        limpiarTablaVentas();
                    } else {
                        JOptionPane.showMessageDialog(ventanaCobro, "Error al registrar la venta.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ventanaCobro, "Ingrese un monto válido." + ex, "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        ventanaCobro.setVisible(true);
    }

    private boolean registrarVenta() {
        if (tablaV.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "No hay ventas para registrar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        Connection conexion = null;
        try {
            conexion = c.conectar();
            conexion.setAutoCommit(false);
            String insertVenta = "INSERT INTO ventaservicios (servicio, precio, subtotal, fechaventa) VALUES (?, ?, ?, ?)";
            PreparedStatement insertarVentaStmt = conexion.prepareStatement(insertVenta);

            for (int i = 0; i < tablaV.getRowCount(); i++) {
                String servicio = (String) tablaV.getValueAt(i, 1);
                Object precioObj = tablaV.getValueAt(i, 2);
                Object subtotalObj = tablaV.getValueAt(i, 3);

                if (precioObj == null || subtotalObj == null) {
                    JOptionPane.showMessageDialog(null, "Error: La tabla contiene valores nulos en la fila " + i, "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                double precio = Double.parseDouble(precioObj.toString());
                double subtotal = Double.parseDouble(subtotalObj.toString());

                insertarVentaStmt.setString(1, servicio);
                insertarVentaStmt.setDouble(2, precio);
                insertarVentaStmt.setDouble(3, subtotal);
                insertarVentaStmt.setString(4, fechaF);
                insertarVentaStmt.addBatch();
            }
            insertarVentaStmt.executeBatch();
            conexion.commit();
            return true;

        } catch (SQLException e) {
            if (conexion != null) {
                try {
                    conexion.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conexion != null) {
                    conexion.setAutoCommit(true);
                    conexion.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void limpiarTablaVentas() {
        DefaultTableModel model = (DefaultTableModel) tablaV.getModel();
        model.setRowCount(0);
        total = 0;
        jLTotal.setText("$");
        codigoS.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pp = new javax.swing.JPanel();
        title = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        codigoS = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();
        btnBorrar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLTotal = new javax.swing.JLabel();
        btnCobrar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaV = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(742, 427));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pp.setBackground(new java.awt.Color(255, 255, 255));

        title.setText("Venta Servicios");

        jLabel1.setText("Codigo");

        btnAgregar.setBackground(new java.awt.Color(255, 130, 84));
        btnAgregar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnAgregar.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnBorrar.setBackground(new java.awt.Color(255, 130, 84));
        btnBorrar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnBorrar.setForeground(new java.awt.Color(255, 255, 255));
        btnBorrar.setText("Borrar");
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });

        jLabel2.setText("Total a pagar");

        jLTotal.setText("$");

        btnCobrar.setBackground(new java.awt.Color(255, 130, 84));
        btnCobrar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnCobrar.setForeground(new java.awt.Color(255, 255, 255));
        btnCobrar.setText("Cobrar");
        btnCobrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCobrarActionPerformed(evt);
            }
        });

        tablaV.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 102)));
        tablaV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "SERVICIO", "PRECIO", "SUBTOTAL"
            }
        ));
        jScrollPane2.setViewportView(tablaV);

        javax.swing.GroupLayout ppLayout = new javax.swing.GroupLayout(pp);
        pp.setLayout(ppLayout);
        ppLayout.setHorizontalGroup(
            ppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppLayout.createSequentialGroup()
                .addGroup(ppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ppLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(title))
                    .addGroup(ppLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel1))
                    .addGroup(ppLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(codigoS, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(349, 349, 349)
                        .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBorrar))
                    .addGroup(ppLayout.createSequentialGroup()
                        .addGap(549, 549, 549)
                        .addComponent(jLabel2)
                        .addGap(6, 6, 6)
                        .addComponent(jLTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ppLayout.createSequentialGroup()
                        .addGap(596, 596, 596)
                        .addComponent(btnCobrar)))
                .addContainerGap(42, Short.MAX_VALUE))
            .addGroup(ppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(ppLayout.createSequentialGroup()
                    .addGap(18, 18, 18)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 706, Short.MAX_VALUE)
                    .addGap(18, 18, 18)))
        );
        ppLayout.setVerticalGroup(
            ppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ppLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(title)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(7, 7, 7)
                .addGroup(ppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(codigoS, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgregar)
                    .addComponent(btnBorrar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 222, Short.MAX_VALUE)
                .addGroup(ppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLTotal))
                .addGap(12, 12, 12)
                .addComponent(btnCobrar)
                .addGap(26, 26, 26))
            .addGroup(ppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(ppLayout.createSequentialGroup()
                    .addGap(163, 163, 163)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                    .addGap(163, 163, 163)))
        );

        add(pp, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getAccessibleContext().setAccessibleParent(this);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        try {
            int id = Integer.parseInt(codigoS.getText().trim());
            String busqueda = "SELECT * FROM servicios WHERE id_servicios = ?";
            try (Connection conexion = c.conectar(); PreparedStatement stmt = conexion.prepareStatement(busqueda)) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    _codigo = String.valueOf(rs.getInt("id_servicios"));
                    _servicio = rs.getString("nom_servicios");
                    _precio = Double.toString(rs.getDouble("precio"));
                    _subTotal = Double.parseDouble(_precio);

                    // Verificación de que todos los valores no sean nulos
                    if (_codigo != null && _servicio != null && _precio != null) {
                        r[0] = _codigo;
                        r[1] = _servicio;
                        r[2] = _precio;
                        r[3] = _subTotal;

                        dtm.insertRow(0, r);
                        total += _subTotal;
                        jLTotal.setText("$" + total);
                        codigoS.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: No se pudieron cargar todos los datos del servicio.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No existe el servicio", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El código debe ser un número entero", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al agregar servicio: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed

        int selectedRow = tablaV.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Selecciona un registro de la tabla", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            double subTotalToRemove = (double) dtm.getValueAt(selectedRow, 3);
            total -= subTotalToRemove;
            jLTotal.setText("$" + Double.toString(total));
            dtm.removeRow(selectedRow);
        }
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void btnCobrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCobrarActionPerformed

        abrirVentanaCobro();
    }//GEN-LAST:event_btnCobrarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnCobrar;
    private javax.swing.JTextField codigoS;
    private javax.swing.JLabel jLTotal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel pp;
    private javax.swing.JTable tablaV;
    private javax.swing.JLabel title;
    // End of variables declaration//GEN-END:variables
}
