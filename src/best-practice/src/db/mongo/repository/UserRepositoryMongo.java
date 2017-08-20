package db.mongo.repository;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import data.User;
import db.interfaces.UserRepository;
import org.bson.Document;
import org.bson.types.Binary;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;


public class UserRepositoryMongo implements UserRepository
{

  private final MongoDatabase db;


  public UserRepositoryMongo(MongoDatabase db)
  {
    this.db = db;
  }

  @Override
  public User getByPrimaryKey(String loginName) throws Exception
  {
    MongoCollection<Document> coll = db.getCollection("user");
    FindIterable<Document> doc = coll.find(eq("login_name", loginName));
    MongoCursor<Document> cursor = doc.iterator();
    if(!cursor.hasNext())
      throw new Exception("no such record");

    return extractUser(cursor.next());
  }

  @Override
  public ArrayList<User> getAvailableUsersFor(int projectId) throws Exception
  {
    return null;
  }

  @Override
  public void add(User item) throws Exception
  {
    MongoCollection<Document> coll = db.getCollection("user");
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

  }

  @Override
  public void delete(User item) throws Exception
  {

  }

  @Override
  public List<User> getAll() throws Exception
  {
    return null;
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
