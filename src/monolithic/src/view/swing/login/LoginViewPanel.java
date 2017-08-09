package view.swing.login;

import javax.swing.*;
import java.awt.*;

/**
 * Created by stephan on 08.07.17.
 */
public class LoginViewPanel extends JPanel
{

  final JTextField     tfUsername;
  final JPasswordField tfPassword;
  final JButton        btResetPassword;
  final JButton        btAddNewUser;
  final JButton        btLogin;

  public LoginViewPanel()
  {
    super(new BorderLayout(5, 5));
    setPreferredSize(new Dimension(355, 165));

    JPanel buttons = new JPanel(new GridLayout(1, 3, 5, 5));
    btAddNewUser = new JButton("Create new Account");
    btResetPassword = new JButton("Reset Password");
    btLogin = new JButton("Login");
    buttons.add(btAddNewUser);
    //buttons.add(btResetPassword);
    buttons.add(btLogin);
    JPanel flow2 = new JPanel(new FlowLayout(0));
    flow2.add(buttons);
    add(flow2, BorderLayout.SOUTH);

    JPanel main = new JPanel(new GridLayout(4, 1, 5, 5));
    JPanel flow = new JPanel(new FlowLayout(0));

    JLabel lb1 = new JLabel("Username");
    JLabel lb2 = new JLabel("Password");
    tfPassword = new JPasswordField();
    tfPassword.setPreferredSize(new Dimension(345, 25));
    tfUsername = new JTextField();
    tfUsername.setPreferredSize(new Dimension(345, 25));
    main.add(lb1);
    main.add(tfUsername);
    main.add(lb2);
    main.add(tfPassword);
    flow.add(main, BorderLayout.CENTER);
    add(flow);
  }
}
