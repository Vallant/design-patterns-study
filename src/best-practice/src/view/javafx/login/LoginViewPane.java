package view.javafx.login;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

class LoginViewPane extends BorderPane
{

  final TextField     tfUsername;
  final PasswordField tfPassword;
  final Button        btResetPassword;
  final Button        btAddNewUser;
  final Button        btLogin;

  public LoginViewPane()
  {

    setPrefSize(355, 70);
    setPadding(new Insets(5, 5, 5, 5));

    GridPane buttons = new GridPane();
    buttons.setPadding(new Insets(5, 5, 5, 5));
    buttons.setHgap(5);
    buttons.setVgap(5);

    btAddNewUser = new Button("Create new Account");
    btResetPassword = new Button("Reset Password");
    btLogin = new Button("Login");
    buttons.add(btAddNewUser, 0, 0);
    //buttons.add(btResetPassword);
    buttons.add(btLogin, 1, 0);
    FlowPane flow2 = new FlowPane();
    flow2.setPadding(new Insets(5, 5, 5, 5));
    flow2.getChildren().add(buttons);
    setBottom(flow2);

    GridPane main = new GridPane();
    main.setPadding(new Insets(5, 5, 5, 5));
    main.setHgap(5);
    main.setVgap(5);
    FlowPane flow = new FlowPane();


    Label lb1 = new Label("Username");
    Label lb2 = new Label("Password");
    tfPassword = new PasswordField();
    tfPassword.setPrefWidth(345);
    tfUsername = new TextField();
    tfUsername.setPrefWidth(345);

    main.add(lb1, 0, 0);
    main.add(tfUsername, 0, 1);
    main.add(lb2, 0, 2);
    main.add(tfPassword, 0, 3);
    flow.getChildren().add(main);
    setCenter(flow);
  }
}
