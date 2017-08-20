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
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.sql.*;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author stephan
 * @created $date
 */
public class Activity implements DBEntity
{
  private int           remoteHash;
  private int           id;
  private ProjectPhase  phase;
  private User          user;
  private String        description;
  private ZonedDateTime start;
  private ZonedDateTime stop;
  private String        comments;


  private Activity(int hash, int id, ProjectPhase phase, User user, String description, ZonedDateTime start,
                   ZonedDateTime stop, String comments)
  {
    this.remoteHash = hash;
    this.id = id;
    this.phase = phase;
    this.user = user;
    this.description = description;
    this.start = ZonedDateTime.ofInstant(start.toInstant(), ZoneId.systemDefault());
    this.stop = ZonedDateTime.ofInstant(stop.toInstant(), ZoneId.systemDefault());
    this.comments = comments;
  }


  public Activity(ProjectPhase phase, User user, String description, ZonedDateTime start, ZonedDateTime stop,
                  String comments)
  {
    this.phase = phase;
    this.user = user;
    this.description = description;
    this.start = ZonedDateTime.ofInstant(start.toInstant(), ZoneId.systemDefault());
    this.stop = ZonedDateTime.ofInstant(stop.toInstant(), ZoneId.systemDefault());
    this.comments = comments;
  }

  public static Activity getByPrimaryKey(int id, DBManagerPostgres db) throws Exception
  {
    try(Connection con = db.getConnection())
    {
      String sql = "SELECT HASH, ID, PROJECT_PHASE_ID, PROJECT_ID, USER_LOGIN_NAME, DESCRIPTION, "
                   + "START_TIME, END_TIME, COMMENTS FROM ACTIVITY "
                   + "WHERE ID = ?";
      PreparedStatement ps = con.prepareStatement(sql);
      int index = 1;
      ps.setInt(index, id);

      ResultSet rs = ps.executeQuery();
      if(!rs.next())
        throw new Exception("No such record");

      return extractActivity(rs, db);
    }
  }

  public static void getParticipatingProjectsAndWorkloadSince(String loginName, ZonedDateTime since,
                                                              ArrayList<Project> projects,
                                                              ArrayList<Duration> durations,
                                                              DBManagerPostgres db)
    throws Exception
  {
    try(Connection con = db.getConnection())
    {
      String sql = "SELECT PROJECT.ID, EXTRACT(EPOCH FROM SUM(END_TIME - START_TIME)) " +
                   "FROM ACTIVITY " +
                   "JOIN PROJECT ON PROJECT.ID = ACTIVITY.PROJECT_ID " +
                   "AND ACTIVITY.USER_LOGIN_NAME = ? " +
                   "AND ACTIVITY.START_TIME > ? " +
                   "GROUP BY PROJECT.ID";
      PreparedStatement ps = con.prepareStatement(sql);
      int index = 1;
      ZonedDateTime zdtSince = ZonedDateTime.ofInstant(since.toInstant(), ZoneId.of("UTC"));
      ps.setString(index++, loginName);
      ps.setTimestamp(index++, Timestamp.from(zdtSince.toInstant()));

      ResultSet rs = ps.executeQuery();
      while(rs.next())
      {
        int id = rs.getInt(1);

        double seconds = rs.getDouble(2);
        Duration duration = Duration.ZERO;

        duration = duration.plusSeconds((long) seconds);
        duration = duration.plusMillis((long) ((seconds % 1) * 1000));

        Project p = Project.getByPrimaryKey(id, db);

        projects.add(p);
        durations.add(duration);
      }
    }
  }

  public static void getPhasesAndWorkloadSince(String loginName, int projectId, ZonedDateTime since,
                                               ArrayList<ProjectPhase> phases, ArrayList<Duration> durations,
                                               DBManagerPostgres db) throws Exception
  {
    try(Connection con = db.getConnection())
    {
      String sql = "SELECT PROJECT_PHASES.ID, EXTRACT(EPOCH FROM SUM(END_TIME - START_TIME)) " +
                   "FROM ACTIVITY " +
                   "JOIN PROJECT_PHASES ON PROJECT_PHASES.ID = ACTIVITY.PROJECT_PHASE_ID " +
                   "AND ACTIVITY.USER_LOGIN_NAME = ? " +
                   "AND ACTIVITY.START_TIME > ? " +
                   "AND ACTIVITY.PROJECT_ID = ? " +
                   "GROUP BY PROJECT_PHASES.ID";
      PreparedStatement ps = con.prepareStatement(sql);
      int index = 1;

      ps.setString(index++, loginName);
      ZonedDateTime zdtSince = ZonedDateTime.ofInstant(since.toInstant(), ZoneId.of("UTC"));
      ps.setTimestamp(index++, Timestamp.from(zdtSince.toInstant()));
      ps.setInt(index++, projectId);
      ResultSet rs = ps.executeQuery();
      while(rs.next())
      {
        int id = rs.getInt(1);

        double seconds = rs.getDouble(2);
        Duration duration = Duration.ZERO;

        duration = duration.plusSeconds((long) seconds);
        duration = duration.plusMillis((long) ((seconds % 1) * 1000));

        ProjectPhase p = ProjectPhase.getByPrimaryKey(id, db);

        phases.add(p);
        durations.add(duration);
      }
    }
  }

