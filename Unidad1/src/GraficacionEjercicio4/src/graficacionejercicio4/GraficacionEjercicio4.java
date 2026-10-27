package graficacionejercicio4;



import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GraficacionEjercicio4 {
    public static void main(String[] args) {
        // Nombres de archivos de prueba
        String[] archivos = {"graficacionejercicio4/src/data/gato.png", 
                             "graficacionejercicio4/src/data/pinguino.jpg", 
                             "graficacionejercicio4/src/data/pollo.gif"};

        for (String ruta : archivos) {
            try {
                File archivo = new File(ruta);
                if (!archivo.exists()) {
                    System.out.println("No se encontró el archivo: " + ruta);
                    continue;
                }
                
                BufferedImage imagen = ImageIO.read(archivo);
                if (imagen != null) {
                    System.out.println("Archivo: " + ruta);
                    System.out.println("Dimensiones: " + imagen.getWidth() + "x" + imagen.getHeight() + " px");
                    System.out.println("Tipo de imagen (Constante INT): " + imagen.getType());
                    
                    // Verificar si el modelo de color soporta el canal alfa (transparencia)
                    boolean tieneAlfa = imagen.getColorModel().hasAlpha();
                    System.out.println("Soporta transparencia (Alfa): " + (tieneAlfa ? "Sí" : "No"));
                    System.out.println("-----------------------------------");
                }
            } catch (IOException e) {
                System.out.println("Error al procesar: " + ruta);
            }
        }
    }
}