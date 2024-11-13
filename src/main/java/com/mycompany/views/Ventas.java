
package com.mycompany.views;

import com.mycompany.db.c;
import java.awt.Color;
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

public class Ventas extends javax.swing.JPanel {
    private DefaultTableModel dtm;
    private Object[] r = new Object[5];
    String _codigo;
    String _producto;
    String _precio;
    double _subTotal = 0;
    double total = 0;
    Date fechaActual = new Date();
    SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
    String fechaF = fecha.format(fechaActual);

    
    public Ventas() {
        initComponents();
        InitStyles();
        dtm = (DefaultTableModel) tableVenta.getModel();
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

                    if (registrarVentaYActualizarStock()) {
                        JOptionPane.showMessageDialog(ventanaCobro, "Cobro exitoso. Cambio: $" + cambio);
                        ventanaCobro.dispose();
                        limpiarTablaVentas();
                    } else {
                        JOptionPane.showMessageDialog(ventanaCobro, "Error al registrar la venta.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ventanaCobro, "Ingrese un monto válido."+ex, "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        ventanaCobro.setVisible(true);
    }

    private boolean registrarVentaYActualizarStock() {
        Connection conexion = null;
        try {
            conexion = c.conectar();
            conexion.setAutoCommit(false);  
            String insertVenta = "INSERT INTO entradas (producto, cantidad, precio, subtotal, fechaventa) VALUES ( ?, ?, ?, ?, ?)";
            PreparedStatement insertarVentaStmt = conexion.prepareStatement(insertVenta);

            for (int i = 0; i < tableVenta.getRowCount(); i++) {
                String producto = (String) tableVenta.getValueAt(i, 1);
                int cantidad = Integer.parseInt(tableVenta.getValueAt(i, 2).toString());
                double precio = Double.parseDouble(tableVenta.getValueAt(i, 3).toString());
                double subtotal = Double.parseDouble(tableVenta.getValueAt(i, 4).toString());

                insertarVentaStmt.setString(1, producto);
                insertarVentaStmt.setInt(2, cantidad);
                insertarVentaStmt.setDouble(3, precio);
                insertarVentaStmt.setDouble(4, subtotal);
                insertarVentaStmt.setString(5, fechaF);
                insertarVentaStmt.addBatch();
            }
            insertarVentaStmt.executeBatch();  
            
            String updateStockSQL = "UPDATE productos SET existencia = existencia - ? WHERE codigo = ?";
            PreparedStatement updateStockStmt = conexion.prepareStatement(updateStockSQL);

            for (int i = 0; i < tableVenta.getRowCount(); i++) {
                String codigo = (String) tableVenta.getValueAt(i, 0);
                int cantidad = (int) tableVenta.getValueAt(i, 2);

                updateStockStmt.setInt(1, cantidad);
                updateStockStmt.setString(2, codigo);
                updateStockStmt.addBatch();
            }
            updateStockStmt.executeBatch();  
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
        DefaultTableModel model = (DefaultTableModel) tableVenta.getModel();
        model.setRowCount(0);
        total = 0;
        jLTotal.setText("$");
        codigo.setText("");
        cantidad.setText("");
    }

    
    private void InitStyles() {
        title.putClientProperty("FlatLaf.styleClass", "h0");
        title.setForeground(Color.black);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        codigoJL = new javax.swing.JLabel();
        codigo = new javax.swing.JTextField();
        cantJL = new javax.swing.JLabel();
        cantidad = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableVenta = new javax.swing.JTable();
        totalJL = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        jLTotal = new javax.swing.JLabel();
        title = new javax.swing.JLabel();
        btnAgregar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(742, 427));

        bg.setBackground(new java.awt.Color(255, 255, 255));

        codigoJL.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        codigoJL.setText("Codigo");

        codigo.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        cantJL.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        cantJL.setText("Cantidad");

        cantidad.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        cantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cantidadActionPerformed(evt);
            }
        });

        tableVenta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 102)));
        tableVenta.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tableVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Producto", "Cantidad", "Precio", "Total"
            }
        ));
        jScrollPane1.setViewportView(tableVenta);

        totalJL.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        totalJL.setText("Total a pagar");

        btnCancelar.setBackground(new java.awt.Color(99, 156, 214));
        btnCancelar.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setText("Borrar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLTotal.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        jLTotal.setText("$");

        title.setText("Ventas");

        btnAgregar.setBackground(new java.awt.Color(99, 156, 214));
        btnAgregar.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btnAgregar.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregar.setText("Agregar");
        btnAgregar.setBorderPainted(false);
        btnAgregar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(99, 156, 214));
        jButton1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Cobrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bgLayout.createSequentialGroup()
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(bgLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addGroup(bgLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(totalJL)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(bgLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 721, Short.MAX_VALUE)))
                .addGap(35, 35, 35))
            .addGroup(bgLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bgLayout.createSequentialGroup()
                        .addComponent(codigoJL, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(cantJL, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(bgLayout.createSequentialGroup()
                        .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22))
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cantJL)
                    .addComponent(codigoJL))
                .addGap(5, 5, 5)
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAgregar)
                        .addComponent(btnCancelar)))
                .addGap(19, 19, 19)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalJL)
                    .addComponent(jLTotal))
                .addGap(18, 18, 18)
                .addComponent(jButton1))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // TODO add your handling code here:
        String cadena = codigo.getText().trim();
        int _cantidad;
        try {
            _cantidad = Integer.parseInt(cantidad.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Cantidad no válida", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String busqueda = "select * from productos where codigo = '" + cadena + "'";
        try {
            Connection conexion = c.conectar();
            Statement s = conexion.createStatement();
            ResultSet rs = s.executeQuery(busqueda);
            while (rs.next()) {
                int cantidadBD = rs.getInt("existencia");
                if (cantidadBD < _cantidad) {
                    JOptionPane.showMessageDialog(null, "Stock insuficiente  " + cantidadBD);
                } else {
                    _codigo = rs.getString("codigo");
                    _producto = rs.getString("producto");
                    _precio = Double.toString(rs.getDouble("precioVenta"));
                    _subTotal = Double.parseDouble(_precio) * _cantidad;
                    r[0] = _codigo;
                    r[1] = _producto;
                    r[2] = _cantidad;
                    r[3] = _precio;
                    r[4] = _subTotal;

                    dtm.addRow(r);
                    total += _subTotal;
                    jLTotal.setText("$" + Double.toString(total));
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        int selectedRow = tableVenta.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Selecciona un registro de la tabla", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            double subTotalToRemove = (double) dtm.getValueAt(selectedRow, 4);
            total -= subTotalToRemove;
            jLTotal.setText("$" + Double.toString(total));
            dtm.removeRow(selectedRow);
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        abrirVentanaCobro();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cantidadActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bg;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JLabel cantJL;
    private javax.swing.JTextField cantidad;
    private javax.swing.JTextField codigo;
    private javax.swing.JLabel codigoJL;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLTotal;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableVenta;
    private javax.swing.JLabel title;
    private javax.swing.JLabel totalJL;
    // End of variables declaration//GEN-END:variables
}
