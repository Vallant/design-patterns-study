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
import exception.ElementChangedException;
import exception.ElementNotFoundException;
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
public class ProjectPhase implements DBEntity
{
  private int     remoteHash;
  private Project project;
  private String  name;
  private int     id;

  public ProjectPhase(int hash, Project project, String name, int id)
  {
    this.remoteHash = hash;
    this.project = project;
    this.name = name;
    this.id = id;
  }

  public ProjectPhase(Project project, String name)
  {
    this.project = project;
    this.name = name;
  }


  public boolean isChanged()
  {
    return getLocalHash() != getRemoteHash();
  }


  public int getLocalHash()
  {
    return new HashCodeBuilder().
      append(project.getId()).
      append(name).
      append(id).
      hashCode();
  }

  public Project getProject()
  {
    return project;
  }

  public void setProject(Project project)
  {
    this.project = project;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }


  public int getRemoteHash()
  {
    return remoteHash;
  }


  public void setRemoteHash(int hash)
  {
    remoteHash = hash;
  }

  public String getProjectName()
  {
    return project.getName();
  }

  public int getProjectId()
  {
    return project.getId();
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
      String sql = "INSERT INTO PROJECT_PHASES(HASH, PROJECT_ID, NAME) "
                   + "VALUES "
                   + "(?, ?, ?) ";
      PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

      int index = 1;
      ps.setInt(index++, getLocalHash());
      ps.setInt(index++, getProjectId());
      ps.setString(index++, getName());

      ps.executeUpdate();
      ResultSet rs = ps.getGeneratedKeys();
      if(rs.next())
      {
        int id = rs.getInt("ID");
        setId(id);
      }
      else
        throw new Exception("Insert failed!");
      setRemoteHash(getLocalHash());

    }
  }

  public void updateInDb(DBManagerPostgres db) throws Exception
  {
    try(Connection con = db.getConnection())
    {
      String sql = "UPDATE PROJECT_PHASES SET HASH = ?, PROJECT_NAME = ?, NAME = ? "
                   + "WHERE ID = ? AND HASH = ?";

      PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

      int index = 1;
      ps.setInt(index++, getLocalHash());
      ps.setString(index++, getProjectName());
      ps.setString(index++, getName());
      ps.setInt(index++, getId());
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
      String sql = "DELETE FROM PROJECT_PHASES "
                   + "WHERE ID = ? AND HASH = ?";
      PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

      int index = 1;
      ps.setInt(index++, getId());
      ps.setInt(index++, getRemoteHash());

      int numRowsAffected = ps.executeUpdate();
      if(numRowsAffected == 0)
        throw new ElementChangedException();
      setRemoteHash(getLocalHash());

    }
  }

  public static ProjectPhase getByPrimaryKey(int id, DBManagerPostgres db) throws Exception
  {
    try(Connection con = db.getConnection())
    {
      String sql = "SELECT HASH, ID, PROJECT_ID, NAME FROM PROJECT_PHASES "
                   + "WHERE ID = ?";

      PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      int index = 1;
      ps.setInt(index++, id);
      ResultSet rs = ps.executeQuery();

      if(!rs.next())
        throw new ElementNotFoundException("ProjectPhase", "ID", Integer.toString(id));

      return extractPhase(rs, db);
    }
  }


  public static ArrayList<ProjectPhase> getByProjectId(int projectId, DBManagerPostgres db) throws Exception
  {
    ArrayList<ProjectPhase> l = new ArrayList<>();

    try(Connection con = db.getConnection())
    {
      String sql = "SELECT HASH, ID, PROJECT_ID, NAME FROM PROJECT_PHASES " +
                   "WHERE PROJECT_ID = ?";

      PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      int index = 1;
      ps.setInt(index++, projectId);

      ResultSet rs = ps.executeQuery();

      while(rs.next())
      {
        ProjectPhase phase = extractPhase(rs, db);
        l.add(phase);
      }
    }
    return l;
  }


  public static ArrayList<String> getNamesByProjectName(String projectName, DBManagerPostgres db) throws Exception
  {
    ArrayList<String> l = new ArrayList<>();

    try(Connection con = db.getConnection())
    {
      String sql = "SELECT PROJECT_PHASES.NAME FROM PROJECT_PHASES " +
                   "JOIN PROJECT ON PROJECT.NAME = ? " +
                   "WHERE PROJECT_PHASES.PROJECT_ID = PROJECT.ID ";

      PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      int index = 1;
      ps.setString(index++, projectName);

      ResultSet rs = ps.executeQuery();

      while(rs.next())
      {
        String name = rs.getString("NAME");

        l.add(name);
      }
    }
    return l;
  }


  public static ProjectPhase getByProjectAndPhaseName(String projectName, String projectPhaseName, DBManagerPostgres
    db) throws Exception
  {
    try(Connection con = db.getConnection())
    {
      String sql =
        "SELECT PROJECT_PHASES.HASH, PROJECT_PHASES.ID, PROJECT_PHASES.PROJECT_ID, PROJECT_PHASES.NAME FROM " +
        "PROJECT_PHASES " +
        "JOIN PROJECT ON PROJECT.NAME = ? " +
        "WHERE PROJECT_PHASES.NAME = ?";

      PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      int index = 1;
      ps.setString(index++, projectName);
      ps.setString(index++, projectPhaseName);
      ResultSet rs = ps.executeQuery();

      if(!rs.next())
        throw new Exception("Element not found");
      //throw new ElementNotFoundException("ProjectPhase", "ID", Integer.toString(id)); //TODO change to correct output

      return extractPhase(rs, db);
    }
  }


  public static List<ProjectPhase> getAll(DBManagerPostgres db) throws Exception
  {
    ArrayList<ProjectPhase> l = new ArrayList<>();

    try(Connection con = db.getConnection())
    {
      String sql = "SELECT HASH, ID, PROJECT_ID, NAME FROM PROJECT_PHASES ";

      PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      ResultSet rs = ps.executeQuery();
      while(rs.next())
      {
        ProjectPhase phase = extractPhase(rs, db);
        l.add(phase);
      }
    }
    return l;
  }

  private static ProjectPhase extractPhase(ResultSet rs, DBManagerPostgres db) throws Exception
  {
    int hash = rs.getInt("HASH");
    int id = rs.getInt("ID");
    int projectId = rs.getInt("PROJECT_ID");
    String name = rs.getString("NAME");

    Project project = Project.getByPrimaryKey(projectId, db);

    return new ProjectPhase(hash, project, name, id);
  }


}
