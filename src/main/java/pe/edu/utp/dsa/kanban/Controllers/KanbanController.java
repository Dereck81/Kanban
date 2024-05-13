package pe.edu.utp.dsa.kanban.Controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pe.edu.utp.dsa.kanban.ListView.ListCell;
import pe.edu.utp.dsa.kanban.Task.KanbanTask;
import pe.edu.utp.dsa.kanban.Utilities.PriorityQueue;
import pe.edu.utp.dsa.kanban.Utilities.Role;
import pe.edu.utp.dsa.kanban.Utilities.User;
import pe.edu.utp.dsa.kanban.Utilities.Utilities;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

public class KanbanController {

    //FXML
    @FXML
    private ListView<KanbanTask> listViewCatalogue = new ListView<>();
    @FXML
    private ListView<KanbanTask> listViewToDo = new ListView<>();
    @FXML
    private ListView<KanbanTask> listViewInProgress = new ListView<>();
    @FXML
    private ListView<KanbanTask> listViewToBeChecked = new ListView<>();
    @FXML
    private ListView<KanbanTask> listViewFinished = new ListView<>();
    @FXML
    private ListView<User> listViewUsers = new ListView<>();
    @FXML
    private ListView<Role> listViewRoles = new ListView<>();
    @FXML
    private TitledPane titledPaneProjectCreator;
    @FXML
    private TitledPane titledPaneTaskOthers;
    @FXML
    private AnchorPane anchorPaneAddTask, anchorPaneTaskInfo, anchorPaneEditTask;
    @FXML
    private TextField textFieldTaskName, textFieldFinishedDate;
    @FXML
    private MenuButton menuButtonPriority, menuButtonState, menuButtonAsgUsr;
    @FXML
    private Menu menuOpenRecentFile;
    @FXML
    private TextField usernameAddUser;

    //Others
    private ContextMenu contextMenuCatalogue = new ContextMenu();
    private ContextMenu contextMenuOthersColumns = new ContextMenu();
    private FileChooser fileChooserOpen, fileChooserExportAsPDF;
    private File file;

    //Others
    private boolean isDeselectionByUser = true;
    private PriorityQueue<KanbanTask> queueCatalogue = new PriorityQueue<>();
    private PriorityQueue<KanbanTask> queueToDo = new PriorityQueue<>();
    private PriorityQueue<KanbanTask> queueInProgres = new PriorityQueue<>();
    private PriorityQueue<KanbanTask> queueToBeChecked = new PriorityQueue<>();
    private PriorityQueue<KanbanTask> queueFinished = new PriorityQueue<>();
    private PriorityQueue<User> queueUser = new PriorityQueue<>();
    private PriorityQueue<Role> queueRole = new PriorityQueue<>();

    private String ApplicationTitle = "Kanban Application - ";

    @FXML
    public void initialize(){

        titledPaneTaskOthers.expandedProperty().addListener(observable -> {
            // falta eliminar los datos ingresados (resetear)
            if(titledPaneTaskOthers.isCollapsible())
                anchorPaneAddTask.setVisible(false);
        });
        loadDifferentFileChooser();
        updateRecentFiles();
        configListView();
        setContextMenuConfig();
        setOnEventOnListView();
    }

    private void configListView(){
        listViewCatalogue.setCellFactory(param -> new ListCell<>());
        listViewToDo.setCellFactory(param -> new ListCell<>());
        listViewInProgress.setCellFactory(param -> new ListCell<>());
        listViewToBeChecked.setCellFactory(param -> new ListCell<>());
        listViewFinished.setCellFactory(param -> new ListCell<>());
        listViewUsers.setCellFactory(param -> new ListCell<>());
        listViewRoles.setCellFactory(param -> new ListCell<>());
    }


