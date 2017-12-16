package pdi;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Filter {

    private final static int RED = 0;
    private final static int GREEN = 1;
    private final static int BLUE = 2;
    private final static int GRAY_SCALE = 3;
    private final static int RANDOM = 4;

    /**
     * Aplica el filtro de tono de grises a una imagen.
     *
     * @param img la imagen
     */
    public static void grayScale(BufferedImage img) {
        color(img, GRAY_SCALE);
    }

    /**
     * Aplica un filtro rojo a una imagen.
     *
     * @param img la imagen
     */
    public static void red(BufferedImage img) {
        color(img, RED);
    }

    /**
     * Aplica un filtro verde a una imagen.
     *
     * @param img la imagen
     */
    public static void green(BufferedImage img) {
        color(img, GREEN);
    }

    /**
     * Aplica un filtro azul a una imagen.
     *
     * @param img la imagen
     */
    public static void blue(BufferedImage img) {
        color(img, BLUE);
    }

    /**
     * Aplica un filtro de brillo a una imagen.
     *
     * @param img la imagen
     * @param brightness el porcentaje de brillo
     */
    public static void brightness(BufferedImage img, int brightness) {

        int width = img.getWidth();
        int height = img.getHeight();

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {

                Color newColor = null;
                int red = fixColor(getImageColor(img, col, row, RED)
                        + brightness);
                int green = fixColor(getImageColor(img, col, row, GREEN)
                        + brightness);
                int blue = fixColor(getImageColor(img, col, row, BLUE)
                        + brightness);

                newColor = new Color(red, green, blue);
                img.setRGB(col, row, newColor.getRGB());
            }
        }
    }

    /**
     * Método auxiliar que arregla el componente r, g o b de un color. Si es
     * mayor a 255, lo fija en 255 y si es menor a 0, lo fija en 0.
     *
     * @param color el componente de un color
     * @return el color entre [0, 255]
     */
    private static int fixColor(int color) {

        if (color > 255) {
            return 255;
        }

        if (color < 0) {
            return 0;
        }

        return color;
    }

    /**
     * Aplica un filtro de color aleatorio a cada pixel de una imagen.
     *
     * @param img la imagen
     */
    public static void random(BufferedImage img) {
        color(img, RANDOM);
    }

    /**
     * Método auxiliar que aplica un filtro de color a una imagen
     *
     * @param img la imagen
     * @param color el color del filtro (rojo, verde o azul)
     */
    private static void color(BufferedImage img, int color) {

        int width = img.getWidth();
        int height = img.getHeight();
        Color newColor = new Color(0, 0, 0);

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {

                int red = getImageColor(img, col, row, RED);
                int green = getImageColor(img, col, row, GREEN);
                int blue = getImageColor(img, col, row, BLUE);
                switch (color) {
                    case RED:
                        newColor = new Color(red, 0, 0);
                        break;
                    case GREEN:
                        newColor = new Color(0, green, 0);
                        break;
                    case BLUE:
                        newColor = new Color(0, 0, blue);
                        break;
                    case GRAY_SCALE:
                        int gray = (int) (red * 0.2126 + green * 0.7152
                                + blue * 0.0722);
                        newColor = new Color(gray, gray, gray);
                        break;
                    case RANDOM:
                        Random random = new Random();
                        int r = random.nextInt(256);
                        int g = random.nextInt(256);
                        int b = random.nextInt(256);
                        newColor = new Color(r, g, b);
                        break;
                }

                img.setRGB(col, row, newColor.getRGB());
            }
        }
    }

    /**
     * Aplica el filtro de alto contraste a cada pixel de una imagen.
     *
     * @param img la imagen
     */
    public static void highContrast(BufferedImage img) {

        int width = img.getWidth();
        int height = img.getHeight();
        Color newColor = null;

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int red = getImageColor(img, col, row, RED);
                int green = getImageColor(img, col, row, GREEN);
                int blue = getImageColor(img, col, row, BLUE);
                int total = (red + green + blue) / 3;
                if (total >= 127) {
                    newColor = new Color(255, 255, 255);
                } else {
                    newColor = new Color(0, 0, 0);
                }

                img.setRGB(col, row, newColor.getRGB());
            }
        }
    }

    /**
     * Aplica un filtro de mosaico a una imagen.
     *
     * @param w el ancho del mosaico
     * @param h la altura del mosaico
     * @param img la imagen
     */
    public static void mosaic(int w, int h, BufferedImage img) {

        int width = img.getWidth();
        int height = img.getHeight();
        int total = w * h;
        int rAvg = 0, bAvg = 0, gAvg = 0;

        for (int i = 0; i < width; i++) {
            if ((i + w) > width) {
                break;
            }

            for (int j = 0; j < height; j++) {
                if ((j + h) > height) {
                    break;
                }

                for (int k = i; k < i + w; k++) {
                    for (int l = j; l < j + h; l++) {
                        rAvg += getImageColor(img, k, l, RED);
                        gAvg += getImageColor(img, k, l, GREEN);
                        bAvg += getImageColor(img, k, l, BLUE);
                    }
                }

                Color newColor = new Color(rAvg / total, gAvg / total,
                        bAvg / total);
                rAvg = 0;
                gAvg = 0;
                bAvg = 0;
                for (int k = i; k < i + w; k++) {
                    for (int l = j; l < j + h; l++) {
                        img.setRGB(k, l, newColor.getRGB());
                    }
                }
                j += (h - 1);
            }
            i += (w - 1);
        }
    }

    /**
     * Método auxiliar que aplica algún filtro de convolución a una imagen
     *
     * @param filter la matriz filtro a aplicar
     * @param factor el factor del filtro
     * @param bias la tendencia del filtro
     * @param img la imagen a aplicar el filtro
     */
    private static void convolution(double[][] filter, double factor,
            double bias, BufferedImage img) {

        Color newColor = null;
        int width = img.getWidth();
        int height = img.getHeight();
        int filterHeight = filter.length;
        int filterWidth = filter[0].length;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                double r = 0.0, g = 0.0, b = 0.0;

                for (int filterY = 0; filterY < filterHeight; filterY++) {
                    for (int filterX = 0; filterX < filterWidth; filterX++) {

                        int imageX = (x - filterWidth / 2 + filterX + width)
                                % width;
                        int imageY = (y - filterHeight / 2 + filterY + height)
                                % height;

                        r += getImageColor(img, imageX, imageY, RED)
                                * filter[filterY][filterX];
                        g += getImageColor(img, imageX, imageY, GREEN)
                                * filter[filterY][filterX];
                        b += getImageColor(img, imageX, imageY, BLUE)
                                * filter[filterY][filterX];
                    }
                }

                int nr = Math.min(Math.max((int) (factor * r + bias), 0), 255);
                int ng = Math.min(Math.max((int) (factor * g + bias), 0), 255);
                int nb = Math.min(Math.max((int) (factor * b + bias), 0), 255);

                newColor = new Color(nr, ng, nb);
                img.setRGB(x, y, newColor.getRGB());

            }
        }
    }

    /**
     * Aplica un filtro que hace una imagen ligeramente borrosa
     *
     * @param img la imagen a aplicar el filtro
     */
    public static void blur(BufferedImage img) {

        double factor = 1.0d;
        double bias = 0.0d;
        double filter[][]
                = {
                    {0.0, 0.2, 0.0},
                    {0.2, 0.2, 0.2},
                    {0.0, 0.2, 0.0}
                };
        convolution(filter, factor, bias, img);
    }

    /**
     * Aplica un filtro que hace una imagen parecer que está movida
     *
     * @param img la imagen a aplicar el filtro
     */
    public static void motionBlur(BufferedImage img) {

        double factor = 1.0d / 9.0d;
        double bias = 0.0d;
        double filter[][]
                = {
                    {1, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 1, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 1, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 1, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 1, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 1, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 1, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 1, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 1}
                };
        convolution(filter, factor, bias, img);
    }

    /**
     * Filtro que resalta los bordes de una imagen
     *
     * @param img la imagen a aplicar el filtro
     */
    public static void findEdges(BufferedImage img) {

        double factor = 1.0d;
        double bias = 0.0d;
        double filter[][]
                = {
                    {0, 0, -1, 0, 0},
                    {0, 0, -1, 0, 0},
                    {0, 0, 2, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0}
                };
        convolution(filter, factor, bias, img);
    }

    /**
     * Filtro que resalta los bordes de manera más definida
     *
     * @param img la imagen a aplicar el filtro
     */
    public static void sharpen(BufferedImage img) {

        double factor = 1.0d;
        double bias = 0.0d;
        double filter[][]
                = {
                    {-1, -1, -1},
                    {-1, 9, -1},
                    {-1, -1, -1}
                };
        convolution(filter, factor, bias, img);
    }

    public static void emboss(BufferedImage img) {
        double factor = 1.0d;
        double bias = 128.0d;
        double filter[][]
                = {
                    {1, -1, 0},
                    {-1, 0, 1},
                    {0, 1, 1}
                };
        convolution(filter, factor, bias, img);
    }

    public static void inverse(BufferedImage img) {

        int width = img.getWidth();
        int height = img.getHeight();
        Color newColor = null;

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {

                int red = getImageColor(img, col, row, RED);
                int green = getImageColor(img, col, row, GREEN);
                int blue = getImageColor(img, col, row, BLUE);
                int total = (red + green + blue) / 3;
                if (total >= 127) {
                    newColor = new Color(0, 0, 0);
                } else {
                    newColor = new Color(255, 255, 255);
                }

                img.setRGB(col, row, newColor.getRGB());
            }
        }
    }

    /**
     * Aplica un filtro tipo imagen Andy Warhol.
     *
     * @param img la imagen
     * @param red el componente rojo
     * @param green el componente verde
     * @param blue el componente azul
     */
    public static void warhol(BufferedImage img, double red, double green,
            double blue) {

        int width = img.getWidth();
        int height = img.getHeight();
        Color newColor = null;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                int r = getImageColor(img, i, j, RED);
                int g = getImageColor(img, i, j, GREEN);
                int b = getImageColor(img, i, j, BLUE);

                int auxR = (r * 255) & (int) red;
                int auxG = (g * 255) & (int) green;
                int auxB = (b * 255) & (int) blue;

                newColor = new Color(auxR, auxG, auxB);
                img.setRGB(i, j, newColor.getRGB());
            }
        }
    }

    /**
     * Quita una marca de agua específica a una imagen.
     *
     * @param img la imagen
     */
    public static void waterMark(BufferedImage img) {

        int width = img.getWidth();
        int height = img.getHeight();
        Color newColor = null;
        // Valor redondeado encontrado 'a ojo' de la marca de agua
        int[] rgb = {235, 175, 185};
        int c = 0;

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int red = getImageColor(img, col, row, RED);
                int green = getImageColor(img, col, row, GREEN);
                int blue = getImageColor(img, col, row, BLUE);

                /* Si la diferencia entre r,g y r,b es considerablemente mayor,
		 * podemos considerar que es la marca de agua.
                 */
                if (red > green + 15 && red > blue + 15) {

                    /* Obtenemos la diferencia entre la marca de agua y 
		     * el pixel actual
                     */
                    int diffR = rgb[0] - red;
                    int diffG = rgb[1] - green;
                    int diffB = rgb[2] - blue;

                    // obtenemos el promedio
                    int avg = fixColor((diffR + diffG + diffB) / 3);

                    /* Si el promedio es considerablemente menor, el original
		     * es un blanco. Si es considerablemente mayor, es un negro.
		     * Y si está entre esos dos valores, obtenemos un gris.
                     */
                    if (avg < 25) {
                        red = green = blue = 255;
                    } else if (avg > 103) {
                        red = green = blue = 0;
                    } else {
                        red = green = blue = (red + green + blue) / 3;
                    }
                }
                newColor = new Color(red, green, blue);

                img.setRGB(col, row, newColor.getRGB());
            }
        }
    }

    /**
     * Filtro que genera una imagen tipo mosaico usando la misma imagen en blanco
     *  y negro
     * @param img la imagen
     * @return la imagen con el filtro aplicado
     */
    public static BufferedImage recursiveBW(BufferedImage img) {

        grayScale(img);

        int newHeight = img.getHeight() / 50;
        int newWidth = img.getWidth() / 50;

        BufferedImage dimg
                = new BufferedImage(img.getWidth(), img.getHeight(),
                        BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = dimg.createGraphics();

        ArrayList<BufferedImage> imgs = generateBWImages(img);
        
        for (int i = 0; i < 50; i++) {

            for (int j = 0; j < 50; j++) {
                float med = LetterFilter.regionAvg(j * newWidth, (j * newWidth)
                        + newWidth, i * newHeight, (i * newHeight) + newHeight,
                        newHeight, newWidth, img);
                
                if (med >= 0 && med <= 12.75) {

                    g.drawImage(imgs.get(0), j * newWidth, i * newHeight, 
                            newWidth, newHeight, null);
                } else if (med > 12.75 && med <= 2 * 12.75) {

                    g.drawImage(imgs.get(1),
                            j * newWidth, i * newHeight, newWidth, newHeight,
                            null);
                } else if (med > 2 * 12.75 && med <= 3 * 12.75) {
                    g.drawImage(imgs.get(2),
                            j * newWidth, i * newHeight, newWidth, newHeight,
                            null);
                } else if (med > 3 * 12.75 && med <= 4 * 12.75) {
                    g.drawImage(imgs.get(3),
                            j * newWidth, i * newHeight, newWidth, newHeight,
                            null);
                } else if (med > 4 * 12.75 && med <= 5 * 12.75) {
                    g.drawImage(imgs.get(4),
                            j * newWidth, i * newHeight, newWidth, newHeight,
                            null);
                } else if (med > 5 * 12.75 && med <= 6 * 12.75) {
                    g.drawImage(imgs.get(5),
                            j * newWidth, i * newHeight, newWidth, newHeight,
                            null);
                } else if (med > 6 * 12.75 && med <= 7 * 12.75) {
                    g.drawImage(imgs.get(6),
                            j * newWidth, i * newHeight, newWidth, newHeight,
                            null);
                } else if (med > 7 * 12.75 && med <= 8 * 12.75) {
                    g.drawImage(imgs.get(7),
                            j * newWidth, i * newHeight, newWidth, newHeight,
                            null);
                } else if (med > 8 * 12.75 && med <= 9 * 12.75) {
                    g.drawImage(imgs.get(8),
                            j * newWidth, i * newHeight, newWidth, newHeight,
                            null);
                } else if (med > 9 * 12.75 && med <= 10 * 12.75) {
                    g.drawImage(imgs.get(9),
                            j * newWidth, i * newHeight, newWidth, newHeight,
                            null);
                } else if (med > 10 * 12.75 && med <= 11 * 12.75) {
                    g.drawImage(imgs.get(10),
                            j * newWidth, i * newHeight, newWidth, newHeight,
                            null);
                } else if (med > 11 * 12.75 && med <= 12 * 12.75) {
                    g.drawImage(imgs.get(11),
                            j * newWidth, i * newHeight, newWidth, newHeight,
                            null);
                } else if (med > 12 * 12.75 && med <= 13 * 12.75) {
                    g.drawImage(imgs.get(12),
                            j * newWidth, i * newHeight, newWidth, newHeight,
                            null);
                } else if (med > 13 * 12.75 && med <= 14 * 12.75) {
                    g.drawImage(imgs.get(13),
                            j * newWidth, i * newHeight, newWidth, newHeight,
                            null);
                } else if (med > 14 * 12.75 && med <= 15 * 12.75) {
                    g.drawImage(imgs.get(14),
                            j * newWidth, i * newHeight, newWidth, newHeight,
                            null);
                } else if (med > 15 * 12.75 && med <= 16 * 12.75) {
                    g.drawImage(imgs.get(15),
                            j * newWidth, i * newHeight, newWidth, newHeight,
                            null);
                } else if (med > 16 * 12.75 && med <= 17 * 12.75) {
                    g.drawImage(imgs.get(16),
                            j * newWidth, i * newHeight, newWidth, newHeight,
                            null);
                } else if (med > 17 * 12.75 && med <= 18 * 12.75) {
                    g.drawImage(imgs.get(17),
                            j * newWidth, i * newHeight, newWidth, newHeight,
                            null);
                } else if (med > 18 * 12.75 && med <= 19 * 12.75) {
                    g.drawImage(imgs.get(18),
                            j * newWidth, i * newHeight, newWidth, newHeight,
                            null);
                } else if (med > 19 * 12.75 && med <= 20 * 12.75) {
                    g.drawImage(imgs.get(19),
                            j * newWidth, i * newHeight, newWidth, newHeight,
                            null);
                }
            }
        }
        return dimg;
    }

    /**
     * Genera una imagen tipo mosaico usando la misma imagen
     * @param img la imagen
     * @return la imagen con el filtro
     */
    public static BufferedImage recursiveColor(BufferedImage img) {
        
        int newHeight = img.getHeight() / 50;
        int newWidth = img.getWidth() / 50;

        BufferedImage dimg
                = new BufferedImage(img.getWidth(), img.getHeight(),
                        BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = dimg.createGraphics();

        for (int i = 0; i < 50; i++) {

            for (int j = 0; j < 50; j++) {

                Color c = LetterFilter.regionAvgColor(j * newWidth,
                        (j * newWidth) + newWidth, i * newHeight,
                        (i * newHeight) + newHeight, newHeight, newWidth, img);
                BufferedImage tmp = copyOf(img);
                warhol(tmp, c.getRed(), c.getGreen(), c.getBlue());
                g.drawImage(tmp, j * newWidth, i * newHeight, newWidth,
                        newHeight, null);
            }
        }

        return dimg;
    }
    
    public static void sepia(BufferedImage img){
        
        int width = img.getWidth();
        int height = img.getHeight();        

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                int r = getImageColor(img, i, j, RED);
                int g = getImageColor(img, i, j, GREEN);
                int b = getImageColor(img, i, j, BLUE);

                int tr = fixColor((int)((0.393d * r) + (0.769d * g) + (0.189d * b)));
                int tg = fixColor((int)((0.349d * r) + (0.686d * g) + (0.168d * b)));
                int tb = fixColor((int)((0.272d * r) + (0.534d * g) + (0.131d * b)));

                Color newColor = new Color(tr, tg, tb);
                img.setRGB(i, j, newColor.getRGB());
            }
        }
    }

    /**
     * Genera 20 imágenes en escala de grises que varían su tonalidad
     * @param src la imagen fuente
     * @return un arraylist con todas las imágenes
     */
    private static ArrayList<BufferedImage> generateBWImages(BufferedImage src) {

        ArrayList<BufferedImage> imgs = new ArrayList<>();
        double factor = 510 / 20;
        for (int i = 0; i < 20; i++) {
            BufferedImage tmp = copyOf(src);
            brightness(tmp, (int) (-255 + i * factor));
            imgs.add(tmp);
        }
        BufferedImage tmp = copyOf(src);
        brightness(tmp, 255);
        imgs.set(19, tmp);
        return imgs;
    }

    /**
     * Crea una copia exacta de una imagen
     * @param source la imagen a copiar
     * @return la nueva imagen
     */
    private static BufferedImage copyOf(BufferedImage source) {
        BufferedImage b = new BufferedImage(source.getWidth(),
                source.getHeight(), source.getType());
        Graphics g = b.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return b;
    }
    
    /**
     * Obtiene el r, g o b de una imagen.
     *
     * @param img la imagen
     * @param col la coordenada x del pixel
     * @param row la coordenada y del pixel
     * @param color la parte de rgb de la imagen que queremos
     * @return un entero de 0 a 255 que es la representación del color
     */
    private static int getImageColor(BufferedImage img, int col, int row,
            int color) {

        switch (color) {
            case RED:
                return new Color(img.getRGB(col, row)).getRed();
            case GREEN:
                return new Color(img.getRGB(col, row)).getGreen();
            case BLUE:
                return new Color(img.getRGB(col, row)).getBlue();
        }
        return -1;
    }
}
