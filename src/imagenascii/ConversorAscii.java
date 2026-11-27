package imagenascii;


import java.awt.image.BufferedImage;

public class ConversorAscii {

    // De mayor a menor cantidad de tinta sobre un fondo blanco.
    private static final String CARACTERES = "@%#*+=-:. ";

    public static String convertir(BufferedImage imagen, int columnas) {
        // Una letra suele ser más alta que ancha. Usamos menos filas
        // para que el dibujo conserve aproximadamente sus proporciones.
        int filas = Math.max(1, (int) Math.round(
                (double) imagen.getHeight() / imagen.getWidth() * columnas * 0.5
        ));

        StringBuilder dibujo = new StringBuilder();

        for (int fila = 0; fila < filas; fila++) {
            int y = (int) ((long) fila * imagen.getHeight() / filas);

            for (int columna = 0; columna < columnas; columna++) {
                int x = (int) ((long) columna * imagen.getWidth() / columnas);
                int pixel = imagen.getRGB(x, y);

                int alfa = (pixel >>> 24) & 255;
                int rojo = (pixel >>> 16) & 255;
                int verde = (pixel >>> 8) & 255;
                int azul = pixel & 255;

                double brillo = 0.299 * rojo + 0.587 * verde + 0.114 * azul;

                // La parte transparente de un PNG se muestra sobre blanco.
                brillo = (brillo * alfa + 255.0 * (255 - alfa)) / 255.0;

                int indice = (int) Math.round(
                        brillo / 255.0 * (CARACTERES.length() - 1)
                );
                dibujo.append(CARACTERES.charAt(indice));
            }

            if (fila < filas - 1) {
                dibujo.append('\n');
            }
        }

        return dibujo.toString();
    }
}