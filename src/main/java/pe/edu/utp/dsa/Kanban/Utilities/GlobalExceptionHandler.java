package pe.edu.utp.dsa.Kanban.Utilities;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.concurrent.atomic.AtomicReference;

public class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        /*
           Here a loop was placed that will go through all the wrappers
           of the original cause, this is mostly because when an exception
           is raised in the application, it wraps that exception in several
           exceptions like InvocationTargetException and Runtime, and in order
           to get the final cause, we would have to go through all the causes
           to get to the original.
       */
        Throwable e1 = e;
        Throwable initialCause = e.getCause();
        Throwable finalCause;
        while (initialCause!=null){
            e1 = initialCause;
            initialCause = initialCause.getCause();
        }
        finalCause = e1;

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error has occurred");
            alert.setContentText(finalCause.getMessage());
            alert.showAndWait();
        });
    }

    /**
     *  Displays an alert in the user interface.
     * @param title      title of the alert
     * @param header     header of the alert
     * @param contextText body text of the alert
     */
    public static void alertInformation(String title, String header, String contextText){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(contextText);
        alert.showAndWait();
    }

    public static void alertWarning(String title, String header, String contextText){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(contextText);
        alert.showAndWait();
    }

    public static ConfirmationOptions alertConfirmation(String title, String header, String contextText){
        ButtonType yes = new ButtonType("Yes"); // true
        ButtonType no = new ButtonType("No"); // false
        ButtonType cancel = new ButtonType("Cancel"); // null
        AtomicReference<ConfirmationOptions> confirmationOption = new AtomicReference<>(ConfirmationOptions.YES);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(contextText);
        alert.getButtonTypes().setAll(yes, no, cancel);
        alert.showAndWait().ifPresent(action -> {
            if(action == yes)
                confirmationOption.set(ConfirmationOptions.YES);
            else if (action == no)
                confirmationOption.set(ConfirmationOptions.NO);
            else
                confirmationOption.set(ConfirmationOptions.CANCEL);
        });
        return confirmationOption.get();
    }

}