  public static ArrayList<Activity> getActivitiesForPhaseSince(String loginName, int phaseId, ZonedDateTime since,
                                                               DBManagerPostgres db)
    throws Exception
  {
    ArrayList<Activity> l = new ArrayList<>();

    try(Connection con = db.getConnection())
    {
      String sql = "SELECT HASH, ID, PROJECT_PHASE_ID, PROJECT_ID, USER_LOGIN_NAME, DESCRIPTION, "
                   + "START_TIME, END_TIME, COMMENTS FROM ACTIVITY " +
                   "WHERE PROJECT_PHASE_ID = ? " +
                   "AND START_TIME > ? " +
                   "AND USER_LOGIN_NAME = ?";

      PreparedStatement ps = con.prepareStatement(sql);
      int index = 1;
      ps.setInt(index++, phaseId);
      ZonedDateTime zdtSince = ZonedDateTime.ofInstant(since.toInstant(), ZoneId.of("UTC"));
      ps.setTimestamp(index++, Timestamp.from(zdtSince.toInstant()));
      ps.setString(index++, loginName);


      ResultSet rs = ps.executeQuery();
      while(rs.next())
      {
        Activity a = extractActivity(rs, db);
        l.add(a);
      }
    }
    return l;
  }

  public static ArrayList<Activity> getActivitiesByUserForPhaseSince(String loginName, int phaseId, ZonedDateTime
    since, DBManagerPostgres db)
    throws Exception
  {
    ArrayList<Activity> l = new ArrayList<>();

    try(Connection con = db.getConnection())
    {
      String sql = "SELECT HASH, ID, PROJECT_PHASE_ID, PROJECT_ID, USER_LOGIN_NAME, DESCRIPTION, "
                   + "START_TIME, END_TIME, COMMENTS FROM ACTIVITY " +
                   "WHERE PROJECT_PHASE_ID = ? " +
                   "AND START_TIME > ? " +
                   "AND (USER_LOGIN_NAME = ? OR ? = '')";

      PreparedStatement ps = con.prepareStatement(sql);
      int index = 1;
      ps.setInt(index++, phaseId);
      ZonedDateTime zdtSince = ZonedDateTime.ofInstant(since.toInstant(), ZoneId.of("UTC"));
      ps.setTimestamp(index++, Timestamp.from(zdtSince.toInstant()));
      ps.setString(index++, loginName);
      ps.setString(index++, loginName);


      ResultSet rs = ps.executeQuery();
      while(rs.next())
      {
        Activity a = extractActivity(rs, db);
        l.add(a);
      }
    }
    return l;
  }

  public static void getOwnedProjectsAndWorkloadSince(String loginName, ZonedDateTime since, ArrayList<Project>
    projects,
                                                      ArrayList<Duration> durations, DBManagerPostgres db)
    throws Exception
  {
    try(Connection con = db.getConnection())
    {
      String sql = "SELECT PROJECT.ID, EXTRACT(EPOCH FROM SUM(END_TIME - START_TIME)) " +
                   "FROM ACTIVITY " +
                   "JOIN PROJECT ON PROJECT.ID = ACTIVITY.PROJECT_ID " +
                   "JOIN PROJECT_MEMBERS ON PROJECT_MEMBERS.PROJECT_ID = PROJECT.ID " +
                   "AND PROJECT_MEMBERS.USER_LOGIN_NAME = ? " +
                   "AND ACTIVITY.START_TIME > ? " +
                   "AND PROJECT_MEMBERS.ROLE = 'LEADER' " +
                   "GROUP BY PROJECT.ID";
      PreparedStatement ps = con.prepareStatement(sql);
      int index = 1;
      ZonedDateTime zdtSince = ZonedDateTime.ofInstant(since.toInstant(), ZoneId.of("UTC"));
      ps.setString(index++, loginName);
      ps.setTimestamp(index++, Timestamp.from(zdtSince.toInstant()));


      ResultSet rs = ps.executeQuery();
      while(rs.next())
      {
        int id = rs.getInt(1);


        double seconds = rs.getDouble(2);
        Duration duration = Duration.ZERO;

        duration = duration.plusSeconds((long) seconds);
        duration = duration.plusMillis((long) ((seconds % 1) * 1000));

        Project p = Project.getByPrimaryKey(id, db);


        projects.add(p);
        durations.add(duration);
      }
    }
  }

