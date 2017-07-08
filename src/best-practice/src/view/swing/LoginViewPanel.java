package view.swing;

import javax.swing.*;
import java.awt.*;

/**
 * Created by stephan on 08.07.17.
 */
public class LoginViewPanel extends JPanel{

    final JTextField tfUsername;
    final JPasswordField tfPassword;
    final JButton btResetPassword;
    final JButton btAddNewUser;
    final JButton btLogin;

    public LoginViewPanel()
    {
        setLayout(new BorderLayout(5, 5));

        JPanel buttons = new JPanel(new GridLayout(1, 3, 5, 5));
        btAddNewUser = new JButton("Create new Account");
        btResetPassword = new JButton("Reset Password");
        btLogin = new JButton("Login");
        buttons.add(btAddNewUser);
        buttons.add(btResetPassword);
        buttons.add(btLogin);
        add(buttons, BorderLayout.SOUTH);

        JPanel main = new JPanel(new GridLayout(4, 1, 5, 5));
        JLabel lb1 = new JLabel("Username");
        JLabel lb2 = new JLabel("Password");
        tfPassword = new JPasswordField();
        tfUsername = new JTextField();
        main.add(lb1);
        main.add(tfUsername);
        main.add(lb2);
        main.add(tfPassword);
        add(main);
    }
}
