package view.javafx.project;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ProjectAddPhaseDialog extends Dialog<ButtonType>
{
  private TextField name;

  public ProjectAddPhaseDialog()
  {
    setTitle("Create new Project Phase");
    setHeaderText("Please provide Project Phase information");


    // Set the button types.
    getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

    // Create the username and password labels and fields.
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));

    name = new TextField();
    name.setPromptText("Project Name");

    grid.add(new Label("Phase Name:"), 0, 0);
    grid.add(name, 1, 0);

    getDialogPane().setContent(grid);
  }

  public String getName()
  {
    return name.getText();
  }
}
