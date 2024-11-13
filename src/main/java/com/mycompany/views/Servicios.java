package com.mycompany.views;

import com.mycompany.db.c;
import static com.mycompany.ilib.Dashboard.ShowJPanel;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;

public class Servicios extends javax.swing.JPanel {

    private DefaultTableModel tm;
    private Object[] r = new Object[3];
    int _id_serv;
    String _servicio;
    double _precio;

    public Servicios() {
        initComponents();
        InitStyles();
        tm = (DefaultTableModel) servT.getModel();
        cargarServicios();

    }

    private void InitStyles() {

        title.putClientProperty("FlatLaf.styleClass", "h0");
        title.setForeground(Color.black);
    }

    private void cargarServicios() {
        String query = "SELECT * FROM servicios";

        try (Connection conexion = c.conectar(); Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id_servicios");
                String servicio = rs.getString("nom_servicios");
                double precio = rs.getDouble("precio");

                // Añadir la fila al modelo de la tabla
                Object[] fila = {id, servicio, precio};
                tm.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar servicios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actualizarServicio(int id, String servicio, double precio) {

        String updateSQL = "UPDATE servicios SET nom_servicios = ?, precio = ? WHERE id_servicios = ?";
        try (Connection conexion = c.conectar(); PreparedStatement updateStmt = conexion.prepareStatement(updateSQL)) {
            updateStmt.setString(1, servicio);
            updateStmt.setDouble(2, precio);
            updateStmt.setInt(3, id);

            int rowsAffected = updateStmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Servicio actualizado correctamente.");
                // Actualizar la tabla en la interfaz
                for (int i = 0; i < tm.getRowCount(); i++) {
                    if ((int) tm.getValueAt(i, 0) == id) {
                        tm.setValueAt(servicio, i, 1);
                        tm.setValueAt(precio, i, 2);
                        break;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar el servicio.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar servicio: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        servJL = new javax.swing.JLabel();
        servTF = new javax.swing.JTextField();
        agregarServicio = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        precioJL = new javax.swing.JLabel();
        precioTF = new javax.swing.JTextField();
        elimBt = new javax.swing.JButton();
        title = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        servT = new javax.swing.JTable();
        btnCobrarS = new javax.swing.JButton();
        addButton1 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        bg.setBackground(new java.awt.Color(255, 255, 255));

        servJL.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        servJL.setText("Servicio");

        agregarServicio.setBackground(new java.awt.Color(99, 156, 214));
        agregarServicio.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        agregarServicio.setForeground(new java.awt.Color(255, 255, 255));
        agregarServicio.setText("Agregar");
        agregarServicio.setBorderPainted(false);
        agregarServicio.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        agregarServicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                agregarServicioMouseClicked(evt);
            }
        });
        agregarServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarServicioActionPerformed(evt);
            }
        });

        editButton.setBackground(new java.awt.Color(99, 156, 214));
        editButton.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        editButton.setForeground(new java.awt.Color(255, 255, 255));
        editButton.setText("Editar");
        editButton.setBorderPainted(false);
        editButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        precioJL.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        precioJL.setText("Precio");

