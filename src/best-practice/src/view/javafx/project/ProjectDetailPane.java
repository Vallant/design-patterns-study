package view.javafx.project;

import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static com.sun.javafx.fxml.expression.Expression.add;

public class ProjectDetailPane extends BorderPane
{
  private final BorderPane   pHeader;
  final         Button btBack;
  private final Label  lbProjectName;

  private final GridPane pCenter;

  private final FlowPane                   pFlowPanel1;
  private final BorderPane                   pPhases;
  private final BorderPane                   pPhasesHeader;
  //final Label lbPhases;
  private final GridPane                   pPhasesButtons;
  final         Button                  btAddPhase;
  final         Button                  btDeletePhase;
  final ListView<String>            lstPhases;

  private final FlowPane  pFlowPanel2;
  private final BorderPane  pMembers;
  private final BorderPane  pMembersHeader;
  //final Label lbMembers;
  private final GridPane  pMembersButtons;
  final         Button btAddMember;
  final         Button btDeleteMember;
  final         Button btPromoteToAdmin;
  final         Button btDegradeToMember;

  final         ListView<String>                    lstMembers;

  private final BorderPane    pDescription;
  private final FlowPane    pFlowPanel3;
  private final GridPane    pDescriptionButtons;
  final         TextArea taDescription;
  final         Button   btUpdateDescription;


  public ProjectDetailPane()
  {
    pCenter = new GridPane();

    pHeader = new BorderPane();
    btBack = new Button("Back");
    lbProjectName = new Label("Detail for Project: ");
    pHeader.setLeft(btBack);
    pHeader.setCenter(lbProjectName);

    pFlowPanel1 = new FlowPane();
    pPhases = new BorderPane();
    pPhasesHeader = new BorderPane();
    btAddPhase = new Button("Add Phase");
    btAddPhase.setPrefSize(175,25);
    btDeletePhase = new Button("Delete Phase");
    btDeletePhase.setPrefSize(175,25);
    lstPhases = new ListView<>();
    lstPhases.setCellFactory(list -> {
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
    //lstPhases.setBorder(new LineBorder(Color.black, 1));
    pPhasesButtons = new GridPane();

    pPhases.setTop(pPhasesHeader);
    //pPhasesHeader.add(lbPhases, BorderLayout.CENTER);
    pPhases.setRight(pFlowPanel1);
    pPhasesButtons.getChildren().add(btAddPhase);
    pPhasesButtons.getChildren().add(btDeletePhase);
    pFlowPanel1.getChildren().add(pPhasesButtons);
    ScrollPane spPhases = new ScrollPane(lstPhases);
    pPhases.setCenter(spPhases);


    pFlowPanel2 = new FlowPane();
    pMembers = new BorderPane();
    pMembersHeader = new BorderPane();
    //        lbMembers = new Label("Project Members");
    btAddMember = new Button("Add Members");
    btAddMember.setPrefSize(175, 25);
    btDeleteMember = new Button("Delete Member");
    btDeleteMember.setPrefSize(175, 25);

    btPromoteToAdmin = new Button("Make Leader");
    btPromoteToAdmin.setPrefSize(175, 25);
    btDegradeToMember = new Button("Make Member");
    btDegradeToMember.setPrefSize(175, 25);
    lstMembers = new ListView<String>();
    lstMembers.setCellFactory(list -> {
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
    pMembersButtons = new GridPane();

    pMembers.setTop(pMembersHeader);
    pMembers.setRight(pFlowPanel2);
    pMembersButtons.add(btAddMember, 0, 0);
    pMembersButtons.add(btDeleteMember, 0, 1);
    pMembersButtons.add(btPromoteToAdmin, 0, 2);
    pMembersButtons.add(btDegradeToMember, 0, 3);
    pFlowPanel2.getChildren().add(pMembersButtons);
    ScrollPane spMembers = new ScrollPane(lstMembers);
    pMembers.setCenter(spMembers);

    pDescription = new BorderPane();
    pDescriptionButtons = new GridPane();
    pFlowPanel3 = new FlowPane();
    taDescription = new TextArea();
    btUpdateDescription = new Button("Update Description");
    btUpdateDescription.setPrefSize(175, 25);
    ScrollPane spDescription = new ScrollPane(taDescription);
    //spDescription.setPreferredSize(spMembers.getPreferredSize());
    pDescription.setCenter(spDescription);
    pDescriptionButtons.getChildren().add(btUpdateDescription);
    pFlowPanel3.getChildren().add(pDescriptionButtons);
    pDescription.setRight(pFlowPanel3);


    pCenter.add(pPhases, 0, 0);
    pCenter.add(pMembers, 0, 1);
    pCenter.add(pDescription, 0, 2);
    setCenter(pCenter);
    setTop(pHeader);
  }

  public void setProjectName(String projectName)
  {
    lbProjectName.setText("Detail for Project: " + projectName);
  }

  public void setPhases(ArrayList<String> phases)
  {
    lstPhases.getItems().clear();
    lstPhases.getItems().addAll(phases);
  }

  public void setMemberInformation(ArrayList<String> members, ArrayList<String> roles)
  {
    assert (members.size() == roles.size());
    lstMembers.getItems().clear();

    for(int i = 0; i < members.size(); ++i)
    {
      lstMembers.getItems().add(members.get(i) + " (" + roles.get(i) + ")");
    }
  }

  public void setDescription(String description)
  {
    taDescription.setText(description);
  }
}
