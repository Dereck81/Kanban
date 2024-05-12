package pe.edu.utp.dsa.kanban.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import pe.edu.utp.dsa.kanban.Task.KanbanTask;

import java.time.LocalDate;

public class KanbanController {
    @FXML
    private Label welcomeText;

    @FXML
    private ListView<Pane> listViewCatalogue = new ListView<>();

    @FXML
    private ListView<Pane> listViewToDo = new ListView<>();

    @FXML
    private ListView<Pane> listViewInProgress = new ListView<>();

    @FXML
    private ListView<Pane> listViewToBeChecked = new ListView<>();

    @FXML
    private ListView<Pane> listViewFinished = new ListView<>();

    @FXML
    private ListView<String> listViewUsers = new ListView<>();
    @FXML
    private ListView<String> listViewRoles = new ListView<>();

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

    private ContextMenu contextMenuCatalogue = new ContextMenu();
    private ContextMenu contextMenuOthersColumns = new ContextMenu();
    private boolean isDeselectionByUser = true;


    @FXML
    public void initialize(){
        titledPaneTaskOthers.expandedProperty().addListener(observable -> {
            if(titledPaneTaskOthers.isCollapsible())
                //falta eliminar los datos ingresados (resetear)
                anchorPaneAddTask.setVisible(false);
        });
        setContextMenuConfig();
        setOnEventOnListView();
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
    public final void deselectListCell(ListView<Pane>...lists){
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
        anchorPaneAddTask.setVisible(true);
        titledPaneTaskOthers.setExpanded(true);
    }

    @FXML
    private void addTask(){
        //if(menuButtonAsgUsr.)
    }

    private void showAnchorPaneAddTask(){

    }


    @FXML
    private void openFile(){

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

    }




    @FXML
    public void init(){
        listViewToDo.getItems().addAll(new KanbanTask("Diego Maricon", "Diego Maricon", "asd", 2, 3, LocalDate.now()).getPaneTask());
        listViewCatalogue.getItems().addAll(new KanbanTask("Diego Maricon", "Diego Maricon", "asd", 2, 3, LocalDate.now()).getPaneTask());
    }



}