package view.javafx.personalstatistic;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import jfxtras.scene.control.LocalDateTimePicker;

import java.time.LocalDateTime;

public class PersonalStatisticActivityUpdateDialog extends Dialog<ButtonType>
{
  private final Label     lbDescription;
  private final Label     lbComment;
  final         TextField tfDescription;
  final         TextField tfComment;

  private final Label               lbStartTime;
  final         LocalDateTimePicker dpStartTime;
  private final Label               lbEndTime;
  final         LocalDateTimePicker          dpEndTime;


  public PersonalStatisticActivityUpdateDialog(String description, String comment, LocalDateTime start, LocalDateTime end)
  {
    this();

    tfDescription.setText(description);
    tfComment.setText(comment);
    dpStartTime.setLocalDateTime(start);
    dpEndTime.setLocalDateTime(end);
  }

  public PersonalStatisticActivityUpdateDialog()
  {
    lbDescription = new Label("Description");
    tfDescription = new TextField();
    lbComment = new Label("Comment");
    tfComment = new TextField();
    lbStartTime = new Label("Start time");

    dpStartTime = new LocalDateTimePicker();

    lbEndTime = new Label("End time");
    dpEndTime = new LocalDateTimePicker();

    GridPane centerPanel = new GridPane();

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
