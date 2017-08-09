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

package db.postgres.repository;

import data.Project;
import data.ProjectMember;
import data.User;
import db.common.DBManagerPostgres;
import db.interfaces.ProjectMemberRepository;
import db.interfaces.ProjectRepository;
import db.interfaces.UserRepository;
import exception.ElementChangedException;
import exception.ElementNotFoundException;

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
public class ProjectMemberRepositoryPostgres implements ProjectMemberRepository
{

  private final DBManagerPostgres db;

  public ProjectMemberRepositoryPostgres(DBManagerPostgres db)
  {
    this.db = db;
  }


  @Override
  public void add(ProjectMember item) throws Exception
  {

    try(Connection con = db.getConnection())
    {
      String sql = "INSERT INTO PROJECT_MEMBERS(HASH, USER_LOGIN_NAME, PROJECT_ID, ROLE) "
                   + "VALUES "
                   + "(?, ?, ?, ?) ";
      PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

      int index = 1;
      ps.setInt(index++, item.getLocalHash());
      ps.setString(index++, item.getUserLoginName());
      ps.setInt(index++, item.getProjectId());
      ps.setString(index++, item.getRole().name());

      int numRowsAffected = ps.executeUpdate();
      if(numRowsAffected == 0)
        throw new Exception("Update failed!");
      item.setRemoteHash(item.getLocalHash());

    }
  }

  @Override
  public void update(ProjectMember item) throws Exception
  {

    try(Connection con = db.getConnection())
    {
      String sql = "UPDATE PROJECT_MEMBERS SET HASH = ?, USER_LOGIN_NAME = ?, PROJECT_ID = ?, ROLE = ? "
                   + "WHERE USER_LOGIN_NAME = ? AND PROJECT_ID = ? AND HASH = ?";
      PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

      int index = 1;
      ps.setInt(index++, item.getLocalHash());
      ps.setString(index++, item.getUserLoginName());
      ps.setInt(index++, item.getProjectId());
      ps.setString(index++, item.getRole().name());
      ps.setString(index++, item.getUserLoginName());
      ps.setInt(index++, item.getProjectId());
      ps.setInt(index++, item.getRemoteHash());

      int numRowsAffected = ps.executeUpdate();
      if(numRowsAffected == 0)
        throw new ElementChangedException();
      item.setRemoteHash(item.getLocalHash());

    }
  }

  @Override
  public void delete(ProjectMember item) throws Exception
  {

    try(Connection con = db.getConnection())
    {
      String sql = "DELETE FROM PROJECT_MEMBERS "
                   + "WHERE USER_LOGIN_NAME = ? AND PROJECT_ID = ?";
      PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

      int index = 1;
      ps.setString(index++, item.getUserLoginName());
      ps.setInt(index++, item.getProjectId());


      int numRowsAffected = ps.executeUpdate();
      if(numRowsAffected == 0)
        throw new ElementChangedException();
    }
  }


  @Override
  public List<ProjectMember> getAll() throws Exception
  {
    ArrayList<ProjectMember> l = new ArrayList<>();

    try(Connection con = db.getConnection())
    {
      String sql = "SELECT HASH, USER_LOGIN_NAME, PROJECT_ID, ROLE FROM PROJECT_MEMBERS ";
      PreparedStatement ps = con.prepareStatement(sql);

      ResultSet rs = ps.executeQuery();
      while(rs.next())
      {
        ProjectMember m = extractMember(rs);
        l.add(m);
      }
    }
    return l;
  }

  @Override
  public ProjectMember getByPrimaryKey(String userLoginName, int projectId) throws Exception
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

      return extractMember(rs);
    }

  }

  @Override
  public ArrayList<ProjectMember> getMembersByProjectId(int projectId) throws Exception
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
        ProjectMember m = extractMember(rs);
        l.add(m);
      }
    }
    return l;
  }

  @Override
  public ArrayList<ProjectMember> getInvolvedProjects(String loginName) throws Exception
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
        ProjectMember m = extractMember(rs);
        l.add(m);
      }
    }
    return l;
  }

  @Override
  public ArrayList<ProjectMember> getOwnedProject(String loginName) throws Exception
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
        ProjectMember m = extractMember(rs);
        l.add(m);
      }
    }
    return l;
  }

  private ProjectMember extractMember(ResultSet rs) throws Exception
  {
    int hash = rs.getInt("HASH");
    int projectId = rs.getInt("PROJECT_ID");
    String userLoginName = rs.getString("USER_LOGIN_NAME");
    String role = rs.getString("ROLE");

    ProjectRepository p = db.getProjectRepository();
    Project project = p.getByPrimaryKey(projectId);

    UserRepository u = db.getUserRepository();
    User user = u.getByPrimaryKey(userLoginName);

    return new ProjectMember(user, project, hash, ProjectMember.ROLE.valueOf(role));
  }

}