  public static void getPhasesAndWorkloadForUserSince(String loginName, int projectId, ZonedDateTime since,
                                                      ArrayList<ProjectPhase> phases, ArrayList<Duration> durations,
                                                      DBManagerPostgres db)
    throws Exception
  {
    try(Connection con = db.getConnection())
    {
      String sql = "SELECT PROJECT_PHASES.ID, EXTRACT(EPOCH FROM SUM(END_TIME - START_TIME)) " +
                   "FROM ACTIVITY " +
                   "JOIN PROJECT_PHASES ON PROJECT_PHASES.ID = ACTIVITY.PROJECT_PHASE_ID " +
                   "AND (ACTIVITY.USER_LOGIN_NAME = ? OR ? = '' ) " +
                   "AND ACTIVITY.START_TIME > ? " +
                   "AND ACTIVITY.PROJECT_ID = ? " +
                   "GROUP BY PROJECT_PHASES.ID";
      PreparedStatement ps = con.prepareStatement(sql);
      int index = 1;

      ps.setString(index++, loginName);
      ps.setString(index++, loginName);
      ZonedDateTime zdtSince = ZonedDateTime.ofInstant(since.toInstant(), ZoneId.of("UTC"));
      ps.setTimestamp(index++, Timestamp.from(zdtSince.toInstant()));
      ps.setInt(index++, projectId);

      ResultSet rs = ps.executeQuery();
      while(rs.next())
      {
        int id = rs.getInt(1);

        double seconds = rs.getDouble(2);
        Duration duration = Duration.ZERO;

        duration = duration.plusSeconds((long) seconds);
        duration = duration.plusMillis((long) ((seconds % 1) * 1000));

        ProjectPhase p = ProjectPhase.getByPrimaryKey(id, db);

        phases.add(p);
        durations.add(duration);
      }
    }
  }

  public static List<Activity> getAll(DBManagerPostgres db) throws Exception
  {
    ArrayList<Activity> l = new ArrayList<>();

    try(Connection con = db.getConnection())
    {
      String sql = "SELECT HASH, ID, PROJECT_PHASE_ID, PROJECT_ID, USER_LOGIN_NAME, DESCRIPTION, "
                   + "START_TIME, END_TIME, COMMENTS FROM ACTIVITY ";
      PreparedStatement ps = con.prepareStatement(sql);

      ResultSet rs = ps.executeQuery();
      while(rs.next())
      {
        Activity a = extractActivity(rs, db);
        l.add(a);
      }
    }
    return l;
  }

  private static Activity extractActivity(ResultSet rs, DBManagerPostgres db) throws Exception
  {
    int hash = rs.getInt("HASH");
    int id = rs.getInt("ID");
    int projectPhaseId = rs.getInt("PROJECT_PHASE_ID");
    int projectId = rs.getInt("PROJECT_ID");
    String userLoginName = rs.getString("USER_LOGIN_NAME");
    String description = rs.getString("DESCRIPTION");
    Timestamp tsStart = rs.getTimestamp("START_TIME");
    ZonedDateTime start = ZonedDateTime.ofInstant(tsStart.toInstant(), ZoneId.systemDefault());
    Timestamp tsEnd = rs.getTimestamp("END_TIME");
    ZonedDateTime end = ZonedDateTime.ofInstant(tsEnd.toInstant(), ZoneId.systemDefault());
    String comments = rs.getString("COMMENTS");


    ProjectPhase phase = ProjectPhase.getByPrimaryKey(projectPhaseId, db);

    User user = User.getByPrimaryKey(userLoginName, db);

    assert (projectId == phase.getProjectId());

    return new Activity(hash, id, phase, user, description, start, end, comments);
  }

