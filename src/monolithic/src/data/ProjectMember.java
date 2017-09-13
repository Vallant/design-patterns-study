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

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import db.common.DBManagerMongo;
import db.common.DBManagerPostgres;
import db.interfaces.DBEntity;
import exception.ElementChangedException;
import exception.ElementNotFoundException;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.bson.Document;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

/**
 * @author stephan
 * @created $date
 */
public class ProjectMember implements DBEntity
{
  private User    user;
  private Project project;
  private int     remoteHash;
  private ROLE    role;
  public ProjectMember(User user, Project project, ROLE role)
  {
    this.user = user;
    this.project = project;
    this.role = role;
  }

  private ProjectMember(User user, Project project, int hash, ROLE role)
  {
    this.user = user;
    this.project = project;
    this.remoteHash = hash;
    this.role = role;
  }

  public static List<ProjectMember> getAll(DBManagerPostgres db) throws Exception
  {
    ArrayList<ProjectMember> l = new ArrayList<>();

    try(Connection con = db.getConnection())
    {
      String sql = "SELECT HASH, USER_LOGIN_NAME, PROJECT_ID, ROLE FROM PROJECT_MEMBERS ";
      PreparedStatement ps = con.prepareStatement(sql);

      ResultSet rs = ps.executeQuery();
      while(rs.next())
      {
        ProjectMember m = extractMember(rs, db);
        l.add(m);
      }
    }
    return l;
  }

  public static ProjectMember getByPrimaryKey(String userLoginName, int projectId, DBManagerPostgres db) throws
    Exception
  {
    try(Connection con = db.getConnection())
    {
      String sql = "SELECT HASH, USER_LOGIN_NAME, PROJECT_ID, ROLE FROM PROJECT_MEMBERS ";
      PreparedStatement ps = con.prepareStatement(sql);

      ResultSet rs = ps.executeQuery();
      if(!rs.next())
        throw new ElementNotFoundException("ProjectMember",
          new ArrayList(Arrays.asList("Username", "ProjectId")),
          new ArrayList(Arrays.asList(userLoginName, Integer.toString(projectId)))
        );

      return extractMember(rs, db);
    }

  }

  public static ArrayList<ProjectMember> getMembersByProjectId(int projectId, DBManagerPostgres db) throws Exception
  {
    ArrayList<ProjectMember> l = new ArrayList<>();

    try(Connection con = db.getConnection())
    {
      String sql =
        "SELECT PROJECT_MEMBERS.HASH, PROJECT_MEMBERS.USER_LOGIN_NAME, PROJECT_MEMBERS.PROJECT_ID, PROJECT_MEMBERS" +
        ".ROLE FROM PROJECT_MEMBERS " +
        "JOIN PROJECT ON PROJECT_MEMBERS.PROJECT_ID = PROJECT.ID " +
        "WHERE PROJECT.Id = ?";
      PreparedStatement ps = con.prepareStatement(sql);

      int index = 1;
      ps.setInt(index++, projectId);

      ResultSet rs = ps.executeQuery();
      while(rs.next())
      {
        ProjectMember m = extractMember(rs, db);
        l.add(m);
      }
    }
    return l;
  }

  public static ArrayList<ProjectMember> getInvolvedProjects(String loginName, DBManagerPostgres db) throws Exception
  {
    ArrayList<ProjectMember> l = new ArrayList<>();

    try(Connection con = db.getConnection())
    {
      String sql =
        "SELECT PROJECT_MEMBERS.HASH, PROJECT_MEMBERS.USER_LOGIN_NAME, PROJECT_MEMBERS.PROJECT_ID, PROJECT_MEMBERS" +
        ".ROLE FROM PROJECT_MEMBERS " +
        "JOIN PROJECT ON PROJECT_MEMBERS.PROJECT_ID = PROJECT.ID " +
        "WHERE PROJECT_MEMBERS.USER_LOGIN_NAME = ? " +
        "AND PROJECT_MEMBERS.ROLE = 'MEMBER'";
      PreparedStatement ps = con.prepareStatement(sql);

      int index = 1;
      ps.setString(index++, loginName);

      ResultSet rs = ps.executeQuery();
      while(rs.next())
      {
        ProjectMember m = extractMember(rs, db);
        l.add(m);
      }
    }
    return l;
  }

