package view.javafx.settings;

import controller.standard.SettingsControllerStandard;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SettingsViewFX
{
  private final SettingsViewPane     pMain;
  private       BorderPane           mainPane;
  private       Stage                mainStage;
  private       SettingsControllerStandard controller;

  public SettingsViewFX()
  {
    pMain = new SettingsViewPane();

    setListener();

  }

  private void setListener()
  {
    pMain.btReset.setOnAction(actionEvent -> controller.resetClicked());
    pMain.btApply.setOnAction(actionEvent -> controller.applyClicked(
      pMain.tfFirst.getText(),
      pMain.tfLast.getText(),
      pMain.tfEmail.getText(),
      pMain.tfOldPw.getText().toCharArray(),
      pMain.tfNewPw.getText().toCharArray(),
      pMain.tfNewPwAgain.getText().toCharArray()
    ));
  }


  public void setController(SettingsControllerStandard controller)
  {
    this.controller = controller;
  }


  public void hide()
  {
    mainPane.getChildren().remove(pMain);
    mainStage.show();
  }


  public void show()
  {
    mainPane.setCenter(pMain);
    mainStage.show();
  }


  public void showError(String error)
  {
    Alert alert = new Alert(Alert.AlertType.ERROR, error);
    alert.showAndWait();
  }


  public void setData(String firstName, String lastName, String email)
  {
    pMain.tfFirst.setText(firstName);
    pMain.tfLast.setText(lastName);
    pMain.tfEmail.setText(email);
    pMain.tfNewPwAgain.setText("");
    pMain.tfNewPw.setText("");
    pMain.tfOldPw.setText("");
  }


  public void updateSuccessful()
  {
    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Update Successful");
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
