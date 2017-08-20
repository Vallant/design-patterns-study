package db.mongo.repository;

import data.Activity;
import data.Project;
import data.ProjectPhase;
import db.interfaces.ActivityRepository;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class ActivityRepositoryMongo implements ActivityRepository
{
  @Override
  public Activity getByPrimaryKey(int id) throws Exception
  {
    return null;
  }

  @Override
  public void getParticipatingProjectsAndWorkloadSince(String loginName, ZonedDateTime since,
                                                       ArrayList<Project> projects, ArrayList<Duration> durations)
    throws Exception
  {

  }

  @Override
  public void getPhasesAndWorkloadSince(String loginName, int projectId, ZonedDateTime since,
                                        ArrayList<ProjectPhase> phases, ArrayList<Duration> durations) throws Exception
  {

  }

  @Override
  public ArrayList<Activity> getActivitiesForPhaseSince(String loginName, int id, ZonedDateTime since) throws Exception
  {
    return null;
  }

  @Override
  public ArrayList<Activity> getActivitiesByUserForPhaseSince(String s, int phaseId, ZonedDateTime since)
    throws Exception
  {
    return null;
  }

  @Override
  public void getOwnedProjectsAndWorkloadSince(String loginName, ZonedDateTime since, ArrayList<Project> projects,
                                               ArrayList<Duration> durations) throws Exception
  {

  }

  @Override
  public void getPhasesAndWorkloadForUserSince(String loginName, int projectId, ZonedDateTime since,
                                               ArrayList<ProjectPhase> phases, ArrayList<Duration> durations)
    throws Exception
  {

  }

  @Override
  public void add(Activity item) throws Exception
  {

  }

  @Override
  public void update(Activity item) throws Exception
  {

  }

  @Override
  public void delete(Activity item) throws Exception
  {

  }

  @Override
  public List<Activity> getAll() throws Exception
  {
    return null;
  }
}
