package pdi;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author rgarcia
 */
public class LetterFilter {

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

    private static int regionAvg(int startH, int endH, int startW, int endW,
            int newHeight, int newWidth, BufferedImage img) {
        Color c = regionAvgColor(startH, endH, startW, endW, newHeight, newWidth,
                img);
        return (c.getRed() + c.getGreen() + c.getBlue()) / 3;
    }

    public static String oneLetterColor(BufferedImage img) {

        int newHeight = img.getHeight() / 144;
        int newWidth = img.getWidth() / 144;
        String html = "";
        for (int i = 0; i < 144; i++) {
            if (i > 0) {
                html += "\n<br>\n";
            }
            for (int j = 0; j < 144; j++) {
                Color c = regionAvgColor(j * newWidth, (j * newWidth) + newWidth,
                        i * newHeight, (i * newHeight) + newHeight, newHeight, 
                        newWidth, img);
                html += "<b style='color:rgb(" + c.getRed() + "," + c.getGreen()
                        + "," + c.getBlue() + "); font-size: 10px;'>@</b>";
            }
        }
        String start = "<html>\n<head>\n<title>Filtro una letra grises</title>\n"
                + "</head>\n<body>\n";
        return start + html + "\n</body>\n</html>";
    }

    public static String oneLetterGrayScale(BufferedImage img) {

        int newHeight = img.getHeight() / 144;
        int newWidth = img.getWidth() / 144;
        String html = "";
        int gray = 0;
        for (int i = 0; i < 144; i++) {
            if (i > 0) {
                html += "\n<br>\n";
            }
            for (int j = 0; j < 144; j++) {
                Color c = regionAvgColor(j * newWidth, (j * newWidth) + newWidth,
                        i * newHeight, (i * newHeight) + newHeight, newHeight, 
                        newWidth, img);
                gray = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
                html += "<b style='color:rgb(" + gray + "," + gray + "," + gray
                        + "); font-size: 10px;'>@</b>";
            }
        }
        String start = "<html>\n<head>\n<title>Filtro una letra grises</title>\n"
                + "</head>\n<body>\n";
        return start + html + "\n</body>\n</html>";
    }

    public static String manyLetters(BufferedImage img) {

        int newHeight = img.getHeight() / 144;
        int newWidth = img.getWidth() / 144;
        String html = "";

        for (int i = 0; i < 144; i++) {
            if (i > 0) {
                html += "\n<br>\n";
            }
            for (int j = 0; j < 144; j++) {
                int med = regionAvg(j * newWidth, (j * newWidth) + newWidth,
                        i * newHeight, (i * newHeight) + newHeight, newHeight, 
                        newWidth, img);
                if (med >= 0 && med < 16) {
                    html += "<b style='font-size: 10px;'>M</b>";
                } else if (med >= 16 && med < 32) {
                    html += "<b style='font-size: 10px;'>N</b>";
                } else if (med >= 32 && med < 48) {
                    html += "<b style='font-size: 10px;'>H</b>";
                } else if (med >= 48 && med < 64) {
                    html += "<b style='font-size: 10px;'>#</b>";
                } else if (med >= 64 && med < 80) {
                    html += "<b style='font-size: 10px;'>Q</b>";
                } else if (med >= 80 && med < 96) {
                    html += "<b style='font-size: 10px;'>U</b>";
                } else if (med >= 96 && med < 112) {
                    html += "<b style='font-size: 10px;'>A</b>";
                } else if (med >= 112 && med < 128) {
                    html += "<b style='font-size: 10px;'>D</b>";
                } else if (med >= 128 && med < 144) {
                    html += "<b style='font-size: 10px;'>O</b>";
                } else if (med >= 144 && med < 160) {
                    html += "<b style='font-size: 10px;'>Y</b>";
                } else if (med >= 160 && med < 176) {
                    html += "<b style='font-size: 10px;'>2</b>";
                } else if (med >= 176 && med < 192) {
                    html += "<b style='font-size: 10px;'>$</b>";
                } else if (med >= 192 && med < 208) {
                    html += "<b style='font-size: 10px;'>%</b>";
                } else if (med >= 208 && med < 224) {
                    html += "<b style='font-size: 10px;'>+</b>";
                } else if (med >= 224 && med < 240) {
                    html += "<b style='font-size: 10px;'>_</b>";
                } else if (med >= 240 && med < 256) {
                    html += "<b style='white-space:pre'> </b>";
                }
            }
        }
        String start = "<html>\n<head>\n<title>Filtro varias letras</title>\n"
                + "</head>\n<body>\n";
        return start + html + "\n</body>\n</html>";
    }

    public static String customTextFilter(BufferedImage img, String txt) {

        int newHeight = img.getHeight() / 200;
        int newWidth = img.getWidth() / 200;
        String html = "";
        int aux = 0;

        for (int i = 0; i < 200; i++) {
            if (i > 0) {
                html += "\n<br>\n";
            }
            for (int j = 0; j < 200; j++) {
                if (aux == txt.length()) {
                    html += "<b style='white-space:pre'> </b>";
                    aux = 0;
                } else {
                    Color c = regionAvgColor(j * newWidth, (j * newWidth) + newWidth,
                        i * newHeight, (i * newHeight) + newHeight, newHeight, 
                        newWidth, img);
                    html += "<b style='color:rgb(" + c.getRed() + ","
                            + c.getGreen() + "," + c.getBlue() + "); font-size:"
                            + " 15px;'>" + txt.substring(aux, aux + 1) + "</b>";
                    aux++;
                }
            }
        }
        String start = "<html>\n<head>\n<title>Filtro una letra grises</title>\n"
                + "</head>\n<body>\n";
        return start + html + "\n</body>\n</html>";
    }
}
