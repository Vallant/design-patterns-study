/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.swing.login;

import controller.interfaces.LoginController;
import data.User;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import view.interfaces.LoginView;

/**
 *
 * @author stephan
 */
public class LoginViewSwing implements LoginView
{
    private LoginController controller;
    
    private final JFrame frame;

    private final LoginViewPanel pLogin;
    private final LoginViewRegisterPanel pNewUser;
    private final LoginViewResetPWPanel pResetPassword;


    public LoginViewSwing(JFrame frame)
    {
        this.frame = frame;
        
        //Main Login Panel
        pLogin = new LoginViewPanel();
        
        //Reset Password

        pResetPassword = new LoginViewResetPWPanel();
        //New Account
        pNewUser = new LoginViewRegisterPanel();

        setListeners();
    }
    
    @Override
    public void switchToResetPassword()
    {
        hide();
        frame.add(pResetPassword);
        update();
    }

    @Override
    public void switchToAddNewUser()
    {
        hide();
        frame.add(pNewUser);
        update();
    }

    @Override
    public void showLoginFailed()
    {
        JOptionPane.showMessageDialog(frame, "Password or Username incorrect.", "Login failed!", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public User getEnteredUser()
    {
        return new User(pNewUser.tfLoginName.getText(), pNewUser.tfFirstName.getText(), pNewUser.tfLastName.getText(), User.ROLE.USER, pNewUser.tfEmailNew.getText(), pNewUser.tfPasswordNew.getPassword(), pNewUser.tfPasswordNewAgain.getPassword());
    }

    @Override
    public String getEnteredEmail()
    {
        return pResetPassword.tfEmail.getText();
    }

    @Override
    public String getEnteredUsername()
    {
        return pLogin.tfUsername.getText();
    }

    @Override
    public char[] getEnteredPassword()
    {
        return pLogin.tfPassword.getPassword();
    }

    @Override
    public void removeAllComponents()
    {
        hide();
        update();
    }

    @Override
    public void switchToLogin()
    {
        hide();
        frame.add(pLogin);
        pLogin.tfUsername.setText("");
        pLogin.tfPassword.setText("");
        update();
    }
    
    private void setListeners()
    {
        pLogin.btLogin.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                controller.loginClicked();
            }
        });

        pLogin.btResetPassword.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                controller.resetPasswordClicked();
            }
        }
        );

        pLogin.btAddNewUser.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                controller.addUserClicked();
            }
        });

        pNewUser.btBackNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                controller.backToLoginClicked();
            }
        });
        
        pResetPassword.btBackReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                controller.backToLoginClicked();
            }
        });
        
        pNewUser.btAdd.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                controller.addClicked();
            }
        });
        
        pResetPassword.btReset.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                controller.resetClicked();
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


    private void hide()
    {
        frame.remove(pLogin);
        frame.remove(pNewUser);
        frame.remove(pResetPassword);
    }

    private void update()
    {
        frame.revalidate();
        frame.repaint();
        frame.pack();
    }
}
