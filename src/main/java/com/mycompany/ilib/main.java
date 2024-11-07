package com.mycompany.ilib;


import com.formdev.flatlaf.FlatLightLaf;
import com.login.inicio;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/*package com.login;*/
public class main {

    public static void main(String[] args) {
        // Establece el look and feel FlatLaf
         try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // Inicia la aplicación mostrando la ventana de inicio de sesión
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new inicio().setVisible(true);
            }
        });
    }
}
