package sample;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Controller {

    @FXML
    private Canvas canvas;

    @FXML
    private CheckBox eraser;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Slider brushSize;

    private Parent root;
    private Stage stage;
    private Scene scene;

    public void login(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("paint.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void initialize() {
        try {
            GraphicsContext g = canvas.getGraphicsContext2D();

            canvas.setOnMouseDragged(e -> {
                double size = brushSize.getValue();
                double x = e.getX() - size / 2;
                double y = e.getY() - size / 2;

                if(eraser.isSelected())
                {
                    g.clearRect(x, y, size, size);
                } else {
                    g.setFill(colorPicker.getValue());
                    g.fillOval(x, y, size, size);
                }
            });
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void onSave() {
        try {
            Image snapshot = canvas.snapshot(null,null);

            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", new File("paint.png"));
        } catch (Exception e) {
            System.out.println("Failed to save image : " + e);
        }
    }

    public void onExit() {
        Platform.exit();
    }
}