    private void loadDifferentFileChooser(){
        // fileChooserOpen
        fileChooserOpen = new FileChooser();
        fileChooserOpen.setTitle("Find File");
        fileChooserOpen.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML", "*.xml")
        );
        // fileChooserExport
        fileChooserExportAsPDF = new FileChooser();
        fileChooserExportAsPDF.setTitle("Export As PDF");
        fileChooserExportAsPDF.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF", "*.pdf")
        );
    }

    private void setContextMenuConfig(){
        MenuItem menuItemDelete = new MenuItem("Delete Task");
        menuItemDelete.setOnAction(actionEvent -> {});
        MenuItem menuItemEdit = new MenuItem("Edit task");
        menuItemEdit.setOnAction(actionEvent -> {});
        MenuItem menuItemBack = new MenuItem("Back column");
        menuItemBack.setOnAction(actionEvent -> {});
        MenuItem menuItemNext = new MenuItem("Next column");
        menuItemNext.setOnAction(actionEvent -> {
        });
        contextMenuCatalogue.getItems().addAll(menuItemDelete, menuItemEdit);
        contextMenuOthersColumns.getItems().addAll(menuItemBack, menuItemNext);
    }

    public void setOnEventOnListView(){
        /*
        A flag is used to prevent this from triggering an infinite loop,
        since deactivating the selection of the listView causes the established
        addlistener to be re-executed, causing an error at run time.
         */
        listViewCatalogue.setContextMenu(contextMenuCatalogue);
        listViewCatalogue.setOnContextMenuRequested(contextMenuEvent -> {
            boolean flag = listViewCatalogue.getSelectionModel().getSelectedItems().isEmpty();
            if(flag) contextMenuCatalogue.hide();
        });
        listViewCatalogue.getSelectionModel()
                .selectedIndexProperty()
                .addListener(observable -> {
                    if(!isDeselectionByUser) return;
                    deselectListCell(
                            listViewToDo,
                            listViewInProgress,
                            listViewToBeChecked,
                            listViewFinished);
                });
        //listViewToDo
        listViewToDo.setContextMenu(contextMenuOthersColumns);
        listViewToDo.setOnContextMenuRequested(contextMenuEvent -> {
            boolean flag = listViewToDo.getSelectionModel().getSelectedItems().isEmpty();
            if(flag) contextMenuOthersColumns.hide();
        });
        listViewToDo.getSelectionModel()
                .selectedIndexProperty()
                .addListener((observable -> {
                    if(!isDeselectionByUser) return;
                    deselectListCell(
                            listViewCatalogue,
                            listViewToBeChecked,
                            listViewInProgress,
                            listViewFinished
                    );
                }));
        //listViewInProgress
        listViewInProgress.setContextMenu(contextMenuOthersColumns);
        listViewInProgress.setOnContextMenuRequested(contextMenuEvent -> {
            boolean flag = listViewInProgress.getSelectionModel().getSelectedItems().isEmpty();
            if(flag) contextMenuOthersColumns.hide();

        });
        listViewInProgress.getSelectionModel()
                .selectedIndexProperty()
                .addListener(observable -> {
                    if(!isDeselectionByUser) return;
                    deselectListCell(
                            listViewCatalogue,
                            listViewToBeChecked,
                            listViewToDo,
                            listViewFinished
                    );
        });
        //listViewToBeChecked
        listViewToBeChecked.setContextMenu(contextMenuOthersColumns);
        listViewToBeChecked.getSelectionModel()
                .selectedIndexProperty()
                .addListener(observable -> {
                    if(!isDeselectionByUser) return;
                    deselectListCell(
                            listViewToDo,
                            listViewCatalogue,
                            listViewFinished,
                            listViewInProgress
                    );
        });
        //listViewFinished
        listViewFinished.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable -> {
                    if(!isDeselectionByUser) return;
                    deselectListCell(
                            listViewCatalogue,
                            listViewToDo,
                            listViewInProgress,
                            listViewToBeChecked,
                            listViewFinished
                    );
                }));
        //
    }

    @SafeVarargs
    public final void deselectListCell(ListView<KanbanTask>...lists){
        isDeselectionByUser = false;
        for (int i = 0; i < lists.length; i++) {
            boolean flag = lists[i].getSelectionModel().getSelectedItems().isEmpty();
            if (!flag) lists[i].getSelectionModel().clearSelection();
        }
        isDeselectionByUser = true;
    }

    public void deselectAllListCell(){
        listViewCatalogue.getSelectionModel().clearSelection();
        listViewToDo.getSelectionModel().clearSelection();
        listViewInProgress.getSelectionModel().clearSelection();
        listViewToBeChecked.getSelectionModel().clearSelection();
        listViewFinished.getSelectionModel().clearSelection();
    }

    @FXML
    private void executeTitledPaneTask(){
        titledPaneTaskOthers.setExpanded(true);
        anchorPaneAddTask.setVisible(true);
    }

    @FXML
    private void addRole(){


    }

    @FXML
    private void editRole(){

    }

    @FXML
    private void editUser(){

    }

    @FXML
    private void editTask(){

    }

    @FXML
    private void taskInfo(){

    }

    @FXML
    private void addUser() throws Exception {
        if(usernameAddUser.getText().isEmpty()) {
            Utilities.alert(
                    "Error",
                    "Error when adding a user",
                    "Username is blank",
                    Alert.AlertType.ERROR
            );
            return;
        }
        queueUser.add(new User(usernameAddUser.getText(), "Jefe"));
        listViewUsers.getItems().setAll(queueUser.toList());
    }

    @FXML
    private void addTask() throws Exception {
        try {
            queueCatalogue.add(new KanbanTask("Diego Maricon", "Diego Maricon", "asd", 2, 3, LocalDate.now()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        listViewCatalogue.getItems().setAll(queueCatalogue.toList());;
    }

    private void showAnchorPaneAddTask(){

    }


    @FXML
    private void openFile(){
        file = fileChooserOpen.showOpenDialog(null);
        if (file == null) return;
        Utilities.writeRecordRecentFiles(file.getPath());
        updateRecentFiles();
        getStage().setTitle(ApplicationTitle+file.getName());
    }

    @FXML
    private void saveFile(){

    }

    @FXML
    private void quit(){
        Platform.exit();
    }

    @FXML
    private void closeFile(){

    }

    @FXML
    private void exportAsPDF(){
        file = fileChooserExportAsPDF.showSaveDialog(null);
        if(file == null) return;

    }

    /**
     * Opens a recently accessed file based on the selected menu item.
     * @param pathRecentFile The path of the recently accessed file.
     */
    private void openRecentFile(String pathRecentFile){
        file = new File(pathRecentFile);
        if (!file.exists()){
            Utilities.alert(
                    "Error",
                    "An Error ocurred",
                    "file not found: "+pathRecentFile,
                    Alert.AlertType.ERROR
            );
            Utilities.deleteRecentFilesRecord(pathRecentFile);
            updateRecentFiles();
            return;
        }
        getStage().setTitle(ApplicationTitle+file.getName());
    }

    /**
     * Updates the list of recent files in the menu.
     */
    private void updateRecentFiles(){
        ArrayList<String> recentFiles = Utilities.readRecordRecentFiles();
        if (menuOpenRecentFile != null) menuOpenRecentFile.getItems().clear();
        for (String pathRecentFile : recentFiles.toArray(new String[0])){
            String[] arraySplit = pathRecentFile.split("[/\\\\\\\\]");
            String nameFile = arraySplit[arraySplit.length - 1];
            MenuItem menuItemRecentFile = new MenuItem(nameFile);
            menuItemRecentFile.setOnAction(event -> {
                openRecentFile(pathRecentFile);
            });
            menuOpenRecentFile.getItems().add(menuItemRecentFile);
        }
    }

    @FXML
    public void init(){
        listViewRoles.getItems().add(new Role("Administracion"));
        listViewUsers.getItems().add(new User("Kevin Huanca Fernández", "estudiante d jorgito uwu"));
        listViewToDo.getItems().addAll(new KanbanTask("Diego Maricon", "Diego Maricon", "asd", 2, 3, LocalDate.now()));
        listViewCatalogue.getItems().addAll(new KanbanTask("Diego Maricon", "Diego Maricon", "asd", 2, 3, LocalDate.now()));
    }

    private Stage getStage(){
        return ((Stage) titledPaneProjectCreator.getScene().getWindow());
    }


}