  public int getLocalHash()
  {
    return new HashCodeBuilder()
      .append(id)
      .append(phase.getRemoteHash())
      .append(user.getRemoteHash())
      .append(description)
      .append(start)
      .append(stop)
      .append(comments)
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

  public void setHash(Integer hash)
  {
    this.remoteHash = hash;
  }

  private Integer getId()
  {
    return id;
  }

  private void setId(Integer id)
  {
    this.id = id;
  }

  public ProjectPhase getPhase()
  {
    return phase;
  }

  public void setPhase(ProjectPhase phase)
  {
    this.phase = phase;
  }

  public User getUser()
  {
    return user;
  }

  public void setUser(User user)
  {
    this.user = user;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public ZonedDateTime getStart()
  {
    return start;
  }

  public void setStart(ZonedDateTime start)
  {
    this.start = start;
  }

  public ZonedDateTime getStop()
  {
    return stop;
  }

  public void setStop(ZonedDateTime stop)
  {
    this.stop = stop;
  }

  public String getComments()
  {
    return comments;
  }

  public void setComments(String comments)
  {
    this.comments = comments;
  }

  public boolean isChanged()
  {
    return getLocalHash() != getRemoteHash();
  }

  public String getProjectPhaseName()
  {
    return phase.getName();
  }

  private int getProjectPhaseId()
  {
    return phase.getId();
  }

  private String getUserLoginName()
  {
    return user.getLoginName();
  }

  private int getProjectId()
  {
    return phase.getProject().getId();
  }

  public void updateInDb(DBManagerPostgres db) throws Exception
  {
    try(Connection con = db.getConnection())
    {
      String sql = "UPDATE ACTIVITY SET HASH = ?, PROJECT_PHASE_ID = ?, PROJECT_ID = ?, USER_LOGIN_NAME = ?, "
                   + "DESCRIPTION = ?, START_TIME = ?, END_TIME = ?, COMMENTS = ? "
                   + "WHERE HASH = ? AND ID = ?";
      PreparedStatement ps = con.prepareStatement(sql);
      int index = 1;

      ps.setInt(index++, hashCode());
      ps.setInt(index++, getProjectPhaseId());
      ps.setInt(index++, getProjectId());
      ps.setString(index++, getUserLoginName());
      ps.setString(index++, getDescription());
      ZonedDateTime zdtStart = ZonedDateTime.ofInstant(getStart().toInstant(), ZoneId.of("UTC"));
      ps.setTimestamp(index++, Timestamp.from(zdtStart.toInstant()));
      ZonedDateTime zdtStop = ZonedDateTime.ofInstant(getStop().toInstant(), ZoneId.of("UTC"));
      ps.setTimestamp(index++, Timestamp.from(zdtStop.toInstant()));
      ps.setString(index++, getComments());

      ps.setInt(index++, getRemoteHash());
      ps.setInt(index++, getId());

      int numRowsAffected = ps.executeUpdate();
      if(numRowsAffected == 0)
        throw new ElementChangedException();
    }
  }

  public void insertIntoDb(DBManagerPostgres db) throws Exception
  {
    try(Connection con = db.getConnection())
    {
      String sql = "INSERT INTO ACTIVITY(HASH, PROJECT_PHASE_ID, PROJECT_ID, USER_LOGIN_NAME, "
                   + "DESCRIPTION, START_TIME, END_TIME, COMMENTS) "
                   + "VALUES "
                   + "(?, ?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

      int index = 1;
      ps.setInt(index++, getRemoteHash());
      ps.setInt(index++, getProjectPhaseId());
      ps.setInt(index++, getProjectId());
      ps.setString(index++, getUserLoginName());
      ps.setString(index++, getDescription());
      ZonedDateTime zdtStart = ZonedDateTime.ofInstant(getStart().toInstant(), ZoneId.of("UTC"));

      ps.setTimestamp(index++, Timestamp.from(zdtStart.toInstant()));
      ZonedDateTime zdtStop = ZonedDateTime.ofInstant(getStop().toInstant(), ZoneId.of("UTC"));
      ps.setTimestamp(index++, Timestamp.from(zdtStop.toInstant()));
      ps.setString(index++, getComments());

      ps.executeUpdate();
      ResultSet rs = ps.getGeneratedKeys();
      if(rs.next())
      {
        int id = rs.getInt("ID");
        setId(id);
      }
    }
  }

  public void deleteInDb(DBManagerPostgres db) throws Exception
  {
    try(Connection con = db.getConnection())
    {
      String sql = "DELETE FROM ACTIVITY "
                   + "WHERE HASH = ? AND ID = ?";
      PreparedStatement ps = con.prepareStatement(sql);

      int index = 1;
      ps.setInt(index++, getRemoteHash());
      ps.setInt(index++, getId());


      int numRowsAffected = ps.executeUpdate();
      if(numRowsAffected == 0)
        throw new ElementChangedException();
    }
  }


}
