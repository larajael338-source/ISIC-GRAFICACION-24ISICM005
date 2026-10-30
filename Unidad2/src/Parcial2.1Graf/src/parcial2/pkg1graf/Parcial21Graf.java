
package parcial2.pkg1graf;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Parcial21Graf {

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
        
           // 4. Conversión de RGB -> HSB -> RGB píxel por píxel
           for (int y = 0; y < imagenJpeg.getHeight(); y++) {
           for (int x = 0; x < imagenJpeg.getWidth(); x++) {
                
          // 1. Obtener el color RGB del píxel actual
           int rgb = imagenJpeg.getRGB(x, y);
           int r = (rgb >> 16) & 0xFF;
           int g = (rgb >> 8) & 0xFF;
           int b = rgb & 0xFF;

           // 2. CONVERSIÓN: RGB a HSB (Matiz, Saturación, Brillo)
          // Esto nos devuelve un arreglo de 3 posiciones con valores flotantes [0.0 a 1.0]
           float[] hsb = Color.RGBtoHSB(r, g, b, null);
           float h = hsb[0]; // Matiz (Hue)
           float s = hsb[1]; // Saturación (Saturation)
           float v = hsb[2]; // Brillo (Value/Brightness)

                // 3. MANIPULACIÓN: Sobresaturar multiplicando por 1.5
                s = s * 1.5f; 
                
                // Asegurar que el valor se mantenga en el rango válido [0.0 a 1.0]
                if (s > 1.0f) s = 1.0f;
                if (s < 0.0f) s = 0.0f;

                // 4. CONVERSIÓN: HSB de regreso a RGB
                int newRgb = Color.HSBtoRGB(h, s, v);

                // 5. Pintar este nuevo color RGB en la misma coordenada
                imagenJpeg.setRGB(x, y, newRgb);
            }
        }
            // 8 Escribir el archivo final en formato JPEG
            File archivoSalida = new File("src/resultado_hsb.jpg");
            ImageIO.write(imagenJpeg, "jpg", archivoSalida);
            
            System.out.println("Conversión a HSB completada con éxito.");

        } catch (IOException e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        }
    }
    
}
    

   
    



    
       
        
        
        
        
   