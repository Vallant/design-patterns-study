package view.swing.activitybar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by stephan on 08.07.17.
 */
public class ActivityBarDialogPanel extends JPanel
{
    public final JTextField tfDescription;
    public final JTextField tfComment;

    public ActivityBarDialogPanel() {
        super();

        tfDescription = new JTextField();
        tfComment = new JTextField();

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 2, 5, 5));
        centerPanel.setBorder(new EmptyBorder(5,5,5,5));

        JLabel lbDescription = new JLabel("Enter Activity Description : ");
        JLabel lbComment = new JLabel("Enter Activity Comment     : ");

        centerPanel.add(lbDescription);
        centerPanel.add(tfDescription);
        centerPanel.add(lbComment);
        centerPanel.add(tfComment);

        add(centerPanel);
    }
}
