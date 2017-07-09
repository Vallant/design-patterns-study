package view.swing;

import javax.swing.*;
import java.awt.*;

/**
 * Created by stephan on 09.07.17.
 */
public class ProjectAddPhasePanel extends JPanel
{

    public final JTextField tfName;

    public ProjectAddPhasePanel() {
        super();

        tfName = new JTextField();
        setOpaque(true);
        setBackground(Color.BLUE.darker());

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, 2, 5, 5));
        centerPanel.setBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5));
        centerPanel.setOpaque(true);
        centerPanel.setBackground(Color.WHITE);

        JLabel lbName = new JLabel(  "Enter Phase Name       : ");

        centerPanel.add(lbName);
        centerPanel.add(tfName);

        add(centerPanel);
    }
}