        elimBt.setBackground(new java.awt.Color(99, 156, 214));
        elimBt.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        elimBt.setForeground(new java.awt.Color(255, 255, 255));
        elimBt.setText("Eliminar");
        elimBt.setBorderPainted(false);
        elimBt.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        elimBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                elimBtActionPerformed(evt);
            }
        });

        title.setText("Servicios");

        servT.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        servT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id Servicio", "Servicio", "Precio"
            }
        ));
        servT.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(servT);

        btnCobrarS.setBackground(new java.awt.Color(99, 156, 214));
        btnCobrarS.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btnCobrarS.setForeground(new java.awt.Color(255, 255, 255));
        btnCobrarS.setText("Cobrar Servicio");
        btnCobrarS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCobrarSActionPerformed(evt);
            }
        });

        addButton1.setBackground(new java.awt.Color(99, 156, 214));
        addButton1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        addButton1.setForeground(new java.awt.Color(255, 255, 255));
        addButton1.setText("Ver ventas");
        addButton1.setBorderPainted(false);
        addButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        addButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bgLayout.createSequentialGroup()
                        .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bgLayout.createSequentialGroup()
                        .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(bgLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(addButton1)
                                .addGap(18, 18, 18)
                                .addComponent(btnCobrarS))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 665, Short.MAX_VALUE)
                            .addGroup(bgLayout.createSequentialGroup()
                                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(precioJL, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                                    .addComponent(servJL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(servTF, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(precioTF, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bgLayout.createSequentialGroup()
                                        .addComponent(agregarServicio)
                                        .addGap(18, 18, 18)
                                        .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(elimBt, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addGap(42, 42, 42))))
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(title)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(servTF, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(servJL)
                        .addComponent(editButton)
                        .addComponent(agregarServicio)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(precioJL)
                    .addComponent(precioTF, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(elimBt))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                .addGap(34, 34, 34)
                .addGroup(bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCobrarS)
                    .addComponent(addButton1))
                .addGap(41, 41, 41))
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

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        int selectedRow = servT.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un servicio para editar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idServicio = (int) tm.getValueAt(selectedRow, 0);
        String servicio = (String) tm.getValueAt(selectedRow, 1);
        double precio = (double) tm.getValueAt(selectedRow, 2);

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Editar Servicio", true);
        dialog.setLayout(new GridBagLayout());
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);

        JTextField servTF = new JTextField(servicio, 15);
        JTextField precioTF = new JTextField(String.valueOf(precio), 10);
        JButton confirmarButton = new JButton("Confirmar");
        JButton cancelarButton = new JButton("Cancelar");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new java.awt.Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        dialog.add(new JLabel("Servicio:"), gbc);

        gbc.gridx = 1;
        dialog.add(servTF, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        dialog.add(new JLabel("Precio:"), gbc);

        gbc.gridx = 1;
        dialog.add(precioTF, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        dialog.add(confirmarButton, gbc);

        gbc.gridx = 1;
        dialog.add(cancelarButton, gbc);

        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nuevoServicio = servTF.getText().trim();
                    double nuevoPrecio = Double.parseDouble(precioTF.getText().trim());

                    actualizarServicio(idServicio, nuevoServicio, nuevoPrecio);

                    dialog.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Precio debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelarButton.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }//GEN-LAST:event_editButtonActionPerformed

    private void agregarServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarServicioActionPerformed

        String servicio = servTF.getText().trim();
        if (servicio.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo Servicio no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        double precio = 0;
        try {
            precio = Double.parseDouble(precioTF.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El precio debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        String stm = "select * from servicios";
        try {
            Connection conexion = c.conectar();
            String insS = "INSERT INTO servicios (nom_servicios, precio) VALUES ( ?, ?) RETURNING id_servicios";
            PreparedStatement insertSer = conexion.prepareStatement(insS);

            insertSer.setString(1, servicio);
            insertSer.setDouble(2, precio);

            ResultSet rs = insertSer.executeQuery();
            if (rs.next()) {
                _id_serv = rs.getInt("id_servicios");
                _servicio = servicio;
                _precio = precio;

                r[0] = _id_serv;
                r[1] = _servicio;
                r[2] = _precio;
                tm.addRow(r);

                servTF.setText("");
                precioTF.setText("");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_agregarServicioActionPerformed

    private void elimBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_elimBtActionPerformed
        // TODO add your handling code here:
        int selectedRow = servT.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un servicio para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idServicio = (int) tm.getValueAt(selectedRow, 0);

        String[] options = {"Sí", "No"};
        int confirm = JOptionPane.showOptionDialog(
                this,
                "¿Está seguro de que desea eliminar el servicio?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        String deleteSQL = "DELETE FROM servicios WHERE id_servicios = ?";
        try (Connection conexion = c.conectar(); PreparedStatement deleteStmt = conexion.prepareStatement(deleteSQL)) {

            deleteStmt.setInt(1, idServicio);
            int rowsAffected = deleteStmt.executeUpdate();

            if (rowsAffected > 0) {
                tm.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Servicio eliminado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el servicio.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar servicio: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_elimBtActionPerformed

    private void btnCobrarSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCobrarSActionPerformed
        // TODO add your handling code here:
        ShowJPanel(new CobrarS());
    }//GEN-LAST:event_btnCobrarSActionPerformed

    private void addButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButton1ActionPerformed
        ShowJPanel(new Returns());
    }//GEN-LAST:event_addButton1ActionPerformed

    private void agregarServicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_agregarServicioMouseClicked
    }//GEN-LAST:event_agregarServicioMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton1;
    private javax.swing.JButton agregarServicio;
    private javax.swing.JPanel bg;
    private javax.swing.JButton btnCobrarS;
    private javax.swing.JButton editButton;
    private javax.swing.JButton elimBt;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel precioJL;
    private javax.swing.JTextField precioTF;
    private javax.swing.JLabel servJL;
    private javax.swing.JTable servT;
    private javax.swing.JTextField servTF;
    private javax.swing.JLabel title;
    // End of variables declaration//GEN-END:variables
}
