package view.javafx.login;

import controller.javafx.LoginControllerFX;
import data.User;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LoginViewFX
{
  private final LoginViewPane         login;
  private final LoginViewRegisterPane register;
  private       LoginControllerFX     controller;
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


  public void switchToResetPassword()
  {

  }


  public void switchToAddNewUser()
  {
    mainPane.setCenter(register);
    register.tfEmailNew.clear();
    register.tfFirstName.clear();
    register.tfLastName.clear();
    register.tfLoginName.clear();
    register.tfPasswordNew.clear();
    register.tfPasswordNewAgain.clear();
    mainStage.show();
    mainPane.autosize();
    mainStage.sizeToScene();
  }


  public void switchToLogin()
  {
    login.tfPassword.clear();
    login.tfUsername.clear();

    mainPane.setCenter(login);
    mainPane.autosize();
    mainStage.sizeToScene();
  }


  public void showLoginFailed()
  {
    Alert alert = new Alert(Alert.AlertType.ERROR, "Username or password incorrect");
    alert.showAndWait();
  }


  public void removeAllComponents()
  {
    mainPane.getChildren().remove(login);
    mainPane.getChildren().remove(register);
    mainStage.show();
    mainPane.autosize();
    mainStage.sizeToScene();
  }


  public User getEnteredUser()
  {
    return new User(register.tfLoginName.getText(), register.tfFirstName.getText(), register.tfLastName.getText(),
      User.ROLE.USER, register.tfEmailNew.getText(), register.tfPasswordNew.getText().toCharArray(),
      register.tfPasswordNewAgain.getText().toCharArray());
  }


  public String getEnteredEmail()
  {
    return null;
  }


  public String getEnteredUsername()
  {
    return login.tfUsername.getText();
  }


  public char[] getEnteredPassword()
  {
    return login.tfPassword.getText().toCharArray();
  }


  public void showDialog(String message)
  {
    Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
    alert.showAndWait();
  }


  public void setController(LoginControllerFX controller)
  {
    this.controller = controller;
  }


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
