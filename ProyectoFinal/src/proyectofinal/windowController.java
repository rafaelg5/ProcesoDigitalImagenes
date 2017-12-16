package proyectofinal;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private ImageView image;

    private FileChooser fileChooser;

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
                run.setVisible(true);
            }

        }
    }

    @FXML
    private void handleRunAction(ActionEvent event) {
        BufferedImage img
                = SwingFXUtils.fromFXImage(image.getImage(), null);
        PhotoMosaic pm = new PhotoMosaic(img);
        try {
            pm.process();
        } catch (IOException ex) {}
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
