package view.swing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by stephan on 08.07.17.
 */
public class ActivityBarPanel extends JPanel
{

    final JComboBox<String> cbProject;
    final JComboBox<String> cbPhase;
    final JButton btStart;
    final JButton btStop;
    final JLabel lbDuration;

    final JTextField tfDescription;
    final JTextField tfComment;

    public ActivityBarPanel() {
        super(new GridLayout(1,5, 5, 5));
        setBorder(new EmptyBorder(5,5,5,5));

        this.cbProject = new JComboBox<>();
        this.cbPhase = new JComboBox<>();
        this.btStart = new JButton("Start Activity");
        this.btStop = new JButton("Stop Activity");
        this.lbDuration = new JLabel();
        btStart.setEnabled(false);
        btStop.setEnabled(false);

        tfDescription = new JTextField();
        tfComment = new JTextField();



        add(cbProject);
        add(cbPhase);
        add(btStart);
        add(btStop);
        add(lbDuration);

    }
}
