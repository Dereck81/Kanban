module pe.edu.utp.dsa.Kanban {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires org.apache.fontbox;
    requires org.apache.pdfbox;

    opens pe.edu.utp.dsa.Kanban to javafx.fxml;
    exports pe.edu.utp.dsa.Kanban;
    exports pe.edu.utp.dsa.Kanban.Controllers;
    exports pe.edu.utp.dsa.Kanban.Utilities;
    opens pe.edu.utp.dsa.Kanban.Controllers to javafx.fxml;
    exports pe.edu.utp.dsa.Kanban.Task;
    opens pe.edu.utp.dsa.Kanban.Task to javafx.fxml;
	exports pe.edu.utp.dsa.DSA;
}