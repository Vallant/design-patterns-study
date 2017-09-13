package db.common;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class DBManagerMongo
{
  private final MongoDatabase db;

  public DBManagerMongo(String url, String username, String password)
  {

    //MongoCredential credential = MongoCredential.createCredential(username, "casestudy", password.toCharArray());
    //MongoClient client = new MongoClient(new ServerAddress(url), Arrays.asList(credential));
    MongoClient client = new MongoClient(url);
    db = client.getDatabase("casestudy");
  }

  public int getNextSequence(String columnName)
  {
    MongoCollection<Document> coll = db.getCollection("sequence");
    Document update = new Document("$inc",
      new Document("seq", 1));
    Document doc = coll.findOneAndUpdate(eq("_id", columnName), update);
    return doc.getDouble("seq").intValue();
  }
  public MongoDatabase getDb()
  {
    return db;
  }
}
