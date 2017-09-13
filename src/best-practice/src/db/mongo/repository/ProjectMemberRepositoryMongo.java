package db.mongo.repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import data.Activity;
import data.Project;
import data.ProjectMember;
import data.User;
import db.common.DBManagerMongo;
import db.interfaces.ActivityRepository;
import db.interfaces.ProjectMemberRepository;
import db.interfaces.ProjectRepository;
import db.interfaces.UserRepository;
import org.bson.Document;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class ProjectMemberRepositoryMongo implements ProjectMemberRepository
{

  private final DBManagerMongo manager;

  public ProjectMemberRepositoryMongo(DBManagerMongo db)
  {
    this.manager = db;
  }

  @Override
  public ProjectMember getByPrimaryKey(String userLoginName, int projectId) throws Exception
  {
    MongoCollection<Document> coll = manager.getDb().getCollection("project_member");
    FindIterable<Document> doc = coll.find(and(eq("user_login_name", userLoginName), eq("project_id", projectId)));
    MongoCursor<Document> cursor = doc.iterator();
    if(!cursor.hasNext())
      throw new Exception("no such record");

    return extractMember(cursor.next());
  }

  private ProjectMember extractMember(Document next) throws Exception
  {
    int hash = next.getInteger("hash");
    String userLoginName = next.getString("user_login_name");
    int projectId = next.getInteger("project_id");
    String role = next.getString("role");

    UserRepository ur = new UserRepositoryMongo(manager);
    User user = ur.getByPrimaryKey(userLoginName);
    ProjectRepository pr = new ProjectRepositoryMongo(manager);
    Project project = pr.getByPrimaryKey(projectId);
    return new ProjectMember(user, project, hash, ProjectMember.ROLE.valueOf(role));
  }

  @Override
  public ArrayList<ProjectMember> getMembersByProjectId(int projectId) throws Exception
  {
    ArrayList<ProjectMember> list = new ArrayList<>();
    MongoCollection<Document> coll = manager.getDb().getCollection("project_member");
    FindIterable<Document> doc = coll.find(eq("project_id", projectId));
    MongoCursor<Document> cursor = doc.iterator();
    while(cursor.hasNext())
      list.add(extractMember(cursor.next()));

    return list;
  }

  @Override
  public ArrayList<ProjectMember> getInvolvedProjects(String loginName) throws Exception
  {
    ArrayList<ProjectMember> list = new ArrayList<>();
    MongoCollection<Document> coll = manager.getDb().getCollection("project_member");
    FindIterable<Document> doc = coll.find(and(eq("user_login_name", loginName), eq("role","MEMBER")));
    MongoCursor<Document> cursor = doc.iterator();
    while(cursor.hasNext())
      list.add(extractMember(cursor.next()));

    return list;
  }

  @Override
  public ArrayList<ProjectMember> getOwnedProject(String loginName) throws Exception
  {
    ArrayList<ProjectMember> list = new ArrayList<>();
    MongoCollection<Document> coll = manager.getDb().getCollection("project_member");
    FindIterable<Document> doc = coll.find(and(eq("user_login_name", loginName), eq("role","LEADER")));
    MongoCursor<Document> cursor = doc.iterator();
    while(cursor.hasNext())
      list.add(extractMember(cursor.next()));

    return list;
  }

  @Override
  public void add(ProjectMember item) throws Exception
  {
    MongoCollection<Document> coll = manager.getDb().getCollection("project_member");
    Document toUpdate = new Document("hash", item.getLocalHash())
      .append("user_login_name", item.getUserLoginName())
      .append("project_id", item.getProjectId())
      .append("role", item.getRole().name());

    coll.insertOne(toUpdate);
  }

  @Override
  public void update(ProjectMember item) throws Exception
  {

    MongoCollection<Document> coll = manager.getDb().getCollection("project_member");
    Document toUpdate = new Document("hash", item.getLocalHash())
      .append("user_login_name", item.getUserLoginName())
      .append("project_id", item.getProjectId())
      .append("role", item.getRole().name());

    UpdateResult result = coll.replaceOne(and(and(eq("user_login_name", item.getUserLoginName()), eq("hash",item
      .getRemoteHash())), eq
      ("project_id", item.getProjectId())), toUpdate);
    if(result.getModifiedCount() != 1)
      throw new Exception("Record was modyfied or not found");

    item.setRemoteHash(item.getLocalHash());
  }

  @Override
  public void delete(ProjectMember item) throws Exception
  {


    MongoCollection<Document> coll = manager.getDb().getCollection("project_member");
    DeleteResult result = coll.deleteOne(and(and(eq("user_login_name", item.getUserLoginName()), eq("hash",item
    .getRemoteHash())), eq
      ("project_id", item.getProjectId())));
    if(result.getDeletedCount() != 1)
      throw new Exception("Record was modyfied or not found");

    MongoCollection activity = manager.getDb().getCollection("activity");
    activity.deleteMany(and(eq("project_id", item.getProjectId()), eq("user_login_name", item.getUserLoginName())));
  }

  @Override
  public List<ProjectMember> getAll() throws Exception
  {
    ArrayList<ProjectMember> list = new ArrayList<>();
    MongoCollection<Document> coll = manager.getDb().getCollection("project_member");
    FindIterable<Document> doc = coll.find();
    MongoCursor<Document> cursor = doc.iterator();
    while(cursor.hasNext())
      list.add(extractMember(cursor.next()));

    return list;
  }
}
