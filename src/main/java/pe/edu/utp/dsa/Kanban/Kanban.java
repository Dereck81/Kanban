package pe.edu.utp.dsa.Kanban;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import pe.edu.utp.dsa.Kanban.Controllers.KanbanController;
import pe.edu.utp.dsa.Kanban.Utilities.GlobalExceptionHandler;
import pe.edu.utp.dsa.Kanban.Utilities.ResetSection;

import java.io.IOException;
import java.util.Objects;

public class Kanban extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Thread.setDefaultUncaughtExceptionHandler(new GlobalExceptionHandler());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Kanban.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1206, 615);
        KanbanController controller = fxmlLoader.getController();
        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            if(keyEvent.getCode().getName().equalsIgnoreCase("ESC")) {
                controller.deselectAllListCell();
                controller.resetForm(ResetSection.ALL);
            }
        });
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styles/styles.css")).toExternalForm());
        stage.setTitle("Kanban Application");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setOnCloseRequest(windowEvent -> controller.quit());
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
