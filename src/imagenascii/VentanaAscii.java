
package imagenascii;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class VentanaAscii extends JFrame {

    private final JTextArea resultado = new JTextArea();

    public VentanaAscii() {
        super("Imagen a ASCII");

        JButton abrir = new JButton("Abrir imagen");
        abrir.addActionListener(evento -> abrirImagen());

        resultado.setEditable(false);
        resultado.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        resultado.setBackground(Color.WHITE);
        resultado.setForeground(Color.BLACK);

        JPanel barra = new JPanel();
        barra.add(abrir);

        add(barra, BorderLayout.NORTH);
        add(new JScrollPane(resultado), BorderLayout.CENTER);

        setSize(850, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void abrirImagen() {
        JFileChooser selector = new JFileChooser();
        selector.setFileFilter(new FileNameExtensionFilter(
                "Imágenes PNG y JPG", "png", "jpg", "jpeg"
        ));

        if (selector.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File archivo = selector.getSelectedFile();

        try {
            BufferedImage imagen = ImageIO.read(archivo);

            if (imagen == null) {
                JOptionPane.showMessageDialog(this, "Elige una imagen PNG o JPG válida.");
                return;
            }

            String dibujo = ConversorAscii.convertir(imagen, 100);
            resultado.setText(dibujo);
            resultado.setCaretPosition(0);
            setTitle("Imagen a ASCII | " + archivo.getName());

        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo leer la imagen: " + e.getMessage(),
                    "Error de lectura",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaAscii().setVisible(true));
    }
}