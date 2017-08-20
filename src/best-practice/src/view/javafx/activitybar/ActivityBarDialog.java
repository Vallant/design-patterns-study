package view.javafx.activitybar;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

class ActivityBarDialog extends Dialog<ButtonType>
{
  private final TextField description;
  private final TextField comment;

  public ActivityBarDialog()
  {
    setTitle("Finish Activity");
    setHeaderText("Please provide Activity information");


    // Set the button types.
    getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

    // Create the username and password labels and fields.
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));

    description = new TextField();
    description.setPromptText("Description");
    comment = new TextField();
    comment.setPromptText("Comment");

    grid.add(new Label("Description:"), 0, 0);
    grid.add(description, 1, 0);
    grid.add(new Label("Comment:"), 0, 1);
    grid.add(comment, 1, 1);

    getDialogPane().setContent(grid);
  }

  public String getDescription()
  {
    return description.getText();
  }

  public String getComment()
  {
    return comment.getText();
  }

}
