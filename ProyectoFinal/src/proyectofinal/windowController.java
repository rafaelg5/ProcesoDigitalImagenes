package proyectofinal;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

/**
 *
 * @author rgarcia
 */
public class windowController implements Initializable {

    @FXML
    private Button run;

    @FXML
    private Button index;

    @FXML
    private ImageView image;

    private FileChooser fileChooser;
    private PhotoMosaic pm;

    @FXML
    private void handleLoadImage(ActionEvent event) throws IOException {

        File file
                = fileChooser.showOpenDialog(image.getScene().getWindow());

        if (file != null) {

            String fileName = file.getName();
            String ext = fileName.substring(fileName.length() - 4);

            boolean matches = ext.matches(".jpg|.gif|jpeg|.png|.svg|.TTF|.PNG|.JPG|.bmp");

            if (!matches) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Advertencia");
                alert.setHeaderText("");
                alert.setContentText("Problema con la extensión de la imagen. "
                        + "Intente de nuevo.");

                alert.showAndWait();
            } else {
                BufferedImage img = ImageIO.read(file);
                image.setImage(SwingFXUtils.toFXImage(img, null));

                File imageIndex = new File("output/index.idx");
                if (!imageIndex.exists()) {
                    run.setVisible(false);
                    index.setVisible(true);
                } else if (imageIndex.exists()) {
                    index.setVisible(false);
                    run.setVisible(true);
                }
                pm = new PhotoMosaic(img);
            }

        }
    }

    @FXML
    private void handleRunAction(ActionEvent event) {
        try {
            pm.process();
        } catch (IOException ex) {
        }
    }

    @FXML
    private void handleIndexAction(ActionEvent event) {
        try {
            pm.makeImageIndex();
        } catch (IOException ex) {
        }
        index.setVisible(false);
        run.setVisible(true);
    }

    @FXML
    private void handleAboutAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Acerca de");
        alert.setHeaderText("");
        alert.setContentText("Para cargar una imagen: Archivo -> Cargar imagen"
                + " -> Seleccionar imagen.\nAl presionar el botón 'Procesar' se "
                + "generarán los archivos de salida en la carpeta: ouptut/");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fileChooser = new FileChooser();
    }

}
