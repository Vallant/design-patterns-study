/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.standard;

import data.User;
import model.impl.LoginModelImpl;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import view.javafx.login.LoginViewFX;
import view.swing.login.LoginViewSwing;

import java.util.Arrays;

/**
 * @author stephan
 */
public class LoginControllerStandard
{
  private LoginViewSwing viewSwing;
  private LoginViewFX viewFX;
  private LoginModelImpl model;

  public LoginControllerStandard()
  {

  }


  public void resetPasswordClicked()
  {
    viewSwing.switchToResetPassword();
  }


  public void loginClicked()
  {
    String username;
    char[] password;
    if(viewSwing != null)
    {
      username = viewSwing.getEnteredUsername();
      password = viewSwing.getEnteredPassword();
    }
    else
    {
      username = viewFX.getEnteredUsername();
      password = viewFX.getEnteredPassword();
    }
    if(username.isEmpty())
    {
      if(viewSwing != null)
        viewSwing.showError("Please provide a valid username");
      else
        viewFX.showError("Please provide a valid username");
    }
    else if(password.length == 0)
    {
      if(viewSwing != null)
        viewSwing.showError("Please provide a valid password");
      else
        viewFX.showError("Please provide a valid password");
    }
    else
      try
      {
        model.login(username, password);
      }
      catch(Exception e)
      {
        e.printStackTrace();
        if(viewSwing != null)
          viewSwing.showError(e.getLocalizedMessage());
        else
          viewFX.showError(e.getLocalizedMessage());
      }
  }


  public void addUserClicked()
  {
    if(viewSwing != null)
      viewSwing.switchToAddNewUser();
    else
      viewFX.switchToAddNewUser();
  }


  public void backToLoginClicked()
  {
    if(viewSwing != null)
      viewSwing.switchToLogin();
    else
      viewFX.switchToLogin();
  }


  public void addClicked()
  {
    User u;
    if(viewSwing != null)
      u = viewSwing.getEnteredUser();
    else
      u = viewFX.getEnteredUser();

    if((u.getEmail().isEmpty() ||
        u.getFirstName().isEmpty() ||
        u.getLastName().isEmpty() ||
        u.getLoginName().isEmpty() ||
        u.getNewPassword().length == 0))
    {
      if(viewSwing != null)
        viewSwing.showError("Please fill all fields");
      else
        viewFX.showError("Please fill all fields");
    }
    else if(!Arrays.equals(u.getNewPassword(), u.getNewPasswordAgain()))
    {
      if(viewSwing != null)
        viewSwing.showError("The passwords do not match");
      else
        viewFX.showError("The passwords do not match");
    }
    else
      try
      {
        model.saveNewUser(u);
      }
      catch(Exception e)
      {
        e.printStackTrace();
        if(viewSwing != null)
          viewSwing.showError(e.getLocalizedMessage());
        else
          viewFX.showError(e.getLocalizedMessage());
      }


  }

  public void resetClicked()
  {
    throw new NotImplementedException();
  }


  public void setViewSwing(LoginViewSwing viewSwing)
  {
    this.viewSwing = viewSwing;
  }

  public void setViewFX(LoginViewFX viewFX)
  {
    this.viewFX = viewFX;
  }


  public void showDialog(String message)
  {
    if(viewSwing != null)
      viewSwing.showDialog(message);
    else
      viewFX.showDialog(message);
  }


  public void loginFailed()
  {
    if(viewSwing != null)
      viewSwing.showLoginFailed();
    else
      viewFX.showLoginFailed();
  }

  private boolean isValid(User u)
  {
    return !(u.getEmail().isEmpty() ||
             u.getFirstName().isEmpty() ||
             u.getLastName().isEmpty() ||
             u.getLoginName().isEmpty() ||
             u.getNewPassword().length == 0);
  }


  public void setModel(LoginModelImpl model)
  {
    this.model = model;
  }

}
