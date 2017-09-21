package pdi;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;

/**
 *
 * @author rgarcia
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Button chooseButton;

    @FXML
    private ImageView originalImage;

    @FXML
    private Button filterButton;

    @FXML
    private ImageView filteredImage;

    @FXML
    private Slider brightnessSlider;

    @FXML
    private TextField mosaicW;

    @FXML
    private TextField mosaicH;

    @FXML
    private TextField colorR;

    @FXML
    private TextField colorG;

    @FXML
    private TextField colorB;

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private Label label3;

    @FXML
    private Label label4;

    @FXML
    private Button mosaicSend;

    @FXML
    private Button saveImage;

    private final FileChooser fileChooser = new FileChooser();

    @FXML
    private void handleChooseButton(ActionEvent event) throws IOException {

        brightnessSlider.setVisible(false);
        mosaicW.setVisible(false);
        mosaicH.setVisible(false);
        mosaicSend.setVisible(false);
        label1.setVisible(false);
        colorR.setVisible(false);
        colorG.setVisible(false);
        colorB.setVisible(false);
        label2.setVisible(false);
        label3.setVisible(false);
        label4.setVisible(false);

        File file = fileChooser.showOpenDialog(chooseButton.getScene().getWindow());

        if (file != null) {

            String fileName = file.getName();
            String ext = fileName.substring(fileName.length() - 4);

            boolean matches = ext.matches(".jpg|.gif|jpeg|.png|.svg");
            
            if (!matches) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Advertencia");
                alert.setHeaderText("");
                alert.setContentText("Problema al cargar la imagen. "
                        + "Intente de nuevo.");

                alert.showAndWait();
            } else {
                
                /* Esta opción no funciona afuera de Netbeans:
                 * Image image = new Image(file.toURI().toString());
                 */
                BufferedImage image = ImageIO.read(file);
                originalImage.setImage(SwingFXUtils.toFXImage(image, null));
                if (filteredImage.getImage() != null) {
                    filteredImage.setImage(null);
                    saveImage.setVisible(false);
                }
            }

        }
    }

    @FXML
    private void handleSaveButton(ActionEvent event) {

        if (filteredImage.getImage() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText("");
            alert.setContentText("Seleccione primero la opción Cargar Imagen y "
                    + "aplique un filtro.");

            alert.showAndWait();
            return;
        }

        FileChooser fc = new FileChooser();
        fc.setTitle("Guardar Image");

        File file = fc.showSaveDialog(saveImage.getScene().getWindow());
        if (file != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(filteredImage.getImage(),
                        null), "png", file);
            } catch (IOException ex) {
            }
        }
    }

    @FXML
    private void handleFilterButton(ActionEvent event) {

        if (originalImage.getImage() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText("");
            alert.setContentText("Seleccione primero la opción Cargar Imagen y "
                    + "agregue una imagen.");

            alert.showAndWait();
        } else {

            Alert choose = new Alert(Alert.AlertType.CONFIRMATION);
            choose.setTitle("Seleccionar filtro");
            choose.setHeaderText("");
            choose.setContentText("");
            ObservableList<String> options
                    = FXCollections.observableArrayList(
                            "Tonos de gris",
                            "Rojo",
                            "Verde",
                            "Azul",
                            "Brillo",
                            "Aleatorio",
                            "Alto Contraste",
                            "Mosaico",
                            "Blur",
                            "Motion Blur",
                            "Encontrar Bordes",
                            "Sharpen",
                            "Emboss",
                            "Inverso",
                            "Micas de Color"
                    );
            final ComboBox<String> comboBox =
		new ComboBox<String>(options);
            comboBox.getSelectionModel().selectFirst();
            choose.setGraphic(comboBox);

            choose.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    brightnessSlider.setVisible(false);
                    mosaicW.setVisible(false);
                    mosaicH.setVisible(false);
                    mosaicSend.setVisible(false);
                    label1.setVisible(false);
                    colorR.setVisible(false);
                    colorG.setVisible(false);
                    colorB.setVisible(false);
                    label2.setVisible(false);
                    label3.setVisible(false);
                    label4.setVisible(false);

                    String option = comboBox.getSelectionModel().getSelectedItem().toString();

                    BufferedImage image = SwingFXUtils.fromFXImage(originalImage.getImage(),
                            null);
                    switch (option) {
                        case "Tonos de gris":
                            Filter.grayScaleFilter(image);
                            break;
                        case "Rojo":
                            Filter.redFilter(image);
                            break;
                        case "Verde":
                            Filter.greenFilter(image);
                            break;
                        case "Azul":
                            Filter.blueFilter(image);
                            break;
                        case "Aleatorio":
                            Filter.randomFilter(image);
                            break;
                        case "Brillo":
                            brightnessSlider.setVisible(true);
                            brightnessSlider.setValue(0);
                            if (filteredImage.getImage() != null) {
                                filteredImage.setImage(null);
                            }
                            saveImage.setVisible(false);
                            return;
                        case "Alto Contraste":
                            Filter.highContrast(image);
                            break;
                        case "Mosaico":
                            mosaicW.setVisible(true);
                            mosaicH.setVisible(true);
                            mosaicSend.setVisible(true);
                            label1.setVisible(true);
                            if (filteredImage.getImage() != null) {
                                filteredImage.setImage(null);
                            }
                            saveImage.setVisible(false);
                            return;
                        case "Blur":
                            Filter.blur(image);
                            break;
                        case "Motion Blur":
                            Filter.motionBlur(image);
                            break;
                        case "Encontrar Bordes":
                            Filter.findEdges(image);
                            break;
                        case "Sharpen":
                            Filter.sharpen(image);
                            break;
                        case "Emboss":
                            Filter.emboss(image);
                            break;
                        case "Inverso":
                            Filter.inverse(image);
                            break;
                        case "Micas de Color":
                            colorR.setVisible(true);
                            colorG.setVisible(true);
                            colorB.setVisible(true);
                            mosaicSend.setVisible(true);
                            label2.setVisible(true);
                            label3.setVisible(true);
                            label4.setVisible(true);
                            if (filteredImage.getImage() != null) {
                                filteredImage.setImage(null);
                            }
                            saveImage.setVisible(false);
                            return;
                    }

                    Image filtered = SwingFXUtils.toFXImage(image, null);
                    filteredImage.setImage(filtered);
                    saveImage.setVisible(true);
                }
            });
        }
    }

    @FXML
    private void handleMosaicSend(ActionEvent event) {
        saveImage.setVisible(false);
        BufferedImage image = SwingFXUtils.fromFXImage(originalImage.getImage(),
                null);

        if (mosaicW.isVisible()) {

            if (!isNumeric(mosaicW.getText()) || !isNumeric(mosaicH.getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Advertencia");
                alert.setHeaderText("");
                alert.setContentText("Valores para tamaño del mosaico inválidos");
                alert.showAndWait();
                return;
            }
            int w = Integer.parseInt(mosaicW.getText());
            int h = Integer.parseInt(mosaicH.getText());

            Filter.mosaic(w, h, image);
        } else if (colorR.isVisible()) {

            if (!isNumeric(colorR.getText()) || !isNumeric(colorG.getText())
                    || !isNumeric(colorB.getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Advertencia");
                alert.setHeaderText("");
                alert.setContentText("Valores para el color inválidos");
                alert.showAndWait();
                return;
            }
            int r = Integer.parseInt(colorR.getText());
            int g = Integer.parseInt(colorG.getText());
            int b = Integer.parseInt(colorB.getText());
            if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Advertencia");
                alert.setHeaderText("");
                alert.setContentText("Valores para el color fuera de rango");
                alert.showAndWait();
                return;
            }

            Filter.warhol(image, r, g, b);
        }

        Image filtered = SwingFXUtils.toFXImage(image, null);
        filteredImage.setImage(filtered);
        saveImage.setVisible(true);
    }

    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fileChooser.setTitle("Cargar Imagen");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        brightnessSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {

                BufferedImage image = SwingFXUtils.fromFXImage(originalImage.getImage(),
                        null);

                Filter.brightnessFilter(image,
                        brightnessSlider.valueProperty().getValue().intValue());

                Image filtered = SwingFXUtils.toFXImage(image, null);
                filteredImage.setImage(filtered);
                saveImage.setVisible(true);
            }
        });
    }

}
