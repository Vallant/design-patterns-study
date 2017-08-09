/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.swing.login;

import controller.swing.LoginControllerSwing;
import data.User;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author stephan
 */
public class LoginViewSwing
{
    private LoginControllerSwing controller;
    
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
    

    public void switchToResetPassword()
    {
        hide();
        frame.add(pResetPassword);
        update();
    }


    public void switchToAddNewUser()
    {
        hide();
        frame.add(pNewUser);
        update();
    }


    public void showLoginFailed()
    {
        JOptionPane.showMessageDialog(frame, "Password or Username incorrect.", "Login failed!", JOptionPane.ERROR_MESSAGE);
    }


    public User getEnteredUser()
    {
        return new User(pNewUser.tfLoginName.getText(), pNewUser.tfFirstName.getText(), pNewUser.tfLastName.getText(), User.ROLE.USER, pNewUser.tfEmailNew.getText(), pNewUser.tfPasswordNew.getPassword(), pNewUser.tfPasswordNewAgain.getPassword());
    }


    public String getEnteredEmail()
    {
        return pResetPassword.tfEmail.getText();
    }


    public String getEnteredUsername()
    {
        return pLogin.tfUsername.getText();
    }


    public char[] getEnteredPassword()
    {
        return pLogin.tfPassword.getPassword();
    }


    public void removeAllComponents()
    {
        hide();
        update();
    }


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

            public void actionPerformed(ActionEvent ae)
            {
                controller.loginClicked();
            }
        });

        pLogin.btResetPassword.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent ae)
            {
                controller.resetPasswordClicked();
            }
        }
        );

        pLogin.btAddNewUser.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent ae)
            {
                controller.addUserClicked();
            }
        });

        pNewUser.btBackNew.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae)
            {
                controller.backToLoginClicked();
            }
        });
        
        pResetPassword.btBackReset.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae)
            {
                controller.backToLoginClicked();
            }
        });
        
        pNewUser.btAdd.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent ae)
            {
                controller.addClicked();
            }
        });
        
        pResetPassword.btReset.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent ae)
            {
                controller.resetClicked();
            }
        });

    }


    public void showDialog(String message) {
        JOptionPane.showMessageDialog(frame, message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }


    public void setController(LoginControllerSwing controller)
    {
        this.controller = controller;
    }


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
