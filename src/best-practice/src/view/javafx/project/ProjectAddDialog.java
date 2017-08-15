package view.javafx.project;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;


public class ProjectAddDialog extends Dialog<ButtonType>
{
  private TextField name;
  private TextField description;

  public ProjectAddDialog()
  {
    setTitle("Create new Project");
    setHeaderText("Please provide Project information");


    // Set the button types.
    getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

    // Create the username and password labels and fields.
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));

    name = new TextField();
    name.setPromptText("Project Name");
    description = new TextField();
    description.setPromptText("Project Description");

    grid.add(new Label("Project Name:"), 0, 0);
    grid.add(name, 1, 0);
    grid.add(new Label("Project Description:"), 0, 1);
    grid.add(description, 1, 1);

    getDialogPane().setContent(grid);
  }

  public String getName()
  {
    return name.getText();
  }

  public String getDescription()
  {
    return description.getText();
  }
}
