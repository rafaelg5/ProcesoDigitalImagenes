/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package histogramaspdi;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

/**
 *
 * @author rgarcia
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private ImageView originalImage;
    @FXML
    private MenuItem loadImage;
    @FXML
    private MenuItem saveImage;
    @FXML
    private MenuItem histogram;
    @FXML
    private LineChart<Number, Number> chart;

    private final FileChooser fileChooser = new FileChooser();

    @FXML
    private void handleHistogram(ActionEvent event) {

        if (originalImage.getImage() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText("");
            alert.setContentText("Seleccione primero la opción Cargar Imagen y "
                    + "agregue una imagen.");

            alert.showAndWait();
            return;
        }
        BufferedImage image
                = SwingFXUtils.fromFXImage(originalImage.getImage(), null);
        
        HistogramFilter.makeHistogram(image, chart);
        
    }
    
    @FXML
    private void handleHistogramStretching(ActionEvent event) {

        if (originalImage.getImage() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText("");
            alert.setContentText("Seleccione primero la opción Cargar Imagen y "
                    + "agregue una imagen.");

            alert.showAndWait();
            return;
        }
        BufferedImage image
                = SwingFXUtils.fromFXImage(originalImage.getImage(), null);
        
        HistogramFilter.histogramStretching(image, chart);
        originalImage.setImage(SwingFXUtils.toFXImage(image, null));
        
    }
    
    @FXML
    private void handleHistogramEqualization(ActionEvent event) {

        if (originalImage.getImage() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText("");
            alert.setContentText("Seleccione primero la opción Cargar Imagen y "
                    + "agregue una imagen.");

            alert.showAndWait();
            return;
        }
        BufferedImage image
                = SwingFXUtils.fromFXImage(originalImage.getImage(), null);
        
        HistogramFilter.histogramEqualization(image, chart);
        originalImage.setImage(SwingFXUtils.toFXImage(image, null));
        
    }

    @FXML
    private void handleLoadImage(ActionEvent event) throws IOException {

        chart.setVisible(false);
        File file
                = fileChooser.showOpenDialog(originalImage.getScene().getWindow());

        if (file != null) {

            String fileName = file.getName();
            String ext = fileName.substring(fileName.length() - 4);

            boolean matches = ext.matches(".jpg|.gif|jpeg|.png|.svg|.TTF");

            if (!matches) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Advertencia");
                alert.setHeaderText("");
                alert.setContentText("Problema al cargar la imagen. "
                        + "Intente de nuevo.");

                alert.showAndWait();
            } else {
                BufferedImage image = ImageIO.read(file);
                HistogramFilter.grayScale(image);
                originalImage.setImage(SwingFXUtils.toFXImage(image, null));                
            }

        }
    }

    @FXML
    private void handleSaveImage(ActionEvent event) {

        /*if (filteredImage.getImage() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText("");
            alert.setContentText("Seleccione primero la opción Cargar Imagen y "
                    + "aplique un filtro.");

            alert.showAndWait();
            return;
        }*/
        FileChooser fc = new FileChooser();
        fc.setTitle("Guardar Imagen");

        /*File file = fc.showSaveDialog(filteredImage.getScene().getWindow());
        if (file != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(filteredImage.getImage(),
                        null), "png", file);
            } catch (IOException ex) {
            }
        }*/
    }

       
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        chart.setCreateSymbols(false);
        chart.setStyle("CHART_COLOR_1: black;");
    }

}
