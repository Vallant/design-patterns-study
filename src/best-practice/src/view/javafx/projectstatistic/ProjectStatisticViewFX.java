package view.javafx.projectstatistic;

import controller.interfaces.ProjectStatisticController;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.interfaces.ProjectStatisticView;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class ProjectStatisticViewFX implements ProjectStatisticView
{
  private BorderPane mainPane;
  private Stage mainStage;
  private ProjectStatisticController controller;

  @Override
  public void setController(ProjectStatisticController controller)
  {
    this.controller = controller;
  }

  @Override
  public void hide()
  {

  }

  @Override
  public void removeAllComponents()
  {

  }

  @Override
  public void showProjectView()
  {

  }

  @Override
  public void setProjectData(ArrayList<String> projectNames, ArrayList<Duration> durations)
  {

  }

  @Override
  public void showPhaseView()
  {

  }

  @Override
  public void setPhaseData(ArrayList<String> phaseNames, ArrayList<Duration> durations, ArrayList<String> memberNames)
  {

  }

  @Override
  public void showActivityView()
  {

  }

  @Override
  public void setActivityData(ArrayList<String> users, ArrayList<String> descriptions, ArrayList<String> comments,
                              ArrayList<ZonedDateTime> startTimes, ArrayList<ZonedDateTime> endTimes,
                              ArrayList<String> memberNames)
  {

  }

  @Override
  public int getSelectedPhasePeriod()
  {
    return 0;
  }

  @Override
  public int getSelectedActivityPeriod()
  {
    return 0;
  }

  @Override
  public int getSelectedProjectPeriod()
  {
    return 0;
  }

  @Override
  public int getSelectedUser()
  {
    return 0;
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

  public void setMainPane(BorderPane mainPane)
  {
    this.mainPane = mainPane;
  }

  public void setMainStage(Stage mainStage)
  {
    this.mainStage = mainStage;
  }
}
