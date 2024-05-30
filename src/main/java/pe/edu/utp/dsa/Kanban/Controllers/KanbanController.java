package pe.edu.utp.dsa.Kanban.Controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pe.edu.utp.dsa.Kanban.ListView.ListCell;
import pe.edu.utp.dsa.Kanban.Task.KanbanTask;
import pe.edu.utp.dsa.DSA.PriorityQueue;
import pe.edu.utp.dsa.Kanban.Task.Role;
import pe.edu.utp.dsa.Kanban.Task.User;
import pe.edu.utp.dsa.Kanban.Utilities.*;
import pe.edu.utp.dsa.StringManipulation.StringBuilderWrapper;
import pe.edu.utp.dsa.XML.XMLIteratorSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

import static pe.edu.utp.dsa.Kanban.Utilities.Utilities.*;

public class KanbanController {

    //FXML Elements
    //FXML ListViews
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

    //FXML TitledPanes
    @FXML
    private TitledPane titledPaneProjectCreator, titledPaneTaskOthers;
    @FXML
    private TitledPane titledPaneRoles, titledPaneUsers;

    //FXML AnchorPanes
    @FXML
    private AnchorPane anchorPaneAddTask, anchorPaneTaskInfo, anchorPaneEditTask;
    @FXML
    private AnchorPane anchorPaneEditRole, anchorPaneAddRole;
    @FXML
    private AnchorPane anchorPaneEditUser, anchorPaneAddUser;

    //FXML TextFields
    @FXML
    private TextField addTaskNameTextField, editTaskNameTextField;
    @FXML
    private TextField editFinishedDateTextField, addFinishedDateTextField;
    @FXML
    private TextField textFieldUsername_add, textFieldUsername_edit;
    @FXML
    private TextField textFieldRole_add, textFieldRole_edit;
    @FXML
    private TextField projectCreatorName;

    //FXML TextAreas
    @FXML
    private TextArea addTaskDescriptionTextArea, taskInfoDescriptionTextArea, editTaskDescriptionTextArea;

    //FXML MenuButtons
    @FXML
    private MenuButton addUserAssignmentMenuButton, editUserAssignmentMenuButton;
    @FXML
    private MenuButton userRoleAddMenuButton, userRoleEditMenuButton;
    @FXML
    private MenuButton addTaskPriorityMenuButton, editTaskPriorityMenuButton;

    //FXML Menus
    @FXML
    private Menu menuOpenRecentFile;

    //FXML Buttons
    @FXML
    private Button editTaskButton, addTaskButton;
    @FXML
    private Button buttonEditRole, buttonEditUser;

    //FXML Labels
    @FXML
    private Label labelTaskName, labelTaskNumber, labelPriority;
    @FXML
    private Label labelCreationDate, labelFinishDate;
    @FXML
    private Label labelAssignedUser;

    //FXML HBox
    @FXML
    private HBox HBoxColumns;


    // JavaFX element's without @FXML tag
    // JavaFX ContextMenus
    private ContextMenu contextMenuCatalogue = new ContextMenu();
    private ContextMenu contextMenuOthersColumns = new ContextMenu();
    private ContextMenu contextMenuToDo = new ContextMenu();
    private ContextMenu contextMenuFinished = new ContextMenu();
    private ContextMenu contextMenuRoles = new ContextMenu();
    private ContextMenu contextMenuUser = new ContextMenu();

    // JavaFX FileChoosers
    private FileChooser fileChooserOpen, fileChooserExportAsPDF, fileChooserSaveFile;

    // JavaFX MenuItems
    private MenuItem menuItemDelete_Role, menuItemEdit_Role, menuItemDelete_User, menuItemEdit_User;

    // JavaFX ListViews
    private ListView<KanbanTask> listViewSelected = null;
    private ListView<KanbanTask> listViewPrevious = null;
    private ListView<KanbanTask> listViewNext = null;


    // Other elements outside of JavaFX
    // Files
    private File file;

    // PriorityQueues
    private PriorityQueue<KanbanTask> queueSelected = null;
    private PriorityQueue<KanbanTask> queuePrevious = null;
    private PriorityQueue<KanbanTask> queueNext = null;
    private PriorityQueue<KanbanTask> queueCatalogue = new PriorityQueue<>();
    private PriorityQueue<KanbanTask> queueToDo = new PriorityQueue<>();
    private PriorityQueue<KanbanTask> queueInProgress = new PriorityQueue<>();
    private PriorityQueue<KanbanTask> queueToBeChecked = new PriorityQueue<>();
    private PriorityQueue<KanbanTask> queueFinished = new PriorityQueue<>();
    private PriorityQueue<User> queueUser = new PriorityQueue<>();
    private PriorityQueue<Role> queueRole = new PriorityQueue<>();

