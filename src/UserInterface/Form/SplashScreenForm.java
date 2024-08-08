package UserInterface.Form;

import java.awt.BorderLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import UserInterface.SIEStyle;

public abstract class SplashScreenForm {
    private static JFrame frmSplash;
    private static JProgressBar prbLoading;
    private static ImageIcon icoImagen;
    private static JLabel lblSplash;

    public static void show() {
        SwingUtilities.invokeLater(() -> {
            // Cargar la imagen original
            icoImagen = new ImageIcon(SIEStyle.URL_SPLASH);
            
            //Variable que cambia el tamaño del Screen de carga principal
            int setSize = 700;
            // Escalar la imagen
            int scaledWidth = setSize; // Ancho deseado
            int scaledHeight = setSize; // Alto deseado
            Image scaledImage = icoImagen.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            lblSplash = new JLabel(scaledIcon);
            prbLoading = new JProgressBar(0, 100);

            prbLoading.setStringPainted(true);

            frmSplash = new JFrame();
            frmSplash.setUndecorated(true);
            frmSplash.getContentPane().add(lblSplash, BorderLayout.CENTER);
            frmSplash.add(prbLoading, BorderLayout.SOUTH);
            frmSplash.setSize(scaledWidth, scaledHeight + 30); // Ajustar el tamaño de la ventana para la barra de progreso
            frmSplash.setLocationRelativeTo(null); // Centrar en la pantalla

            frmSplash.setVisible(true);

            new Thread(() -> {
                for (int i = 0; i <= 100; i++) {
                    try {
                        Thread.sleep(50); // Espera por 50 milisegundos
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    prbLoading.setValue(i);
                }
                frmSplash.setVisible(false);
                frmSplash.dispose();
            }).start();
        });
    }
}
