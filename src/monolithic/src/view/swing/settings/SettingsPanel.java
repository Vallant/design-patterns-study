package view.swing.settings;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

class SettingsPanel extends JPanel
{
  private final JPanel         pMain;
  private final JLabel         lbFirst;
  final         JTextField     tfFirst;
  private final JLabel         lbLast;
  final         JTextField     tfLast;
  private final JLabel         lbEmail;
  final         JTextField     tfEmail;
  private final JLabel         lbOldPw;
  final         JPasswordField tfOldPw;
  private final JLabel         lbNewPw;
  final         JPasswordField tfNewPw;
  private final JLabel         lbNewPwAgain;
  final         JPasswordField tfNewPwAgain;

  private final JPanel  pBottom;
  final         JButton btApply;
  final         JButton btReset;

  public SettingsPanel()
  {
    super(new BorderLayout(5, 5));
    setBorder(new EtchedBorder());

    pMain = new JPanel(new GridLayout(6, 2, 5, 5));
    lbFirst = new JLabel("First Name");
    lbLast = new JLabel("Last Name");
    tfFirst = new JTextField("");
    tfLast = new JTextField("");
    lbEmail = new JLabel("Email");
    tfEmail = new JTextField("");
    lbOldPw = new JLabel("Old Password");
    tfOldPw = new JPasswordField();
    lbNewPw = new JLabel("New Password");
    tfNewPw = new JPasswordField();
    lbNewPwAgain = new JLabel("Repeat new Password");
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

    pBottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
    btApply = new JButton("Apply Changes");
    btReset = new JButton("Discard Changes");
    pBottom.add(btApply);
    pBottom.add(btReset);

    add(pBottom, BorderLayout.SOUTH);


  }
}
