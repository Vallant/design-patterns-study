package model.impl;

import controller.javafx.PersonalStatisticControllerFX;
import controller.swing.PersonalStatisticControllerSwing;
import data.Activity;
import data.Project;
import data.ProjectPhase;
import data.User;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

/**
 * Created by stephan on 17/07/17.
 */
public class PersonalStatisticModelImpl
{

  public enum PERIOD
  {
    ALLTIME,
    YEAR,
    MONTH,
    WEEK,
    DAY
  }

  private MainModelImpl                    mainModel;
  private PersonalStatisticControllerSwing controllerSwing;
  private PersonalStatisticControllerFX    controllerFX;
  private User                             user;


  public void deleteActivity(Activity toDelete) throws Exception
  {
    toDelete.deleteInDb(mainModel.db());
    if(controllerSwing != null)
      controllerSwing.refresh();
    else
      controllerFX.refresh();
  }


  public void addActivity(ProjectPhase detailPhase, String description, String comment, ZonedDateTime zdtStart,
                          ZonedDateTime zdtEnd) throws Exception
  {
    if(zdtEnd.isBefore(zdtStart))
      throw new Exception("Start date has to be before End date");
    Activity a = new Activity(detailPhase, user, description, zdtStart, zdtEnd, comment);

    a.insertIntoDb(mainModel.db());
    if(controllerSwing != null)
      controllerSwing.refresh();
    else
      controllerFX.refresh();
  }


  public void updateActivity(Activity a) throws Exception
  {
    a.updateInDb(mainModel.db());
    if(controllerSwing != null)
      controllerSwing.refresh();
    else
      controllerFX.refresh();
  }


  public void setUser(User user)
  {
    this.user = user;
  }


  public void setMainModel(MainModelImpl mainModel)
  {
    this.mainModel = mainModel;
  }


  public void setController(PersonalStatisticControllerSwing controller)
  {
    this.controllerSwing = controller;
  }
  public void setController(PersonalStatisticControllerFX controller)
  {
    controllerFX = controller;
  }


  public void refresh()
  {
    if(controllerSwing != null)
      controllerSwing.refresh();
    else
      controllerFX.refresh();
  }


  public void requestedDetailFor(Project project) throws Exception
  {

    phasePeriodChanged(project.getId(), PERIOD.ALLTIME.ordinal());
    if(controllerSwing != null)
      controllerSwing.showPhaseView();
    else
      controllerFX.showPhaseView();
  }


  public void requestedDetailFor(ProjectPhase detailPhase) throws Exception
  {
    activityPeriodChanged(detailPhase.getId(), PERIOD.ALLTIME.ordinal());
    if(controllerSwing != null)
      controllerSwing.showActivityView();
    else
      controllerFX.showActivityView();
  }


  public void phasePeriodChanged(int projectId, int selectedIndex) throws Exception
  {
    PERIOD period = PERIOD.values()[selectedIndex];
    ArrayList<ProjectPhase> phases = new ArrayList<>();
    ArrayList<Duration> durations = new ArrayList<>();


    ZonedDateTime since = subtract(period);

    Activity.getPhasesAndWorkloadSince(user.getLoginName(), projectId, since, phases, durations, mainModel.db());
    if(controllerSwing != null)
      controllerSwing.setPhaseData(phases, durations);
    else
      controllerFX.setPhaseData(phases, durations);
  }


  public void projectPeriodChanged(int selectedIndex) throws Exception
  {
    PERIOD period = PERIOD.values()[selectedIndex];
    ArrayList<Project> projects = new ArrayList<>();
    ArrayList<Duration> durations = new ArrayList<>();


    ZonedDateTime since = subtract(period);



    Activity.getParticipatingProjectsAndWorkloadSince(user.getLoginName(), since, projects, durations, mainModel.db());
    if(controllerSwing != null)
      controllerSwing.setProjectData(projects, durations);
    else
      controllerFX.setProjectData(projects, durations);
  }


  public void activityPeriodChanged(int phaseId, int selectedIndex) throws Exception
  {
    PERIOD period = PERIOD.values()[selectedIndex];
    ZonedDateTime since = subtract(period);


    ArrayList<Activity> activities = Activity.getActivitiesForPhaseSince(user.getLoginName(), phaseId, since, mainModel.db());
    if(controllerSwing != null)
    {
      controllerSwing.showActivityView();
      controllerSwing.setActivityData(activities);
    }
    else
    {
      controllerFX.showActivityView();
      controllerFX.setActivityData(activities);
    }

  }

  private ZonedDateTime subtract(PERIOD period)
  {
    switch(period)
    {
      case ALLTIME:
        return ZonedDateTime.ofInstant(Instant.EPOCH, ZoneId.systemDefault());
      case YEAR:
        return ZonedDateTime.now().minusYears(1);
      case MONTH:
        return ZonedDateTime.now().minusMonths(1);
      case WEEK:
        return ZonedDateTime.now().minusWeeks(1);
      case DAY:
        return ZonedDateTime.now().minusDays(1);
      default:
        return ZonedDateTime.ofInstant(Instant.EPOCH, ZoneId.systemDefault());
    }
  }
}
