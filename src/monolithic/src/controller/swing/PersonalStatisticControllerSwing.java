package controller.swing;

import data.Activity;
import data.Project;
import data.ProjectPhase;
import model.impl.PersonalStatisticModelImpl;
import view.swing.personalstatistic.PersonalStatisticViewSwing;

import java.time.*;
import java.util.ArrayList;

/**
 * Created by stephan on 17/07/17.
 */
public class PersonalStatisticControllerSwing
{
  private PersonalStatisticModelImpl model;
  private PersonalStatisticViewSwing view;

  private ArrayList<Project>      currentProjects;
  private ArrayList<ProjectPhase> currentPhases;
  private ArrayList<Activity>     currentActivities;
  private Project                 detailProject;
  private ProjectPhase            detailPhase;

  public PersonalStatisticControllerSwing()
  {

  }


  public void setModel(PersonalStatisticModelImpl model)
  {
    this.model = model;
  }


  public void setView(PersonalStatisticViewSwing view)
  {
    this.view = view;
  }


  public void refresh()
  {
    try
    {
      if(detailProject != null)
        model.phasePeriodChanged(detailProject.getId(), view.getSelectedPhasePeriod());
      if(currentActivities != null)
        model.activityPeriodChanged(detailPhase.getId(), view.getSelectedActivityPeriod());

      model.projectPeriodChanged(view.getSelectedProjectPeriod());

      view.updateUI();

    }
    catch(Exception e)
    {
      view.showError(e.getLocalizedMessage());
      e.printStackTrace();
    }
  }


  public void phasePeriodChanged(int selectedIndex)
  {
    try
    {
      assert (detailProject != null);
      model.phasePeriodChanged(detailProject.getId(), selectedIndex);
    }
    catch(Exception e)
    {
      view.showError(e.getLocalizedMessage());
      e.printStackTrace();
    }
  }


  public void projectPeriodChanged(int selectedIndex)
  {
    try
    {
      model.projectPeriodChanged(selectedIndex);
    }
    catch(Exception e)
    {
      view.showError(e.getLocalizedMessage());
      e.printStackTrace();
    }
  }


  public void doubleClickOnProject(int index)
  {
    detailProject = currentProjects.get(index);
    try
    {
      model.requestedDetailFor(detailProject);
    }
    catch(Exception e)
    {
      view.showError(e.getLocalizedMessage());
      e.printStackTrace();
    }
  }


  public void setProjectData(ArrayList<Project> projects, ArrayList<Duration> durations)
  {
    currentProjects = projects;
    detailProject = null;
    currentPhases = null;
    ArrayList<String> projectNames = new ArrayList<>();
    for(Project p : projects)
    {
      projectNames.add(p.getName());
    }
    view.setProjectData(projectNames, durations);
  }


  public void setPhaseData(ArrayList<ProjectPhase> phases, ArrayList<Duration> durations)
  {
    currentPhases = phases;
    ArrayList<String> phaseNames = new ArrayList<>();
    for(ProjectPhase pp : phases)
    {
      phaseNames.add(pp.getName());
    }

    view.setPhaseData(phaseNames, durations);
  }


  private void showProjectView()
  {
    view.showProjectView();
  }


  public void showPhaseView()
  {
    view.showPhaseView();
  }


  public void backToOverviewClicked()
  {
    currentPhases = null;
    showProjectView();
  }


  public void doubleClickOnPhase(int index)
  {
    detailPhase = currentPhases.get(index);
    try
    {
      model.requestedDetailFor(detailPhase);
    }
    catch(Exception e)
    {
      view.showError(e.getLocalizedMessage());
      e.printStackTrace();
    }
  }


  public void activityPeriodChanged(int selectedIndex)
  {
    try
    {
      assert (detailPhase != null);
      model.activityPeriodChanged(detailPhase.getId(), selectedIndex);
    }
    catch(Exception e)
    {
      view.showError(e.getLocalizedMessage());
      e.printStackTrace();
    }
  }


  public void setActivityData(ArrayList<Activity> activities)
  {
    currentActivities = activities;
    ArrayList<String> descriptions = new ArrayList<>();
    ArrayList<String> comments = new ArrayList<>();
    ArrayList<ZonedDateTime> startTimes = new ArrayList<>();
    ArrayList<ZonedDateTime> endTimes = new ArrayList<>();
    for(Activity a : activities)
    {
      descriptions.add(a.getDescription());
      comments.add(a.getComments());
      startTimes.add(a.getStart());
      endTimes.add(a.getStop());
    }
    view.setActivityData(descriptions, comments, startTimes, endTimes);


  }


  public void showActivityView()
  {
    view.showActivityView();
  }


  public void addActivityClicked()
  {
    view.showAddActivityDialog();
  }


  public void deleteActivityClicked()
  {
    if(view.confirmDeletion())
    {
      Activity toDelete = currentActivities.get(view.getSelectedActivity());
      try
      {
        model.deleteActivity(toDelete);
      }
      catch(Exception e)
      {
        view.showError(e.getLocalizedMessage());
        e.printStackTrace();
      }
    }
  }


  public void updateActivityClicked()
  {
    Activity a = currentActivities.get(view.getSelectedActivity());
    view.showUpdateActivityDialog(a.getDescription(), a.getComments(), a.getStart().toLocalDateTime(),
      a.getStop().toLocalDateTime());
  }


  public void addActivity(String description, String comment, LocalDateTime start, LocalDateTime end)
  {
    if(description.isEmpty())
    {
      view.showError("Description of the Activity cannot be empty.");
      view.showAddActivityDialog();
      return;
    }
    ZonedDateTime zdtStart = start.atZone(ZoneId.of("UTC"));
    ZonedDateTime zdtEnd = end.atZone(ZoneId.of("UTC"));

    try
    {
      model.addActivity(detailPhase, description, comment, zdtStart, zdtEnd);
    }
    catch(Exception e)
    {
      view.showError(e.getLocalizedMessage());
      e.printStackTrace();
    }
  }


  public void updateActivity(String description, String comment, LocalDateTime start, LocalDateTime end)
  {
    Activity a = currentActivities.get(view.getSelectedActivity());

    ZonedDateTime zdtStart = start.atZone(ZoneId.of("UTC"));
    ZonedDateTime zdtEnd = end.atZone(ZoneId.of("UTC"));
    a.setDescription(description);
    a.setComments(comment);
    a.setStart(zdtStart);
    a.setStop(zdtEnd);
    try
    {
      model.updateActivity(a);
    }
    catch(Exception e)
    {
      view.showError(e.getLocalizedMessage());
      e.printStackTrace();
    }

  }


  public void backToPhaseViewClicked()
  {
    currentActivities = null;
    showPhaseView();
  }
}
