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
import db.common.DBManagerMongo;
import db.common.DBManagerPostgres;
import db.interfaces.DBEntity;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.bson.Document;
import org.bson.types.Binary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

/**
 * @author stephan
 */

public class User implements DBEntity
{


  private int    remoteHash;
  private String loginName;
  private String firstName;
  private String lastName;
  private ROLE   role;
  private String email;
  private byte[] password;
  private byte[] salt;
  private char[] newPassword;
  private char[] newPasswordAgain;
  private char[] oldPassword;
  private User(int remoteHash, String loginName, String firstName, String lastName, ROLE role, String email,
               byte[] password, byte[] salt)
  {
    this.remoteHash = remoteHash;
    this.loginName = loginName;
    this.firstName = firstName;
    this.lastName = lastName;
    this.role = role;
    this.email = email;
    this.password = password;
    this.salt = salt;
  }

  public User(String loginName, String firstName, String lastName, ROLE role, String email, byte[] password,
              byte[] salt)
  {
    this.loginName = loginName;
    this.firstName = firstName;
    this.lastName = lastName;
    this.role = role;
    this.email = email;
    this.password = password;
    this.salt = salt;
  }

  public User(String loginName, String firstName, String lastName, ROLE role, String email, char[] password,
              char[] passwordAgain)
  {
    this.loginName = loginName;
    this.firstName = firstName;
    this.lastName = lastName;
    this.role = role;
    this.email = email;
    this.newPassword = password;
    this.newPasswordAgain = passwordAgain;
  }

  public static User getByPrimaryKey(String loginName, DBManagerPostgres db) throws Exception
  {
    try(Connection con = db.getConnection())
    {
      String sql = "SELECT HASH, FIRST_NAME, LAST_NAME, ROLE, "
                   + "SALT, PASSWORD, LOGIN_NAME, EMAIL "
                   + "FROM USERS "
                   + "WHERE LOGIN_NAME = ?";

      int index = 1;
      PreparedStatement ps = con.prepareStatement(sql);
      ps.setString(index++, loginName);

      ResultSet rs = ps.executeQuery();
      if(!rs.next())
        throw new Exception("No such record");

      return extractUser(rs, db);
    }
  }

  public static ArrayList<User> getAvailableUsersFor(int projectId, DBManagerPostgres db) throws Exception
  {
    ArrayList<User> l = new ArrayList<>();

    try(Connection con = db.getConnection())
    {
      String sql = "SELECT USERS.HASH, USERS.FIRST_NAME, USERS.LAST_NAME, USERS.ROLE, " +
                   "USERS.SALT, USERS.PASSWORD, USERS.LOGIN_NAME, USERS.EMAIL " +
                   "FROM USERS " +
                   "RIGHT JOIN PROJECT_MEMBERS " +
                   "ON PROJECT_MEMBERS.USER_LOGIN_NAME = USERS.LOGIN_NAME " +
                   "WHERE PROJECT_MEMBERS.PROJECT_ID IS NULL OR PROJECT_MEMBERS.PROJECT_ID != ?";
      PreparedStatement ps = con.prepareStatement(sql);
      int index = 1;
      ps.setInt(index++, projectId);

      ResultSet rs = ps.executeQuery();
      while(rs.next())
      {
        User u = extractUser(rs, db);
        l.add(u);
      }
    }
    return l;
  }

  public static List<User> getAll(DBManagerPostgres db) throws Exception
  {
    ArrayList<User> l = new ArrayList<>();

    try(Connection con = db.getConnection())
    {
      String sql = "SELECT HASH, FIRST_NAME, LAST_NAME, ROLE, "
                   + "SALT, PASSWORD, LOGIN_NAME, EMAIL "
                   + "FROM USERS ";
      PreparedStatement ps = con.prepareStatement(sql);

      ResultSet rs = ps.executeQuery();
      while(rs.next())
      {
        User u = extractUser(rs, db);
        l.add(u);
      }
    }
    return l;
  }

  private static User extractUser(ResultSet rs, DBManagerPostgres db) throws Exception
  {
    int hash = rs.getInt("HASH");
    String firstName = rs.getString("FIRST_NAME");
    String lastName = rs.getString("LAST_NAME");
    String role = rs.getString("ROLE");
    byte[] salt = rs.getBytes("SALT");
    byte[] password = rs.getBytes("PASSWORD");
    String loginName = rs.getString("LOGIN_NAME");
    String email = rs.getString("EMAIL");

    return new User(hash, loginName, firstName, lastName,
      User.ROLE.valueOf(role), email, password, salt);
  }

  public char[] getOldPassword()
  {
    return oldPassword;
  }

  public void setOldPassword(char[] oldPassword)
  {
    this.oldPassword = oldPassword;
  }

  public char[] getNewPasswordAgain()
  {
    return newPasswordAgain;
  }

  public byte[] getPassword()
  {
    return password;
  }

  public void setPassword(byte[] password)
  {
    this.password = password;
  }

  public byte[] getSalt()
  {
    return salt;
  }

  public void setSalt(byte[] salt)
  {
    this.salt = salt;
  }

