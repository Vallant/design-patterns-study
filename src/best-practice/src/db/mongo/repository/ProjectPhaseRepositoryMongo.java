package db.mongo.repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import data.Project;
import data.ProjectPhase;
import db.interfaces.ProjectPhaseRepository;
import db.interfaces.ProjectRepository;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.ne;

public class ProjectPhaseRepositoryMongo implements ProjectPhaseRepository
{
  private final MongoDatabase db;

  public ProjectPhaseRepositoryMongo(MongoDatabase db)
  {
    this.db = db;
  }

  @Override
  public ProjectPhase getByPrimaryKey(int id) throws Exception
  {
    MongoCollection<Document> coll = db.getCollection("project_phases");
    FindIterable<Document> doc = coll.find(eq("_id", id));
    MongoCursor<Document> cursor = doc.iterator();
    if(!cursor.hasNext())
      throw new Exception("no such record");

    return extractPhase(cursor.next());
  }

  private ProjectPhase extractPhase(Document next) throws Exception
  {
    int hash = next.getInteger("hash");
    int projectId = next.getInteger("project_id");
    String name = next.getString("name");
    int id = next.getInteger("_id");

    ProjectRepository pr = new ProjectRepositoryMongo(db);
    Project project = pr.getByPrimaryKey(projectId);
    return new ProjectPhase(hash, project, name, id);
  }

  @Override
  public ArrayList<ProjectPhase> getByProjectId(int projectId) throws Exception
  {
    ArrayList<ProjectPhase> list = new ArrayList<>();
    MongoCollection<Document> coll = db.getCollection("project_phases");
    FindIterable<Document> doc = coll.find(eq("project_id", projectId));
    MongoCursor<Document> cursor = doc.iterator();
    while(cursor.hasNext())
      list.add(extractPhase(cursor.next()));

    return list;
  }

  @Override
  public ArrayList<String> getNamesByProjectName(String projectName) throws Exception
  {
    return null;
  }

  @Override
  public ProjectPhase getByProjectAndPhaseName(String projectName, String projectPhaseName) throws Exception
  {
    return null;
  }

  @Override
  public void add(ProjectPhase item) throws Exception
  {
    MongoCollection<Document> coll = db.getCollection("project_phases");
    Document toAdd = new Document("hash", item.getLocalHash())
      .append("name", item.getName())
      .append("project_id", item.getProjectId());
    coll.insertOne(toAdd);
  }

  @Override
  public void update(ProjectPhase item) throws Exception
  {
    MongoCollection<Document> coll = db.getCollection("project_phases");
    Document toUpdate = new Document("hash", item.getLocalHash())
      .append("name", item.getName())
      .append("project_id", item.getProjectId());
    UpdateResult result = coll.updateOne(and(eq("_id", item.getId()), eq("hash", item.getRemoteHash())), toUpdate);
    if(result.getModifiedCount() != 1)
      throw new Exception("Record was modyfied or not found");
  }

  @Override
  public void delete(ProjectPhase item) throws Exception
  {
    MongoCollection<Document> coll = db.getCollection("project_phases");
    DeleteResult result = coll.deleteOne(and(eq("_id", item.getId()), eq("hash", item.getRemoteHash())));
    if(result.getDeletedCount() != 1)
      throw new Exception("Record was modyfied or not found");
  }

  @Override
  public List<ProjectPhase> getAll() throws Exception
  {
    ArrayList<ProjectPhase> list = new ArrayList<>();
    MongoCollection<Document> coll = db.getCollection("project_phases");
    FindIterable<Document> doc = coll.find();
    MongoCursor<Document> cursor = doc.iterator();
    while(cursor.hasNext())
      list.add(extractPhase(cursor.next()));

    return list;
  }
}
