package view.javafx.personalstatistic;

import controller.standard.PersonalStatisticControllerStandard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.javafx.projectstatistic.ProjectStatisticTableData;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Optional;

public class PersonalStatisticViewFX
{

  private final PersonalStatisticProjectPane  pProject;
  private final PersonalStatisticPhasePane    pPhase;
  private final PersonalStatisticActivityPane pActivity;
  private       BorderPane                    mainPane;
  private       Stage                         mainStage;
  private       PersonalStatisticControllerStandard controller;
  public PersonalStatisticViewFX()
  {
    pProject = new PersonalStatisticProjectPane();
    pPhase = new PersonalStatisticPhasePane();
    pActivity = new PersonalStatisticActivityPane();

    setListeners();
  }

  public void setMainPane(BorderPane mainPane)
  {
    this.mainPane = mainPane;
  }

  public void setMainStage(Stage mainStage)
  {
    this.mainStage = mainStage;
  }

  private void setListeners()
  {
    pProject.cbPeriod.setOnAction(
      actionEvent -> controller.projectPeriodChanged(pProject.cbPeriod.getSelectionModel().getSelectedIndex()));
    pPhase.cbPeriod.setOnAction(actionEvent -> controller.phasePeriodChanged(pPhase.cbPeriod.getSelectionModel()
      .getSelectedIndex()));

    pActivity.cbPeriod.setOnAction(
      actionEvent -> controller.activityPeriodChanged(pActivity.cbPeriod.getSelectionModel().getSelectedIndex()));

    pProject.tblProjects.setOnMouseClicked(mouseEvent ->
    {
      if(mouseEvent.getClickCount() == 2)
      {
        int index = pProject.tblProjects.getSelectionModel().getSelectedIndex();
        if(index == -1)
          return;
        controller.doubleClickOnProject(index);
      }
    });

    pPhase.tblPhases.setOnMouseClicked(mouseEvent ->
    {
      if(mouseEvent.getClickCount() == 2)
      {
        int index = pPhase.tblPhases.getSelectionModel().getSelectedIndex();
        if(index == -1)
          return;
        controller.doubleClickOnPhase(index);
      }
    });

    pActivity.btAddActivity.setOnAction(actionEvent -> controller.addActivityClicked());
    pActivity.btDeleteActivity.setOnAction(actionEvent -> controller.deleteActivityClicked());
    pActivity.btUpdateActivity.setOnAction(actionEvent -> controller.updateActivityClicked());
    pActivity.btBack.setOnAction(actionEvent -> controller.backToPhaseViewClicked());
    pPhase.btBack.setOnAction(actionEvent -> controller.backToOverviewClicked());
  }


  public void setProjectData(ArrayList<String> projectNames, ArrayList<Duration> durations)
  {
    Duration total = Duration.ZERO;

    for(Duration duration : durations)
    {
      total = total.plus(duration);
    }

    ObservableList<ProjectStatisticTableData> data = FXCollections.observableArrayList();
    for(int i = 0; i < projectNames.size(); ++i)
    {
      data.add(new ProjectStatisticTableData(projectNames.get(i), durations.get(i), total));
    }

    pProject.tblProjects.getItems().clear();
    pProject.tblProjects.getItems().addAll(data);
  }


  public void setPhaseData(ArrayList<String> phaseNames, ArrayList<Duration> durations)
  {
    Duration total = Duration.ZERO;

    for(Duration duration : durations)
    {
      total = total.plus(duration);
    }

    ObservableList<ProjectStatisticTableData> data = FXCollections.observableArrayList();
    for(int i = 0; i < phaseNames.size(); ++i)
    {
      data.add(new ProjectStatisticTableData(phaseNames.get(i), durations.get(i), total));
    }

    pPhase.tblPhases.getItems().clear();
    pPhase.tblPhases.getItems().addAll(data);
  }


  public void showActivityView()
  {
    hide();

    mainPane.setCenter(pActivity);
    mainPane.autosize();
    mainStage.sizeToScene();
    mainStage.show();
  }


