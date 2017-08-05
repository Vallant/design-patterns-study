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
    void setModel(LoginModel model);
    void setView(LoginView view);
    
    void resetPasswordClicked();
    void loginClicked();
    void addUserClicked();
    void backToLoginClicked();
    void addClicked();
    void resetClicked();

    void showDialog(String message);
    void loginFailed();
}
