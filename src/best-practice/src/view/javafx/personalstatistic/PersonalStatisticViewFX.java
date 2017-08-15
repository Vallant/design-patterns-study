package view.javafx.personalstatistic;

import controller.interfaces.LoginController;
import controller.interfaces.PersonalStatisticController;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.interfaces.PersonalStatisticView;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class PersonalStatisticViewFX implements PersonalStatisticView
{
  private PersonalStatisticController controller;

  public void setMainPane(BorderPane mainPane)
  {
    this.mainPane = mainPane;
  }

  public void setMainStage(Stage mainStage)
  {
    this.mainStage = mainStage;
  }

  private BorderPane mainPane;
  private Stage      mainStage;

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
  public void setPhaseData(ArrayList<String> phaseNames, ArrayList<Duration> durations)
  {

  }

  @Override
  public void showActivityView()
  {

  }

  @Override
  public void setActivityData(ArrayList<String> descriptions, ArrayList<String> comments,
                              ArrayList<ZonedDateTime> startTimes, ArrayList<ZonedDateTime> endTimes)
  {

  }

  @Override
  public void setController(PersonalStatisticController controller)
  {
    this.controller = controller;
  }

  @Override
  public void RemoveAllComponents()
  {

  }

  @Override
  public void showError(String localizedMessage)
  {

  }

  @Override
  public int getSelectedProjectPeriod()
  {
    return 0;
  }

  @Override
  public int getSelectedActivity()
  {
    return 0;
  }

  @Override
  public void hide()
  {

  }

  @Override
  public void showAddActivityDialog()
  {

  }

  @Override
  public boolean confirmDeletion()
  {
    return false;
  }

  @Override
  public void showUpdateActivityDialog(String description, String comment, LocalDate start, LocalDate end)
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
  public void updateUI()
  {

  }
}
