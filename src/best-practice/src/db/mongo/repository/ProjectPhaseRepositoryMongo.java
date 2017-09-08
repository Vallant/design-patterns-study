package db.mongo.repository;

import com.mongodb.client.AggregateIterable;
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
import db.interfaces.ProjectRepository;
import org.bson.BsonArray;
import org.bson.BsonBinary;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class ProjectPhaseRepositoryMongo implements ProjectPhaseRepository
{
  private final DBManagerMongo manager;

  public ProjectPhaseRepositoryMongo(DBManagerMongo manager)
  {
    this.manager = manager;
  }

  @Override
  public ProjectPhase getByPrimaryKey(int id) throws Exception
  {
    MongoCollection<Document> coll = manager.getDb().getCollection("project_phase");
    FindIterable<Document> doc = coll.find(eq("id", id));
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
    int id = next.getInteger("id");

    ProjectRepository pr = new ProjectRepositoryMongo(manager);
    Project project = pr.getByPrimaryKey(projectId);
    return new ProjectPhase(hash, project, name, id);
  }

  @Override
  public ArrayList<ProjectPhase> getByProjectId(int projectId) throws Exception
  {
    ArrayList<ProjectPhase> list = new ArrayList<>();
    MongoCollection<Document> coll = manager.getDb().getCollection("project_phase");
    FindIterable<Document> doc = coll.find(eq("project_id", projectId));
    MongoCursor<Document> cursor = doc.iterator();
    while(cursor.hasNext())
      list.add(extractPhase(cursor.next()));

    return list;
  }

  @Override
  public ArrayList<String> getNamesByProjectName(String projectName) throws Exception
  {
    ArrayList<String> list = new ArrayList<>();
    MongoCollection<Document> coll = manager.getDb().getCollection("project_phase");
    Bson lookup = new Document("$lookup",
      new Document("from", "project")
        .append("localField", "project_id")
        .append("foreignField", "id") //local field, remote field
        .append("as", "project"));

    Bson match = new Document("$match",
        new Document("project.name", projectName));

    List<Bson> filters = new ArrayList<>();
    filters.add(lookup);
    filters.add(match);
    AggregateIterable<Document> it = coll.aggregate(filters);

    for(Document row : it)
      list.add(row.getString("name"));

    return list;
  }

  @Override
  public ProjectPhase getByProjectAndPhaseName(String projectName, String projectPhaseName) throws Exception
  {
    MongoCollection<Document> coll = manager.getDb().getCollection("project_phase");
    Bson lookup = new Document("$lookup",
      new Document("from", "project")
        .append("localField", "project_id")
        .append("foreignField", "id") //local field, remote field
        .append("as", "project"));

    Bson match = new Document("$match",
      new Document("$and", Arrays.asList(
        new Document("project.name", projectName),
        new Document("name", projectPhaseName))
      ));

    List<Bson> filters = new ArrayList<>();
    filters.add(lookup);
    filters.add(match);
    AggregateIterable<Document> it = coll.aggregate(filters);
    return extractPhase(it.first());
  }

  @Override
  public void add(ProjectPhase item) throws Exception
  {
    MongoCollection<Document> coll = manager.getDb().getCollection("project_phase");
    Document toAdd = new Document("hash", item.getLocalHash())
      .append("name", item.getName())
      .append("project_id", item.getProjectId())
      .append("id", manager.getNextSequence("project_phase"));
    coll.insertOne(toAdd);
    item.setId(toAdd.getInteger("id"));
  }

  @Override
  public void update(ProjectPhase item) throws Exception
  {
    MongoCollection<Document> coll = manager.getDb().getCollection("project_phase");
    Document toUpdate = new Document("hash", item.getLocalHash())
      .append("name", item.getName())
      .append("project_id", item.getProjectId())
      .append("id", item.getId());
    UpdateResult result = coll.replaceOne(and(eq("id", item.getId()), eq("hash", item.getRemoteHash())), toUpdate);
    if(result.getModifiedCount() != 1)
      throw new Exception("Record was modyfied or not found");
    item.setRemoteHash(item.getLocalHash());
  }

  @Override
  public void delete(ProjectPhase item) throws Exception
  {
    MongoCollection<Document> coll = manager.getDb().getCollection("project_phase");
    DeleteResult result = coll.deleteOne(and(eq("id", item.getId()), eq("hash", item.getRemoteHash())));
    if(result.getDeletedCount() != 1)
      throw new Exception("Record was modyfied or not found");

    MongoCollection activities = manager.getDb().getCollection("activity");
    activities.deleteMany(eq("project_phase_id", item.getId()));
  }

  @Override
  public List<ProjectPhase> getAll() throws Exception
  {
    ArrayList<ProjectPhase> list = new ArrayList<>();
    MongoCollection<Document> coll = manager.getDb().getCollection("project_phase");
    FindIterable<Document> doc = coll.find();
    MongoCursor<Document> cursor = doc.iterator();
    while(cursor.hasNext())
      list.add(extractPhase(cursor.next()));

    return list;
  }
}
