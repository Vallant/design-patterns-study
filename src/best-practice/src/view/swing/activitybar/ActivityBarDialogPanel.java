package view.swing.activitybar;

import javax.swing.*;
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
        setOpaque(true);
        setBackground(Color.BLUE.darker());

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 2, 5, 5));
        centerPanel.setBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5));
        centerPanel.setOpaque(true);
        centerPanel.setBackground(Color.WHITE);

        JLabel lbDescription = new JLabel("Enter Activity Description : ");
        JLabel lbComment = new JLabel("Enter Activity Comment     : ");

        centerPanel.add(lbDescription);
        centerPanel.add(tfDescription);
        centerPanel.add(lbComment);
        centerPanel.add(tfComment);

        add(centerPanel);
    }
}
