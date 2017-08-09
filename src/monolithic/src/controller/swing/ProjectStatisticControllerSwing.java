package controller.swing;

import data.Activity;
import data.Project;
import data.ProjectMember;
import data.ProjectPhase;
import model.impl.ProjectStatisticModelImpl;
import view.swing.projectstatistic.ProjectStatisticViewSwing;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class ProjectStatisticControllerSwing
{

  private ProjectStatisticModelImpl model;
  private ProjectStatisticViewSwing view;

  private ArrayList<Project> projects;

  private ArrayList<ProjectPhase> phases;
  private ProjectPhase            currentPhase;

  private ArrayList<Activity> activities;

  private ArrayList<ProjectMember> members;


  public void setModel(ProjectStatisticModelImpl model)
  {
    this.model = model;
  }


  public void setView(ProjectStatisticViewSwing view)
  {
    this.view = view;
  }


  public void refresh()
  {
    try
    {
      model.projectPeriodChanged(view.getSelectedProjectPeriod());
      if(currentPhase != null)
        model.phaseDropDownChanged(currentPhase.getId(), view.getSelectedPhasePeriod(), view.getSelectedUser() == 0,
          members.get(view.getSelectedUser()));
      if(activities != null)
      {
        assert currentPhase != null;
        model.activityDropDownChanged(currentPhase.getId(), view.getSelectedActivityPeriod(),
          view.getSelectedUser() == 0, members.get(view.getSelectedUser()));
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }


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


  private void showProjectView()
  {
    view.showProjectView();
    refresh();
  }


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


  public void showPhaseView()
  {
    view.showPhaseView();
  }


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


  public void showActivityView()
  {
    view.showActivityView();
  }


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


  public void backToProjectClicked()
  {
    showProjectView();
  }


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


  public void backToPhaseClicked()
  {
    showPhaseView();
  }
}
