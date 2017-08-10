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
import java.util.Arrays;
import java.util.List;

/**
 * @author stephan
 * @created $date
 */
public class ProjectMember implements DBEntity
{
  public enum ROLE
  {
    MEMBER,
    LEADER
  }

  private User    user;
  private Project project;
  private int     remoteHash;
  private ROLE    role;

  public User getUser()
  {
    return user;
  }

  public Project getProject()
  {
    return project;
  }

  public ROLE getRole()
  {
    return role;
  }

  public void setRole(ROLE role)
  {
    this.role = role;
  }

  public ProjectMember(User user, Project project, ROLE role)
  {
    this.user = user;
    this.project = project;
    this.role = role;
  }

  public ProjectMember(User user, Project project, int hash, ROLE role)
  {
    this.user = user;
    this.project = project;
    this.remoteHash = hash;
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

  public void setUser(User user)
  {
    this.user = user;
  }

  public void setProject(Project project)
  {
    this.project = project;
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

  public void deleteInDb(DBManagerPostgres db) throws Exception
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
  
}
