module pe.edu.utp.dsa.kanban {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens pe.edu.utp.dsa.kanban to javafx.fxml;
    exports pe.edu.utp.dsa.kanban;
    exports pe.edu.utp.dsa.kanban.Controllers;
    opens pe.edu.utp.dsa.kanban.Controllers to javafx.fxml;
    exports pe.edu.utp.dsa.kanban.Task;
    opens pe.edu.utp.dsa.kanban.Task to javafx.fxml;
}