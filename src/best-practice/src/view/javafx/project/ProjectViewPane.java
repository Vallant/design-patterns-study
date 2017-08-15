package view.javafx.project;

import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

import javax.swing.*;
import java.util.List;

public class ProjectViewPane extends GridPane
{

  private final BorderPane               pOwned;
  private final GridPane                    pOwnedButtons;
  final         Button                   btAddProject;
  final         Button                   btDeleteProject;
  private final FlowPane                 pFlowPanel1;
  private final ScrollPane               spOwned;
  final         DefaultListModel<String> lstOwnedModel;
  final         ListView<String>             lstOwned;

  private final BorderPane                    pInvolved;
  private final GridPane                    pInvolvedButtons;
  private final FlowPane                    pFlowPanel2;
  final         Button                   btLeaveProject;
  private final ScrollPane               spInvolved;
  final         DefaultListModel<String> lstInvolvedModel;
  final         ListView<String>             lstInvolved;

  public ProjectViewPane()
  {
    setPrefSize(700, 400);
    //setBorder(new EmptyBorder(5,5,5,5));
    pOwned = new BorderPane();
    pOwned.setPrefSize(150, 250);
    pFlowPanel1 = new FlowPane();
    pOwnedButtons = new GridPane();
    btAddProject = new Button("Add Project");
    btAddProject.setPrefSize(130, 25);
    btDeleteProject = new Button("Delete Project");
    btDeleteProject.setPrefSize(130, 25);
    lstOwnedModel = new DefaultListModel<>();
    lstOwned = new ListView<String>();
    lstOwned.setCellFactory(list -> {
      // usual list cell:
      ListCell<String> cell = new ListCell<String>() {
        @Override
        protected void updateItem(String item, boolean empty) {
          super.updateItem(item, empty);
          setText(empty ? null : item);
        }
      };
      if(cell.getIndex() % 2 == 1)
      {
        cell.setStyle("-fx-background-color: #EEF1FD;");
      }

      return cell;
    });
    lstOwned.setPrefSize(150, 100);
    spOwned = new ScrollPane(lstOwned);

    add(pOwned, 0,0);
    pOwned.setRight(pFlowPanel1);
    pOwnedButtons.getChildren().add(btAddProject);
    pOwnedButtons.getChildren().add(btDeleteProject);
    pFlowPanel1.getChildren().add(pOwnedButtons);
    pOwned.setCenter(spOwned);


    pInvolved = new BorderPane();
    pInvolved.setPrefSize(130, 250);
    pFlowPanel2 = new FlowPane();
    pInvolvedButtons = new GridPane();
    btLeaveProject = new Button("Leave Project");
    btLeaveProject.setPrefSize(130,25);
    lstInvolvedModel = new DefaultListModel<>();
    lstInvolved = new ListView<String>();
    lstInvolved.setCellFactory(list -> {
      // usual list cell:
      ListCell<String> cell = new ListCell<String>() {
        @Override
        protected void updateItem(String item, boolean empty) {
          super.updateItem(item, empty);
          setText(empty ? null : item);
        }
      };
      if(cell.getIndex() % 2 == 1)
      {
        cell.setStyle("-fx-background-color: #EEF1FD;");
      }

      return cell;
    });

    lstInvolved.setPrefSize(150, 100);
    spInvolved = new ScrollPane(lstInvolved);

    add(pInvolved, 0, 1);
    pInvolved.setRight(pFlowPanel2);
    pInvolvedButtons.getChildren().add(btLeaveProject);
    pInvolved.setCenter(spInvolved);
    pFlowPanel2.getChildren().add(pInvolvedButtons);
  }
}
