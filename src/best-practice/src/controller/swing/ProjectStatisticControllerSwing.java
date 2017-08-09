package controller.swing;

import controller.interfaces.ProjectStatisticController;
import data.Activity;
import data.Project;
import data.ProjectMember;
import data.ProjectPhase;
import model.interfaces.ProjectStatisticModel;
import view.interfaces.ProjectStatisticView;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class ProjectStatisticControllerSwing implements ProjectStatisticController
{

  ArrayList<Project> projects;
  ArrayList<ProjectPhase> phases;
  ProjectPhase            currentPhase;
  ArrayList<Activity> activities;
  ArrayList<ProjectMember> members;
  private ProjectStatisticModel model;
  private ProjectStatisticView  view;

  @Override
  public void setModel(ProjectStatisticModel model)
  {
    this.model = model;
  }

  @Override
  public void setView(ProjectStatisticView view)
  {
    this.view = view;
  }

  @Override
  public void refresh()
  {
    try
    {
      model.projectPeriodChanged(view.getSelectedProjectPeriod());
      if(currentPhase != null)
        model.phaseDropDownChanged(currentPhase.getId(), view.getSelectedPhasePeriod(), view.getSelectedUser() == 0,
          members.get(view.getSelectedUser()));
      if(activities != null)
        model.activityDropDownChanged(currentPhase.getId(), view.getSelectedActivityPeriod(),
          view.getSelectedUser() == 0, members.get(view.getSelectedUser()));
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  @Override
  public void phaseDropDownChanged(int selectedPeriodIndex, int selectedUserIndex)
  {
    try
    {
      if(members != null && currentPhase != null)
        model.phaseDropDownChanged(currentPhase.getId(), selectedPeriodIndex, selectedUserIndex == 0,
          members.get(selectedPeriodIndex));
    }
    catch(Exception e)
    {
      view.showError(e.getLocalizedMessage());
      e.printStackTrace();
    }
  }

  @Override
  public void projectPeriodChanged(int selectedPeriodIndex)
  {
    try
    {
      model.projectPeriodChanged(selectedPeriodIndex);
    }
    catch(Exception e)
    {
      view.showError(e.getLocalizedMessage());
      e.printStackTrace();
    }
  }

  @Override
  public void activityDropDownChanged(int selectedPeriodIndex, int selectedUserIndex)
  {
    try
    {
      model.activityDropDownChanged(currentPhase.getId(), selectedPeriodIndex, selectedUserIndex == 0,
        members.get(selectedPeriodIndex));
    }
    catch(Exception e)
    {
      view.showError(e.getLocalizedMessage());
      e.printStackTrace();
    }
  }

  @Override
  public void showProjectView()
  {
    view.showProjectView();
    refresh();
  }

  @Override
  public void setProjectData(ArrayList<Project> projects, ArrayList<Duration> durations)
  {
    this.projects = projects;

    ArrayList<String> projectNames = new ArrayList<>();
    for(Project p : projects)
    {
      projectNames.add(p.getName());
    }
    view.setProjectData(projectNames, durations);
  }

  @Override
  public void showPhaseView()
  {
    view.showPhaseView();
  }

  @Override
  public void setPhaseData(ArrayList<ProjectMember> members, ArrayList<ProjectPhase> phases,
                           ArrayList<Duration> durations)
  {
    this.phases = phases;
    this.members = members;
    ArrayList<String> phaseNames = new ArrayList<>();

    for(ProjectPhase pp : phases)
    {
      phaseNames.add(pp.getName());
    }

    ArrayList<String> memberNames = new ArrayList<>();
    for(ProjectMember pm : members)
    {
      memberNames.add(pm.getUser().getFirstName() + " " + pm.getUser().getLastName());
    }

    view.setPhaseData(phaseNames, durations, memberNames);
  }

  @Override
  public void showActivityView()
  {
    view.showActivityView();
  }

  @Override
  public void setActivityData(ArrayList<Activity> activities)
  {
    this.activities = activities;
    ArrayList<String> descriptions = new ArrayList<>();
    ArrayList<String> comments = new ArrayList<>();
    ArrayList<ZonedDateTime> startTimes = new ArrayList<>();
    ArrayList<ZonedDateTime> endTimes = new ArrayList<>();
    ArrayList<String> userNames = new ArrayList<>();
    for(Activity a : activities)
    {
      descriptions.add(a.getDescription());
      comments.add(a.getComments());
      startTimes.add(a.getStart());
      endTimes.add(a.getStop());
      userNames.add(a.getUser().getFirstName() + " " + a.getUser().getLastName());
    }

    ArrayList<String> memberNames = new ArrayList<>();
    for(ProjectMember pm : members)
    {
      memberNames.add(pm.getUser().getFirstName() + " " + pm.getUser().getLastName());
    }

    view.setActivityData(userNames, descriptions, comments, startTimes, endTimes, memberNames);
  }

  @Override
  public void doubleClickOnProject(int index)
  {
    try
    {
      model.requestedDetailFor(projects.get(index));
    }
    catch(Exception e)
    {
      view.showError(e.getLocalizedMessage());
      e.printStackTrace();
    }
  }

  @Override
  public void backToProjectClicked()
  {
    showProjectView();
  }

  @Override
  public void doubleClickOnPhase(int index)
  {
    try
    {
      currentPhase = phases.get(index);
      model.requestedDetailFor(phases.get(index));
    }
    catch(Exception e)
    {
      view.showError(e.getLocalizedMessage());
      e.printStackTrace();
    }
  }

  @Override
  public void backToPhaseClicked()
  {
    showPhaseView();
  }
}
