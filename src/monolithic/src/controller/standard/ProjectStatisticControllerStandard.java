package controller.standard;

import data.Activity;
import data.Project;
import data.ProjectMember;
import data.ProjectPhase;
import model.impl.ProjectStatisticModelImpl;
import view.javafx.projectstatistic.ProjectStatisticViewFX;
import view.swing.projectstatistic.ProjectStatisticViewSwing;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class ProjectStatisticControllerStandard
{

  private ProjectStatisticModelImpl model;
  private ProjectStatisticViewSwing viewSwing;
  private ProjectStatisticViewFX viewFX;

  private ArrayList<Project> projects;

  private ArrayList<ProjectPhase> phases;
  private ProjectPhase            currentPhase;
  private Project                  currentProject;

  private ArrayList<Activity> activities;

  private ArrayList<ProjectMember> members;


  public void setModel(ProjectStatisticModelImpl model)
  {
    this.model = model;
  }


  public void setViewSwing(ProjectStatisticViewSwing viewSwing)
  {
    this.viewSwing = viewSwing;
  }

  public void setViewFX(ProjectStatisticViewFX viewFX)
  {
    this.viewFX = viewFX;
  }

  public void refresh()
  {
    try
    {
      if(viewSwing != null)
      {
        model.projectPeriodChanged(viewSwing.getSelectedProjectPeriod());
        if(currentPhase != null)
          model.phaseDropDownChanged(currentPhase.getId(), viewSwing.getSelectedPhasePeriod(),
            viewSwing.getSelectedUserPhase() == 0,
            members.get(viewSwing.getSelectedUserPhase()));
        if(activities != null)
        {
          assert currentPhase != null;
          model.activityDropDownChanged(currentPhase.getId(), viewSwing.getSelectedActivityPeriod(),
            viewSwing.getSelectedUserActivity() == 0, members.get(viewSwing.getSelectedUserActivity()));
        }
      }
      else
      {
        model.projectPeriodChanged(viewFX.getSelectedProjectPeriod());
        if(currentPhase != null)
          model.phaseDropDownChanged(currentPhase.getId(), viewFX.getSelectedPhasePeriod(),
            viewFX.getSelectedUserPhase() == 0,
            members.get(viewFX.getSelectedUserPhase()));
        if(activities != null)
        {
          assert currentPhase != null;
          model.activityDropDownChanged(currentPhase.getId(), viewFX.getSelectedActivityPeriod(),
            viewFX.getSelectedUserActivity() == 0, members.get(viewFX.getSelectedUserActivity()));
        }
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
      if(selectedPeriodIndex == -1 || selectedUserIndex == -1)
        return;

      if(members != null)
        model.phaseDropDownChanged(currentProject.getId(), selectedPeriodIndex, selectedUserIndex == 0,
          members.get(selectedUserIndex));
    }
    catch(Exception e)
    {
      if(viewSwing != null)
        viewSwing.showError(e.getLocalizedMessage());
      else
        viewFX.showError(e.getLocalizedMessage());
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
      if(viewSwing != null)
        viewSwing.showError(e.getLocalizedMessage());
      else
        viewFX.showError(e.getLocalizedMessage());
      e.printStackTrace();
    }
  }


  public void activityDropDownChanged(int selectedPeriodIndex, int selectedUserIndex)
  {
    try
    {
      if(selectedPeriodIndex == -1 || selectedUserIndex == -1)
        return;
      model.activityDropDownChanged(currentPhase.getId(), selectedPeriodIndex, selectedUserIndex == 0,
        members.get(selectedUserIndex));
    }
    catch(Exception e)
    {
      if(viewSwing != null)
        viewSwing.showError(e.getLocalizedMessage());
      else
        viewFX.showError(e.getLocalizedMessage());
      e.printStackTrace();
    }
  }


  public void showProjectView()
  {
    if(viewSwing != null)
      viewSwing.showProjectView();
    else
      viewFX.showProjectView();
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
    if(viewSwing != null)
      viewSwing.setProjectData(projectNames, durations);
    else
      viewFX.setProjectData(projectNames, durations);
  }


  public void showPhaseView()
  {
    viewSwing.showPhaseView();
  }


  public void setPhaseData(ArrayList<ProjectMember> members, ArrayList<ProjectPhase> phases,
                           ArrayList<Duration> durations)
  {
    this.phases = phases;
    this.members = members;
    this.members.add(0, null);
    ArrayList<String> phaseNames = new ArrayList<>();

    for(ProjectPhase pp : phases)
    {
      phaseNames.add(pp.getName());
    }

    ArrayList<String> memberNames = new ArrayList<>();
    for(ProjectMember pm : members)
    {
      if(pm != null)
        memberNames.add(pm.getUser().getFirstName() + " " + pm.getUser().getLastName());

    }

    if(viewSwing != null)
      viewSwing.setPhaseData(phaseNames, durations, memberNames);
    else
      viewFX.setPhaseData(phaseNames, durations, memberNames);
  }


  public void showActivityView()
  {
    if(viewSwing != null)
      viewSwing.showActivityView();
    else
      viewFX.showActivityView();
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
      if(pm != null)
        memberNames.add(pm.getUser().getFirstName() + " " + pm.getUser().getLastName());

    }

    if(viewSwing != null)
      viewSwing.setActivityData(userNames, descriptions, comments, startTimes, endTimes, memberNames);
    else
      viewFX.setActivityData(userNames, descriptions, comments, startTimes, endTimes, memberNames);
  }


  public void doubleClickOnProject(int index)
  {
    try
    {
      model.requestedDetailFor(projects.get(index));
    }
    catch(Exception e)
    {
      if(viewSwing != null)
        viewSwing.showError(e.getLocalizedMessage());
      else
        viewFX.showError(e.getLocalizedMessage());
      e.printStackTrace();
    }
  }


  public void backToProjectClicked()
  {
    currentPhase = null;
    currentProject = null;
    activities = null;
    phases = null;
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
      if(viewSwing != null)
        viewSwing.showError(e.getLocalizedMessage());
      else
        viewFX.showError(e.getLocalizedMessage());
      e.printStackTrace();
    }
  }


  public void backToPhaseClicked()
  {
    showPhaseView();
  }
}
