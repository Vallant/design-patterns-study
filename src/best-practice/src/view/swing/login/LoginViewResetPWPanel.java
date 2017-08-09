package view.swing.login;

import javax.swing.*;
import java.awt.*;

/**
 * Created by stephan on 08.07.17.
 */
class LoginViewResetPWPanel extends JPanel
{


  final JTextField tfEmail;
  final JButton    btReset;
  final JButton    btBackReset;

  public LoginViewResetPWPanel()
  {
    super(new BorderLayout());
    JPanel main2 = new JPanel(new GridLayout(2, 1, 5, 5));
    JLabel lb3 = new JLabel("Email");
    tfEmail = new JTextField();
    main2.add(lb3);
    main2.add(tfEmail);
    add(main2, BorderLayout.CENTER);

    JPanel buttons2 = new JPanel(new GridLayout(1, 2, 5, 5));
    btBackReset = new JButton("Back to Login");
    btReset = new JButton("Reset Password");
    buttons2.add(btBackReset);
    buttons2.add(btReset);
    add(buttons2, BorderLayout.SOUTH);
  }
}
