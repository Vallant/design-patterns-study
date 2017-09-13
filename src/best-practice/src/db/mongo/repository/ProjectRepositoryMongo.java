package db.mongo.repository;

import com.mongodb.client.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import data.Project;
import db.common.DBManagerMongo;
import db.interfaces.ProjectRepository;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class ProjectRepositoryMongo implements ProjectRepository
{
  private final DBManagerMongo manager;

  public ProjectRepositoryMongo(DBManagerMongo manager)
  {
    this.manager = manager;
  }

  @Override
  public Project getByPrimaryKey(int projectId) throws Exception
  {
    MongoCollection<Document> coll = manager.getDb().getCollection("project");
    FindIterable<Document> doc = coll.find(eq("id", projectId));
    MongoCursor<Document> cursor = doc.iterator();
    if(!cursor.hasNext())
      throw new Exception("no such record");

    return extractProject(cursor.next());
  }

  private Project extractProject(Document next)
  {
    int hash = next.getInteger("hash");
    int id = next.getInteger("id");
    String name = next.getString("name");
    String description = next.getString("description");
    return new Project(hash, id, name, description);
  }

  @Override
  public ArrayList<Project> getProjectsByUserName(String loginName) throws Exception
  {
    ArrayList<Project> list = new ArrayList<>();
    MongoCollection<Document> coll = manager.getDb().getCollection("project");
    Bson lookup = new Document("$lookup",
      new Document("from", "project_member")
        .append("localField", "id")
        .append("foreignField", "project_id")
        .append("as", "project_member"));

    Bson match = new Document("$match",
      new Document("project_member.user_login_name", loginName));

    List<Bson> filters = new ArrayList<>();
    filters.add(lookup);
    filters.add(match);
    AggregateIterable<Document> it = coll.aggregate(filters);

      for(Document row : it)
      list.add(extractProject(row));

    return list;
  }

  @Override
  public String getDescriptionByProjectName(String projectName) throws Exception
  {
    MongoCollection<Document> coll = manager.getDb().getCollection("project");
    FindIterable<Document> doc = coll.find(eq("name", projectName));
    MongoCursor<Document> cursor = doc.iterator();
    if(!cursor.hasNext())
      throw new Exception("no such record");

    return cursor.next().getString("description");
  }

  @Override
  public void add(Project item) throws Exception
  {
    MongoCollection<Document> coll = manager.getDb().getCollection("project");
    Document toAdd = new Document("hash", item.getLocalHash())
      .append("name", item.getName())
      .append("description", item.getDescription())
      .append("id", manager.getNextSequence("project"));
    coll.insertOne(toAdd);
    item.setId(toAdd.getInteger("id"));
  }

  @Override
  public void update(Project item) throws Exception
  {
    MongoCollection<Document> coll = manager.getDb().getCollection("project");
    Document toUpdate = new Document("hash", item.getLocalHash())
      .append("name", item.getName())
      .append("description", item.getDescription())
      .append("id", item.getId())
      .append("hash", item.getLocalHash());
    UpdateResult result = coll.replaceOne(and(eq("id", item.getId()), eq("hash", item.getRemoteHash())), toUpdate);
    if(result.getModifiedCount() != 1)
      throw new Exception("Record was modyfied or not found");
    item.setRemoteHash(item.getLocalHash());
  }

  @Override
  public void delete(Project item) throws Exception
  {
    MongoCollection<Document> coll = manager.getDb().getCollection("project");
    DeleteResult result = coll.deleteOne(and(eq("id", item.getId()), eq("hash", item.getRemoteHash())));
    if(result.getDeletedCount() != 1)
      throw new Exception("Record was modyfied or not found");

    MongoCollection members = manager.getDb().getCollection("project_member");
    members.deleteMany(eq("project_id", item.getId()));

    MongoCollection phases = manager.getDb().getCollection("project_phase");
    phases.deleteMany(eq("project_id", item.getId()));

    MongoCollection activities = manager.getDb().getCollection("activity");
    activities.deleteMany(eq("project_id", item.getId()));
  }

  @Override
  public List<Project> getAll() throws Exception
  {
    ArrayList<Project> list = new ArrayList<>();
    MongoCollection<Document> coll = manager.getDb().getCollection("project");
    FindIterable<Document> doc = coll.find();
    MongoCursor<Document> cursor = doc.iterator();
    while(cursor.hasNext())
      list.add(extractProject(cursor.next()));

    return list;
  }
}
