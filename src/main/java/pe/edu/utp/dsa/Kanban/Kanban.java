package pe.edu.utp.dsa.Kanban;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import pe.edu.utp.dsa.Kanban.Controllers.KanbanController;

import java.io.IOException;
import java.util.Objects;

public class Kanban extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Kanban.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1206, 615);
        KanbanController controller = fxmlLoader.getController();
        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            if(keyEvent.getCode().getName().equalsIgnoreCase("ESC")) {
                controller.deselectAllListCell();
                controller.resetForm();
            }
        });
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styles/styles.css")).toExternalForm());
        stage.setTitle("Kanban Application");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setOnCloseRequest(windowEvent -> Platform.exit());
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
