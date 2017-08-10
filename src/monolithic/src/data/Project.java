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

import db.common.DBManagerPostgres;
import db.interfaces.DBEntity;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

  public Project(int hash, int id, String name, String description)
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


  public void setRemoteHash(int hash)
  {
    this.remoteHash = hash;
  }

  public int getId()
  {
    return id;
  }

  public void setId(int id)
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

  public void delete(DBManagerPostgres db) throws Exception
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
}


