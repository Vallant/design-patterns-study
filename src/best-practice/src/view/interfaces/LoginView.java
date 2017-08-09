/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.interfaces;

import controller.interfaces.LoginController;
import data.User;

/**
 * @author stephan
 */
public interface LoginView
{
  void switchToResetPassword();

  void switchToAddNewUser();

  void switchToLogin();

  void showLoginFailed();

  void removeAllComponents();

  User getEnteredUser();

  String getEnteredEmail();

  String getEnteredUsername();

  char[] getEnteredPassword();

  void showDialog(String message);

  void setController(LoginController controller);

  void showError(String localizedMessage);
}
