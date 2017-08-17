package view.javafx.projectstatistic;

import controller.interfaces.ProjectStatisticController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.interfaces.ProjectStatisticView;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class ProjectStatisticViewFX implements ProjectStatisticView
{
  private BorderPane mainPane;
  private Stage mainStage;
  private ProjectStatisticController controller;

  private final ProjectStatisticProjectPane project;
  private final ProjectStatisticPhasePane phase;
  private final ProjectStatisticActivityPane activity;

  public ProjectStatisticViewFX()
  {
    project = new ProjectStatisticProjectPane();
    phase = new ProjectStatisticPhasePane();
    activity = new ProjectStatisticActivityPane();

    setListener();
  }

  private void setListener()
  {
    project.cbPeriod.getSelectionModel().selectedIndexProperty().addListener((options, oldValue, newValue) -> {
      controller.projectPeriodChanged(newValue.intValue());
    });

    phase.cbPeriod.getSelectionModel().selectedIndexProperty().addListener((options, oldValue, newValue) -> {
      controller.phaseDropDownChanged(newValue.intValue(), phase.cbMembers.getSelectionModel().getSelectedIndex());
    });
    phase.cbMembers.getSelectionModel().selectedIndexProperty().addListener((options, oldValue, newValue) -> {
      System.out.println(newValue);
      controller.phaseDropDownChanged(phase.cbPeriod.getSelectionModel().getSelectedIndex(), newValue.intValue());
    });

    activity.cbPeriod.getSelectionModel().selectedIndexProperty().addListener((options, oldValue, newValue) -> {
      controller.activityDropDownChanged(newValue.intValue(), activity.cbMembers.getSelectionModel().getSelectedIndex());
    });
    activity.cbMembers.getSelectionModel().selectedIndexProperty().addListener((options, oldValue, newValue) -> {
      controller.activityDropDownChanged(activity.cbPeriod.getSelectionModel().getSelectedIndex(), newValue.intValue());
    });

    project.tblProjects.setOnMouseClicked(mouseEvent -> {
      if(mouseEvent.getClickCount() == 2)
      {
        int index = project.tblProjects.getSelectionModel().getSelectedIndex();
        if(index == -1)
          return;
        controller.doubleClickOnProject(index);
      }
    });

    phase.btBack.setOnAction(actionEvent -> controller.backToProjectClicked());

    phase.tblPhases.setOnMouseClicked(mouseEvent -> {
        if(mouseEvent.getClickCount() == 2)
        {
          int index = phase.tblPhases.getSelectionModel().getSelectedIndex();
          if(index == -1)
            return;
          controller.doubleClickOnPhase(index);
        }
    });

    activity.btBack.setOnAction(actionEvent -> controller.backToPhaseClicked());
  }

  @Override
  public void setController(ProjectStatisticController controller)
  {
    this.controller = controller;
  }

  @Override
  public void hide()
  {
    mainPane.getChildren().remove(project);
    mainPane.getChildren().remove(phase);
    mainPane.getChildren().remove(activity);
    mainPane.autosize();
    mainStage.sizeToScene();
    mainStage.show();
  }

  @Override
  public void removeAllComponents()
  {
    hide();
  }

  @Override
  public void showProjectView()
  {
    hide();
    mainPane.setCenter(project);
    mainPane.autosize();
    mainStage.sizeToScene();
    mainStage.show();
    project.cbPeriod.getSelectionModel().selectFirst();
  }

  @Override
  public void setProjectData(ArrayList<String> projectNames, ArrayList<Duration> durations)
  {
    Duration total = Duration.ZERO;

    for(Duration duration : durations)
      total = total.plus(duration);

    ObservableList<ProjectTableData> data = FXCollections.observableArrayList();
    for(int i = 0; i < projectNames.size(); ++i)
    {
      data.add(new ProjectTableData(projectNames.get(i), durations.get(i), total));
    }

    project.tblProjects.getItems().clear();
    project.tblProjects.getItems().addAll(data);
  }

  @Override
  public void showPhaseView()
  {
    hide();
    mainPane.setCenter(phase);
    mainPane.autosize();
    mainStage.sizeToScene();
    mainStage.show();
    phase.cbPeriod.getSelectionModel().selectFirst();
    phase.cbMembers.getSelectionModel().selectFirst();
  }

  @Override
  public void setPhaseData(ArrayList<String> phaseNames, ArrayList<Duration> durations, ArrayList<String> memberNames)
  {
    Duration total = Duration.ZERO;

    for(Duration duration : durations)
      total = total.plus(duration);

    ObservableList<ProjectTableData> data = FXCollections.observableArrayList();
    for(int i = 0; i < phaseNames.size(); ++i)
    {
      data.add(new ProjectTableData(phaseNames.get(i), durations.get(i), total));
    }

    phase.tblPhases.getItems().clear();
    phase.tblPhases.getItems().addAll(data);
    phase.setMemberNames(memberNames);
  }

  @Override
  public void showActivityView()
  {
    hide();

    mainPane.setCenter(activity);
    mainPane.autosize();
    mainStage.sizeToScene();
    mainStage.show();
  }

  @Override
  public void setActivityData(ArrayList<String> users, ArrayList<String> descriptions, ArrayList<String> comments,
                              ArrayList<ZonedDateTime> startTimes, ArrayList<ZonedDateTime> endTimes,
                              ArrayList<String> memberNames)
  {

    ObservableList<ActivityTableData> data = FXCollections.observableArrayList();
    for(int i = 0; i < users.size(); ++i)
    {
      data.add(new ActivityTableData(startTimes.get(i), endTimes.get(i), users.get(i), descriptions.get(i),
        comments.get(i)));
    }
    activity.tblActivity.getItems().clear();
    activity.tblActivity.getItems().addAll(data);
    activity.setMembers(memberNames);
  }

  @Override
  public int getSelectedPhasePeriod()
  {
    return phase.cbPeriod.getSelectionModel().getSelectedIndex();
  }

  @Override
  public int getSelectedActivityPeriod()
  {
    return activity.cbPeriod.getSelectionModel().getSelectedIndex();
  }

  @Override
  public int getSelectedProjectPeriod()
  {
    return project.cbPeriod.getSelectionModel().getSelectedIndex();
  }

  @Override
  public int getSelectedUserPhase()
  {
    return activity.cbMembers.getSelectionModel().getSelectedIndex();
  }

  @Override
  public boolean confirmDeletion()
  {
    return false;
  }

  @Override
  public void showError(String error)
  {

  }

  @Override
  public int getSelectedUserActivity()
  {
    return activity.cbMembers.getSelectionModel().getSelectedIndex();
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