  public static ArrayList<ProjectMember> getOwnedProject(String loginName, DBManagerPostgres db) throws Exception
  {
    ArrayList<ProjectMember> l = new ArrayList<>();

    try(Connection con = db.getConnection())
    {
      String sql =
        "SELECT PROJECT_MEMBERS.HASH, PROJECT_MEMBERS.USER_LOGIN_NAME, PROJECT_MEMBERS.PROJECT_ID, PROJECT_MEMBERS" +
        ".ROLE FROM PROJECT_MEMBERS " +
        "JOIN PROJECT ON PROJECT_MEMBERS.PROJECT_ID = PROJECT.ID " +
        "WHERE PROJECT_MEMBERS.USER_LOGIN_NAME = ? " +
        "AND PROJECT_MEMBERS.ROLE = 'LEADER'";
      PreparedStatement ps = con.prepareStatement(sql);

      int index = 1;
      ps.setString(index++, loginName);

      ResultSet rs = ps.executeQuery();
      while(rs.next())
      {
        ProjectMember m = extractMember(rs, db);
        l.add(m);
      }
    }
    return l;
  }

  private static ProjectMember extractMember(ResultSet rs, DBManagerPostgres db) throws Exception
  {
    int hash = rs.getInt("HASH");
    int projectId = rs.getInt("PROJECT_ID");
    String userLoginName = rs.getString("USER_LOGIN_NAME");
    String role = rs.getString("ROLE");

    Project project = Project.getByPrimaryKey(projectId, db);

    User user = User.getByPrimaryKey(userLoginName, db);

    return new ProjectMember(user, project, hash, ProjectMember.ROLE.valueOf(role));
  }

  public User getUser()
  {
    return user;
  }

  public void setUser(User user)
  {
    this.user = user;
  }

  public Project getProject()
  {
    return project;
  }

  public void setProject(Project project)
  {
    this.project = project;
  }

  public ROLE getRole()
  {
    return role;
  }

  public void setRole(ROLE role)
  {
    this.role = role;
  }

  @Override
  public boolean isChanged()
  {
    return getLocalHash() != getRemoteHash();
  }

  @Override
  public int getLocalHash()
  {
    return new HashCodeBuilder().
      append(user.getLoginName()).
      append(project.getId()).
      append(role).
      hashCode();

  }

  public int getRemoteHash()
  {
    return remoteHash;
  }

  public void setRemoteHash(int remoteHash)
  {
    this.remoteHash = remoteHash;
  }

  public String getUserLoginName()
  {
    return user.getLoginName();
  }

  public int getProjectId()
  {
    return project.getId();
  }

  public String getProjectName()
  {
    return project.getName();
  }

  public void insertIntoDb(DBManagerPostgres db) throws Exception
  {

    try(Connection con = db.getConnection())
    {
      String sql = "INSERT INTO PROJECT_MEMBERS(HASH, USER_LOGIN_NAME, PROJECT_ID, ROLE) "
                   + "VALUES "
                   + "(?, ?, ?, ?) ";
      PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

      int index = 1;
      ps.setInt(index++, getLocalHash());
      ps.setString(index++, getUserLoginName());
      ps.setInt(index++, getProjectId());
      ps.setString(index++, getRole().name());

      int numRowsAffected = ps.executeUpdate();
      if(numRowsAffected == 0)
        throw new Exception("Update failed!");
      setRemoteHash(getLocalHash());

    }


  }

  public void updateInDb(DBManagerPostgres db) throws Exception
  {

    try(Connection con = db.getConnection())
    {
      String sql = "UPDATE PROJECT_MEMBERS SET HASH = ?, USER_LOGIN_NAME = ?, PROJECT_ID = ?, ROLE = ? "
                   + "WHERE USER_LOGIN_NAME = ? AND PROJECT_ID = ? AND HASH = ?";
      PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

      int index = 1;
      ps.setInt(index++, getLocalHash());
      ps.setString(index++, getUserLoginName());
      ps.setInt(index++, getProjectId());
      ps.setString(index++, getRole().name());
      ps.setString(index++, getUserLoginName());
      ps.setInt(index++, getProjectId());
      ps.setInt(index++, getRemoteHash());

      int numRowsAffected = ps.executeUpdate();
      if(numRowsAffected == 0)
        throw new ElementChangedException();
      setRemoteHash(getLocalHash());

    }
  }

  public void deleteFromDb(DBManagerPostgres db) throws Exception
  {

    try(Connection con = db.getConnection())
    {
      String sql = "DELETE FROM PROJECT_MEMBERS "
                   + "WHERE USER_LOGIN_NAME = ? AND PROJECT_ID = ?";
      PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

      int index = 1;
      ps.setString(index++, getUserLoginName());
      ps.setInt(index++, getProjectId());


      int numRowsAffected = ps.executeUpdate();
      if(numRowsAffected == 0)
        throw new ElementChangedException();
    }
  }

