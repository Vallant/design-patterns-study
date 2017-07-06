/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.interfaces;

import model.interfaces.LoginModel;
import view.interfaces.LoginView;

/**
 *
 * @author stephan
 */
public interface LoginController
{
    
    void SetModel(LoginModel model);
    void SetView(LoginView view);
    
    void ResetPasswordClicked();
    void LoginClicked();
    void AddUserClicked();
    
    void BackToLoginClicked();
    void AddClicked();
    void ResetClicked();

    void showDialog(String message);

    public void loginFailed();
}
