package db.common;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import db.interfaces.*;
import db.mongo.repository.*;
import org.bson.Document;

import java.util.Arrays;

import static com.mongodb.client.model.Filters.eq;

public class DBManagerMongo extends DBManager
{
  private final MongoDatabase db;

  public DBManagerMongo(String url, String username, String password)
  {

    //MongoCredential credential = MongoCredential.createCredential(username, "casestudy", password.toCharArray());
    //MongoClient client = new MongoClient(new ServerAddress(url), Arrays.asList(credential));
    MongoClient client = new MongoClient(url);
    db = client.getDatabase("casestudy");
  }


  @Override
  public UserRepository getUserRepository()
  {
    return new UserRepositoryMongo(this);
  }

  @Override
  public ProjectRepository getProjectRepository()
  {
    return new ProjectRepositoryMongo(this);
  }

  @Override
  public ProjectMemberRepository getProjectMemberRepository()
  {
    return new ProjectMemberRepositoryMongo(this);
  }

  @Override
  public ProjectPhaseRepository getProjectPhaseRepository()
  {
    return new ProjectPhaseRepositoryMongo(this);
  }

  @Override
  public ActivityRepository getActivityRepository()
  {
    return new ActivityRepositoryMongo(this);
  }

  public MongoDatabase getDb()
  {
    return db;
  }

  public int getNextSequence(String columnName)
  {
    MongoCollection<Document> coll = db.getCollection("sequence");
    Document update = new Document("$inc",
      new Document("seq", 1));
    Document doc = coll.findOneAndUpdate(eq("_id", columnName), update);
    return doc.getDouble("seq").intValue();
  }
}
