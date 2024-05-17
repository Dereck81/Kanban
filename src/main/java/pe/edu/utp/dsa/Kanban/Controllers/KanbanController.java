package pe.edu.utp.dsa.Kanban.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pe.edu.utp.dsa.Kanban.ListView.ListCell;
import pe.edu.utp.dsa.Kanban.Task.KanbanTask;
import pe.edu.utp.dsa.DSA.PriorityQueue;
import pe.edu.utp.dsa.Kanban.Task.Role;
import pe.edu.utp.dsa.Kanban.Task.User;
import pe.edu.utp.dsa.Kanban.Utilities.Utilities;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

import static pe.edu.utp.dsa.Kanban.Utilities.Utilities.truncateString;

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
    private TextField usernameAddUser, textFieldRole;
    @FXML
    private TextArea textAreaDescription;
    @FXML
    private MenuButton menuButtonRole;
    @FXML
    private Label labelTaskName, labelTaskNumber, labelPriority;
    @FXML
    private Label labelState, labelCreationDate, labelFinishDate;
    @FXML
    private Label labelAssignedUser;
    @FXML
    private TextArea textAreaDescription_info;

    //Others
    private ContextMenu contextMenuCatalogue = new ContextMenu();
    private ContextMenu contextMenuOthersColumns = new ContextMenu();
    private ContextMenu contextMenuFinished = new ContextMenu();
    private FileChooser fileChooserOpen, fileChooserExportAsPDF;
    private File file;

    //Others

    private ListView<KanbanTask> listViewSelected = null;
    private PriorityQueue<KanbanTask> linkedListSelected = null;
    private PriorityQueue<KanbanTask> linkedListPrevious = null;
    private PriorityQueue<KanbanTask> linkedListNext = null;
    private PriorityQueue<KanbanTask> linkedListCatalogue = new PriorityQueue<>();
    private PriorityQueue<KanbanTask> linkedListToDo = new PriorityQueue<>();
    private PriorityQueue<KanbanTask> linkedListInProgress = new PriorityQueue<>();
    private PriorityQueue<KanbanTask> linkedListToBeChecked = new PriorityQueue<>();
    private PriorityQueue<KanbanTask> linkedListFinished = new PriorityQueue<>();
    private PriorityQueue<User> linkedListUser = new PriorityQueue<>();
    private PriorityQueue<Role> linkedListRole = new PriorityQueue<>();
    private String ApplicationTitle = "Kanban Application - ";

    //Others
    private boolean isDeselectionByUser = true;
    private Role selectedRole = null;

    @FXML
    public void initialize(){
        setupFileChoosers();
        updateRecentFiles();
        setupListViews();
        setupContextMenus();
        setOnEventOnListView();
        setupTextAreas();
    }

    // Open File
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
            //use .substring
            String[] arraySplit = pathRecentFile.split("[/\\\\\\\\]");
            String nameFile = arraySplit[arraySplit.length - 1];
            MenuItem menuItemRecentFile = new MenuItem(nameFile);
            menuItemRecentFile.setOnAction(event -> {
                openRecentFile(pathRecentFile);
            });
            menuOpenRecentFile.getItems().add(menuItemRecentFile);
        }
    }


    // SETUPS
    private void setupListViews(){
        listViewCatalogue.setCellFactory(param -> new ListCell<>());
        listViewToDo.setCellFactory(param -> new ListCell<>());
        listViewInProgress.setCellFactory(param -> new ListCell<>());
        listViewToBeChecked.setCellFactory(param -> new ListCell<>());
        listViewFinished.setCellFactory(param -> new ListCell<>());
        listViewUsers.setCellFactory(param -> new ListCell<>());
        listViewRoles.setCellFactory(param -> new ListCell<>());
    }

    private void setupTextAreas(){
        textAreaDescription.setWrapText(true);
        textAreaDescription_info.setWrapText(true);
    }

    private void setupTitlePanes(){
        titledPaneTaskOthers.expandedProperty().addListener(observable -> {
            // falta eliminar los datos ingresados (resetear)
            if(titledPaneTaskOthers.isCollapsible())
                anchorPaneAddTask.setVisible(false);
        });
    }

    private void setupContextMenus(){
        MenuItem menuItemDelete0 = new MenuItem("Delete Task");
        menuItemDelete0.setOnAction(actionEvent -> {
            // Modificar cuando en la linked list se añada
            // restriccion para elementos unicos
            int index = listViewSelected.getSelectionModel().getSelectedIndex();
            linkedListSelected.removeItemAtIndex(index);
            resetForm();
            updateListView();
        });

        MenuItem menuItemDelete1 = new MenuItem("Delete Task");
        menuItemDelete1.setOnAction(actionEvent -> {
            // Modificar cuando en la linked list se añada
            // restriccion para elementos unicos
            int index = listViewSelected.getSelectionModel().getSelectedIndex();
            linkedListSelected.removeItemAtIndex(index);
            resetForm();
            updateListView();
        });

        MenuItem menuItemEdit0 = new MenuItem("Edit task");
        menuItemEdit0.setOnAction(actionEvent -> {
            titledPaneTaskOthers.setExpanded(true);
            anchorPaneEditTask.setVisible(true);
            resetForm();
        });

        MenuItem menuItemEdit1 = new MenuItem("Edit task");
        menuItemEdit1.setOnAction(actionEvent -> {
            titledPaneTaskOthers.setExpanded(true);
            anchorPaneEditTask.setVisible(true);
            resetForm();
        });

        MenuItem menuItemBack = new MenuItem("Back column");
        menuItemBack.setOnAction(actionEvent -> {
            int index = listViewSelected.getSelectionModel().getSelectedIndex();
            try{
                KanbanTask ktb = linkedListSelected.getElement(index);
                linkedListSelected.removeItemAtIndex(index);
                linkedListPrevious.add(ktb);
            }catch (Exception ignored){}
            updateListView();
        });
        MenuItem menuItemNext = new MenuItem("Next column");
        menuItemNext.setOnAction(actionEvent -> {
            int index = listViewSelected.getSelectionModel().getSelectedIndex();
            try {
                KanbanTask ktb = linkedListSelected.getElement(index);
                linkedListSelected.removeItemAtIndex(index);
                linkedListNext.add(ktb);
            }catch (Exception ignored){}
            updateListView();
        });
        contextMenuCatalogue.getItems().setAll(menuItemDelete0, menuItemEdit0);
        contextMenuOthersColumns.getItems().setAll(menuItemEdit1, menuItemBack, menuItemNext);
        contextMenuFinished.getItems().setAll(menuItemDelete1);

    }

    private void setupFileChoosers(){
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
                .addListener((observable, oldvalue, newvalue) -> {
                    if(!isDeselectionByUser) return;
                    deselectListCell(
                            listViewToDo,
                            listViewInProgress,
                            listViewToBeChecked,
                            listViewFinished);
                    listViewSelected = listViewCatalogue;
                    linkedListSelected = linkedListCatalogue;
                    linkedListPrevious = null;
                    linkedListNext = linkedListToDo;
                    taskInfo();

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
                    listViewSelected = listViewToDo;
                    linkedListSelected = linkedListToDo;

                    linkedListPrevious = linkedListCatalogue;
                    linkedListNext = linkedListInProgress;
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
                    listViewSelected = listViewInProgress;
                    linkedListSelected = linkedListInProgress;

                    linkedListPrevious = linkedListToDo;
                    linkedListNext = linkedListToBeChecked;
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
                    listViewSelected = listViewToBeChecked;
                    linkedListSelected = linkedListToBeChecked;

                    linkedListPrevious = linkedListInProgress;
                    linkedListNext = linkedListFinished;

        });
        //listViewFinished
        listViewFinished.setContextMenu(contextMenuFinished);
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
                    listViewSelected = listViewFinished;
                    linkedListSelected = linkedListFinished;
                    linkedListPrevious = null;
                    linkedListNext = null;
                }));
        //
    }

    @SafeVarargs
    public final void deselectListCell(ListView<KanbanTask>...lists){
        isDeselectionByUser = false;
        for (ListView<KanbanTask> list : lists) {
            boolean flag = list.getSelectionModel().getSelectedItems().isEmpty();
            if (!flag) list.getSelectionModel().clearSelection();
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
        anchorPaneEditTask.setVisible(false);
        anchorPaneTaskInfo.setVisible(false);
        titledPaneTaskOthers.setExpanded(true);
        anchorPaneAddTask.setVisible(true);
    }

    @FXML
    private void addRole() throws Exception {
        String textRole = textFieldRole.getText();
        if(textRole.isEmpty() || textRole.isBlank()) {
            Utilities.alert(
                    "Error",
                    "Error adding role",
                    "The role name is empty.",
                    Alert.AlertType.ERROR
            );
            return;
        }
        linkedListRole.add(new Role(textRole));
        updateListView(listViewRoles, linkedListRole);
        updateMenuButtonRole();
        textFieldRole.clear();
    }

    @FXML
    private void editRole(){

    }

    @FXML
    private void editUser(){

    }

    @FXML
    private void editTask(){
        KanbanTask ktb = listViewSelected.getSelectionModel().getSelectedItem();

    }

    @FXML
    private void taskInfo(){
        int index = listViewSelected.getSelectionModel().getSelectedIndex();
        KanbanTask task;
        resetForm();
        try {
            task = linkedListSelected.getElement(index);
        } catch (Exception ignore) {
            return;
        }
        anchorPaneAddTask.setVisible(false);
        anchorPaneEditTask.setVisible(false);
        titledPaneTaskOthers.setExpanded(true);
        anchorPaneTaskInfo.setVisible(true);
        labelTaskName.setText(truncateString(task.getName(), 30));
        labelTaskNumber.setText(String.valueOf(task.getNumberTask()));
        labelPriority.setText(String.valueOf(task.getPriority()));
        labelCreationDate.setText(task.getRegistrationDate().toString());
        labelFinishDate.setText(task.getFinishDate().toString());
        labelAssignedUser.setText(
                truncateString(task.getuserAssignedToTheTask(), 28)
        );
        textAreaDescription_info.setText(task.getDescription());
    }

    public void resetForm(){
        //task info
        labelTaskName.setText("None");
        labelTaskNumber.setText("None");
        labelPriority.setText("None");
        labelCreationDate.setText("None");
        labelFinishDate.setText("None");
        labelAssignedUser.setText("None");
        textAreaDescription_info.setText("");
        //add user
        textFieldTaskName.clear();
        textFieldFinishedDate.clear();
        textAreaDescription.clear();
        menuButtonPriority.setText("Select...");
        //priority


    }

    @FXML
    private void addUser() throws Exception {
        if(usernameAddUser.getText().isEmpty() || selectedRole == null) {
            Utilities.alert(
                    "Error",
                    "Error when adding a user",
                    "Username is blank",
                    Alert.AlertType.ERROR
            );
            return;
        }
        linkedListUser.add(new User(usernameAddUser.getText(), selectedRole));
        listViewUsers.getItems().setAll(linkedListUser.toList());
        updateListView(listViewUsers, linkedListUser);
        menuButtonRole.setText("");
        usernameAddUser.clear();
        selectedRole = null;
    }

    @FXML
    private void addTask() throws Exception {


        try {
            linkedListCatalogue.add(new KanbanTask("Golas","Diego Ochoa", "Diego Ochoa", "asd", 2, 3, "Prueba", LocalDate.now()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        listViewCatalogue.getItems().setAll(linkedListCatalogue.toList());;
        anchorPaneAddTask.setVisible(false);
        titledPaneTaskOthers.setExpanded(false);
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

    private void updateListViews(){
        listViewCatalogue.getItems().setAll(linkedListCatalogue.toList());
        listViewToDo.getItems().setAll(linkedListToDo.toList());
        listViewToBeChecked.getItems().setAll(linkedListToBeChecked.toList());
        listViewInProgress.getItems().setAll(linkedListInProgress.toList());
        listViewFinished.getItems().setAll(linkedListFinished.toList());
    }

    private void updateListView(){
        listViewSelected.getItems().setAll(linkedListSelected.toList());
    }

    private void updateMenuButtonRole(){
        menuButtonRole.getItems().clear();
        for (Role role: linkedListRole){
            MenuItem menuItem = new MenuItem(role.getRolName());
            menuItem.setOnAction(actionEvent -> {
                menuButtonRole.setText(role.getRolName());
                selectedRole = role;
            });
            menuButtonRole.getItems().add(menuItem);
        }
        selectedRole = null;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void updateListView(ListView listView, PriorityQueue linkedList){
        listView.getItems().setAll(linkedList.toList());
    }

    @FXML
    public void init(){
        listViewUsers.getItems().add(new User("Kevin Huanca Fernández", new Role("estudiante")));
        listViewToDo.getItems().addAll(new KanbanTask("sdsd", "Diego Ochoa asdasdasd asd asdasd assd", "Diego Ochoa asdasd sad asd sa sd sa d a sd", "asd", 2, 3, "Hola esto es prueba", LocalDate.now()));
        listViewCatalogue.getItems().addAll(new KanbanTask("asdasd","1234567891234568912345678", "Diego Ochoa", "asd", 2, 5, "Gola asddasd ",LocalDate.now()));
        listViewCatalogue.getItems().addAll(new KanbanTask("asdsad","Diego Ochoa", "Diego Ochoa", "asd", 2, 1, "Gola asddasd ",LocalDate.now()));
    }

    private Stage getStage(){
        return ((Stage) titledPaneProjectCreator.getScene().getWindow());
    }


}