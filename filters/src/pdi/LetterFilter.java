package pdi;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author rgarcia
 */
public class LetterFilter {

    private static final String HTML = "<!DOCTYPE html>\n<meta charset='UTF-8'>"
            + "\n<meta http-equiv='Content-type' content='text/html; "
            + "charset=UTF-8'>\n<html>\n<head>\n" + "<style>\n?S\n</style>\n"
            + "<title>?T</title>\n<body>\n?B\n</body>\n</html>";

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

    public static String oneLetterColor(BufferedImage img, int x, int y) {

        int newHeight = img.getHeight() / y;
        int newWidth = img.getWidth() / x;
        String html = "";
        for (int i = 0; i < y; i++) {
            if (i > 0) {
                html += "\n<br>\n";
            }
            for (int j = 0; j < x; j++) {
                Color c = regionAvgColor(j * newWidth, (j * newWidth) + newWidth,
                        i * newHeight, (i * newHeight) + newHeight, newHeight,
                        newWidth, img);
                html += "<b style='color:rgb(" + c.getRed() + "," + c.getGreen()
                        + "," + c.getBlue() + ");'>@</b>";
            }
        }
        String style = "body{\nfont-size: 10px\n}";
        String title = "Imagen 1 Letra Color";
        int styleIndex = HTML.indexOf("?S"), titleIndex = HTML.indexOf("?T"),
                bodyIndex = HTML.indexOf("?B");
        String htmlFile = HTML.substring(0, styleIndex) + style;
        htmlFile += HTML.substring(styleIndex + 2, titleIndex) + title;
        htmlFile += HTML.substring(titleIndex + 2, bodyIndex) + html;
        htmlFile += HTML.substring(bodyIndex + 2);

        return htmlFile;
    }

    public static String oneLetterGrayScale(BufferedImage img, int x, int y) {

        int newHeight = img.getHeight() / y;
        int newWidth = img.getWidth() / x;
        String html = "";
        int gray = 0;
        for (int i = 0; i < y; i++) {
            if (i > 0) {
                html += "\n<br>\n";
            }
            for (int j = 0; j < x; j++) {
                Color c = regionAvgColor(j * newWidth, (j * newWidth) + newWidth,
                        i * newHeight, (i * newHeight) + newHeight, newHeight,
                        newWidth, img);
                gray = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
                html += "<b style='color:rgb(" + gray + "," + gray + "," + gray
                        + ");'>@</b>";
            }
        }
        String title = "Imagen 1 Letra Tonos de Gris";
        String style = "body{\nfont-size: 10px\n}";
        int styleIndex = HTML.indexOf("?S"), titleIndex = HTML.indexOf("?T"),
                bodyIndex = HTML.indexOf("?B");
        String htmlFile = HTML.substring(0, styleIndex) + style;
        htmlFile += HTML.substring(styleIndex + 2, titleIndex) + title;
        htmlFile += HTML.substring(titleIndex + 2, bodyIndex) + html;
        htmlFile += HTML.substring(bodyIndex + 2);
        return htmlFile;
    }

