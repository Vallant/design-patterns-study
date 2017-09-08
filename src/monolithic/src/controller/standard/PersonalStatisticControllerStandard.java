package controller.standard;

import data.Activity;
import data.Project;
import data.ProjectPhase;
import model.impl.PersonalStatisticModelImpl;
import view.javafx.personalstatistic.PersonalStatisticViewFX;
import view.swing.personalstatistic.PersonalStatisticViewSwing;

import java.time.*;
import java.util.ArrayList;

/**
 * Created by stephan on 17/07/17.
 */
public class PersonalStatisticControllerStandard
{
  private PersonalStatisticModelImpl model;
  private PersonalStatisticViewSwing viewSwing;
  private PersonalStatisticViewFX viewFX;

  private ArrayList<Project>      currentProjects;
  private ArrayList<ProjectPhase> currentPhases;
  private ArrayList<Activity>     currentActivities;
  private Project                 detailProject;
  private ProjectPhase            detailPhase;

  public PersonalStatisticControllerStandard()
  {

  }


  public void setModel(PersonalStatisticModelImpl model)
  {
    this.model = model;
  }


  public void setViewSwing(PersonalStatisticViewSwing viewSwing)
  {
    this.viewSwing = viewSwing;
  }

  public void setViewFX(PersonalStatisticViewFX viewFX)
  {
    this.viewFX = viewFX;
  }

  public void refresh()
  {
    try
    {
      if(viewSwing != null)
      {
        if(detailProject != null)
          model.phasePeriodChanged(detailProject.getId(), viewSwing.getSelectedPhasePeriod());
        if(currentActivities != null)
          model.activityPeriodChanged(detailPhase.getId(), viewSwing.getSelectedActivityPeriod());

        model.projectPeriodChanged(viewSwing.getSelectedProjectPeriod());

        viewSwing.updateUI();
      }
      else
      {
        if(detailProject != null)
          model.phasePeriodChanged(detailProject.getId(), viewFX.getSelectedPhasePeriod());
        if(currentActivities != null)
          model.activityPeriodChanged(detailPhase.getId(), viewFX.getSelectedActivityPeriod());

        model.projectPeriodChanged(viewFX.getSelectedProjectPeriod());

        viewFX.updateUI();
      }


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


  public void phasePeriodChanged(int selectedIndex)
  {
    try
    {
      assert (detailProject != null);
      model.phasePeriodChanged(detailProject.getId(), selectedIndex);
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


  public void projectPeriodChanged(int selectedIndex)
  {
    try
    {
      model.projectPeriodChanged(selectedIndex);
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


  public void doubleClickOnProject(int index)
  {
    detailProject = currentProjects.get(index);
    try
    {
      model.requestedDetailFor(detailProject);
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
    if(viewSwing != null)
      viewSwing.setProjectData(projectNames, durations);
    else
      viewFX.setProjectData(projectNames, durations);
  }


  public void setPhaseData(ArrayList<ProjectPhase> phases, ArrayList<Duration> durations)
  {
    currentPhases = phases;
    ArrayList<String> phaseNames = new ArrayList<>();
    for(ProjectPhase pp : phases)
    {
      phaseNames.add(pp.getName());
    }

    if(viewSwing != null)
      viewSwing.setPhaseData(phaseNames, durations);
    else
      viewFX.setPhaseData(phaseNames, durations);
  }


  private void showProjectView()
  {

    if(viewSwing != null)
      viewSwing.showProjectView();
    else
      viewFX.showProjectView();
  }


  public void showPhaseView()
  {
    if(viewSwing != null)
      viewSwing.showPhaseView();
    else
      viewFX.showPhaseView();
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
      if(viewSwing != null)
        viewSwing.showError(e.getLocalizedMessage());
      else
        viewFX.showError(e.getLocalizedMessage());
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
      if(viewSwing != null)
        viewSwing.showError(e.getLocalizedMessage());
      else
        viewFX.showError(e.getLocalizedMessage());
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
    if(viewSwing != null)
      viewSwing.setActivityData(descriptions, comments, startTimes, endTimes);
    else
      viewFX.setActivityData(descriptions, comments, startTimes, endTimes);


  }


  public void showActivityView()
  {
    if(viewSwing != null)
      viewSwing.showActivityView();
    else
      viewFX.showActivityView();
  }


  public void addActivityClicked()
  {
    if(viewSwing != null)
      viewSwing.showAddActivityDialog();
    else
      viewFX.showAddActivityDialog();
  }


  public void deleteActivityClicked()
  {
    boolean confirmed;
    if(viewSwing != null)
      confirmed = viewSwing.confirmDeletion();
    else
      confirmed = viewFX.confirmDeletion();

    if(confirmed)
    {
      Activity toDelete;
      if(viewSwing != null)
        toDelete = currentActivities.get(viewSwing.getSelectedActivity());
      else
        toDelete = currentActivities.get(viewFX.getSelectedActivity());
      try
      {
        model.deleteActivity(toDelete);
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
  }


  public void updateActivityClicked()
  {
    if(viewSwing != null)
    {
      Activity a = currentActivities.get(viewSwing.getSelectedActivity());
      viewSwing.showUpdateActivityDialog(a.getDescription(), a.getComments(), a.getStart().toLocalDateTime(),
        a.getStop().toLocalDateTime());
    }
    else
    {
      Activity a = currentActivities.get(viewFX.getSelectedActivity());
      viewFX.showUpdateActivityDialog(a.getDescription(), a.getComments(), a.getStart().toLocalDateTime(),
        a.getStop().toLocalDateTime());
    }

  }


  public void addActivity(String description, String comment, LocalDateTime start, LocalDateTime end)
  {
    if(description.isEmpty())
    {
      if(viewSwing != null)
      {
        viewSwing.showError("Description of the Activity cannot be empty.");
        viewSwing.showAddActivityDialog();
      }
      else
      {
        viewFX.showError("Description of the Activity cannot be empty.");
        viewFX.showAddActivityDialog();
      }

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
      if(viewSwing != null)
        viewSwing.showError(e.getLocalizedMessage());
      else
        viewFX.showError(e.getLocalizedMessage());
      e.printStackTrace();
    }
  }


  public void updateActivity(String description, String comment, LocalDateTime start, LocalDateTime end)
  {
    Activity a;
    if(viewSwing != null)
      a = currentActivities.get(viewSwing.getSelectedActivity());
    else
      a = currentActivities.get(viewFX.getSelectedActivity());

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
      if(viewSwing != null)
        viewSwing.showError(e.getLocalizedMessage());
      else
        viewFX.showError(e.getLocalizedMessage());
      e.printStackTrace();
    }

  }


  public void backToPhaseViewClicked()
  {
    currentActivities = null;
    showPhaseView();
  }
}
