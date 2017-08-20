package view.javafx.projectstatistic;

import controller.javafx.ProjectStatisticControllerFX;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class ProjectStatisticViewFX
{
  private BorderPane                   mainPane;
  private Stage                        mainStage;
  private ProjectStatisticControllerFX controller;

  private final ProjectStatisticProjectPane  pProject;
  private final ProjectStatisticPhasePane    pPhase;
  private final ProjectStatisticActivityPane pActivity;

  public ProjectStatisticViewFX()
  {
    pProject = new ProjectStatisticProjectPane();
    pPhase = new ProjectStatisticPhasePane();
    pActivity = new ProjectStatisticActivityPane();

    setListener();
  }

  private void setListener()
  {
    pProject.cbPeriod.getSelectionModel().selectedIndexProperty().addListener((options, oldValue, newValue) -> {
      controller.projectPeriodChanged(newValue.intValue());
    });

    pPhase.cbPeriod.getSelectionModel().selectedIndexProperty().addListener((options, oldValue, newValue) -> {
      controller.phaseDropDownChanged(newValue.intValue(), pPhase.cbMembers.getSelectionModel().getSelectedIndex());
    });
    pPhase.cbMembers.getSelectionModel().selectedIndexProperty().addListener((options, oldValue, newValue) -> {
      System.out.println(newValue);
      controller.phaseDropDownChanged(pPhase.cbPeriod.getSelectionModel().getSelectedIndex(), newValue.intValue());
    });

    pActivity.cbPeriod.getSelectionModel().selectedIndexProperty().addListener((options, oldValue, newValue) -> {
      controller.activityDropDownChanged(newValue.intValue(), pActivity.cbMembers.getSelectionModel().getSelectedIndex());
    });
    pActivity.cbMembers.getSelectionModel().selectedIndexProperty().addListener((options, oldValue, newValue) -> {
      controller.activityDropDownChanged(pActivity.cbPeriod.getSelectionModel().getSelectedIndex(), newValue.intValue());
    });

    pProject.tblProjects.setOnMouseClicked(mouseEvent -> {
      if(mouseEvent.getClickCount() == 2)
      {
        int index = pProject.tblProjects.getSelectionModel().getSelectedIndex();
        if(index == -1)
          return;
        controller.doubleClickOnProject(index);
      }
    });

    pPhase.btBack.setOnAction(actionEvent -> controller.backToProjectClicked());

    pPhase.tblPhases.setOnMouseClicked(mouseEvent -> {
        if(mouseEvent.getClickCount() == 2)
        {
          int index = pPhase.tblPhases.getSelectionModel().getSelectedIndex();
          if(index == -1)
            return;
          controller.doubleClickOnPhase(index);
        }
    });

    pActivity.btBack.setOnAction(actionEvent -> controller.backToPhaseClicked());
  }

  
  public void setController(ProjectStatisticControllerFX controller)
  {
    this.controller = controller;
  }

  
  public void hide()
  {
    mainPane.getChildren().remove(pProject);
    mainPane.getChildren().remove(pPhase);
    mainPane.getChildren().remove(pActivity);
    mainPane.autosize();
    mainStage.sizeToScene();
    mainStage.show();
  }

  
  public void removeAllComponents()
  {
    hide();
  }

  
  public void showProjectView()
  {
    hide();
    mainPane.setCenter(pProject);
    mainPane.autosize();
    mainStage.sizeToScene();
    mainStage.show();
    pProject.cbPeriod.getSelectionModel().selectFirst();
  }

  
  public void setProjectData(ArrayList<String> projectNames, ArrayList<Duration> durations)
  {
    Duration total = Duration.ZERO;

    for(Duration duration : durations)
      total = total.plus(duration);

    ObservableList<ProjectStatisticTableData> data = FXCollections.observableArrayList();
    for(int i = 0; i < projectNames.size(); ++i)
    {
      data.add(new ProjectStatisticTableData(projectNames.get(i), durations.get(i), total));
    }

    pProject.tblProjects.getItems().clear();
    pProject.tblProjects.getItems().addAll(data);
  }

  
  public void showPhaseView()
  {
    hide();
    mainPane.setCenter(pPhase);
    mainPane.autosize();
    mainStage.sizeToScene();
    mainStage.show();
    pPhase.cbPeriod.getSelectionModel().selectFirst();
    pPhase.cbMembers.getSelectionModel().selectFirst();
  }

  
  public void setPhaseData(ArrayList<String> phaseNames, ArrayList<Duration> durations, ArrayList<String> memberNames)
  {
    Duration total = Duration.ZERO;

    for(Duration duration : durations)
      total = total.plus(duration);

    ObservableList<ProjectStatisticTableData> data = FXCollections.observableArrayList();
    for(int i = 0; i < phaseNames.size(); ++i)
    {
      data.add(new ProjectStatisticTableData(phaseNames.get(i), durations.get(i), total));
    }

    pPhase.tblPhases.getItems().clear();
    pPhase.tblPhases.getItems().addAll(data);
    pPhase.setMemberNames(memberNames);
  }

  
  public void showActivityView()
  {
    hide();

    mainPane.setCenter(pActivity);
    mainPane.autosize();
    mainStage.sizeToScene();
    mainStage.show();
  }

  
  public void setActivityData(ArrayList<String> users, ArrayList<String> descriptions, ArrayList<String> comments,
                              ArrayList<ZonedDateTime> startTimes, ArrayList<ZonedDateTime> endTimes,
                              ArrayList<String> memberNames)
  {

    ObservableList<ProjectStatisticActivityTableData> data = FXCollections.observableArrayList();
    for(int i = 0; i < users.size(); ++i)
    {
      data.add(new ProjectStatisticActivityTableData(startTimes.get(i), endTimes.get(i), users.get(i), descriptions.get(i),
        comments.get(i)));
    }
    pActivity.tblActivity.getItems().clear();
    pActivity.tblActivity.getItems().addAll(data);
    pActivity.setMembers(memberNames);
  }

  
  public int getSelectedPhasePeriod()
  {
    return pPhase.cbPeriod.getSelectionModel().getSelectedIndex();
  }

  
  public int getSelectedActivityPeriod()
  {
    return pActivity.cbPeriod.getSelectionModel().getSelectedIndex();
  }

  
  public int getSelectedProjectPeriod()
  {
    return pProject.cbPeriod.getSelectionModel().getSelectedIndex();
  }

  
  public int getSelectedUserPhase()
  {
    return pActivity.cbMembers.getSelectionModel().getSelectedIndex();
  }

  
  public boolean confirmDeletion()
  {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete this project?", ButtonType.YES,
      ButtonType.NO);
    return alert.getResult() == ButtonType.YES;
  }

  
  public void showError(String error)
  {
    Alert alert = new Alert(Alert.AlertType.ERROR, error);
    alert.showAndWait();
  }

  
  public int getSelectedUserActivity()
  {
    return pActivity.cbMembers.getSelectionModel().getSelectedIndex();
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