  public String getLoginName()
  {
    return loginName;
  }

  public void setLoginName(String userName)
  {
    this.loginName = userName;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

  public void setLastName(String lastName)
  {
    this.lastName = lastName;
  }

  public ROLE getRole()
  {
    return role;
  }

  public void setRole(ROLE role)
  {
    this.role = role;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public boolean isChanged()
  {
    return getLocalHash() != getRemoteHash();
  }

  public int getLocalHash()
  {
    return new HashCodeBuilder().
      append(firstName).
      append(lastName).
      append(role).
      append(loginName).
      append(email).
      hashCode();
  }

  public int getRemoteHash()
  {
    return remoteHash;
  }

  public void setRemoteHash(int hash)
  {
    this.remoteHash = hash;
  }

  public char[] getNewPassword()
  {
    return newPassword;
  }

  public void setNewPassword(char[] newPassword)
  {
    this.newPassword = newPassword;
  }

  public void insertIntoDb(DBManagerPostgres db) throws Exception
  {
    try(Connection con = db.getConnection())
    {
      String sql = "INSERT INTO USERS(HASH, FIRST_NAME, LAST_NAME, ROLE, "
                   + "SALT, PASSWORD, LOGIN_NAME, EMAIL) "
                   + "VALUES "
                   + "(?, ?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement ps = con.prepareStatement(sql);

      int index = 1;
      ps.setInt(index++, getLocalHash());
      ps.setString(index++, getFirstName());
      ps.setString(index++, getLastName());
      ps.setString(index++, getRole().name());
      ps.setBytes(index++, getSalt());
      ps.setBytes(index++, getPassword());
      ps.setString(index++, getLoginName());
      ps.setString(index++, getEmail());

      ps.executeUpdate();
      setRemoteHash(getLocalHash());
    }
  }

  public void updateInDb(DBManagerPostgres db) throws Exception
  {
    try(Connection con = db.getConnection())
    {
      String sql = "UPDATE USERS SET HASH = ?, FIRST_NAME = ?, LAST_NAME = ?, ROLE = ?, "
                   + "SALT = ?, PASSWORD = ?, LOGIN_NAME = ?, EMAIL = ? "
                   + "WHERE LOGIN_NAME = ? AND HASH = ?";

      PreparedStatement ps = con.prepareStatement(sql);


      int index = 1;
      ps.setInt(index++, getLocalHash());
      ps.setString(index++, getFirstName());
      ps.setString(index++, getLastName());
      ps.setString(index++, getRole().name());
      ps.setBytes(index++, getSalt());
      ps.setBytes(index++, getPassword());
      ps.setString(index++, getLoginName());
      ps.setString(index++, getEmail());

      ps.setString(index++, getLoginName());
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
      String sql = "DELETE FROM USERS "
                   + "WHERE LOGIN_NAME = ? AND HASH = ?";

      PreparedStatement ps = con.prepareStatement(sql);
      int index = 1;
      ps.setString(index++, getLoginName());
      ps.setInt(index++, getRemoteHash());


      int numRowsAffected = ps.executeUpdate();
      if(numRowsAffected == 0)
        throw new Exception("Record has changed or was not found!");
    }
  }

  public static User getByPrimaryKey(String loginName, DBManagerMongo manager) throws Exception
  {
    MongoCollection<Document> coll = manager.getDb().getCollection("user");
    FindIterable<Document> doc = coll.find(eq("login_name", loginName));
    MongoCursor<Document> cursor = doc.iterator();
    if(!cursor.hasNext())
      throw new Exception("no such record");

    return extractUser(cursor.next());
  }

  public void deleteFromDb(DBManagerMongo manager) throws Exception
  {
    ArrayList<Project> ownedProjects = Project.getProjectsByUserName(getLoginName(), manager);
    for(Project p : ownedProjects)
      p.deleteFromDb(manager);

    MongoCollection<Document> coll = manager.getDb().getCollection("user");
    DeleteResult result = coll.deleteOne(and(eq("login_name", getLoginName()), eq("hash", getRemoteHash()
    )));
    if(result.getDeletedCount() != 1)
      throw new Exception("Record was modyfied or not found");

    MongoCollection members = manager.getDb().getCollection("project_member");
    members.deleteMany(eq("user_login_name", getLoginName()));

    MongoCollection activities = manager.getDb().getCollection("activity");
    activities.deleteMany(eq("user_login_name", getLoginName()));

    MongoCollection project = manager.getDb().getCollection("project");
    project.deleteMany(eq("user_login_name", getLoginName()));
  }

  public static List<User> getAll(DBManagerMongo manager) throws Exception
  {
    ArrayList<User> list = new ArrayList<>();
    MongoCollection<Document> coll = manager.getDb().getCollection("user");
    FindIterable<Document> doc = coll.find();
    MongoCursor<Document> cursor = doc.iterator();
    while(cursor.hasNext())
      list.add(extractUser(cursor.next()));

    return list;
  }

  private static User extractUser(Document current)
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

  public enum ROLE
  {
    ADMIN,
    USER
  }
}
