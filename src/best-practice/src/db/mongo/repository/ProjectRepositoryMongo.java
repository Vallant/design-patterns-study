package db.mongo.repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import data.Project;
import db.interfaces.ProjectRepository;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.ne;

public class ProjectRepositoryMongo implements ProjectRepository
{
  private final MongoDatabase db;

  public ProjectRepositoryMongo(MongoDatabase db)
  {
    this.db = db;
  }

  @Override
  public Project getByPrimaryKey(int projectId) throws Exception
  {
    MongoCollection<Document> coll = db.getCollection("project");
    FindIterable<Document> doc = coll.find(eq("id", projectId));
    MongoCursor<Document> cursor = doc.iterator();
    if(!cursor.hasNext())
      throw new Exception("no such record");

    return extractProject(cursor.next());
  }

  private Project extractProject(Document next)
  {
    int hash = next.getInteger("hash");
    int id = next.getInteger("_id");
    String name = next.getString("name");
    String description = next.getString("description");
    return new Project(hash, id, name, description);
  }

  @Override
  public ArrayList<String> getProjectsByUserName(String loginName) throws Exception
  {
    //todo
    return null;
  }

  @Override
  public String getDescriptionByProjectName(String projectName) throws Exception
  {
    MongoCollection<Document> coll = db.getCollection("project");
    FindIterable<Document> doc = coll.find(eq("name", projectName));
    MongoCursor<Document> cursor = doc.iterator();
    if(!cursor.hasNext())
      throw new Exception("no such record");

    return cursor.next().getString("description");
  }

  @Override
  public void add(Project item) throws Exception
  {
    MongoCollection<Document> coll = db.getCollection("project");
    Document toAdd = new Document("hash", item.getLocalHash())
      .append("name", item.getName())
      .append("description", item.getDescription());
    coll.insertOne(toAdd);
  }

  @Override
  public void update(Project item) throws Exception
  {
    MongoCollection<Document> coll = db.getCollection("project");
    Document toUpdate = new Document("hash", item.getLocalHash())
      .append("name", item.getName())
      .append("description", item.getDescription());
    UpdateResult result = coll.updateOne(and(eq("_id", item.getId()), eq("hash", item.getRemoteHash())), toUpdate);
    if(result.getModifiedCount() != 1)
      throw new Exception("Record was modyfied or not found");
  }

  @Override
  public void delete(Project item) throws Exception
  {
    MongoCollection<Document> coll = db.getCollection("project");
    DeleteResult result = coll.deleteOne(and(eq("_id", item.getId()), eq("hash", item.getRemoteHash())));
    if(result.getDeletedCount() != 1)
      throw new Exception("Record was modyfied or not found");
  }

  @Override
  public List<Project> getAll() throws Exception
  {
    ArrayList<Project> list = new ArrayList<>();
    MongoCollection<Document> coll = db.getCollection("project");
    FindIterable<Document> doc = coll.find();
    MongoCursor<Document> cursor = doc.iterator();
    while(cursor.hasNext())
      list.add(extractProject(cursor.next()));

    return list;
  }
}
