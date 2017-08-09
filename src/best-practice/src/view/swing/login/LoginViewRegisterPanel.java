package view.swing.login;

import javax.swing.*;
import java.awt.*;

/**
 * Created by stephan on 08.07.17.
 */
class LoginViewRegisterPanel extends JPanel
{

  final JTextField     tfLoginName;
  final JTextField     tfFirstName;
  final JTextField     tfLastName;
  final JTextField     tfEmailNew;
  final JPasswordField tfPasswordNew;
  final JPasswordField tfPasswordNewAgain;
  final JButton        btBackNew;
  final JButton        btAdd;

  public LoginViewRegisterPanel()
  {
    super(new BorderLayout(5, 5));
    JPanel main3 = new JPanel(new GridLayout(12, 1, 5, 5));

    JLabel lb4 = new JLabel("Username");
    tfLoginName = new JTextField();
    tfLoginName.setPreferredSize(new Dimension(125, 25));
    JLabel lb5 = new JLabel("First Name");
    tfFirstName = new JTextField();
    JLabel lb6 = new JLabel("Last Name");
    tfLastName = new JTextField();
    JLabel lb7 = new JLabel("Email");
    tfEmailNew = new JTextField();
    JLabel lb8 = new JLabel("Password");
    tfPasswordNew = new JPasswordField();
    JLabel lb9 = new JLabel("Repeat Password");
    tfPasswordNewAgain = new JPasswordField();

    main3.add(lb4);
    main3.add(tfLoginName);
    main3.add(lb5);
    main3.add(tfFirstName);
    main3.add(lb6);
    main3.add(tfLastName);
    main3.add(lb7);
    main3.add(tfEmailNew);
    main3.add(lb8);
    main3.add(tfPasswordNew);
    main3.add(lb9);
    main3.add(tfPasswordNewAgain);
    add(main3, BorderLayout.CENTER);

    JPanel buttons3 = new JPanel(new GridLayout(1, 2, 5, 5));
    btAdd = new JButton("Create Account");
    btBackNew = new JButton("Back to Login");
    buttons3.add(btBackNew);
    buttons3.add(btAdd);
    add(buttons3, BorderLayout.SOUTH);

  }
}
