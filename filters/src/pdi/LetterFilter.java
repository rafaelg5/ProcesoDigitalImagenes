package pdi;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author rgarcia
 */
public class LetterFilter {

    public static int regionAvg(int inicioa, int fina, int iniciol, 
            int finl, BufferedImage img) {
        int rp = 0;
        int gp = 0;
        int bp = 0;
        int cantidad = (img.getHeight() / 90) * (img.getWidth() / 90);
        for (int a = inicioa; a < fina; a++) {
            for (int b = iniciol; b < finl; b++) {
                if (a < img.getWidth()) {
                    if (b < img.getHeight()) {
                        Color c1 = new Color(img.getRGB(a, b));
                        rp += c1.getRed();
                        gp += c1.getGreen();
                        bp += c1.getBlue();
                    }
                }
            }
        }
        rp = (int) (rp / cantidad);
        gp = (int) (gp / cantidad);
        bp = (int) (bp / cantidad);
        int promedio = (int) ((rp + gp + bp) / 3);
        return promedio;
    }

    public static Color regionAvg(int inicioa, int fina, int iniciol, 
            int finl, BufferedImage img, Boolean f) {
        int rp = 0;
        int gp = 0;
        int bp = 0;
        int cantidad = (img.getHeight() / 90) * (img.getWidth() / 90);
        for (int a = inicioa; a < fina; a++) {
            for (int b = iniciol; b < finl; b++) {
                if (a < img.getWidth()) {
                    if (b < img.getHeight()) {
                        Color c1 = new Color(img.getRGB(a, b));
                        rp += c1.getRed();
                        gp += c1.getGreen();
                        bp += c1.getBlue();
                    }
                }
            }
        }
        rp = (int) (rp / cantidad);
        gp = (int) (gp / cantidad);
        bp = (int) (bp / cantidad);
        return new Color(rp, gp, bp);
    }

    public static String oneLetterGrayScale(BufferedImage img) {
        
        int newHeight = img.getHeight() / 90;
        int newWidth = img.getWidth() / 90;
        String html = "";
        int gray = 0;
        for (int i = 0; i < 90; i++) {
            if (i > 0) {
                html += "<br>";
            }
            for (int j = 0; j < 90; j++) {
                Color c = regionAvg(j * newWidth, (j * newWidth) + newWidth, 
                        i * newHeight, (i * newHeight) + newHeight, img, false);
                gray = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
                html += "<b style='color:rgb(" + gray + "," + gray + "," + gray
                        + ");'>@</b>";
            }
        }
        String start = "<html>\n<head>\n<title>Filtro una letra grises</title>\n"
                + "</head>\n<body>\n";
        return start + html + "\n</body></html>";
    }
}
