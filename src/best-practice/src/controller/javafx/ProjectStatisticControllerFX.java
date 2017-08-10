package controller.javafx;

import controller.interfaces.ProjectStatisticController;
import data.Activity;
import data.Project;
import data.ProjectMember;
import data.ProjectPhase;
import model.interfaces.ProjectStatisticModel;
import view.interfaces.ProjectStatisticView;

import java.time.Duration;
import java.util.ArrayList;

public class ProjectStatisticControllerFX implements ProjectStatisticController
{
  @Override
  public void setModel(ProjectStatisticModel model)
  {

  }

  @Override
  public void setView(ProjectStatisticView view)
  {

  }

  @Override
  public void refresh()
  {

  }

  @Override
  public void phaseDropDownChanged(int selectedPeriodIndex, int selectedUserIndex)
  {

  }

  @Override
  public void projectPeriodChanged(int selectedPeriodIndex)
  {

  }

  @Override
  public void activityDropDownChanged(int selectedPeriodIndex, int selectedUserIndex)
  {

  }

  @Override
  public void doubleClickOnProject(int index)
  {

  }

  @Override
  public void backToProjectClicked()
  {

  }

  @Override
  public void doubleClickOnPhase(int index)
  {

  }

  @Override
  public void backToPhaseClicked()
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
  public void setProjectData(ArrayList<Project> projects, ArrayList<Duration> durations)
  {

  }

  @Override
  public void setPhaseData(ArrayList<ProjectMember> members, ArrayList<ProjectPhase> phases,
                           ArrayList<Duration> durations)
  {

  }

  @Override
  public void setActivityData(ArrayList<Activity> activities)
  {

  }
}
