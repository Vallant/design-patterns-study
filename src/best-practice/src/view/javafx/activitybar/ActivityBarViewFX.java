package view.javafx.activitybar;

import controller.interfaces.ActivityBarController;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.interfaces.ActivityBarView;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ActivityBarViewFX implements ActivityBarView
{
  private BorderPane            mainPane;
  private Stage                 mainStage;
  private ActivityBarController controller;
  private final ActivityBarPane pMain;

  private Duration  duration;
  private TimerTask task;
  private Timer     timer;

  public ActivityBarViewFX()
  {
    pMain = new ActivityBarPane();
    stopTimer();
    setListeners();
  }

  private void setListeners()
  {
    pMain.btStart.setOnAction(actionEvent -> controller.startClicked());

    pMain.btStop.setOnAction(actionEvent -> controller.stopClicked());


    pMain.cbPhase.setOnAction(
      actionEvent ->
      {
        String selectedPhase = pMain.cbPhase.getValue();
        controller.phaseSelected(selectedPhase);
      }
    );

    pMain.cbProject.setOnAction(
      actionEvent ->
      {
        String selectedProject = pMain.cbProject.getValue();
        controller.projectSelected(selectedProject);
      }
    );
  }

  @Override
  public void setController(ActivityBarController controller)
  {
    this.controller = controller;

  }

  @Override
  public void show()
  {
    mainPane.setTop(pMain);
    mainStage.show();
  }

  @Override
  public void enableStart()
  {
    pMain.btStart.setDisable(false);
  }

  @Override
  public void disableStart()
  {
    pMain.btStart.setDisable(true);

  }

  @Override
  public void enableStop()
  {
    pMain.btStop.setDisable(false);
  }

  @Override
  public void disableStop()
  {
    pMain.btStop.setDisable(true);
  }

  @Override
  public void setProjectPhases(ArrayList<String> phases)
  {
    pMain.cbPhase.getItems().clear();
    pMain.cbPhase.getItems().addAll(phases);
    pMain.cbPhase.getSelectionModel().clearAndSelect(0);
  }

  @Override
  public void setProjects(ArrayList<String> projects)
  {
    pMain.cbProject.getItems().clear();
    pMain.cbProject.getItems().addAll(projects);
    pMain.cbProject.getSelectionModel().clearAndSelect(0);
  }

  public void startTimer()
  {
    timer.scheduleAtFixedRate(task, 0, 1000);
  }

  @Override
  public void stopTimer()
  {

    resetTimer();

    duration = Duration.ZERO;
    long seconds = duration.getSeconds();
    pMain.lbDuration.setText(String.format("%d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, (seconds % 60)));
  }

  private void resetTimer()
  {
    if(timer != null)
    {
      timer.cancel();
      timer.purge();
      task.cancel();
    }

    timer = new Timer();

    task = new TimerTask()
    {
      @Override
      public void run()
      {
        duration = duration.plusSeconds(1);
        long seconds = duration.getSeconds();
        Platform.runLater(new Runnable()
        {
          @Override
          public void run()
          {
            pMain.lbDuration.setText(String.format("%d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, (seconds % 60)));
          }
        });

      }
    };
  }

  @Override
  public void showError(String localizedMessage)
  {
    Alert alert = new Alert(Alert.AlertType.ERROR, localizedMessage);
    alert.showAndWait();
  }

  @Override
  public void disableComboBoxes()
  {
    pMain.cbProject.setDisable(true);
    pMain.cbPhase.setDisable(true);
  }

  @Override
  public void showCommentDescriptionDialog()
  {
    ActivityBarDialog dlg = new ActivityBarDialog();
    dlg.showAndWait().ifPresent(response -> {

      if (response == ButtonType.OK)
      {
        String description = dlg.getDescription();
        String comment = dlg.getComment();

        String project = pMain.cbProject.getValue();
        String phase = pMain.cbPhase.getValue();
        controller.activityFinished(project, phase, description, comment);
      }
      else
      {
        showFinishActivityDialog();
      }
    });
  }

  @Override
  public void enableComboBoxes()
  {
    pMain.cbProject.setDisable(false);
    pMain.cbPhase.setDisable(false);
  }

  @Override
  public void hide()
  {
    mainPane.setTop(null);
  }

  @Override
  public void showFinishActivityDialog()
  {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to discard the activity", ButtonType.YES, ButtonType.NO);
    alert.showAndWait();

    if (alert.getResult() == ButtonType.YES)
      controller.discardActivity();
    else
      showFinishActivityDialog();
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
