/*
 * Copyright (C) 2017 stephan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package data;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import db.common.DBManagerMongo;
import db.common.DBManagerPostgres;
import db.interfaces.DBEntity;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

/**
 * @author stephan
 * @created $date
 */
public class Project implements DBEntity
{

  private int    remoteHash;
  private int    id;
  private String name;
  private String description;


  private Project(int hash, int id, String name, String description)
  {
    this.remoteHash = hash;
    this.name = name;
    this.description = description;
    this.id = id;
  }

  public Project(String name, String description)
  {
    this.name = name;
    this.description = description;
  }

  public static Project getByPrimaryKey(int projectId, DBManagerPostgres db) throws Exception
  {
    try(Connection con = db.getConnection())
    {
      String sql = "SELECT HASH,  ID, NAME, DESCRIPTION FROM PROJECT "
                   + "WHERE ID = ?";
      PreparedStatement ps = con.prepareStatement(sql);

      int index = 1;
      ps.setInt(index++, projectId);

      ResultSet rs = ps.executeQuery();
      if(!rs.next())
        throw new Exception("No such record");

      return extractProject(rs);
    }
  }

  public static ArrayList<String> getProjectsByUserName(String loginName, DBManagerPostgres db) throws Exception
  {
    ArrayList<String> list = new ArrayList<>();

    try(Connection con = db.getConnection())
    {
      String sql = "SELECT NAME FROM PROJECT " +
                   "JOIN PROJECT_MEMBERS ON PROJECT_MEMBERS.USER_LOGIN_NAME = ? " +
                   "WHERE PROJECT.ID = PROJECT_MEMBERS.PROJECT_ID";
      PreparedStatement ps = con.prepareStatement(sql);
      int index = 1;
      ps.setString(index++, loginName);


      ResultSet rs = ps.executeQuery();
      while(rs.next())
      {
        String name = rs.getString("NAME");
        list.add(name);
      }
    }
    return list;
  }

  public static String getDescriptionByProjectName(String projectName, DBManagerPostgres db) throws Exception
  {
    try(Connection con = db.getConnection())
    {
      String sql = "SELECT DESCRIPTION FROM PROJECT "
                   + "WHERE NAME = ?";
      PreparedStatement ps = con.prepareStatement(sql);

      int index = 1;
      ps.setString(index++, projectName);

      ResultSet rs = ps.executeQuery();
      if(!rs.next())
        throw new Exception("No such record");

      return rs.getString("DESCRIPTION");

    }
  }

  public static List<Project> getAll(DBManagerPostgres db) throws Exception
  {
    ArrayList<Project> list = new ArrayList<>();

    try(Connection con = db.getConnection())
    {
      String sql = "SELECT HASH, ID, NAME, DESCRIPTION FROM PROJECT ";
      PreparedStatement ps = con.prepareStatement(sql);


      ResultSet rs = ps.executeQuery();
      while(rs.next())
      {
        Project p = extractProject(rs);
        list.add(p);
      }
    }
    return list;
  }

  private static Project extractProject(ResultSet rs) throws Exception
  {
    int hash = rs.getInt("HASH");
    int id = rs.getInt("ID");
    String name = rs.getString("NAME");
    String description = rs.getString("DESCRIPTION");


    return new Project(hash, id, name, description);
  }

  public int getLocalHash()
  {
    return new HashCodeBuilder()
      .append(name.hashCode())
      .append(id)
      .append(description.hashCode())
      .hashCode();
  }

  public int getRemoteHash()
  {
    return remoteHash;
  }

  public void setRemoteHash(int hash)
  {
    this.remoteHash = hash;
  }

  public int getHash()
  {
    return remoteHash;
  }

