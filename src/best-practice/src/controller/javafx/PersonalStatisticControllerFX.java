package controller.javafx;

import controller.interfaces.PersonalStatisticController;
import data.Activity;
import data.Project;
import data.ProjectPhase;
import model.interfaces.PersonalStatisticModel;
import view.interfaces.PersonalStatisticView;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;

public class PersonalStatisticControllerFX implements PersonalStatisticController
{
  @Override
  public void setModel(PersonalStatisticModel model)
  {

  }

  @Override
  public void setView(PersonalStatisticView view)
  {

  }

  @Override
  public void refresh()
  {

  }

  @Override
  public void phasePeriodChanged(int selectedIndex)
  {

  }

  @Override
  public void projectPeriodChanged(int selectedIndex)
  {

  }

  @Override
  public void doubleClickOnProject(int index)
  {

  }

  @Override
  public void addActivityClicked()
  {

  }

  @Override
  public void deleteActivityClicked()
  {

  }

  @Override
  public void updateActivityClicked()
  {

  }

  @Override
  public void backToPhaseViewClicked()
  {

  }

  @Override
  public void backToOverviewClicked()
  {

  }

  @Override
  public void doubleClickOnPhase(int index)
  {

  }

  @Override
  public void activityPeriodChanged(int selectedIndex)
  {

  }

  @Override
  public void showProjectView()
  {

  }

  @Override
  public void showPhaseView()
  {

  }

  @Override
  public void showActivityView()
  {

  }

  @Override
  public void setActivityData(ArrayList<Activity> activities)
  {

  }

  @Override
  public void setPhaseData(ArrayList<ProjectPhase> phases, ArrayList<Duration> durations)
  {

  }

  @Override
  public void setProjectData(ArrayList<Project> projects, ArrayList<Duration> durations)
  {

  }

  @Override
  public void addActivity(String description, String comment, LocalDate start, LocalDate end)
  {

  }

  @Override
  public void updateActivity(String description, String comment, LocalDate start, LocalDate end)
  {

  }
}
