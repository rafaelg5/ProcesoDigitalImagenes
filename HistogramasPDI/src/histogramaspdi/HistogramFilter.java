package histogramaspdi;

import java.awt.Color;
import java.awt.image.BufferedImage;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;

/**
 *
 * @author rgarcia
 */
public class HistogramFilter {

    public static void makeHistogram(BufferedImage img, LineChart chart) {

        chart.getData().clear();
        XYChart.Series series = new XYChart.Series();

        int[] frequencies = getFrequencies(img);

        for (int i = 0; i < frequencies.length; i++) {
            series.getData().add(new XYChart.Data<>(i, frequencies[i]));

        }
        chart.getData().add(series);
        chart.setVisible(true);
    }

    private static int[] getFrequencies(BufferedImage img) {

        int[] frequencies = new int[256];

        int width = img.getWidth();
        int height = img.getHeight();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                // Las 3 componentes rgb son iguales
                int color = new Color(img.getRGB(i, j)).getRed();
                frequencies[color]++;
            }
        }        
        return frequencies;
    }

    public static void histogramStretching(BufferedImage img, LineChart chart) {

        int min = 255;
        int max = 0;
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                
                // basta con un componente
                int red = new Color(img.getRGB(i, j)).getRed();                
                if(red > max)
                    max = red;
                if(red < min)
                    min = red;
            }
        }
        
        int maxContrast = Math.abs(max - min);
        System.out.println(maxContrast);
        
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                
                // basta con un componente
                int red = new Color(img.getRGB(i, j)).getRed();                

                int newGray = (int) ((double)(red * 255) / maxContrast);
                newGray = newGray < 0 ? 0 : newGray > 255 ? 255 : newGray;
                
                Color newColor = new Color(newGray, newGray, newGray);

                img.setRGB(i, j, newColor.getRGB());
            }
        }
        
        makeHistogram(img, chart);
    }
    
    public static void histogramEqualization(BufferedImage img, LineChart chart) {

        // Cumulative Distribution Frequency
        int[] cdf = new int[256];
        
        int[] frequencies = getFrequencies(img);
        
        cdf[0] = frequencies[0];
        
        int sum = frequencies[0];
        
        for (int i = 1; i < frequencies.length; i++) {
            
            cdf[i] = sum + frequencies[i];
            sum += frequencies[i];
        }

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                
                // basta con un componente
                int red = new Color(img.getRGB(i, j)).getRed();                

                int newGray = (int)(((double)(cdf[red] - 1) / cdf[255]) * 255);
                
                newGray = newGray < 0 ? 0 : newGray > 255 ? 255 : newGray;
                
                Color newColor = new Color(newGray, newGray, newGray);

                img.setRGB(i, j, newColor.getRGB());
            }
        }
        
        makeHistogram(img, chart);
    }

    public static void grayScale(BufferedImage img) {

        int width = img.getWidth();
        int height = img.getHeight();
        Color newColor = null;

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {

                int red = new Color(img.getRGB(col, row)).getRed();
                int green = new Color(img.getRGB(col, row)).getGreen();
                int blue = new Color(img.getRGB(col, row)).getBlue();

                int gray = (int) (red * 0.2126 + green * 0.7152
                        + blue * 0.0722);
                newColor = new Color(gray, gray, gray);

                img.setRGB(col, row, newColor.getRGB());
            }
        }
    }
}
