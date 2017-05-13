/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.interfaces;

import controller.interfaces.LoginController;
import data.User;

/**
 *
 * @author stephan
 */
public interface LoginView
{
    void SwitchToResetPassword();
    void SwitchToAddNewUser();
    void SwitchToLogin();
    void ShowLoginFailed();
    void RemoveAllComponents();
            
    User getEnteredUser();
    String getEnteredEmail();
    String getEnteredUsername();
    char[] getEnteredPassword();

    public void showError(String localizedMessage);

    public void setController(LoginController controller);
}
