package view.swing.personalstatistic;

import com.github.lgooddatepicker.components.DateTimePicker;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

class PersonalStatisticUpdateActivityDialogPanel extends JPanel
{
  final JTextField tfDescription;
  final JTextField tfComment;

  final DateTimePicker dpStartTime;
  final DateTimePicker dpEndTime;


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
    JLabel lbDescription = new JLabel("Description");
    tfDescription = new JTextField();
    JLabel lbComment = new JLabel("Comment");
    tfComment = new JTextField();
    JLabel lbStartTime = new JLabel("Start time");

    dpStartTime = new DateTimePicker();
    JLabel lbEndTime = new JLabel("End time");
    dpEndTime = new DateTimePicker();

    JPanel pBottom = new JPanel(new FlowLayout(5));
    JButton btCancel = new JButton("Cancel");
    JButton btOk = new JButton("OK");


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
