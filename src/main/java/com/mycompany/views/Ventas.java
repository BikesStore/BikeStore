
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
        title.putClientProperty("FlatLaf.styleClass", "h1");
        title.setForeground(Color.black);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        codigoJL = new javax.swing.JLabel();
        codigo = new javax.swing.JTextField();
        prodJL = new javax.swing.JLabel();
        producto = new javax.swing.JTextField();
        cantJL = new javax.swing.JLabel();
        cantidad = new javax.swing.JTextField();
        predioJL = new javax.swing.JLabel();
        precio = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableVenta = new javax.swing.JTable();
        totalJL = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        jLTotal = new javax.swing.JLabel();
        title = new javax.swing.JLabel();
        btnAgregar = new javax.swing.JButton();
        btnCobrar = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(742, 427));

        bg.setBackground(new java.awt.Color(255, 255, 255));

        codigoJL.setText("Codigo");

        prodJL.setText("Producto");

        producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productoActionPerformed(evt);
            }
        });

        cantJL.setText("Cantidad");

        predioJL.setText("Precio");

        precio.setEditable(false);
        precio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                precioActionPerformed(evt);
            }
        });

        tableVenta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 102)));
        tableVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "PRODUCTO", "CANTIDAD", "PRECIO", "TOTAL"
            }
        ));
        jScrollPane1.setViewportView(tableVenta);
        if (tableVenta.getColumnModel().getColumnCount() > 0) {
            tableVenta.getColumnModel().getColumn(0).setPreferredWidth(30);
            tableVenta.getColumnModel().getColumn(1).setPreferredWidth(100);
            tableVenta.getColumnModel().getColumn(2).setPreferredWidth(30);
            tableVenta.getColumnModel().getColumn(3).setPreferredWidth(30);
            tableVenta.getColumnModel().getColumn(4).setPreferredWidth(30);
        }

        totalJL.setText("Total a pagar");

        btnCancelar.setBackground(new java.awt.Color(255, 130, 84));
        btnCancelar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setText("Borrar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLTotal.setText("---");

        title.setText("Ventas");

        btnAgregar.setBackground(new java.awt.Color(255, 130, 84));
        btnAgregar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnAgregar.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregar.setText("Agregar");
        btnAgregar.setBorderPainted(false);
        btnAgregar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnCobrar.setBackground(new java.awt.Color(255, 130, 84));
        btnCobrar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnCobrar.setForeground(new java.awt.Color(255, 255, 255));
        btnCobrar.setText("Cobrar");
        btnCobrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCobrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgLayout.createSequentialGroup()
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, bgLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(bgLayout.createSequentialGroup()
                                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(codigoJL, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(29, 29, 29)
                                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(producto, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(prodJL))
                                .addGap(18, 18, 18)
                                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cantJL, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(44, 44, 44)
                                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(bgLayout.createSequentialGroup()
                                        .addComponent(predioJL)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(bgLayout.createSequentialGroup()
                                        .addComponent(precio, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                                        .addComponent(btnCancelar)
                                        .addGap(10, 10, 10))))
                            .addGroup(bgLayout.createSequentialGroup()
                                .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(bgLayout.createSequentialGroup()
                        .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(bgLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnCobrar))
                            .addGroup(bgLayout.createSequentialGroup()
                                .addGap(557, 557, 557)
                                .addComponent(totalJL)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(36, 36, 36)))
                .addGap(14, 14, 14))
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(codigoJL, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(prodJL)
                    .addComponent(cantJL)
                    .addComponent(predioJL)
                    .addComponent(btnAgregar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(producto, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(codigo)
                    .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(precio)
                        .addComponent(btnCancelar)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                .addGap(94, 94, 94)
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalJL)
                    .addComponent(jLTotal))
                .addGap(18, 18, 18)
                .addComponent(btnCobrar)
                .addGap(46, 46, 46))
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

    private void productoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productoActionPerformed
    throw new UnsupportedOperationException("Not supported yet.");
    }//GEN-LAST:event_productoActionPerformed

    private void precioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_precioActionPerformed
    throw new UnsupportedOperationException("Not supported yet.");
    }//GEN-LAST:event_precioActionPerformed

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

    private void btnCobrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCobrarActionPerformed
        // TODO add your handling code here:
        abrirVentanaCobro();
    }//GEN-LAST:event_btnCobrarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bg;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCobrar;
    private javax.swing.JLabel cantJL;
    private javax.swing.JTextField cantidad;
    private javax.swing.JTextField codigo;
    private javax.swing.JLabel codigoJL;
    private javax.swing.JLabel jLTotal;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField precio;
    private javax.swing.JLabel predioJL;
    private javax.swing.JLabel prodJL;
    private javax.swing.JTextField producto;
    private javax.swing.JTable tableVenta;
    private javax.swing.JLabel title;
    private javax.swing.JLabel totalJL;
    // End of variables declaration//GEN-END:variables
}
