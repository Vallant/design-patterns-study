package view.javafx.activitybar;

import controller.javafx.ActivityBarControllerFX;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ActivityBarViewFX
{
  private       BorderPane              mainPane;
  private       Stage                   mainStage;
  private       ActivityBarControllerFX controller;
  private final ActivityBarPane         pMain;

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

  
  public void setController(ActivityBarControllerFX controller)
  {
    this.controller = controller;

  }

  
  public void show()
  {
    mainPane.setTop(pMain);
    mainStage.show();
  }

  
  public void enableStart()
  {
    pMain.btStart.setDisable(false);
  }

  
  public void disableStart()
  {
    pMain.btStart.setDisable(true);

  }

  
  public void enableStop()
  {
    pMain.btStop.setDisable(false);
  }

  
  public void disableStop()
  {
    pMain.btStop.setDisable(true);
  }

  
  public void setProjectPhases(ArrayList<String> phases)
  {
    pMain.cbPhase.getItems().clear();
    pMain.cbPhase.getItems().addAll(phases);
    pMain.cbPhase.getSelectionModel().clearAndSelect(0);
  }

  
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
      
      public void run()
      {
        duration = duration.plusSeconds(1);
        long seconds = duration.getSeconds();
        Platform.runLater(new Runnable()
        {
          
          public void run()
          {
            pMain.lbDuration.setText(String.format("%d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, (seconds % 60)));
          }
        });

      }
    };
  }

  
  public void showError(String localizedMessage)
  {
    Alert alert = new Alert(Alert.AlertType.ERROR, localizedMessage);
    alert.showAndWait();
  }

  
  public void disableComboBoxes()
  {
    pMain.cbProject.setDisable(true);
    pMain.cbPhase.setDisable(true);
  }

  
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

  
  public void enableComboBoxes()
  {
    pMain.cbProject.setDisable(false);
    pMain.cbPhase.setDisable(false);
  }

  
  public void hide()
  {
    mainPane.setTop(null);
  }

  
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
