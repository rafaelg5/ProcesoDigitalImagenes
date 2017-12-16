package proyectofinal;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author rgarcia
 */
public class PhotoMosaic {

    private final BufferedImage srcImage;

    public PhotoMosaic(BufferedImage image) {
        srcImage = image;
    }

    /**
     * Procesa la foto para generar un foto mosaico usando la biblioteca de
     * imágenes
     *
     * @throws IOException si hay algún problema con las imágenes
     */
    public void process() throws IOException {

        File imageIndex = new File("output/index.idx");

        String html = "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n"
                + "<tr><td><nobr>";

        // El tamaño de las subregiones, siendo que la imagen va a tener 90 x 90
        // subregiones y las subimágenes son de tamaño 10 x 10.
        int newWidth = srcImage.getWidth() / 90;
        int newHeight = srcImage.getHeight() / 90;
        int minDistance = Integer.MAX_VALUE;
        String minImage = "";

        for (int i = 0; i < 90; i++) {
            if (i > 0) {
                html += "</nobr></td></tr>\n"
                        + "<tr><td><nobr>";
            }
            for (int j = 0; j < 90; j++) {

                // Calcula color de la región
                Color c = regionAvgColor(j * newWidth, (j * newWidth) + newWidth,
                        i * newHeight, (i * newHeight) + newHeight, newHeight,
                        newWidth, srcImage);

                FileReader fr = new FileReader("output/index.idx");
                BufferedReader br = new BufferedReader(fr);
                String line;

                while ((line = br.readLine()) != null) {
                    String[] chars = line.split(" ");
                    Color newColor = new Color(Integer.parseInt(chars[0]),
                            Integer.parseInt(chars[1]), Integer.parseInt(chars[2]));
                    int distance = getDistance(c, newColor);

                    if (distance < minDistance) {
                        minDistance = distance;
                        minImage = chars[3];
                    }
                }
                minDistance = Integer.MAX_VALUE;
                html += "<img src=\"../images/" + minImage + "\" width=\"10\" height=\"10\">";
            }
        }

        html += "</nobr></td></tr></table>";
        File htmlFile = new File("output/output.html");

        FileWriter fw = new FileWriter(htmlFile, false);
        fw.write(html);
        fw.close();
    }

    /**
     * Calcula la distancia euclidiana entre dos colores
     *
     * @param c1 el color 1
     * @param c2 el color 2
     * @return la distancia entre c1 y c2
     */
    private int getDistance(Color c1, Color c2) {

        // Distancia Euclidiana
        int r1 = c1.getRed(), g1 = c1.getGreen(), b1 = c1.getBlue();
        int r2 = c2.getRed(), g2 = c2.getGreen(), b2 = c2.getBlue();

        double prod1 = Math.pow(r1 - r2, 2);
        double prod2 = Math.pow(g1 - g2, 2);
        double prod3 = Math.pow(b1 - b2, 2);

        double distance = Math.sqrt(prod1 + prod2 + prod3);

        return (int) Math.round(distance);
    }

    /**
     * Calcula el color promedio de una imagen
     *
     * @param image la imagen
     * @return el color promedio
     */
    private Color getImageAvgColor(BufferedImage image) {

        int rAvg = 0, gAvg = 0, bAvg = 0;
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {

                Color c = new Color(image.getRGB(i, j));

                rAvg += c.getRed();
                gAvg += c.getGreen();
                bAvg += c.getBlue();
            }
        }
        rAvg = (int) ((double) rAvg / (image.getHeight() * image.getWidth()));
        gAvg = (int) ((double) gAvg / (image.getHeight() * image.getWidth()));
        bAvg = (int) ((double) bAvg / (image.getHeight() * image.getWidth()));

        Color averageColor = new Color(rAvg, gAvg, bAvg);
        return averageColor;
    }
    
    /**
     * Agrega una línea al archivo de índices de imágenes. Una línea consta de
     * los componentes rgb promedio de la imagen y su nombre
     * @throws IOException si hay algún problema con archivos
     */
    public void makeImageIndex() throws IOException {

        File imageIndex = new File("output/index.idx");
        if(imageIndex.exists()){
            return;
        }
        
        File folder = new File("images/");
        File[] listOfFiles = folder.listFiles();
        
        for (File file : listOfFiles) {
            
            BufferedImage img = ImageIO.read(file);
            // Obtener color promedio
            Color color = getImageAvgColor(img);

            String line = String.format("%d %d %d %s\n", color.getRed(),
                    color.getGreen(), color.getBlue(), file.getName());

            // Escribir en el archivo
            try {
                FileWriter fw = new FileWriter(imageIndex, true);
                fw.write(line);
                fw.close();
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        }

    }

    /**
     * Determina el promedio de color de una región de una imagen
     *
     * @param startH el inicio de y
     * @param endH el fin de y
     * @param startW el inicio de x
     * @param endW el fin de x
     * @param newHeight el ancho de la región
     * @param newWidth el alto de la región
     * @param img la imagen
     * @return el color de la región
     */
    private static Color regionAvgColor(int startH, int endH, int startW,
            int endW, int newHeight, int newWidth, BufferedImage img) {
        int r = 0;
        int g = 0;
        int b = 0;
        int total = newHeight * newWidth;
        for (int h = startH; h < endH; h++) {
            for (int w = startW; w < endW; w++) {
                if (h < img.getWidth()) {
                    if (w < img.getHeight()) {
                        Color c = new Color(img.getRGB(h, w));
                        r += c.getRed();
                        g += c.getGreen();
                        b += c.getBlue();
                    }
                }
            }
        }
        r = r / total;
        g = g / total;
        b = b / total;
        return new Color(r, g, b);
    }
}