  public void setHash(int hash)
  {
    this.remoteHash = hash;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public boolean isChanged()
  {
    return getLocalHash() != getRemoteHash();
  }

  public int getId()
  {
    return id;
  }

  private void setId(int id)
  {
    this.id = id;
  }

  public void insertIntoDb(DBManagerPostgres db) throws Exception
  {
    try(Connection con = db.getConnection())
    {
      String sql = "INSERT INTO PROJECT(HASH, NAME, "
                   + "DESCRIPTION) "
                   + "VALUES "
                   + "(?, ?, ?)";
      PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

      int index = 1;
      ps.setInt(index++, getLocalHash());
      ps.setString(index++, getName());
      ps.setString(index++, getDescription());

      ps.executeUpdate();
      ResultSet rs = ps.getGeneratedKeys();
      if(rs.next())
      {
        int id = rs.getInt("ID");
        setId(id);
      }
      setRemoteHash(getLocalHash());
    }
  }

  public void updateInDb(DBManagerPostgres db) throws Exception
  {

    try(Connection con = db.getConnection())
    {

      String sql = "UPDATE PROJECT SET HASH = ?, NAME = ?, DESCRIPTION = ? "
                   + "WHERE ID = ? AND HASH = ?";
      PreparedStatement ps = con.prepareStatement(sql);

      int index = 1;
      ps.setInt(index++, getLocalHash());
      ps.setString(index++, getName());
      ps.setString(index++, getDescription());

      ps.setInt(index++, getId());
      ps.setInt(index++, getRemoteHash());

      int numRowsAffected = ps.executeUpdate();
      if(numRowsAffected == 0)
        throw new Exception("Record has changed or was not found!");

    }
  }

  public void deleteFromDb(DBManagerPostgres db) throws Exception
  {

    try(Connection con = db.getConnection())
    {
      String sql = "DELETE FROM PROJECT "
                   + "WHERE ID = ? AND HASH = ?";
      PreparedStatement ps = con.prepareStatement(sql);
      int index = 1;
      ps.setInt(index++, getId());
      ps.setInt(index++, getRemoteHash());

      int numRowsAffected = ps.executeUpdate();
      if(numRowsAffected == 0)
        throw new Exception("Record has changed or was not found!");

      setRemoteHash(getLocalHash());

    }
  }

  public static Project getByPrimaryKey(int projectId, DBManagerMongo manager) throws Exception
  {
    MongoCollection<Document> coll = manager.getDb().getCollection("project");
    FindIterable<Document> doc = coll.find(eq("id", projectId));
    MongoCursor<Document> cursor = doc.iterator();
    if(!cursor.hasNext())
      throw new Exception("no such record");

    return extractProject(cursor.next());
  }

  private static Project extractProject(Document next)
  {
    int hash = next.getInteger("hash");
    int id = next.getInteger("id");
    String name = next.getString("name");
    String description = next.getString("description");
    return new Project(hash, id, name, description);
  }

  public static ArrayList<Project> getProjectsByUserName(String loginName, DBManagerMongo manager) throws Exception
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

  public static String getDescriptionByProjectName(String projectName, DBManagerMongo manager) throws Exception
  {
    MongoCollection<Document> coll = manager.getDb().getCollection("project");
    FindIterable<Document> doc = coll.find(eq("name", projectName));
    MongoCursor<Document> cursor = doc.iterator();
    if(!cursor.hasNext())
      throw new Exception("no such record");

    return cursor.next().getString("description");
  }

  public void insertIntoDb(DBManagerMongo manager) throws Exception
  {
    MongoCollection<Document> coll = manager.getDb().getCollection("project");
    Document toAdd = new Document("hash", getLocalHash())
      .append("name", getName())
      .append("description", getDescription())
      .append("id", manager.getNextSequence("project"));
    coll.insertOne(toAdd);
    setId(toAdd.getInteger("id"));
  }

  public void update(DBManagerMongo manager) throws Exception
  {
    MongoCollection<Document> coll = manager.getDb().getCollection("project");
    Document toUpdate = new Document("hash", getLocalHash())
      .append("name", getName())
      .append("description", getDescription())
      .append("id", getId())
      .append("hash", getLocalHash());
    UpdateResult result = coll.replaceOne(and(eq("id", getId()), eq("hash", getRemoteHash())), toUpdate);
    if(result.getModifiedCount() != 1)
      throw new Exception("Record was modyfied or not found");
    setRemoteHash(getLocalHash());
  }

  public void deleteFromDb(DBManagerMongo manager) throws Exception
  {
    MongoCollection<Document> coll = manager.getDb().getCollection("project");
    DeleteResult result = coll.deleteOne(and(eq("id", getId()), eq("hash", getRemoteHash())));
    if(result.getDeletedCount() != 1)
      throw new Exception("Record was modyfied or not found");

    MongoCollection members = manager.getDb().getCollection("project_member");
    members.deleteMany(eq("project_id", getId()));

    MongoCollection phases = manager.getDb().getCollection("project_phase");
    phases.deleteMany(eq("project_id", getId()));

    MongoCollection activities = manager.getDb().getCollection("activity");
    activities.deleteMany(eq("project_id", getId()));
  }

  public static List<Project> getAll(DBManagerMongo manager) throws Exception
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


