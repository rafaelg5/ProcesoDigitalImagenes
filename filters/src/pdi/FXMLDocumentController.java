package pdi;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    private MenuItem loadImage;
    @FXML
    private MenuItem saveImage;
    @FXML
    private MenuItem grayScaleF;
    @FXML
    private MenuItem redF;
    @FXML
    private MenuItem greenF;
    @FXML
    private MenuItem blueF;
    @FXML
    private MenuItem randomF;
    @FXML
    private MenuItem brightnessF;
    @FXML
    private MenuItem highContrastF;
    @FXML
    private MenuItem mosaicF;
    @FXML
    private MenuItem blurF;
    @FXML
    private MenuItem mblurF;
    @FXML
    private MenuItem findEdgesF;
    @FXML
    private MenuItem sharpenF;
    @FXML
    private MenuItem embossF;
    @FXML
    private MenuItem inverseF;
    @FXML
    private MenuItem andyWF;
    @FXML
    private MenuItem customWatermarkF;
    @FXML
    private MenuItem oneLetterColorF;
    @FXML
    private MenuItem oneLetterGSF;
    @FXML
    private MenuItem manyLettersF;
    @FXML
    private MenuItem customTextF;
    @FXML
    private ImageView originalImage;
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
    private final FileChooser fileChooser = new FileChooser();

    @FXML
    private void handleLoadImage(ActionEvent event) throws IOException {
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
    private void handleSaveImage(ActionEvent event) {

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
        fc.setTitle("Guardar Imagen");

        File file = fc.showSaveDialog(filteredImage.getScene().getWindow());
        if (file != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(filteredImage.getImage(),
                        null), "png", file);
            } catch (IOException ex) {
            }
        }
    }

    @FXML
    private void handleGrayScale(ActionEvent event) {
        handleFilters(grayScaleF.getText());
    }

    @FXML
    private void handleRed(ActionEvent event) {
        handleFilters(redF.getText());
    }

    @FXML
    private void handleGreen(ActionEvent event) {
        handleFilters(greenF.getText());
    }

    @FXML
    private void handleBlue(ActionEvent event) {
        handleFilters(blueF.getText());
    }

    @FXML
    private void handleRandom(ActionEvent event) {
        handleFilters(randomF.getText());
    }

    @FXML
    private void handleBrightness(ActionEvent event) {
        handleFilters(brightnessF.getText());
    }

    @FXML
    private void handleHC(ActionEvent event) {
        handleFilters(highContrastF.getText());
    }

    @FXML
    private void handleMosaic(ActionEvent event) {
        handleFilters(mosaicF.getText());
    }

    @FXML
    private void handleBlur(ActionEvent event) {
        handleFilters(blurF.getText());
    }

    @FXML
    private void handleMBlur(ActionEvent event) {
        handleFilters(mblurF.getText());
    }

    @FXML
    private void handleFindEdges(ActionEvent event) {
        handleFilters(findEdgesF.getText());
    }

    @FXML
    private void handleSharpen(ActionEvent event) {
        handleFilters(sharpenF.getText());
    }

    @FXML
    private void handleEmboss(ActionEvent event) {
        handleFilters(embossF.getText());
    }

    @FXML
    private void handleInverse(ActionEvent event) {
        handleFilters(inverseF.getText());
    }

    @FXML
    private void handleAndyWarhol(ActionEvent event) {
        handleFilters(andyWF.getText());
    }

    @FXML
    private void handleWM(ActionEvent event) {
        handleFilters(customWatermarkF.getText());
    }

    @FXML
    private void handleOLC(ActionEvent event) {
        handleFilters(oneLetterColorF.getText());
    }

    @FXML
    private void handleOLGS(ActionEvent event) {
        handleFilters(oneLetterGSF.getText());
    }

    @FXML
    private void handleML(ActionEvent event) {
        handleFilters(manyLettersF.getText());
    }

    @FXML
    private void handleCustomText(ActionEvent event) {
        handleFilters(customTextF.getText());
    }

    private void handleFilters(String filterName) {

        if (originalImage.getImage() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText("");
            alert.setContentText("Seleccione primero la opción Cargar Imagen y "
                    + "agregue una imagen.");

            alert.showAndWait();
            return;
        }
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

        BufferedImage image
                = SwingFXUtils.fromFXImage(originalImage.getImage(), null);

        switch (filterName) {
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
            case "Marca de Agua":
                Filter.waterMark(image);
                break;
            case "Una Letra (Color)":

                String html = LetterFilter.oneLetterColor(image);
                saveHTMLDialog(html);
                return;
            case "Una Letra (Tonos de gris)":
                html = LetterFilter.oneLetterGrayScale(image);
                saveHTMLDialog(html);
                return;
            case "Varias Letras":
                html = LetterFilter.manyLetters(image);
                saveHTMLDialog(html);
                return;

            case "Texto":

                TextInputDialog text = new TextInputDialog("");
                text.setTitle("Definir texto");
                text.setContentText("Texto:");
                Optional<String> rtext = text.showAndWait();
                rtext.ifPresent(t -> {
                    String h = LetterFilter.customTextFilter(image, t);
                    saveHTMLDialog(h);
                });
                return;
        }

        Image filtered = SwingFXUtils.toFXImage(image, null);
        filteredImage.setImage(filtered);
        saveImage.setVisible(true);
    }

    private void saveHTMLDialog(String html) {
        TextInputDialog dialog = new TextInputDialog("img");
        dialog.setTitle("Guardar imagen");
        dialog.setContentText("Nombre de archivo:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(file -> {
            PrintWriter writer;
            try {
                writer = new PrintWriter("src/saved_images/" + file
                        + ".html", "UTF-8");
                writer.println(html);
                writer.close();
            } catch (Exception ex) {
            }
        });
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
