package view.swing.project;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by stephan on 09.07.17.
 */
class ProjectAddPhasePanel extends JPanel
{

  final JTextField tfName;

  public ProjectAddPhasePanel()
  {
    super();

    tfName = new JTextField();

    JPanel centerPanel = new JPanel();
    centerPanel.setLayout(new GridLayout(1, 2, 5, 5));
    centerPanel.setBorder(new EmptyBorder(5, 5, 5, 5));


    JLabel lbName = new JLabel("Enter Phase Name       : ");

    centerPanel.add(lbName);
    centerPanel.add(tfName);

    add(centerPanel);
  }
}
