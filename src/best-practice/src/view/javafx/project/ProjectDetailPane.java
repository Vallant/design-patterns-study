package view.javafx.project;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.util.ArrayList;

class ProjectDetailPane extends BorderPane
{
  final         Button btBack;
  final Button           btAddPhase;
  final Button           btDeletePhase;
  final ListView<String> lstPhases;
  final Button btAddMember;
  final Button btDeleteMember;
  final Button btPromoteToAdmin;
  final Button btDegradeToMember;
  final ListView<String> lstMembers;
  final TextArea taDescription;
  final Button   btUpdateDescription;
  private final Label  lbProjectName;


  public ProjectDetailPane()
  {
    setPadding(new Insets(5, 5, 5, 5));

    GridPane pCenter = new GridPane();
    GridPane pHeader = new GridPane();
    pHeader.setAlignment(Pos.CENTER_LEFT);
    pHeader.setHgap(5);
    pHeader.setPadding(new Insets(0, 0, 5, 0));
    btBack = new Button("Back");
    lbProjectName = new Label("Detail for Project: ");
    pHeader.add(btBack, 0, 0);
    pHeader.add(lbProjectName, 1, 0);

    BorderPane pPhases = new BorderPane();
    pPhases.setPadding(new Insets(0, 5, 5, 0));
    BorderPane pPhasesHeader = new BorderPane();
    btAddPhase = new Button("Add Phase");
    int BUTTON_WIDTH = 150;
    btAddPhase.setPrefWidth(BUTTON_WIDTH);
    btDeletePhase = new Button("Delete Phase");
    btDeletePhase.setPrefWidth(BUTTON_WIDTH);
    lstPhases = new ListView<>();
    //lstPhases.setBorder(new LineBorder(Color.black, 1));
    GridPane pPhasesButtons = new GridPane();
    pPhasesButtons.setPadding(new Insets(0, 0, 5, 5));
    pPhasesButtons.setVgap(5);

    pPhases.setTop(pPhasesHeader);
    Label lbPhases = new Label("Phases");
    pPhasesHeader.setLeft(lbPhases);
    pPhases.setRight(pPhasesButtons);
    pPhasesButtons.add(btAddPhase, 0, 0);
    pPhasesButtons.add(btDeletePhase, 0, 1);
    ScrollPane spPhases = new ScrollPane(lstPhases);
    pPhases.setCenter(spPhases);


    BorderPane pMembers = new BorderPane();
    pMembers.setPadding(new Insets(0, 5, 5, 0));
    BorderPane pMembersHeader = new BorderPane();
    Label lbMembers = new Label("Project Members");
    btAddMember = new Button("Add Members");
    btAddMember.setPrefWidth(BUTTON_WIDTH);
    btDeleteMember = new Button("Delete Member");
    btDeleteMember.setPrefWidth(BUTTON_WIDTH);

    btPromoteToAdmin = new Button("Make Leader");
    btPromoteToAdmin.setPrefWidth(BUTTON_WIDTH);
    btDegradeToMember = new Button("Make Member");
    btDegradeToMember.setPrefWidth(BUTTON_WIDTH);
    lstMembers = new ListView<>();
    GridPane pMembersButtons = new GridPane();
    pMembersButtons.setPadding(new Insets(0, 0, 5, 5));
    pMembersButtons.setVgap(5);
    pMembersHeader.setLeft(lbMembers);
    pMembers.setTop(pMembersHeader);
    pMembers.setRight(pMembersButtons);
    pMembersButtons.add(btAddMember, 0, 0);
    pMembersButtons.add(btDeleteMember, 0, 1);
    pMembersButtons.add(btPromoteToAdmin, 0, 2);
    pMembersButtons.add(btDegradeToMember, 0, 3);
    ScrollPane spMembers = new ScrollPane(lstMembers);
    pMembers.setCenter(spMembers);

    BorderPane pDescription = new BorderPane();
    pDescription.setPadding(new Insets(0, 5, 0, 0));
    GridPane pDescriptionButtons = new GridPane();
    taDescription = new TextArea();
    btUpdateDescription = new Button("Update Description");
    btUpdateDescription.setPrefWidth(BUTTON_WIDTH);
    ScrollPane spDescription = new ScrollPane(taDescription);
    pDescription.setCenter(spDescription);
    pDescriptionButtons.getChildren().add(btUpdateDescription);
    pDescriptionButtons.setPadding(new Insets(0, 0, 5, 5));
    pDescription.setRight(pDescriptionButtons);

    BorderPane pDescriptionHeader = new BorderPane();
    Label lbDescription = new Label("Project Description");
    pDescriptionHeader.setLeft(lbDescription);
    pDescription.setTop(pDescriptionHeader);


    pCenter.add(pPhases, 0, 0);
    pCenter.add(pMembers, 0, 1);
    pCenter.add(pDescription, 0, 2);
    setCenter(pCenter);
    setTop(pHeader);

    GridPane.setHgrow(lstPhases, Priority.ALWAYS);
    GridPane.setVgrow(lstPhases, Priority.ALWAYS);
    GridPane.setHgrow(spPhases, Priority.ALWAYS);
    GridPane.setVgrow(spPhases, Priority.ALWAYS);
    GridPane.setHgrow(pPhases, Priority.ALWAYS);
    GridPane.setVgrow(pPhases, Priority.ALWAYS);

    spPhases.setFitToHeight(true);
    spPhases.setFitToWidth(true);
    spPhases.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    spPhases.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

    spMembers.setFitToHeight(true);
    spMembers.setFitToWidth(true);
    spMembers.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    spMembers.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

    spDescription.setFitToWidth(true);
    spDescription.setFitToHeight(true);
    spDescription.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    spDescription.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
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
