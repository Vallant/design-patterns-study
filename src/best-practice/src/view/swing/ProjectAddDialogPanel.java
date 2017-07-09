package view.swing;

import javax.swing.*;
import java.awt.*;

/**
 * Created by stephan on 09.07.17.
 */
public class ProjectAddDialogPanel extends JPanel
{
    public final JTextField tfName;
    public final JTextField tfDescription;

    public ProjectAddDialogPanel() {
        super();

        tfDescription = new JTextField();
        tfName = new JTextField();
        setOpaque(true);
        setBackground(Color.BLUE.darker());

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 2, 5, 5));
        centerPanel.setBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5));
        centerPanel.setOpaque(true);
        centerPanel.setBackground(Color.WHITE);

        JLabel lbDescription = new JLabel(  "Enter Project Name       : ");
        JLabel lbComment = new JLabel(      "Enter Project Description: ");

        centerPanel.add(lbDescription);
        centerPanel.add(tfDescription);
        centerPanel.add(lbComment);
        centerPanel.add(tfName);

        add(centerPanel);
    }
}
