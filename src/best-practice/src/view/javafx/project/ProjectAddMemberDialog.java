package view.javafx.project;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;

public class ProjectAddMemberDialog extends Dialog<ButtonType>
{

  final         ListView<String>            lstAvailableUsers;
  private       ArrayList<String>        available;

  public ProjectAddMemberDialog()
  {
    setTitle("Add new Members to Project");
    getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

    BorderPane grid = new BorderPane();
    grid.setPadding(new Insets(20, 150, 10, 10));

    lstAvailableUsers = new ListView<String>();
    lstAvailableUsers.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    ScrollPane spList = new ScrollPane(lstAvailableUsers);
    //spList.setBorder(new TitledBorder(new LineBorder(Color.black, 1), "Available Users for Project"));

    getDialogPane().setContent(spList);
    getDialogPane().setPrefSize(350, 200);

    spList.setFitToHeight(true);
    spList.setFitToWidth(true);
    spList.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    spList.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

    setResizable(true);
  }

  void setAvailableNames(ArrayList<String> names)
  {
    lstAvailableUsers.getItems().clear();
    lstAvailableUsers.getItems().addAll(names);
  }


}
