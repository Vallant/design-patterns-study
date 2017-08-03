package view.swing.personalstatistic;

import com.github.lgooddatepicker.components.DateTimePicker;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class PersonalStatisticUpdateActivityDialogPanel extends JPanel
{
    final JLabel lbDescription;
    final JLabel lbComment;
    final JTextField tfDescription;
    final JTextField tfComment;

    final JLabel lbStartTime;
    final DateTimePicker dpStartTime;
    final JLabel lbEndTime;
    final DateTimePicker dpEndTime;

    final JPanel pBottom;
    final JButton btCancel;
    final JButton btOk;


    final private void initComponents()
    {

    }

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


        setOpaque(true);
        setBackground(Color.BLUE.darker());

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(4, 2, 5, 5));
        centerPanel.setOpaque(true);
        centerPanel.setBackground(Color.WHITE);

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
