package controller.javafx;

import controller.interfaces.LoginController;
import model.interfaces.LoginModel;
import view.interfaces.LoginView;

public class LoginControllerFX implements LoginController
{
  private LoginModel model;
  private LoginView view;

  @Override
  public void setModel(LoginModel model)
  {
    this.model = model;
  }

  @Override
  public void setView(LoginView view)
  {
    this.view = view;
  }

  @Override
  public void resetPasswordClicked()
  {

  }

  @Override
  public void loginClicked()
  {

  }

  @Override
  public void addUserClicked()
  {

  }

  @Override
  public void backToLoginClicked()
  {

  }

  @Override
  public void addClicked()
  {

  }

  @Override
  public void resetClicked()
  {

  }

  @Override
  public void showDialog(String message)
  {

  }

  @Override
  public void loginFailed()
  {

  }
}