    public static String manyLetters(BufferedImage img, int x, int y) {

        int newHeight = img.getHeight() / y;
        int newWidth = img.getWidth() / x;
        String html = "";

        for (int i = 0; i < y; i++) {
            if (i > 0) {
                html += "<br>\n";
            }
            for (int j = 0; j < x; j++) {
                int med = regionAvg(j * newWidth, (j * newWidth) + newWidth,
                        i * newHeight, (i * newHeight) + newHeight, newHeight,
                        newWidth, img);
                if (med >= 0 && med < 16) {
                    html += "<b>M</b>";
                } else if (med >= 16 && med < 32) {
                    html += "<b>N</b>";
                } else if (med >= 32 && med < 48) {
                    html += "<b>H</b>";
                } else if (med >= 48 && med < 64) {
                    html += "<b>#</b>";
                } else if (med >= 64 && med < 80) {
                    html += "<b>Q</b>";
                } else if (med >= 80 && med < 96) {
                    html += "<b>U</b>";
                } else if (med >= 96 && med < 112) {
                    html += "<b>A</b>";
                } else if (med >= 112 && med < 128) {
                    html += "<b>D</b>";
                } else if (med >= 128 && med < 144) {
                    html += "<b>O</b>";
                } else if (med >= 144 && med < 160) {
                    html += "<b>Y</b>";
                } else if (med >= 160 && med < 176) {
                    html += "<b>2</b>";
                } else if (med >= 176 && med < 192) {
                    html += "<b>$</b>";
                } else if (med >= 192 && med < 208) {
                    html += "<b>%</b>";
                } else if (med >= 208 && med < 224) {
                    html += "<b>+</b>";
                } else if (med >= 224 && med < 240) {
                    html += "<b>_</b>";
                } else if (med >= 240 && med < 256) {
                    html += "<b>&nbsp</b>";
                }
            }
        }
        String style = "body{\nfont-family: Courier New;\n}";
        String title = "Imagen Varias Letras BN";
        int styleIndex = HTML.indexOf("?S"), titleIndex = HTML.indexOf("?T"),
                bodyIndex = HTML.indexOf("?B");
        String htmlFile = HTML.substring(0, styleIndex) + style;
        htmlFile += HTML.substring(styleIndex + 2, titleIndex) + title;
        htmlFile += HTML.substring(titleIndex + 2, bodyIndex) + html;
        htmlFile += HTML.substring(bodyIndex + 2);
        return htmlFile;
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
                    html += "<b>&nbsp</b>";
                    aux = 0;
                } else {
                    Color c = regionAvgColor(j * newWidth, (j * newWidth) + newWidth,
                            i * newHeight, (i * newHeight) + newHeight, newHeight,
                            newWidth, img);
                    html += "<b style='color:rgb(" + c.getRed() + ","
                            + c.getGreen() + "," + c.getBlue() + ");'>"
                            + txt.substring(aux, aux + 1) + "</b>";
                    aux++;
                }
            }
        }
        String style = "body{\nfont-size: 15px\n}";
        String title = "Imagen Texto";
        int styleIndex = HTML.indexOf("?S"), titleIndex = HTML.indexOf("?T"),
                bodyIndex = HTML.indexOf("?B");
        String htmlFile = HTML.substring(0, styleIndex) + style;
        htmlFile += HTML.substring(styleIndex + 2, titleIndex) + title;
        htmlFile += HTML.substring(titleIndex + 2, bodyIndex) + html;
        htmlFile += HTML.substring(bodyIndex + 2);
        return htmlFile;
    }

    public static String pokerFilter(BufferedImage img, int x, int y) {

        int newHeight = img.getHeight() / y;
        int newWidth = img.getWidth() / x;
        String html = "";

        for (int i = 0; i < y; i++) {
            if (i > 0) {
                html += "\n<br>\n";
            }
            for (int j = 0; j < x; j++) {
                int med = regionAvg(j * newWidth, (j * newWidth) + newWidth,
                        i * newHeight, (i * newHeight) + newHeight, newHeight,
                        newWidth, img);
                if (med >= 0 && med < 23) {
                    html += "<b>Z</b>";
                } else if (med >= 23 && med < 46) {
                    html += "<b>W</b>";
                } else if (med >= 46 && med < 69) {
                    html += "<b>V</b>";
                } else if (med >= 69 && med < 92) {
                    html += "<b>U</b>";
                } else if (med >= 92 && med < 115) {
                    html += "<b>T</b>";
                } else if (med >= 115 && med < 138) {
                    html += "<b>S</b>";
                } else if (med >= 138 && med < 161) {
                    html += "<b>R</b>";
                } else if (med >= 161 && med < 184) {
                    html += "<b>Q</b>";
                } else if (med >= 184 && med < 207) {
                    html += "<b>P</b>";
                } else if (med >= 207 && med < 230) {
                    html += "<b>O</b>";
                } else if (med >= 230 && med < 256) {
                    html += "<b>N</b>";
                }
            }
        }
        String title = "Imagen Naipes";
        String style = "body{\nfont-family: 'PlayingCards';\n}";
        int styleIndex = HTML.indexOf("?S"), titleIndex = HTML.indexOf("?T"),
                bodyIndex = HTML.indexOf("?B");
        String htmlFile = HTML.substring(0, styleIndex) + style;
        htmlFile += HTML.substring(styleIndex + 2, titleIndex) + title;
        htmlFile += HTML.substring(titleIndex + 2, bodyIndex) + html;
        htmlFile += HTML.substring(bodyIndex + 2);
        return htmlFile;
    }

    public static String blackDominoFilter(BufferedImage img, int x, int y) {

        int newHeight = img.getHeight() / y;
        int newWidth = img.getWidth() / x;
        String html = "";

        for (int i = 0; i < y; i++) {
            if (i > 0) {
                html += "\n<br>\n";
            }
            for (int j = 0; j < x; j++) {
                int med = regionAvg(j * newWidth, (j * newWidth) + newWidth,
                        i * newHeight, (i * newHeight) + newHeight, newHeight,
                        newWidth, img);
                if (med >= 0 && med < 37) {
                    html += "<b>6</b>";
                } else if (med >= 37 && med < 74) {
                    html += "<b>5</b>";
                } else if (med >= 74 && med < 111) {
                    html += "<b>4</b>";
                } else if (med >= 111 && med < 148) {
                    html += "<b>3</b>";
                } else if (med >= 148 && med < 185) {
                    html += "<b>2</b>";
                } else if (med >= 185 && med < 222) {
                    html += "<b>1</b>";
                } else if (med >= 222 && med < 256) {
                    html += "<b>0</b>";;
                }
            }
        }
        String title = "Imagen Dominós Negros";
        String style = "body{\nfont-family: 'Las Vegas Black Dominoes';\n}";
        int styleIndex = HTML.indexOf("?S"), titleIndex = HTML.indexOf("?T"),
                bodyIndex = HTML.indexOf("?B");
        String htmlFile = HTML.substring(0, styleIndex) + style;
        htmlFile += HTML.substring(styleIndex + 2, titleIndex) + title;
        htmlFile += HTML.substring(titleIndex + 2, bodyIndex) + html;
        htmlFile += HTML.substring(bodyIndex + 2);
        return htmlFile;
    }
    
    public static String whiteDominoFilter(BufferedImage img, int x, int y) {

        int newHeight = img.getHeight() / y;
        int newWidth = img.getWidth() / x;
        String html = "";

        for (int i = 0; i < y; i++) {
            if (i > 0) {
                html += "\n<br>\n";
            }
            for (int j = 0; j < x; j++) {
                int med = regionAvg(j * newWidth, (j * newWidth) + newWidth,
                        i * newHeight, (i * newHeight) + newHeight, newHeight,
                        newWidth, img);
                if (med >= 0 && med < 37) {
                    html += "<b>6</b>";
                } else if (med >= 37 && med < 74) {
                    html += "<b>5</b>";
                } else if (med >= 74 && med < 111) {
                    html += "<b>4</b>";
                } else if (med >= 111 && med < 148) {
                    html += "<b>3</b>";
                } else if (med >= 148 && med < 185) {
                    html += "<b>2</b>";
                } else if (med >= 185 && med < 222) {
                    html += "<b>1</b>";
                } else if (med >= 222 && med < 256) {
                    html += "<b>0</b>";;
                }
            }
        }
        String title = "Imagen Dominós Blancos";
        String style = "body{\nfont-family: 'Las Vegas White Dominoes';\n}";
        int styleIndex = HTML.indexOf("?S"), titleIndex = HTML.indexOf("?T"),
                bodyIndex = HTML.indexOf("?B");
        String htmlFile = HTML.substring(0, styleIndex) + style;
        htmlFile += HTML.substring(styleIndex + 2, titleIndex) + title;
        htmlFile += HTML.substring(titleIndex + 2, bodyIndex) + html;
        htmlFile += HTML.substring(bodyIndex + 2);
        return htmlFile;
    }
}
