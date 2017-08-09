package controller.interfaces;

import data.Activity;
import data.Project;
import data.ProjectMember;
import data.ProjectPhase;
import model.interfaces.ProjectStatisticModel;
import view.interfaces.ProjectStatisticView;

import java.time.Duration;
import java.util.ArrayList;

public interface ProjectStatisticController
{

  void setModel(ProjectStatisticModel model);

  void setView(ProjectStatisticView view);

  void refresh();

  void phaseDropDownChanged(int selectedPeriodIndex, int selectedUserIndex);

  void projectPeriodChanged(int selectedPeriodIndex);

  void activityDropDownChanged(int selectedPeriodIndex, int selectedUserIndex);

  void doubleClickOnProject(int index);

  void backToProjectClicked();

  void doubleClickOnPhase(int index);

  void backToPhaseClicked();

  void showProjectView();

  void showPhaseView();

  void showActivityView();

  void setProjectData(ArrayList<Project> projects, ArrayList<Duration> durations);

  void setPhaseData(ArrayList<ProjectMember> members, ArrayList<ProjectPhase> phases, ArrayList<Duration> durations);

  void setActivityData(ArrayList<Activity> activities);
}
