/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.interfaces;

import view.interfaces.LoginView;

/**
 *
 * @author stephan
 */
public interface LoginController
{
    void ResetPasswordClicked();
    void LoginClicked();
    void AddUserClicked();
    
    void BackToLoginClicked();
    void AddClicked();
    void ResetClicked();
    
    void SetView(LoginView view);

    void showError(String localizedMessage);

    public void loginFailed();
}
