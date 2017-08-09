package view.swing.personalstatistic;

import com.github.lgooddatepicker.components.DateTimePicker;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

class PersonalStatisticUpdateActivityDialogPanel extends JPanel
{
  private final JLabel     lbDescription;
  private final JLabel     lbComment;
  final         JTextField tfDescription;
  final         JTextField tfComment;

  private final JLabel         lbStartTime;
  final         DateTimePicker dpStartTime;
  private final JLabel         lbEndTime;
  final         DateTimePicker dpEndTime;

  private final JPanel  pBottom;
  private final JButton btCancel;
  private final JButton btOk;


  public PersonalStatisticUpdateActivityDialogPanel(String description, String comment, LocalDate start, LocalDate end)
  {
    this();
    tfDescription.setText(description);
    tfComment.setText(comment);
    dpStartTime.getDatePicker().setDate(start);
    dpEndTime.getDatePicker().setDate(end);
  }

  public PersonalStatisticUpdateActivityDialogPanel()
  {
    lbDescription = new JLabel("Description");
    tfDescription = new JTextField();
    lbComment = new JLabel("Comment");
    tfComment = new JTextField();
    lbStartTime = new JLabel("Start time");

    dpStartTime = new DateTimePicker();
    lbEndTime = new JLabel("End time");
    dpEndTime = new DateTimePicker();

    pBottom = new JPanel(new FlowLayout(5));
    btCancel = new JButton("Cancel");
    btOk = new JButton("OK");


    JPanel centerPanel = new JPanel();
    centerPanel.setLayout(new GridLayout(4, 2, 5, 5));

    centerPanel.add(lbDescription);
    centerPanel.add(tfDescription);
    centerPanel.add(lbComment);
    centerPanel.add(tfComment);
    centerPanel.add(lbStartTime);
    centerPanel.add(dpStartTime);
    centerPanel.add(lbEndTime);
    centerPanel.add(dpEndTime);

    add(centerPanel);
  }
}
