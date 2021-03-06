package view.swing.activitybar;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

/**
 * Created by stephan on 08.07.17.
 */
class ActivityBarPanel extends JPanel
{

  final JComboBox<String> cbProject;
  final JComboBox<String> cbPhase;
  final JButton           btStart;
  final JButton           btStop;
  final JLabel            lbDuration;

  public ActivityBarPanel()
  {
    super(new FlowLayout(5));
    JPanel pMain = new JPanel(new GridLayout(1, 5, 5, 0));

    JPanel pFlow1 = new JPanel(new FlowLayout());
    this.cbProject = new JComboBox<>();

    JLabel lbProject = new JLabel("Project:");

    JPanel pFlow2 = new JPanel(new FlowLayout());
    this.cbPhase = new JComboBox<>();
    JLabel lbPhase = new JLabel("Phase:");

    this.btStart = new JButton("Start Activity");
    this.btStop = new JButton("Stop Activity");
    this.lbDuration = new JLabel();
    lbDuration.setPreferredSize(new Dimension(50, 20));
    btStart.setEnabled(false);
    btStop.setEnabled(false);


    pFlow1.add(lbProject);
    pFlow1.add(cbProject);
    pMain.add(pFlow1);
    pFlow2.add(lbPhase);
    pFlow2.add(cbPhase);

    JPanel pFlow3 = new JPanel(new FlowLayout());
    pFlow3.add(btStart);
    JPanel pFlow4 = new JPanel(new FlowLayout());
    pFlow4.add(btStop);
    pMain.add(pFlow2);
    pMain.add(pFlow3);
    pMain.add(pFlow4);
    pMain.add(lbDuration);
    add(pMain);

    setBorder(new EtchedBorder());

  }
}