  public void setActivityData(ArrayList<String> descriptions, ArrayList<String> comments,
                              ArrayList<ZonedDateTime> startTimes, ArrayList<ZonedDateTime> endTimes)
  {
    ObservableList<PersonalStatisticActivityTableData> data = FXCollections.observableArrayList();
    for(int i = 0; i < descriptions.size(); ++i)
    {
      data.add(new PersonalStatisticActivityTableData(startTimes.get(i), endTimes.get(i), descriptions.get(i),
        comments.get(i)));
    }
    pActivity.tblActivity.getItems().clear();
    pActivity.tblActivity.getItems().addAll(data);
  }


  public void setController(PersonalStatisticControllerStandard controller)
  {
    this.controller = controller;
  }


  public void RemoveAllComponents()
  {
    hide();
  }


  public void showError(String localizedMessage)
  {
    Alert alert = new Alert(Alert.AlertType.ERROR, localizedMessage);
    alert.showAndWait();
  }


  public void showProjectView()
  {
    hide();

    mainPane.setCenter(pProject);
    mainPane.autosize();
    mainStage.sizeToScene();
    mainStage.show();
  }


  public void showPhaseView()
  {
    hide();

    mainPane.setCenter(pPhase);
    mainPane.autosize();
    mainStage.sizeToScene();
    mainStage.show();
  }


  public int getSelectedProjectPeriod()
  {
    return pProject.cbPeriod.getSelectionModel().getSelectedIndex();
  }


  public int getSelectedActivity()
  {
    return pActivity.tblActivity.getSelectionModel().getSelectedIndex();
  }


  private void hide()
  {
    mainPane.getChildren().remove(pPhase);
    mainPane.getChildren().remove(pProject);
    mainPane.getChildren().remove(pActivity);
    mainPane.autosize();
    mainStage.sizeToScene();
    mainStage.show();
  }


  public void showAddActivityDialog()
  {
    PersonalStatisticActivityUpdateDialog dlg = new PersonalStatisticActivityUpdateDialog();
    dlg.showAndWait().ifPresent(response ->
    {
      if(response == ButtonType.OK)
      {
        String description = dlg.tfDescription.getText();
        String comment = dlg.tfComment.getText();
        LocalDateTime startTime = dlg.dpStartTime.getLocalDateTime();
        LocalDateTime endTime = dlg.dpEndTime.getLocalDateTime();
        controller.addActivity(description, comment, startTime, endTime);
      }
    });
  }


  public boolean confirmDeletion()
  {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete this activity?", ButtonType.YES,
      ButtonType.NO);
    Optional<ButtonType> result = alert.showAndWait();

    return result.isPresent() && alert.getResult() == ButtonType.YES;
  }


  public void showUpdateActivityDialog(String description, String comment, LocalDateTime start, LocalDateTime end)
  {
    PersonalStatisticActivityUpdateDialog dlg = new PersonalStatisticActivityUpdateDialog(description, comment,
      start, end);
    dlg.showAndWait().ifPresent(response ->
    {
      if(response == ButtonType.OK)
      {
        String newDescription = dlg.tfDescription.getText();
        String newComment = dlg.tfComment.getText();
        LocalDateTime newStartTime = dlg.dpStartTime.getLocalDateTime();
        LocalDateTime newEndTime = dlg.dpEndTime.getLocalDateTime();
        controller.updateActivity(newDescription, newComment, newStartTime, newEndTime);
      }
    });
  }


  public int getSelectedPhasePeriod()
  {
    return pPhase.cbPeriod.getSelectionModel().getSelectedIndex();
  }


  public int getSelectedActivityPeriod()
  {
    return pActivity.cbPeriod.getSelectionModel().getSelectedIndex();
  }


  public void updateUI()
  {
    pProject.tblProjects.refresh();
    pPhase.tblPhases.refresh();
    pActivity.tblActivity.refresh();
  }
}
