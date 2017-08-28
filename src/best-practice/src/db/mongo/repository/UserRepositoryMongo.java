package db.mongo.repository;

import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import data.Activity;
import data.Project;
import data.User;
import db.common.DBManager;
import db.common.DBManagerMongo;
import db.interfaces.ActivityRepository;
import db.interfaces.ProjectRepository;
import db.interfaces.UserRepository;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.Binary;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.ne;


public class UserRepositoryMongo implements UserRepository
{

  private final DBManagerMongo manager;


  public UserRepositoryMongo(DBManagerMongo manager)
  {
    this.manager= manager;
  }

  @Override
  public User getByPrimaryKey(String loginName) throws Exception
  {
    MongoCollection<Document> coll = manager.getDb().getCollection("user");
    FindIterable<Document> doc = coll.find(eq("login_name", loginName));
    MongoCursor<Document> cursor = doc.iterator();
    if(!cursor.hasNext())
      throw new Exception("no such record");

    return extractUser(cursor.next());
  }

  @Override
  public ArrayList<User> getAvailableUsersFor(int projectId) throws Exception
  {
    ArrayList<User> list = new ArrayList<>();
    MongoCollection<Document> coll = manager.getDb().getCollection("user");
    Bson lookup = new Document("$lookup",
      new Document("from", "project_member")
        .append("localField", "login_name")
        .append("foreignField", "user_login_name") //local field, remote field
        .append("as", "project_member"));

    Bson match = new Document("$match",
      new Document("$or", Arrays.asList(
        new Document("project_member.project_id", null),
        new Document("project_member.project_id", projectId))
      ));

    List<Bson> filters = new ArrayList<>();
    filters.add(lookup);
    filters.add(match);
    AggregateIterable<Document> it = coll.aggregate(filters);

    for(Document row : it)
      list.add(extractUser(row));

    return list;
  }

  @Override
  public void add(User item) throws Exception
  {
    MongoCollection<Document> coll = manager.getDb().getCollection("user");
    Document toAdd = new Document("hash", item.getLocalHash())
      .append("login_name", item.getLoginName())
      .append("first_name", item.getFirstName())
      .append("last_name", item.getLastName())
      .append("email", item.getEmail())
      .append("salt", item.getSalt())
      .append("password", item.getPassword())
      .append("role", item.getRole().name());
    coll.insertOne(toAdd);
  }

  @Override
  public void update(User item) throws Exception
  {
    MongoCollection<Document> coll = manager.getDb().getCollection("user");
    Document toUpdate = new Document("hash", item.getLocalHash())
      .append("login_name", item.getLoginName())
      .append("first_name", item.getFirstName())
      .append("last_name", item.getLastName())
      .append("email", item.getEmail())
      .append("salt", item.getSalt())
      .append("password", item.getPassword())
      .append("role", item.getRole().name());

    UpdateResult result = coll.replaceOne(and(eq("login_name", item.getLoginName()), eq("hash", item.getRemoteHash()
    )), toUpdate);
    if(result.getModifiedCount() != 1)
      throw new Exception("Record was modyfied or not found");
    item.setRemoteHash(item.getLocalHash());

  }

  @Override
  public void delete(User item) throws Exception
  {
    ProjectRepository pr = manager.getProjectRepository();
    ArrayList<Project> ownedProjects = pr.getProjectsByUserName(item.getLoginName());
    for(Project p : ownedProjects)
      pr.delete(p);

    MongoCollection<Document> coll = manager.getDb().getCollection("user");
    DeleteResult result = coll.deleteOne(and(eq("login_name", item.getLoginName()), eq("hash", item.getRemoteHash()
    )));
    if(result.getDeletedCount() != 1)
      throw new Exception("Record was modyfied or not found");

    MongoCollection members = manager.getDb().getCollection("project_member");
    members.deleteMany(eq("user_login_name", item.getLoginName()));

    MongoCollection activities = manager.getDb().getCollection("activity");
    activities.deleteMany(eq("user_login_name", item.getLoginName()));

    MongoCollection project = manager.getDb().getCollection("project");
    project.deleteMany(eq("user_login_name", item.getLoginName()));
  }

  @Override
  public List<User> getAll() throws Exception
  {
    ArrayList<User> list = new ArrayList<>();
    MongoCollection<Document> coll = manager.getDb().getCollection("user");
    FindIterable<Document> doc = coll.find();
    MongoCursor<Document> cursor = doc.iterator();
    while(cursor.hasNext())
      list.add(extractUser(cursor.next()));

    return list;
  }

  private User extractUser(Document current)
  {
    String loginName = (String) current.get("login_name");
    int hash = (Integer) current.get("hash");
    String firstName = (String) current.get("first_name");
    String lastName = (String) current.get("last_name");
    String role = (String) current.get("role");
    String email = (String) current.get("email");
    Binary saltBinary = (Binary) current.get("salt");
    byte[] salt = saltBinary.getData();
    Binary passwordBinary = (Binary) current.get("password");
    byte[] password = passwordBinary.getData();
    return new User(hash, loginName, firstName, lastName, User.ROLE.valueOf(role), email, password, salt);
  }
}
