package view.javafx.settings;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

class SettingsViewPane extends BorderPane
{
  final TextField     tfFirst;
  final TextField     tfLast;
  final TextField     tfEmail;
  final PasswordField tfOldPw;
  final PasswordField tfNewPw;
  final PasswordField tfNewPwAgain;

  final Button btApply;
  final Button btReset;

  public SettingsViewPane()
  {
    GridPane pMain = new GridPane();
    Label lbFirst = new Label("First Name");
    Label lbLast = new Label("Last Name");
    tfFirst = new TextField("");
    tfLast = new TextField("");
    Label lbEmail = new Label("Email");
    tfEmail = new TextField("");
    Label lbOldPw = new Label("Old Password");
    tfOldPw = new PasswordField();
    Label lbNewPw = new Label("New Password");
    tfNewPw = new PasswordField();
    Label lbNewPwAgain = new Label("Repeat new Password");
    tfNewPwAgain = new PasswordField();

    pMain.add(lbFirst, 0, 0);
    pMain.add(tfFirst, 1, 0);
    pMain.add(lbLast, 0, 1);
    pMain.add(tfLast, 1, 1);
    pMain.add(lbEmail, 0, 2);
    pMain.add(tfEmail, 1, 2);
    pMain.add(lbOldPw, 0, 3);
    pMain.add(tfOldPw, 1, 3);
    pMain.add(lbNewPw, 0, 4);
    pMain.add(tfNewPw, 1, 4);
    pMain.add(lbNewPwAgain, 0, 5);
    pMain.add(tfNewPwAgain, 1, 5);
    FlowPane pFlow = new FlowPane();
    pFlow.getChildren().add(pMain);
    setCenter(pFlow);

    FlowPane pBottom = new FlowPane();
    btApply = new Button("Apply Changes");
    btReset = new Button("Discard Changes");
    pBottom.getChildren().add(btApply);
    pBottom.getChildren().add(btReset);

    setBottom(pBottom);
  }
}
