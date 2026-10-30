
package parcial2graf;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Parcial2Graf {
 
    public static void main(String[] args) {
 
                try {
            // 1. Cargar la imagen original en formato PNG
            File archivoEntrada = new File("src/original.jpeg");
            BufferedImage imagenPng = ImageIO.read(archivoEntrada);

            // 2. Crear un nuevo BufferedImage tipo RGB (sin canal alfa) para el JPEG
            BufferedImage imagenJpeg = new BufferedImage(
                    imagenPng.getWidth(),
                    imagenPng.getHeight(),
                    BufferedImage.TYPE_INT_RGB
            );

            // 3. Dibujar un fondo blanco sólido y luego pintar el PNG encima
            Graphics2D g2d = imagenJpeg.createGraphics();
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, imagenJpeg.getWidth(), imagenJpeg.getHeight());
            g2d.drawImage(imagenPng, 0, 0, null);
            g2d.dispose(); // Liberar los recursos gráficos
            
            
            // 4 Conversión de RGB -> CMYK -> RGB píxel por píxel
            for (int y = 0; y < imagenJpeg.getHeight(); y++) {
            for (int x = 0; x < imagenJpeg.getWidth(); x++) {
        
            // 5 Obtener el color RGB del píxel actual
            int rgb = imagenJpeg.getRGB(x, y);
            int r = (rgb >> 16) & 0xFF;
            int g = (rgb >> 8) & 0xFF;
            int b = rgb & 0xFF;

            // 6 CONVERSIÓN: RGB a CMYK
            double rPrime = r / 255.0;
            double gPrime = g / 255.0;
            double bPrime = b / 255.0;

            double k = 1.0 - Math.max(rPrime, Math.max(gPrime, bPrime));
            double c = 0, m = 0, yC = 0; // 'yC' para no confundir con la variable 'y' del ciclo
        
            if (k < 1.0) {
            c = (1.0 - rPrime - k) / (1.0 - k);
            m = (1.0 - gPrime - k) / (1.0 - k);
            yC = (1.0 - bPrime - k) / (1.0 - k);
               
                            
        }
            //Esta linea altera el color
            //c=0.0;      //tonos morados
            //yC = 1.0;   //tonos amarillos
            //m = yC;     //tonos azules
            k = k * 0.2;  //saturacion extrema, se ven todos los colores muy brillosos

            // 7 CONVERSIÓN: CMYK de regreso a RGB
            int newR = (int) Math.round(255.0 * (1.0 - c) * (1.0 - k));
            int newG = (int) Math.round(255.0 * (1.0 - m) * (1.0 - k));
            int newB = (int) Math.round(255.0 * (1.0 - yC) * (1.0 - k));
 
           // 8 Recomponer el píxel y guardarlo en la imagen
           int newRgb = (newR << 16) | (newG << 8) | newB;
           imagenJpeg.setRGB(x, y, newRgb);
    }
}
            

            // 9 Escribir el archivo final en formato JPEG
            File archivoSalida = new File("src/resultado5_cmyk.jpg");
            ImageIO.write(imagenJpeg, "jpg", archivoSalida);
            
            System.out.println("Conversión a JPEG completada con éxito.");

        } catch (IOException e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        }
        }
    
       }