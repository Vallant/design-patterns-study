package view.swing.settings;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

class SettingsPanel extends JPanel
{
  final JTextField     tfFirst;
  final JTextField     tfLast;
  final JTextField     tfEmail;
  final JPasswordField tfOldPw;
  final JPasswordField tfNewPw;
  final JPasswordField tfNewPwAgain;

  final JButton btApply;
  final JButton btReset;

  public SettingsPanel()
  {
    super(new BorderLayout(5, 5));
    setBorder(new EtchedBorder());

    JPanel pMain = new JPanel(new GridLayout(6, 2, 5, 5));
    JLabel lbFirst = new JLabel("First Name");
    JLabel lbLast = new JLabel("Last Name");
    tfFirst = new JTextField("");
    tfLast = new JTextField("");
    JLabel lbEmail = new JLabel("Email");
    tfEmail = new JTextField("");
    JLabel lbOldPw = new JLabel("Old Password");
    tfOldPw = new JPasswordField();
    JLabel lbNewPw = new JLabel("New Password");
    tfNewPw = new JPasswordField();
    JLabel lbNewPwAgain = new JLabel("Repeat new Password");
    tfNewPwAgain = new JPasswordField();

    pMain.add(lbFirst);
    pMain.add(tfFirst);
    pMain.add(lbLast);
    pMain.add(tfLast);
    pMain.add(lbEmail);
    pMain.add(tfEmail);
    pMain.add(lbOldPw);
    pMain.add(tfOldPw);
    pMain.add(lbNewPw);
    pMain.add(tfNewPw);
    pMain.add(lbNewPwAgain);
    pMain.add(tfNewPwAgain);
    JPanel pFlow = new JPanel(new FlowLayout(FlowLayout.LEFT));
    pFlow.add(pMain);
    add(pFlow, BorderLayout.CENTER);

    JPanel pBottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
    btApply = new JButton("Apply Changes");
    btReset = new JButton("Discard Changes");
    pBottom.add(btApply);
    pBottom.add(btReset);

    add(pBottom, BorderLayout.SOUTH);


  }
}
