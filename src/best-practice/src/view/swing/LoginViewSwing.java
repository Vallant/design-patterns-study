/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.swing;

import controller.interfaces.LoginController;
import data.User;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.tree.DefaultTreeCellEditor;
import view.interfaces.LoginView;

/**
 *
 * @author stephan
 */
public class LoginViewSwing implements LoginView
{
    private LoginController controller;
    
    private final JFrame frame;
    
    private final JPanel pLogin;
    private final JTextField tfUsername;
    private final JPasswordField tfPassword;
    private final JButton btResetPassword;
    private final JButton btAddNewUser;
    private final JButton btLogin;
    
    private final JPanel pNewUser;
    private final JTextField tfLoginName;
    private final JTextField tfFirstName;
    private final JTextField tfLastName;
    private final JTextField tfEmailNew;
    private final JPasswordField tfPasswordNew;
    private final JPasswordField tfPasswordNewAgain;
    private final JButton btBackNew;
    private final JButton btAdd;
   
    private final JPanel pResetPassword; 
    private final JTextField tfEmail;
    private final JButton btReset;
    private final JButton btBackReset;

    public LoginViewSwing(JFrame frame)
    {
        this.frame = frame;
        
        //Main Login Panel
        pLogin = new JPanel();
        pLogin.setLayout(new BorderLayout(5, 5));
        
        JPanel buttons = new JPanel(new GridLayout(1, 3, 5, 5));
        btAddNewUser = new JButton("Create new Account");
        btResetPassword = new JButton("Reset Password");
        btLogin = new JButton("Login");
        buttons.add(btAddNewUser);
        buttons.add(btResetPassword);
        buttons.add(btLogin);
        pLogin.add(buttons, BorderLayout.SOUTH);
        
        JPanel main = new JPanel(new GridLayout(4, 1, 5, 5));
        JLabel lb1 = new JLabel("Username");
        JLabel lb2 = new JLabel("Password");
        tfPassword = new JPasswordField();
        tfUsername = new JTextField();
        main.add(lb1);
        main.add(tfUsername);
        main.add(lb2);
        main.add(tfPassword);
        pLogin.add(main);
        
        
        //Reset Password
        pResetPassword = new JPanel(new BorderLayout());
        JPanel main2 = new JPanel(new GridLayout(2,1,5,5));
        JLabel lb3 = new JLabel("Email");
        tfEmail = new JTextField();
        main2.add(lb3);
        main2.add(tfEmail);
        pResetPassword.add(main2, BorderLayout.CENTER);
        
        JPanel buttons2 = new JPanel(new GridLayout(1,2,5,5));
        btBackReset = new JButton("Back to Login");
        btReset = new JButton("Reset Password");
        buttons2.add(btBackReset);
        buttons2.add(btReset);
        pResetPassword.add(buttons2, BorderLayout.SOUTH);
        
        //New Account
        pNewUser = new JPanel(new BorderLayout());
        JPanel main3 = new JPanel(new GridLayout(12,1,5,5));
        
        JLabel lb4 = new JLabel("Username");
        tfLoginName = new JTextField();
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
        pNewUser.add(main3, BorderLayout.CENTER);
        
        JPanel buttons3 = new JPanel(new GridLayout(1,2,5,5));
        btAdd = new JButton("Create Account");
        btBackNew = new JButton("Back to Login");
        buttons3.add(btBackNew);
        buttons3.add(btAdd);
        pNewUser.add(buttons3, BorderLayout.SOUTH);
        
        setListeners();
    }
    
    @Override
    public void SwitchToResetPassword()
    {
        removeAll();
        frame.add(pResetPassword);
        update();
    }

    @Override
    public void SwitchToAddNewUser()
    {
        removeAll();
        frame.add(pNewUser);
        update();
    }

    @Override
    public void ShowLoginFailed()
    {
        JOptionPane.showMessageDialog(frame, "Password or Username incorrect.", "Loign failed!", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public User getEnteredUser()
    {
        return new User(tfLoginName.getText(), tfFirstName.getText(), tfLastName.getText(), User.ROLE.USER, tfEmailNew.getText(), tfPasswordNew.getPassword(), tfPasswordNewAgain.getPassword());
    }

    @Override
    public String getEnteredEmail()
    {
        return tfEmail.getText();
    }

    @Override
    public String getEnteredUsername()
    {
        return tfUsername.getText();
    }

    @Override
    public char[] getEnteredPassword()
    {
        return tfPassword.getPassword();
    }

    @Override
    public void RemoveAllComponents()
    {
        removeAll();
        update();
    }

    @Override
    public void SwitchToLogin()
    {
        removeAll();
        frame.add(pLogin);
        update();
    }
    
    private void setListeners()
    {
        btLogin.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                controller.LoginClicked();
            }
        });
        
        btResetPassword.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                controller.ResetPasswordClicked();
            }
        }
        );
        
        btAddNewUser.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                controller.AddUserClicked();
            }
        });
        
        btBackNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                controller.BackToLoginClicked();
            }
        });
        
        btBackReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                controller.BackToLoginClicked();
            }
        });
        
        btAdd.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                controller.AddClicked();
            }
        });
        
        btReset.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                controller.ResetClicked();
            }
        });
        
        
        
    }



    @Override
    public void showDialog(String message) {
        JOptionPane.showMessageDialog(frame, message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void setController(LoginController controller)
    {
        this.controller = controller;
    }

    @Override
    public void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }


    private void removeAll()
    {
        frame.remove(pLogin);
        frame.remove(pNewUser);
        frame.remove(pResetPassword);
    }

    private void update()
    {
        frame.revalidate();
        frame.repaint();
    }
}
