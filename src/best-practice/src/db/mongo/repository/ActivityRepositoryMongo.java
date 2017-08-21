package db.mongo.repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import data.Activity;
import data.Project;
import data.ProjectPhase;
import data.User;
import db.interfaces.ActivityRepository;
import db.interfaces.ProjectPhaseRepository;
import db.interfaces.UserRepository;
import org.bson.Document;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class ActivityRepositoryMongo implements ActivityRepository
{
  private final MongoDatabase db;

  public ActivityRepositoryMongo(MongoDatabase db)
  {
    this.db = db;
  }

  @Override
  public Activity getByPrimaryKey(int id) throws Exception
  {
    MongoCollection<Document> coll = db.getCollection("activity");
    FindIterable<Document> doc = coll.find(eq("_id", id));
    MongoCursor<Document> cursor = doc.iterator();
    if(!cursor.hasNext())
      throw new Exception("no such record");

    return extractActivity(cursor.next());
  }

  private Activity extractActivity(Document next) throws Exception
  {
    int id = next.getInteger("_id");
    int hash = next.getInteger("hash");
    int projectPhaseId = next.getInteger("project_phase_id");
    int projectId = next.getInteger("project_id");
    String userLoginName = next.getString("user_login_name");
    String description = next.getString("description");
    LocalDateTime start = (LocalDateTime) next.get("start_time");
    ZonedDateTime zonedStart = ZonedDateTime.of(start, ZoneId.systemDefault());
    LocalDateTime end = (LocalDateTime) next.get("end_time");
    ZonedDateTime zonedEnd = ZonedDateTime.of(end, ZoneId.systemDefault());
    String comments = next.getString("comments");

    ProjectPhaseRepository ppr = new ProjectPhaseRepositoryMongo(db);
    ProjectPhase phase = ppr.getByPrimaryKey(projectPhaseId);
    UserRepository ur = new UserRepositoryMongo(db);
    User user = ur.getByPrimaryKey(userLoginName);
    return new Activity(hash, id, phase, user, description, zonedStart, zonedEnd, comments);
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
