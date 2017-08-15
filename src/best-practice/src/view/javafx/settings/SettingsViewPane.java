package view.javafx.settings;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

public class SettingsViewPane extends BorderPane
{
  private final GridPane      pMain;
  private final Label         lbFirst;
  final         TextField     tfFirst;
  private final Label         lbLast;
  final         TextField     tfLast;
  private final Label         lbEmail;
  final         TextField     tfEmail;
  private final Label         lbOldPw;
  final         PasswordField tfOldPw;
  private final Label         lbNewPw;
  final         PasswordField tfNewPw;
  private final Label         lbNewPwAgain;
  final         PasswordField tfNewPwAgain;

  private final FlowPane   pBottom;
  final         Button btApply;
  final         Button btReset;

  public SettingsViewPane()
  {
    pMain = new GridPane();
    lbFirst = new Label("First Name");
    lbLast = new Label("Last Name");
    tfFirst = new TextField("");
    tfLast = new TextField("");
    lbEmail = new Label("Email");
    tfEmail = new TextField("");
    lbOldPw = new Label("Old Password");
    tfOldPw = new PasswordField();
    lbNewPw = new Label("New Password");
    tfNewPw = new PasswordField();
    lbNewPwAgain = new Label("Repeat new Password");
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

    pBottom = new FlowPane();
    btApply = new Button("Apply Changes");
    btReset = new Button("Discard Changes");
    pBottom.getChildren().add(btApply);
    pBottom.getChildren().add(btReset);

    setBottom(pBottom);
  }
}
