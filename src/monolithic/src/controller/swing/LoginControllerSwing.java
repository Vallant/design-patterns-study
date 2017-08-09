/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.swing;

import controller.interfaces.LoginController;
import data.User;
import java.util.Arrays;
import model.interfaces.LoginModel;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import view.interfaces.LoginView;

/**
 *
 * @author stephan
 */
public class LoginControllerSwing implements LoginController
{
    private LoginView view;
    private LoginModel model;

    public LoginControllerSwing()
    {
        
    }
    
    @Override
    public void resetPasswordClicked()
    {
        view.switchToResetPassword();
    }

    @Override
    public void loginClicked()
    {
        String username = view.getEnteredUsername();
        char[] password = view.getEnteredPassword();
        if(username.isEmpty())
            view.showError("Please provide a valid username");
        else if(password.length == 0)
            view.showError("Please provide a valid password");
        else
            try {
                model.login(username, password);
            } catch (Exception e) {
                e.printStackTrace();
                view.showError(e.getLocalizedMessage());
            }
    }

    @Override
    public void addUserClicked()
    {
        view.switchToAddNewUser();
    }

    @Override
    public void backToLoginClicked()
    {
        view.switchToLogin();
    }

    @Override
    public void addClicked()
    {
        User u = view.getEnteredUser();
        if((u.getEmail().isEmpty() ||
            u.getFirstName().isEmpty() ||
            u.getLastName().isEmpty() ||
            u.getLoginName().isEmpty() ||
            u.getNewPassword().length == 0))
                view.showError("Please fill all fields");
        else if(!Arrays.equals(u.getNewPassword(), u.getNewPasswordAgain()))
            view.showError("The passwords do not match");
        else
            try {
                model.saveNewUser(u);
            } catch (Exception e) {
                e.printStackTrace();
                view.showError(e.getLocalizedMessage());
            }


    }

    @Override
    public void resetClicked()
    {
        throw new NotImplementedException();
    }

    @Override
    public void setView(LoginView view)
    {
        this.view = view;
    }

    @Override
    public void showDialog(String message) {
        view.showDialog(message);
    }

    @Override
    public void loginFailed()
    {
        view.showLoginFailed();
    }

    private boolean isValid(User u)
    {
        return !(u.getEmail().isEmpty() ||
                 u.getFirstName().isEmpty() ||
                 u.getLastName().isEmpty() ||
                 u.getLoginName().isEmpty() ||
                 u.getNewPassword().length == 0);
    }

    @Override
    public void setModel(LoginModel model)
    {
        this.model = model;
    }
    
}
