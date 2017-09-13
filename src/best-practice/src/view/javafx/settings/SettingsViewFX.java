package view.javafx.settings;

import controller.interfaces.SettingsController;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.interfaces.SettingsView;

public class SettingsViewFX implements SettingsView
{
  private final SettingsViewPane   pMain;
  private       BorderPane         mainPane;
  private       Stage              mainStage;
  private       SettingsController controller;

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

  @Override
  public void setController(SettingsController controller)
  {
    this.controller = controller;
  }

  @Override
  public void hide()
  {
    mainPane.getChildren().remove(pMain);
    mainStage.show();
  }

  @Override
  public void show()
  {
    mainPane.setCenter(pMain);
    mainStage.show();
  }

  @Override
  public void showError(String error)
  {
    Alert alert = new Alert(Alert.AlertType.ERROR, error);
    alert.showAndWait();
  }

  @Override
  public void setData(String firstName, String lastName, String email)
  {
    pMain.tfFirst.setText(firstName);
    pMain.tfLast.setText(lastName);
    pMain.tfEmail.setText(email);
    pMain.tfNewPwAgain.setText("");
    pMain.tfNewPw.setText("");
    pMain.tfOldPw.setText("");
  }

  @Override
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
