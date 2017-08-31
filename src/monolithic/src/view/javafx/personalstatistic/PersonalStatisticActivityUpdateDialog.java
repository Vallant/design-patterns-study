package view.javafx.personalstatistic;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import jfxtras.scene.control.LocalDateTimePicker;

import java.time.LocalDateTime;

class PersonalStatisticActivityUpdateDialog extends Dialog<ButtonType>
{
  final TextField tfDescription;
  final TextField tfComment;

  final LocalDateTimePicker dpStartTime;
  final LocalDateTimePicker dpEndTime;


  public PersonalStatisticActivityUpdateDialog(String description, String comment, LocalDateTime start,
                                               LocalDateTime end)
  {
    this();

    tfDescription.setText(description);
    tfComment.setText(comment);
    dpStartTime.setLocalDateTime(start);
    dpEndTime.setLocalDateTime(end);
  }

  public PersonalStatisticActivityUpdateDialog()
  {
    Label lbDescription = new Label("Description");
    tfDescription = new TextField();
    Label lbComment = new Label("Comment");
    tfComment = new TextField();
    Label lbStartTime = new Label("Start time");

    dpStartTime = new LocalDateTimePicker();

    Label lbEndTime = new Label("End time");
    dpEndTime = new LocalDateTimePicker();

    GridPane centerPanel = new GridPane();
    centerPanel.setPadding(new Insets(5,5,5,5));
    centerPanel.setVgap(5);
    ColumnConstraints columnConstraints = new ColumnConstraints();
    columnConstraints.setFillWidth(true);
    columnConstraints.setHgrow(Priority.ALWAYS);
    centerPanel.getColumnConstraints().add(columnConstraints);

    centerPanel.add(lbDescription, 0, 0);
    centerPanel.add(tfDescription, 1, 0);
    centerPanel.add(lbComment, 0, 1);
    centerPanel.add(tfComment, 1, 1);
    centerPanel.add(lbStartTime, 0, 2);
    centerPanel.add(dpStartTime, 1, 2);
    centerPanel.add(lbEndTime, 0, 3);
    centerPanel.add(dpEndTime, 1, 3);


    getDialogPane().setContent(centerPanel);
    getDialogPane().setPrefSize(350, 200);
    getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);


    setResizable(true);
  }
}
