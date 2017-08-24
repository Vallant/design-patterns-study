package db.mongo.repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import data.Activity;
import data.Project;
import data.ProjectPhase;
import data.User;
import db.common.DBManagerMongo;
import db.interfaces.ActivityRepository;
import db.interfaces.ProjectPhaseRepository;
import db.interfaces.UserRepository;
import org.bson.Document;

import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class ActivityRepositoryMongo implements ActivityRepository
{
  private final DBManagerMongo manager;

  public ActivityRepositoryMongo(DBManagerMongo db)
  {
    this.manager = db;
  }

  @Override
  public Activity getByPrimaryKey(int id) throws Exception
  {
    MongoCollection<Document> coll = manager.getDb().getCollection("activity");
    FindIterable<Document> doc = coll.find(eq("id", id));
    MongoCursor<Document> cursor = doc.iterator();
    if(!cursor.hasNext())
      throw new Exception("no such record");

    return extractActivity(cursor.next());
  }

  private Activity extractActivity(Document next) throws Exception
  {
    int id = next.getInteger("id");
    int hash = next.getInteger("hash");
    int projectPhaseId = next.getInteger("project_phase_id");
    int projectId = next.getInteger("project_id");
    String userLoginName = next.getString("user_login_name");
    String description = next.getString("description");
    Instant start = next.getDate("start_time").toInstant();
    ZonedDateTime zonedStart = ZonedDateTime.ofInstant(start, ZoneId.systemDefault());
    Instant end = next.getDate("end_time").toInstant();
    ZonedDateTime zonedEnd = ZonedDateTime.ofInstant(end, ZoneId.systemDefault());
    String comments = next.getString("comments");

    ProjectPhaseRepository ppr = new ProjectPhaseRepositoryMongo(manager);
    ProjectPhase phase = ppr.getByPrimaryKey(projectPhaseId);
    UserRepository ur = new UserRepositoryMongo(manager);
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
    MongoCollection<Document> coll = manager.getDb().getCollection("activity");

    Document toAdd = new Document("hash", item.getLocalHash())
      .append("project_phase_id", item.getProjectPhaseId())
      .append("project_id", item.getProjectId())
      .append("user_login_name", item.getUserLoginName())
      .append("description", item.getDescription())
      .append("start_time", Date.from(item.getStart().toLocalDateTime().atZone(ZoneId.of("UTC")).toInstant()))
      .append("end_time", Date.from(item.getStop().toLocalDateTime().atZone(ZoneId.of("UTC")).toInstant()))
      .append("comment", item.getComments())
      .append("id", manager.getNextSequence("activity"));
    coll.insertOne(toAdd);
    item.setId(toAdd.getInteger("id"));
  }

  @Override
  public void update(Activity item) throws Exception
  {
    MongoCollection<Document> coll = manager.getDb().getCollection("activity");
    Document toUpdate = new Document("hash", item.getLocalHash())
      .append("project_phase_id", item.getProjectPhaseId())
      .append("project_id", item.getProjectId())
      .append("user_login_name", item.getUserLoginName())
      .append("description", item.getDescription())
      .append("start_time", Date.from(item.getStart().toLocalDateTime().atZone(ZoneId.of("UTC")).toInstant()))
      .append("end_time", Date.from(item.getStop().toLocalDateTime().atZone(ZoneId.of("UTC")).toInstant()))
      .append("comment", item.getComments())
      .append("id", item.getId());

    UpdateResult result = coll.updateOne(and(eq("id", item.getId()), eq("hash", item.getRemoteHash())), toUpdate);
    if(result.getModifiedCount() != 1)
      throw new Exception("Record was modified or not found");
  }

  @Override
  public void delete(Activity item) throws Exception
  {
    MongoCollection<Document> coll = manager.getDb().getCollection("activity");
    DeleteResult result = coll.deleteOne(and(eq("id", item.getId()), eq("hash", item.getRemoteHash())));
    if(result.getDeletedCount() != 1)
      throw new Exception("Record was modified or not found");
  }

  @Override
  public List<Activity> getAll() throws Exception
  {
    ArrayList<Activity> list = new ArrayList<>();
    MongoCollection<Document> coll = manager.getDb().getCollection("activity");
    FindIterable<Document> doc = coll.find();
    MongoCursor<Document> cursor = doc.iterator();
    while(cursor.hasNext())
      list.add(extractActivity(cursor.next()));

    return list;
  }
}
