package model.impl;

import controller.standard.ProjectStatisticControllerStandard;
import data.*;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class ProjectStatisticModelImpl
{

  public enum PERIOD
  {
    ALLTIME,
    YEAR,
    MONTH,
    WEEK,
    DAY
  }

  private User                               user;
  private MainModelImpl                      mainModel;
  private ProjectStatisticControllerStandard controller;


  public void projectPeriodChanged(int selectedPeriodIndex) throws Exception
  {
    PERIOD period = PERIOD.values()[selectedPeriodIndex];
    ArrayList<Project> projects = new ArrayList<>();
    ArrayList<Duration> durations = new ArrayList<>();

    ZonedDateTime since = subtract(period);

    if(mainModel.dbPostgres() != null)
      Activity.getOwnedProjectsAndWorkloadSince(user.getLoginName(), since, projects, durations, mainModel.dbPostgres());
    else
      Activity.getOwnedProjectsAndWorkloadSince(user.getLoginName(), since, projects, durations, mainModel.dbMongo());
    controller.setProjectData(projects, durations);
  }


  public void activityDropDownChanged(int phaseId, int selectedPeriodIndex, boolean showAll, ProjectMember selectedUser)
    throws Exception
  {
    PERIOD period = PERIOD.values()[selectedPeriodIndex];
    ZonedDateTime since = subtract(period);

    ArrayList<Activity> activities;

    if(selectedUser == null)
    {
      if(mainModel.dbPostgres() != null)
        activities = Activity.getActivitiesByUserForPhaseSince("", phaseId, since, mainModel.dbPostgres());
      else
        activities = Activity.getActivitiesByUserForPhaseSince("", phaseId, since, mainModel.dbMongo());
    }

    else
    {
      if(mainModel.dbPostgres() != null)
        activities = Activity.getActivitiesByUserForPhaseSince(selectedUser.getUserLoginName(), phaseId, since, mainModel.dbPostgres());
      else
        activities = Activity.getActivitiesByUserForPhaseSince(selectedUser.getUserLoginName(), phaseId, since,
          mainModel.dbMongo());
    }
    controller.setActivityData(activities);
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
      if(mainModel.dbPostgres() != null)
        Activity.getPhasesAndWorkloadForUserSince("", projectId, since, phases, durations, mainModel.dbPostgres());
      else
        Activity.getPhasesAndWorkloadForUserSince("", projectId, since, phases, durations, mainModel.dbMongo());
    }
    else
    {
      if(mainModel.dbPostgres() != null)
        Activity.getPhasesAndWorkloadForUserSince(selectedUser.getUserLoginName(), projectId, since, phases, durations,
          mainModel.dbPostgres());
      else
        Activity.getPhasesAndWorkloadForUserSince(selectedUser.getUserLoginName(), projectId, since, phases, durations,
          mainModel.dbMongo());
    }


    if(mainModel.dbPostgres() != null)
      members = ProjectMember.getMembersByProjectId(projectId, mainModel.dbPostgres());
    else
      members = ProjectMember.getMembersByProjectId(projectId, mainModel.dbMongo());
    controller.setPhaseData(members, phases, durations);

  }


  public void setUser(User user)
  {
    this.user = user;
  }


  public void setMainModel(MainModelImpl mainModel)
  {
    this.mainModel = mainModel;
  }


  public void setController(ProjectStatisticControllerStandard controller)
  {
    this.controller = controller;
  }


  public void refresh()
  {
    controller.refresh();
  }


  public void requestedDetailFor(Project project) throws Exception
  {
    phaseDropDownChanged(project.getId(), PERIOD.ALLTIME.ordinal(), true, null);
    controller.showPhaseView();
  }


  public void requestedDetailFor(ProjectPhase projectPhase) throws Exception
  {
    activityDropDownChanged(projectPhase.getId(), PERIOD.ALLTIME.ordinal(), true, null);
    controller.showActivityView();
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
