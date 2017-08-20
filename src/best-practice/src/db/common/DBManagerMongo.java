package db.common;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import db.interfaces.*;
import db.mongo.repository.UserRepositoryMongo;

import java.util.Arrays;

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
    return new UserRepositoryMongo(db);
  }

  @Override
  public ProjectRepository getProjectRepository()
  {
    return null;
  }

  @Override
  public ProjectMemberRepository getProjectMemberRepository()
  {
    return null;
  }

  @Override
  public ProjectPhaseRepository getProjectPhaseRepository()
  {
    return null;
  }

  @Override
  public ActivityRepository getActivityRepository()
  {
    return null;
  }
}