  public static ProjectMember getByPrimaryKey(String userLoginName, int projectId, DBManagerMongo manager) throws
    Exception
  {
    MongoCollection<Document> coll = manager.getDb().getCollection("project_member");
    FindIterable<Document> doc = coll.find(and(eq("user_login_name", userLoginName), eq("project_id", projectId)));
    MongoCursor<Document> cursor = doc.iterator();
    if(!cursor.hasNext())
      throw new Exception("no such record");

    return extractMember(cursor.next(), manager);
  }

  private static ProjectMember extractMember(Document next, DBManagerMongo manager) throws Exception
  {
    int hash = next.getInteger("hash");
    String userLoginName = next.getString("user_login_name");
    int projectId = next.getInteger("project_id");
    String role = next.getString("role");


    User user = User.getByPrimaryKey(userLoginName, manager);
    Project project = Project.getByPrimaryKey(projectId, manager);
    return new ProjectMember(user, project, hash, ProjectMember.ROLE.valueOf(role));
  }

  public static ArrayList<ProjectMember> getMembersByProjectId(int projectId, DBManagerMongo manager) throws Exception
  {
    ArrayList<ProjectMember> list = new ArrayList<>();
    MongoCollection<Document> coll = manager.getDb().getCollection("project_member");
    FindIterable<Document> doc = coll.find(eq("project_id", projectId));
    MongoCursor<Document> cursor = doc.iterator();
    while(cursor.hasNext())
      list.add(extractMember(cursor.next(), manager));

    return list;
  }

  public static ArrayList<ProjectMember> getInvolvedProjects(String loginName, DBManagerMongo manager) throws Exception
  {
    ArrayList<ProjectMember> list = new ArrayList<>();
    MongoCollection<Document> coll = manager.getDb().getCollection("project_member");
    FindIterable<Document> doc = coll.find(and(eq("user_login_name", loginName), eq("role","MEMBER")));
    MongoCursor<Document> cursor = doc.iterator();
    while(cursor.hasNext())
      list.add(extractMember(cursor.next(), manager));

    return list;
  }

  public static ArrayList<ProjectMember> getOwnedProject(String loginName, DBManagerMongo manager) throws Exception
  {
    ArrayList<ProjectMember> list = new ArrayList<>();
    MongoCollection<Document> coll = manager.getDb().getCollection("project_member");
    FindIterable<Document> doc = coll.find(and(eq("user_login_name", loginName), eq("role","LEADER")));
    MongoCursor<Document> cursor = doc.iterator();
    while(cursor.hasNext())
      list.add(extractMember(cursor.next(), manager));

    return list;
  }

  public void insertIntoDb(DBManagerMongo manager) throws Exception
  {
    MongoCollection<Document> coll = manager.getDb().getCollection("project_member");
    Document toUpdate = new Document("hash", getLocalHash())
      .append("user_login_name", getUserLoginName())
      .append("project_id", getProjectId())
      .append("role", getRole().name());

    coll.insertOne(toUpdate);
  }

  public void updateInDb(DBManagerMongo manager) throws Exception
  {

    MongoCollection<Document> coll = manager.getDb().getCollection("project_member");
    Document toUpdate = new Document("hash", getLocalHash())
      .append("user_login_name", getUserLoginName())
      .append("project_id", getProjectId())
      .append("role", getRole().name());

    UpdateResult result = coll.replaceOne(and(and(eq("user_login_name", getUserLoginName()), eq("hash",getRemoteHash())), eq
      ("project_id", getProjectId())), toUpdate);
    if(result.getModifiedCount() != 1)
      throw new Exception("Record was modyfied or not found");

    setRemoteHash(getLocalHash());
  }

  public void deleteFromDb(DBManagerMongo manager) throws Exception
  {
    MongoCollection<Document> coll = manager.getDb().getCollection("project_member");
    DeleteResult result = coll.deleteOne(and(and(eq("user_login_name", getUserLoginName()), eq("hash",getRemoteHash())), eq
      ("project_id", getProjectId())));
    if(result.getDeletedCount() != 1)
      throw new Exception("Record was modyfied or not found");

    MongoCollection activity = manager.getDb().getCollection("activity");
    activity.deleteMany(and(eq("project_id", getProjectId()), eq("user_login_name", getUserLoginName())));
  }

  public static List<ProjectMember> getAll(DBManagerMongo manager) throws Exception
  {
    ArrayList<ProjectMember> list = new ArrayList<>();
    MongoCollection<Document> coll = manager.getDb().getCollection("project_member");
    FindIterable<Document> doc = coll.find();
    MongoCursor<Document> cursor = doc.iterator();
    while(cursor.hasNext())
      list.add(extractMember(cursor.next(), manager));

    return list;
  }


  public enum ROLE
  {
    MEMBER,
    LEADER
  }

}
