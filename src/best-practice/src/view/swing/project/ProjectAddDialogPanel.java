package view.swing.project;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by stephan on 09.07.17.
 */
public class ProjectAddDialogPanel extends JPanel
{
  final JTextField tfName;
  final JTextField tfDescription;

  public ProjectAddDialogPanel()
  {
    super();
    tfDescription = new JTextField();
    tfName = new JTextField();

    JPanel centerPanel = new JPanel();
    centerPanel.setLayout(new GridLayout(2, 2, 5, 5));
    centerPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

    JLabel lbDescription = new JLabel("Enter Project Name       : ");
    JLabel lbComment = new JLabel("Enter Project Description: ");

    centerPanel.add(lbDescription);
    centerPanel.add(tfDescription);
    centerPanel.add(lbComment);
    centerPanel.add(tfName);

    add(centerPanel);
  }
}