    // Strings
    private String ApplicationTitle = "Kanban Application ";

    // Booleans
    private boolean isDeselectionByUser = true;

    // Integers
    private int numberOfTask = 0;

    // Bytes
    private byte selectedPriority = -1;

    // Users
    private User selectUser = null;

    // Roles
    private Role selectedRole = null;

    // Functions and methods
    @FXML
    public void initialize(){
        setupFileChoosers();
        updateRecentFiles();
        setupListViews();
        setupContextMenus();
        setContextMenuToListViews();
        setOnEventOnListView();
        setupTitlePanes();
        setupMenuButtons();
        setupTextAreas();
    }

    // Setups

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
        // fileChooserSaveFile
        fileChooserSaveFile = new FileChooser();
        fileChooserSaveFile.setTitle("Save File");
        fileChooserSaveFile.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML", "*.xml")
        );
    }

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
        editTaskDescriptionTextArea.setWrapText(true);
        addTaskDescriptionTextArea.setWrapText(true);
        taskInfoDescriptionTextArea.setWrapText(true);
    }

    private void setupMenuButtons(){
        // setupMenuButtons Priority
        for (byte i = 1; i <= 5; i++) {
            MenuItem menuItemAdd = new MenuItem(String.valueOf(i));
            MenuItem menuItemEdit = new MenuItem(String.valueOf(i));
            byte finalI = i;
            menuItemAdd.setOnAction(actionEvent -> {
                addTaskPriorityMenuButton.setText(String.valueOf(finalI));
                selectedPriority = finalI;
            });
            menuItemEdit.setOnAction(actionEvent -> {
                editTaskPriorityMenuButton.setText(String.valueOf(finalI));
                selectedPriority = finalI;
            });
            addTaskPriorityMenuButton.getItems().add(menuItemAdd);
            editTaskPriorityMenuButton.getItems().add(menuItemEdit);
        }
    }

    private void setupTitlePanes(){
        titledPaneTaskOthers.expandedProperty().addListener(observable -> {
            resetForm(ResetSection.ADD_TASK, ResetSection.EDIT_TASK, ResetSection.TASK_INFO);
            if(titledPaneTaskOthers.isCollapsible()) {
                anchorPaneAddTask.setVisible(false);
                anchorPaneEditTask.setVisible(false);
            }
        });
        titledPaneRoles.expandedProperty().addListener(observable -> {
            resetForm(ResetSection.ADD_ROLE, ResetSection.EDIT_ROLE);
            if(menuItemDelete_Role.isDisable())
                menuItemDelete_Role.setDisable(false);// habilita boton
            if(titledPaneRoles.isCollapsible()){
                anchorPaneEditRole.setVisible(false);
                anchorPaneAddRole.setVisible(true);
            }
        });
        titledPaneUsers.expandedProperty().addListener(observable -> {
            resetForm(ResetSection.ADD_USER, ResetSection.EDIT_USER);
            if(menuItemDelete_User.isDisable())
                menuItemDelete_User.setDisable(false);//habilita boton
            if(titledPaneUsers.isCollapsible()){
                anchorPaneEditUser.setVisible(false);
                anchorPaneAddUser.setVisible(true);
            }
        });

    }

    private void setupContextMenus(){

        EventHandler<ActionEvent> deleteFn = (ae) -> {
            KanbanTask kt = listViewSelected.getSelectionModel().getSelectedItem();
            int queueIndex = queueSelected.find(kt);
            queueSelected.deleteAt(queueIndex);
            resetForm(ResetSection.ADD_TASK, ResetSection.EDIT_USER, ResetSection.TASK_INFO);
            updateListView();
        };

        MenuItem menuItemDelete0 = new MenuItem("Delete Task");
        menuItemDelete0.setOnAction(deleteFn);

        MenuItem menuItemDelete1 = new MenuItem("Delete Task");
        menuItemDelete1.setOnAction(deleteFn);

        MenuItem menuItemDelete2 = new MenuItem("Delete Task");
        menuItemDelete2.setOnAction(deleteFn);

        MenuItem menuItemDelete3 = new MenuItem("Delete Task");
        menuItemDelete3.setOnAction(deleteFn);

        MenuItem menuItemDeleteAll = new MenuItem("Delete All");
        menuItemDeleteAll.setOnAction(actionEvent -> {
            if(!queueSelected.isEmpty())
                queueSelected.clear();
            resetForm(ResetSection.ADD_TASK, ResetSection.EDIT_USER, ResetSection.TASK_INFO);
            updateListView();
        });

        MenuItem menuItemEdit0 = new MenuItem("Edit task");
        menuItemEdit0.setOnAction(actionEvent -> {
            resetForm(ResetSection.ADD_TASK, ResetSection.EDIT_USER, ResetSection.TASK_INFO);
            try {
                executeAnchorPanedEditTask();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        MenuItem menuItemEdit1 = new MenuItem("Edit task");
        menuItemEdit1.setOnAction(actionEvent -> {
            resetForm(ResetSection.ADD_TASK, ResetSection.EDIT_USER, ResetSection.TASK_INFO);
            try {
                executeAnchorPanedEditTask();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        MenuItem menuItemEdit2 = new MenuItem("Edit task");
        menuItemEdit2.setOnAction(actionEvent -> {
            resetForm(ResetSection.ADD_TASK, ResetSection.EDIT_USER, ResetSection.TASK_INFO);
            try {
                executeAnchorPanedEditTask();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        MenuItem menuItemBack = new MenuItem("Back column");
        menuItemBack.setOnAction(actionEvent -> {
            try{
                queuePrevious.enqueue(queueSelected.dequeue());
            }catch (Exception ignored){}
            updateListView();
            refreshListViews();
        });
        MenuItem menuItemNext = new MenuItem("Next column");
        menuItemNext.setOnAction(actionEvent -> {
            try {
                KanbanTask t = listViewSelected.getSelectionModel().getSelectedItem();
                int i = queueSelected.find(t);
                queueSelected.deleteAt(i);
                queueNext.enqueue(t);
            }catch (Exception ignored){}
            updateListView();
            refreshListViews();
        });
        MenuItem menuItemNext1 = new MenuItem("Next column");
        menuItemNext1.setOnAction(actionEvent -> {
            try {
                KanbanTask t = listViewSelected.getSelectionModel().getSelectedItem();
                int i = queueSelected.find(t);
                queueSelected.deleteAt(i);
                queueNext.enqueue(t);
            }catch (Exception ignored){}
            updateListView();
            refreshListViews();
        });

        menuItemDelete_Role = new MenuItem("Delete Role");
        menuItemDelete_Role.setOnAction(actionEvent -> {
            Role r = listViewRoles.getSelectionModel().getSelectedItem();
            int queueIndex = queueRole.find(r);
            queueRole.deleteAt(queueIndex);
            updateListView(listViewRoles, queueRole);
        });

        menuItemEdit_Role = new MenuItem("Edit Role");
        menuItemEdit_Role.setOnAction(actionEvent -> {
            try {
                menuItemDelete_Role.setDisable(true);
                executeAnchorPaneEditRole();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        menuItemEdit_User = new MenuItem("Edit User");
        menuItemEdit_User.setOnAction(actionEvent -> {
            try {
                menuItemDelete_User.setDisable(true);
                executeAnchorPaneEditUser();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        menuItemDelete_User = new MenuItem("Delete User");
        menuItemDelete_User.setOnAction(actionEvent -> {
            User u = listViewUsers.getSelectionModel().getSelectedItem();
            queueUser.deleteAt(queueUser.find(u));
            updateListView(listViewUsers, queueUser);
        });
        contextMenuRoles.getItems().setAll(menuItemEdit_Role, menuItemDelete_Role);
        contextMenuUser.getItems().setAll(menuItemEdit_User, menuItemDelete_User);
        contextMenuToDo.getItems().setAll(menuItemDelete3, menuItemEdit2, menuItemNext1);
        contextMenuCatalogue.getItems().setAll(menuItemDelete0, menuItemEdit0);
        contextMenuOthersColumns.getItems().setAll(menuItemDelete2, menuItemEdit1, menuItemBack, menuItemNext);
        contextMenuFinished.getItems().setAll(menuItemDelete1, menuItemDeleteAll);

    }

    // Sets
    private void setContextMenuToListViews(){
        listViewCatalogue.setContextMenu(contextMenuCatalogue);
        listViewCatalogue.setOnContextMenuRequested(contextMenuEvent -> {
            boolean flag = listViewCatalogue.getSelectionModel().getSelectedItems().isEmpty();
            if(flag) contextMenuCatalogue.hide();
        });
        listViewToDo.setContextMenu(contextMenuToDo);
        listViewToDo.setOnContextMenuRequested(contextMenuEvent -> {
            boolean flag = listViewToDo.getSelectionModel().getSelectedItems().isEmpty();
            if(flag) contextMenuToDo.hide();
        });
        listViewInProgress.setContextMenu(contextMenuOthersColumns);
        listViewInProgress.setOnContextMenuRequested(contextMenuEvent -> {
            boolean flag = listViewInProgress.getSelectionModel().getSelectedItems().isEmpty();
            if(flag) contextMenuOthersColumns.hide();

        });
        listViewToBeChecked.setContextMenu(contextMenuOthersColumns);
        listViewFinished.setContextMenu(contextMenuFinished);

        listViewRoles.setContextMenu(contextMenuRoles);
        listViewRoles.setOnContextMenuRequested(contextMenuEvent -> {
            boolean flag = listViewRoles.getSelectionModel().getSelectedItems().isEmpty();
            if(flag) contextMenuRoles.hide();
        });

        listViewUsers.setContextMenu(contextMenuUser);
        listViewUsers.setOnContextMenuRequested(contextMenuEvent -> {
            boolean flag = listViewUsers.getSelectionModel().getSelectedItems().isEmpty();
            if(flag) contextMenuUser.hide();
        });


    }

    public void setOnEventOnListView(){
        /*
        A flag is used to prevent this from triggering an infinite loop,
        since deactivating the selection of the listView causes the established
        addlistener to be re-executed, causing an error at run time.
         */

        listViewCatalogue.getSelectionModel().selectedIndexProperty().addListener(
                observable -> {
                    if(!isDeselectionByUser) return;
                    deselectListCell(listViewToDo, listViewInProgress,
                            listViewToBeChecked, listViewFinished);
                    listViewSelected = listViewCatalogue;
                    listViewPrevious = null;
                    listViewNext = listViewToDo;
                    queueSelected = queueCatalogue;
                    queuePrevious = null;
                    queueNext = queueToDo;
                    taskInfo();

                });
        //listViewToDo

        listViewToDo.getSelectionModel().selectedIndexProperty().addListener(
                observable -> {
                    if(!isDeselectionByUser) return;
                    deselectListCell(listViewCatalogue, listViewToBeChecked,
                            listViewInProgress, listViewFinished);
                    listViewSelected = listViewToDo;
                    listViewPrevious = null;
                    listViewNext = listViewInProgress;
                    queueSelected = queueToDo;
                    queuePrevious = null;
                    queueNext = queueInProgress;
                    taskInfo();
                });
        //listViewInProgress

        listViewInProgress.getSelectionModel().selectedIndexProperty().addListener(
                observable -> {
                    if(!isDeselectionByUser) return;
                    deselectListCell(listViewCatalogue, listViewToBeChecked,
                            listViewToDo, listViewFinished);
                    listViewSelected = listViewInProgress;
                    listViewPrevious = listViewToDo;
                    listViewNext = listViewToBeChecked;
                    queueSelected = queueInProgress;
                    queuePrevious = queueToDo;
                    queueNext = queueToBeChecked;
                    taskInfo();
                });
        //listViewToBeChecked
        listViewToBeChecked.getSelectionModel().selectedIndexProperty().addListener(
                observable -> {
                    if(!isDeselectionByUser) return;
                    deselectListCell(listViewToDo, listViewCatalogue,
                            listViewFinished, listViewInProgress);
                    listViewSelected = listViewToBeChecked;
                    listViewPrevious = listViewInProgress;
                    listViewNext = listViewFinished;
                    queueSelected = queueToBeChecked;
                    queuePrevious = queueInProgress;
                    queueNext = queueFinished;
                    taskInfo();

                });
        //listViewFinished
        listViewFinished.getSelectionModel().selectedItemProperty().addListener(
                observable -> {
                    if(!isDeselectionByUser) return;
                    deselectListCell(listViewCatalogue, listViewToDo,
                            listViewInProgress, listViewToBeChecked);
                    listViewSelected = listViewFinished;
                    listViewPrevious = null;
                    listViewNext = null;
                    queueSelected = queueFinished;
                    queuePrevious = null;
                    queueNext = null;
                    taskInfo();
                });
        //Role
    }

    // Executes
    @FXML
    private void taskInfo(){
        resetForm(ResetSection.TASK_INFO);
        int index = listViewSelected.getSelectionModel().getSelectedIndex();
        if (index == -1) return;
        //KanbanTask task = queueSelected.getElement(index);
	    KanbanTask task = listViewSelected.getItems().get(index);
        if (task == null) return;
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
                truncateString(task.getuserAssignedToTheTask().getName(), 28)
        );
        taskInfoDescriptionTextArea.setText(task.getDescription());
    }

    @FXML
    private void executeAnchorPaneAddTask(){
        anchorPaneEditTask.setVisible(false);
        anchorPaneTaskInfo.setVisible(false);
        titledPaneTaskOthers.setExpanded(true);
        anchorPaneAddTask.setVisible(true);
        addTaskButton.setOnAction(actionEvent -> {
            try{
                addTask();
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        });
    }

    private void executeAnchorPanedEditTask(){
        anchorPaneAddTask.setVisible(false);
        anchorPaneTaskInfo.setVisible(false);
        titledPaneTaskOthers.setExpanded(true);
        anchorPaneEditTask.setVisible(true);
        int index = listViewSelected.getSelectionModel().getSelectedIndex();
		KanbanTask task = listViewSelected.getItems().get(index);
        editTaskNameTextField.setText(task.getName());
        editFinishedDateTextField.setText(task.getFinishDate().toString());
        editTaskDescriptionTextArea.setText(task.getDescription());
        selectedPriority = task.getPriority();
        selectUser = task.getuserAssignedToTheTask();
        editTaskPriorityMenuButton.setText(String.valueOf(selectedPriority));
        editUserAssignmentMenuButton.setText(selectUser.getName());
        editTaskButton.setOnAction(actionEvent -> {
            try{
                editTask(index);
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        });
    }

    private void executeAnchorPaneEditRole(){
        anchorPaneAddRole.setVisible(false);
        titledPaneRoles.setExpanded(true);
        anchorPaneEditRole.setVisible(true);
        int index = listViewRoles.getSelectionModel().getSelectedIndex();
		Role role = listViewRoles.getItems().get(index);
        textFieldRole_edit.setText(role.getRolName());
        buttonEditRole.setOnAction(actionEvent -> {
            try {
                editRole(index);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void executeAnchorPaneEditUser(){
        anchorPaneAddUser.setVisible(false);
        titledPaneUsers.setExpanded(true);
        anchorPaneEditUser.setVisible(true);
        User user = listViewUsers.getSelectionModel().getSelectedItem();
        int indexUser = queueUser.find(user);
        selectedRole = user.getRol();
        textFieldUsername_edit.setText(user.getName());
        userRoleEditMenuButton.setText(selectedRole.getRolName());
        buttonEditUser.setOnAction(actionEvent -> {
            try {
                editUser(indexUser);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    // Adds, Edits and Deletes

    private void addTask() {
        String taskName = addTaskNameTextField.getText();
        String finishDate = addFinishedDateTextField.getText();
        String description = addTaskDescriptionTextArea.getText();

        if(!isValidString(projectCreatorName.getText()))
            throw new IllegalArgumentException("Project Creator name is empty");

        if(!isValidString(taskName) || !isValidString(finishDate))
            throw new IllegalArgumentException("Task name is empty or completion date is empty");

        if(!isValidString(description))
            throw new IllegalArgumentException("Description is empty");

        if(selectedPriority == -1)
            throw new IllegalArgumentException("Priority are not valid");

        try {
            if(selectUser == null)
                queueCatalogue.enqueue(new KanbanTask(
                        taskName, projectCreatorName.getText(), numberOfTask+1,
                        selectedPriority, description, LocalDate.parse(finishDate))
                );
            else
                queueToDo.enqueue(new KanbanTask(
                        taskName, selectUser, projectCreatorName.getText(), numberOfTask+1,
                        selectedPriority, description, LocalDate.parse(finishDate))
                );
            numberOfTask++;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        updateListView(listViewCatalogue, queueCatalogue);
        updateListView(listViewToDo, queueToDo);
        anchorPaneAddTask.setVisible(false);
        titledPaneTaskOthers.setExpanded(false);
        resetForm(ResetSection.ADD_TASK);
    }

    @FXML
    private void addUser(){
        String textUser = textFieldUsername_add.getText();
        if(!isValidString(textUser))
            throw new IllegalArgumentException("Username is blank");

        if(selectedRole == null)
            throw new IllegalArgumentException("Role is not selected!");

        queueUser.enqueue(new User(textUser, selectedRole));
        updateListView(listViewUsers, queueUser);
        updateMenuButtonUser();
        resetForm(ResetSection.ADD_USER);
    }

    @FXML
    private void addRole(){
        String textRole = textFieldRole_add.getText();
        if(!isValidString(textRole))
            throw new IllegalArgumentException("The role name is empty.");

        queueRole.enqueue(new Role(textRole));
        updateListView(listViewRoles, queueRole);
        updateMenuButtonRole();
        resetForm(ResetSection.ADD_ROLE);
        menuItemDelete_Role.setDisable(false);
    }

    @FXML
    private void editTask(int index){
		KanbanTask selectedTask = listViewSelected.getItems().get(index);
		int numberTask = queueSelected.find(selectedTask);
        String taskName = editTaskNameTextField.getText();
        String finishedDate = editFinishedDateTextField.getText();
        String taskDescription = editTaskDescriptionTextArea.getText();

        // move a task to de "To Do" queue when an user is assigned to the task.
        if(selectUser != null && queueSelected == queueCatalogue){
            KanbanTask task = new KanbanTask(
                    taskName, selectUser, projectCreatorName.getText(),
                    numberTask, selectedPriority, taskDescription, LocalDate.parse(finishedDate)
            );
            queueSelected.deleteAt(numberTask);
            queueNext.enqueue(task);
        } else {
            queueSelected.editElement(
                    (KanbanTask kt) -> {
                        kt.setName(taskName);
                        kt.setuserAssignedToTheTask(selectUser);
                        kt.setAuthor(projectCreatorName.getText());
                        kt.setPriority(selectedPriority);
                        kt.setDescription(taskDescription);
                        kt.setFinishDate(LocalDate.parse(finishedDate));
                    },
                    numberTask
            );
        }
        updateListView();
        //refreshListViews();
        // This line was omitted since when editing a task
        // and changing the priority to a lower one that was before,
        // it has no effect on the ordering of the elements.
        resetForm(ResetSection.EDIT_TASK);
        anchorPaneEditTask.setVisible(false);
        titledPaneTaskOthers.setExpanded(false);
    }

    private void editUser(int queueIndex){
        String textUser = textFieldUsername_edit.getText();

        if(!isValidString(textUser))
            throw new IllegalArgumentException("The username is empty.");

		queueUser.editElement(
                (User u) -> {
                    u.setName(textUser);
                    u.setRol(selectedRole);
                    }, queueIndex);

        refreshEverything();
        resetForm(ResetSection.EDIT_USER);
        anchorPaneEditUser.setVisible(false);
        anchorPaneAddUser.setVisible(true);
        menuItemDelete_User.setDisable(false);
    }

    private void editRole(int index){
        String textRole = textFieldRole_edit.getText();
		int queueIndex = queueRole.find(listViewRoles.getItems().get(index));
        if(!isValidString(textRole))
            throw new IllegalArgumentException("The role name is empty.");

		queueRole.editElement(
				(Role r) -> {r.setRolName(textRole);},
				queueIndex
		);

        refreshEverything();
        resetForm(ResetSection.EDIT_ROLE);
        anchorPaneEditRole.setVisible(false);
        anchorPaneAddRole.setVisible(true);
        menuItemDelete_Role.setDisable(false);
    }

    // Open File

    @FXML
    private void openFile(){
        file = fileChooserOpen.showOpenDialog(null);
        if (file == null) return;
        IO.writeRecordRecentFiles(file.getPath());
        updateRecentFiles();
        getStage().setTitle(ApplicationTitle+file.getName());
    }

    /**
     * Opens a recently accessed file based on the selected menu item.
     * @param pathRecentFile The path of the recently accessed file.
     */
    private void openRecentFile(String pathRecentFile) throws FileNotFoundException {
        file = new File(pathRecentFile);
        if (!file.exists()){
            IO.deleteRecentFilesRecord(pathRecentFile);
            updateRecentFiles();
            throw new FileNotFoundException("File not found: "+pathRecentFile);
        }
        getStage().setTitle(ApplicationTitle+" - "+file.getName());
    }

    /**
     * Updates the list of recent files in the menu.
     */
    private void updateRecentFiles(){
        String[] directorySeparators = new String[]{"/", "\\"};
        ArrayList<String> recentFiles = IO.readRecordRecentFiles();
        int index = 0;

        if (menuOpenRecentFile != null) menuOpenRecentFile.getItems().clear();

        for (String pathRecentFile : recentFiles.toArray(new String[0])){
            for (String ds: directorySeparators){
                index = pathRecentFile.lastIndexOf(ds);
                if(index != -1)
                    break;
            }
            String nameFile = pathRecentFile.substring(index+1);
            MenuItem menuItemRecentFile = new MenuItem(nameFile);
            menuItemRecentFile.setOnAction(event -> {
                try {
                    openRecentFile(pathRecentFile);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
            menuOpenRecentFile.getItems().add(menuItemRecentFile);
        }
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


    public void resetForm(ResetSection...resetSections){
        for (ResetSection rs: resetSections){
            switch (rs){
                case ALL:
                    resetTaskInfo();
                    resetAddTask();
                    resetEditTask();
                    resetAddUser();
                    resetEditUser();
                    resetAddRole();
                    resetEditRole();
                    break;
                case TASK_INFO:
                    resetTaskInfo();
                    break;
                case ADD_TASK:
                    resetAddTask();
                    break;
                case EDIT_TASK:
                    resetEditTask();
                    break;
                case ADD_USER:
                    resetAddUser();
                    break;
                case EDIT_USER:
                    resetEditUser();
                    break;
                case ADD_ROLE:
                    resetAddRole();
                    break;
                case EDIT_ROLE:
                    resetEditRole();
                    break;
                default:
                    break;
            }
        }
    }

    private void resetTaskInfo(){
        labelTaskName.setText("None");
        labelTaskNumber.setText("None");
        labelPriority.setText("None");
        labelCreationDate.setText("None");
        labelFinishDate.setText("None");
        labelAssignedUser.setText("None");
        taskInfoDescriptionTextArea.clear();
    }

    private void resetAddTask(){
        addTaskNameTextField.clear();
        addFinishedDateTextField.clear();
        addUserAssignmentMenuButton.setText("Select...");
        addTaskDescriptionTextArea.clear();
        addTaskPriorityMenuButton.setText("Select...");
        selectedPriority = -1;
        selectUser = null;
    }

    private void resetEditTask(){
        editTaskNameTextField.clear();
        editFinishedDateTextField.clear();
        editTaskDescriptionTextArea.clear();
        editTaskPriorityMenuButton.setText("Select...");
        editUserAssignmentMenuButton.setText("Selected...");
        selectedPriority = -1;
        selectUser = null;
    }

    private void resetAddUser(){
        userRoleAddMenuButton.setText("Select...");
        textFieldUsername_add.clear();
        selectedRole = null;
    }

    private void resetEditUser(){
        userRoleEditMenuButton.setText("Select...");
        textFieldUsername_edit.clear();
        selectedRole = null;
        selectUser = null;
    }

    private void resetAddRole(){
        textFieldRole_add.clear();
    }

    private void resetEditRole(){
        textFieldRole_edit.clear();
        selectedRole = null;
    }

    @FXML
    private void saveFile() throws Exception {
        // open file - vver esto
        file = fileChooserSaveFile.showSaveDialog(null);
        if (file == null) return;

        //String outputPath = "KanbanBoard-" + projectCreatorName + ".xml";
        StringBuilderWrapper sbw = new StringBuilderWrapper();

        XMLIteratorSerializer<User, PriorityQueue<User>> usersXIS = new XMLIteratorSerializer<>();
        XMLIteratorSerializer<Role, PriorityQueue<Role>> rolesXIS = new XMLIteratorSerializer<>();
        XMLIteratorSerializer<KanbanTask, PriorityQueue<KanbanTask>> tasksXIS = new XMLIteratorSerializer<>();

        int hierachy = 0;
        // String preffix = StringCreator.ntimes('\t', hierachy);
        sbw.openXMLTag("KanbanBoard");

        usersXIS.setHierachy(hierachy + 1);
        rolesXIS.setHierachy(hierachy + 1);
        tasksXIS.setHierachy(hierachy + 1);

        usersXIS.addIterable(queueUser, "Users");
        rolesXIS.addIterable(queueRole, "Roles");
        tasksXIS.addIterable(queueCatalogue, "Catalogue")
                .addIterable(queueToDo, "ToDo")
                .addIterable(queueInProgress, "InProgress")
                .addIterable(queueFinished, "Finished")
                .addIterable(queueToBeChecked, "ToBeChecked");

        sbw.append(usersXIS.export())
                .append(rolesXIS.export())
                .append(tasksXIS.export());

        sbw.closeXMLTag();
        IO.saveData(sbw.toString(), file.getPath());
        //System.out.println(sbw);
    }

    @FXML
    private void closeFile(){

    }

    @FXML
    public void quit(){
        ConfirmationOptions confirmationOption = GlobalExceptionHandler.alertConfirmation(
                "Save File",
                "Are you sure to continue?",
                "Do you want to save your changes to a file?"
        );
        if(confirmationOption == ConfirmationOptions.YES) {
            try {
                saveFile();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else if (confirmationOption == ConfirmationOptions.NO) Platform.exit();
    }


    @FXML
    private void exportAsPDF() throws IOException {
        deselectAllListCell();
        file = fileChooserExportAsPDF.showSaveDialog(null);
        if(file == null) return;
        IO.exportAsPDF(HBoxColumns, file.getPath());
        GlobalExceptionHandler.alertInformation(
                "Export",
                "Export to PDF",
                "Export to PDF was successful"
        );
    }

    private void refreshEverything() {
        refreshListViews();
        listViewRoles.refresh();
        listViewUsers.refresh();
        updateMenuButtonUser();
        updateMenuButtonRole();
    }

    private void refreshListViews(){
        listViewCatalogue.refresh();
        listViewToDo.refresh();
        listViewToBeChecked.refresh();
        listViewInProgress.refresh();
        listViewFinished.refresh();
    }

    private void updateListView(){
        if(queueSelected.isEmpty()) listViewSelected.getItems().clear();
        else listViewSelected.getItems().setAll(queueSelected.toList());
        if (listViewNext != null)
            listViewNext.getItems().setAll(queueNext.toList());
        if (listViewPrevious != null)
            listViewPrevious.getItems().setAll(queuePrevious.toList());
    }

    private void updateMenuButtonRole(){
        userRoleAddMenuButton.getItems().clear();
        userRoleEditMenuButton.getItems().clear();
        for (Role role: queueRole.toList()){
            MenuItem menuItemAddRole = new MenuItem(role.getRolName());
            MenuItem menuItemEditRole = new MenuItem(role.getRolName());
            menuItemAddRole.setOnAction(actionEvent -> {
                userRoleAddMenuButton.setText(role.getRolName());
                selectedRole = role;
            });
            menuItemEditRole.setOnAction(actionEvent -> {
                userRoleEditMenuButton.setText(role.getRolName());
                selectedRole = role;
            });
            userRoleAddMenuButton.getItems().add(menuItemAddRole);
            userRoleEditMenuButton.getItems().add(menuItemEditRole);
        }
        selectedRole = null;
    }

    private void updateMenuButtonUser(){
        addUserAssignmentMenuButton.getItems().clear();
        editUserAssignmentMenuButton.getItems().clear();
        for (User user: queueUser.toList()){
            MenuItem menuItemAdd = new MenuItem(user.getName());
            MenuItem menuItemEdit = new MenuItem(user.getName());
            menuItemAdd.setOnAction(actionEvent -> {
                addUserAssignmentMenuButton.setText(user.getName());
                selectUser = user;
            });
            menuItemEdit.setOnAction(actionEvent -> {
                editUserAssignmentMenuButton.setText(user.getName());
                selectUser = user;
            });
            addUserAssignmentMenuButton.getItems().add(menuItemAdd);
            editUserAssignmentMenuButton.getItems().add(menuItemEdit);
        }
        selectUser = null;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void updateListView(ListView listView, PriorityQueue priorityQueue){
        listView.getItems().setAll(priorityQueue.toList());
    }

    private Stage getStage(){
        return ((Stage) titledPaneProjectCreator.getScene().getWindow());
    }
}