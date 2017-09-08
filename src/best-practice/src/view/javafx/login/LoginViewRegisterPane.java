package view.javafx.login;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

class LoginViewRegisterPane extends BorderPane
{
  final TextField     tfLoginName;
  final TextField     tfFirstName;
  final TextField     tfLastName;
  final TextField     tfEmailNew;
  final PasswordField tfPasswordNew;
  final PasswordField tfPasswordNewAgain;
  final Button        btBackNew;
  final Button        btAdd;

  public LoginViewRegisterPane()
  {
    GridPane main3 = new GridPane();
    main3.setPadding(new Insets(5,5,5,5));
    main3.setHgap(5);
    main3.setPrefWidth(175);

    ColumnConstraints columnConstraints = new ColumnConstraints();
    columnConstraints.setFillWidth(true);
    columnConstraints.setHgrow(Priority.ALWAYS);

    main3.getColumnConstraints().add(columnConstraints);
    Label lb4 = new Label("Username");
    tfLoginName = new TextField();

    Label lb5 = new Label("First Name");
    tfFirstName = new TextField();
    Label lb6 = new Label("Last Name");
    tfLastName = new TextField();
    Label lb7 = new Label("Email");
    tfEmailNew = new TextField();
    Label lb8 = new Label("Password");
    tfPasswordNew = new PasswordField();
    Label lb9 = new Label("Repeat Password");
    tfPasswordNewAgain = new PasswordField();

    main3.add(lb4, 0, 0);
    main3.add(tfLoginName, 0, 1);
    main3.add(lb5, 0, 2);
    main3.add(tfFirstName, 0, 3);
    main3.add(lb6, 0, 4);
    main3.add(tfLastName, 0, 5);
    main3.add(lb7, 0, 6);
    main3.add(tfEmailNew, 0, 7);
    main3.add(lb8, 0, 8);
    main3.add(tfPasswordNew, 0, 9);
    main3.add(lb9, 0, 10);
    main3.add(tfPasswordNewAgain, 0, 11);
    setCenter(main3);

    GridPane buttons3 = new GridPane();
    btAdd = new Button("Create Account");
    btBackNew = new Button("Back to Login");
    buttons3.add(btBackNew, 0, 0);
    buttons3.add(btAdd, 1, 0);
    setBottom(buttons3);

    buttons3.setPadding(new Insets(0,5,5,5));
    buttons3.setHgap(5);
  }
}
