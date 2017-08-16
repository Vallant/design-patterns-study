package view.javafx.login;

import controller.interfaces.LoginController;
import data.User;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.interfaces.LoginView;
import view.javafx.FXDialogs;

public class LoginViewFX implements LoginView
{
  private final LoginViewPane         login;
  private final LoginViewRegisterPane register;
  private       LoginController       controller;
  private       BorderPane            mainPane;
  private       Stage                 mainStage;

  public LoginViewFX()
  {
    login = new LoginViewPane();
    register = new LoginViewRegisterPane();
    setListeners();
  }

  private void setListeners()
  {

    login.btLogin.setOnAction(ae -> controller.loginClicked());
    login.btResetPassword.setOnAction(ae -> controller.resetPasswordClicked());

    login.btAddNewUser.setOnAction(ae -> controller.addUserClicked());

    register.btBackNew.setOnAction(ae -> controller.backToLoginClicked());

    register.btAdd.setOnAction(ae -> controller.addClicked());
  }

  @Override
  public void switchToResetPassword()
  {
    
  }

  @Override
  public void switchToAddNewUser()
  {
    mainPane.setCenter(register);
    mainStage.show();
    mainPane.autosize();
    mainStage.sizeToScene();
  }

  @Override
  public void switchToLogin()
  {
    login.tfPassword.clear();
    login.tfUsername.clear();

    mainPane.setCenter(login);
    mainPane.autosize();
    mainStage.sizeToScene();
  }

  @Override
  public void showLoginFailed()
  {
    FXDialogs.showError("Login failed", "Username or password incorrect");
  }

  @Override
  public void removeAllComponents()
  {
    mainPane.getChildren().remove(login);
    mainPane.getChildren().remove(register);
    mainStage.show();
    mainPane.autosize();
    mainStage.sizeToScene();
  }

  @Override
  public User getEnteredUser()
  {
    return new User(register.tfLoginName.getText(), register.tfFirstName.getText(), register.tfLastName.getText(),
      User.ROLE.USER, register.tfEmailNew.getText(), register.tfPasswordNew.getText().toCharArray(),
      register.tfPasswordNewAgain.getText().toCharArray());
  }

  @Override
  public String getEnteredEmail()
  {
    return null;
  }

  @Override
  public String getEnteredUsername()
  {
    return login.tfUsername.getText();
  }

  @Override
  public char[] getEnteredPassword()
  {
    return login.tfPassword.getText().toCharArray();
  }

  @Override
  public void showDialog(String message)
  {
    FXDialogs.showInformation("Information", message);
  }

  @Override
  public void setController(LoginController controller)
  {
    this.controller = controller;
  }

  @Override
  public void showError(String localizedMessage)
  {
    Alert alert = new Alert(Alert.AlertType.ERROR, localizedMessage);
    alert.showAndWait();
  }

  public void setMainPane(BorderPane mainPane)
  {
    this.mainPane = mainPane;
  }
  public void setMainStage(Stage mainStage)
  {
    this.mainStage = mainStage;
  }
}
