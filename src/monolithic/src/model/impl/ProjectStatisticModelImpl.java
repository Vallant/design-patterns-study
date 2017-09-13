package model.impl;

import controller.javafx.ProjectStatisticControllerFX;
import controller.swing.ProjectStatisticControllerSwing;
import data.*;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class ProjectStatisticModelImpl
{

  private User                            user;
  private MainModelImpl                   mainModel;
  private ProjectStatisticControllerSwing controllerSwing;
  private ProjectStatisticControllerFX    controllerFX;

  public void projectPeriodChanged(int selectedPeriodIndex) throws Exception
  {
    PERIOD period = PERIOD.values()[selectedPeriodIndex];
    ArrayList<Project> projects = new ArrayList<>();
    ArrayList<Duration> durations = new ArrayList<>();

    ZonedDateTime since = subtract(period);

    if(mainModel.db() != null)
      Activity.getOwnedProjectsAndWorkloadSince(user.getLoginName(), since, projects, durations, mainModel.db());
    else
      Activity.getOwnedProjectsAndWorkloadSince(user.getLoginName(), since, projects, durations, mainModel.dbMongo());

    if(controllerSwing != null)
      controllerSwing.setProjectData(projects, durations);
    else
      controllerFX.setProjectData(projects, durations);
  }

  public void activityDropDownChanged(int phaseId, int selectedPeriodIndex, boolean showAll, ProjectMember selectedUser)
    throws Exception
  {
    PERIOD period = PERIOD.values()[selectedPeriodIndex];
    ZonedDateTime since = subtract(period);

    ArrayList<Activity> activities;

    if(selectedUser == null)
    {
      if(mainModel.db() != null)
        activities = Activity.getActivitiesByUserForPhaseSince("", phaseId, since, mainModel.db());
      else
        activities = Activity.getActivitiesByUserForPhaseSince("", phaseId, since, mainModel.dbMongo());
    }

    else
    {
      if(mainModel.db() != null)
        activities = Activity.getActivitiesByUserForPhaseSince(selectedUser.getUserLoginName(), phaseId, since, mainModel.db());
      else
        activities = Activity.getActivitiesByUserForPhaseSince(selectedUser.getUserLoginName(), phaseId, since,
          mainModel.dbMongo());
    }


    if(controllerSwing != null)
      controllerSwing.setActivityData(activities);
    else
      controllerFX.setActivityData(activities);
  }

  public void phaseDropDownChanged(int projectId, int selectedPeriodIndex, boolean showAll, ProjectMember selectedUser)
    throws Exception
  {
    PERIOD period = PERIOD.values()[selectedPeriodIndex];
    ArrayList<ProjectPhase> phases = new ArrayList<>();
    ArrayList<Duration> durations = new ArrayList<>();
    ArrayList<ProjectMember> members = new ArrayList<>();


    ZonedDateTime since = subtract(period);

    if(selectedUser == null)
    {
      if(mainModel.db() != null)
        Activity.getPhasesAndWorkloadForUserSince("", projectId, since, phases, durations, mainModel.db());
      else
        Activity.getPhasesAndWorkloadForUserSince("", projectId, since, phases, durations, mainModel.dbMongo());
    }
    else
    {
      if(mainModel.db() != null)
        Activity.getPhasesAndWorkloadForUserSince(selectedUser.getUserLoginName(), projectId, since, phases, durations,
          mainModel.db());
      else
        Activity.getPhasesAndWorkloadForUserSince(selectedUser.getUserLoginName(), projectId, since, phases, durations,
          mainModel.dbMongo());
    }


    if(mainModel.db() != null)
      members = ProjectMember.getMembersByProjectId(projectId, mainModel.db());
    else
      members = ProjectMember.getMembersByProjectId(projectId, mainModel.dbMongo());

    if(controllerSwing != null)
      controllerSwing.setPhaseData(members, phases, durations);
    else
      controllerFX.setPhaseData(members, phases, durations);

  }

  public void setUser(User user)
  {
    this.user = user;
  }

  public void setMainModel(MainModelImpl mainModel)
  {
    this.mainModel = mainModel;
  }

  public void setController(ProjectStatisticControllerSwing controller)
  {
    this.controllerSwing = controller;
  }

  public void setController(ProjectStatisticControllerFX controller)
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
    phaseDropDownChanged(project.getId(), PERIOD.ALLTIME.ordinal(), true, null);

    if(controllerSwing != null)
      controllerSwing.showPhaseView();
    else
      controllerFX.showPhaseView();
  }

  public void requestedDetailFor(ProjectPhase projectPhase) throws Exception
  {
    activityDropDownChanged(projectPhase.getId(), PERIOD.ALLTIME.ordinal(), true, null);

    if(controllerSwing != null)
      controllerSwing.showActivityView();
    else
      controllerFX.showActivityView();
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

  public enum PERIOD
  {
    ALLTIME,
    YEAR,
    MONTH,
    WEEK,
    DAY
  }
}
