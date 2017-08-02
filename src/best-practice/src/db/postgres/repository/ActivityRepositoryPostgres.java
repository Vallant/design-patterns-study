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

import data.Activity;
import data.Project;
import data.ProjectPhase;
import data.User;
import db.common.DBManager;
import db.common.DBManagerPostgres;
import db.interfaces.ActivityRepository;
import db.interfaces.ProjectPhaseRepository;
import db.interfaces.ProjectRepository;

import java.sql.*;
import java.time.Duration;
import java.util.ArrayList;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import db.interfaces.UserRepository;
import exception.ElementChangedException;

import java.util.List;

/**
 * @created $date
 * @author stephan
 */
public class ActivityRepositoryPostgres implements ActivityRepository
{
    private final DBManagerPostgres db;

    public ActivityRepositoryPostgres(DBManagerPostgres db)
    {
        this.db = db;
    }
    
    @Override
    public void add(Activity item) throws Exception
    {
        try(Connection con = db.getConnection())
        {
            String sql = "INSERT INTO ACTIVITY(HASH, PROJECT_PHASE_ID, PROJECT_ID, USER_LOGIN_NAME, "
                            + "DESCRIPTION, START_TIME, END_TIME, COMMENTS) "
                            + "VALUES "
                            + "(?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            int index = 1;
            ps.setInt(index++, item.getRemoteHash());
            ps.setInt(index++, item.getProjectPhaseId());
            ps.setInt(index++, item.getProjectId());
            ps.setString(index++, item.getUserLoginName());
            ps.setString(index++, item.getDescription());
            ZonedDateTime zdtStart = ZonedDateTime.ofInstant(item.getStart().toInstant(), ZoneId.of("UTC"));

            ps.setTimestamp(index++, Timestamp.from(zdtStart.toInstant()));
            ZonedDateTime zdtStop = ZonedDateTime.ofInstant(item.getStop().toInstant(), ZoneId.of("UTC"));
            ps.setTimestamp(index++, Timestamp.from(zdtStop.toInstant()));
            ps.setString(index++, item.getComments());
            
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next())
            {
                int id = rs.getInt("ID");
                item.setId(id);
            }
        }
    }

    @Override
    public void update(Activity item) throws Exception
    {
        
        try(Connection con = db.getConnection())
        {   
            String sql = "UPDATE ACTIVITY SET HASH = ?, PROJECT_PHASE_ID = ?, PROJECT_ID = ?, USER_LOGIN_NAME = ?, "
                    + "DESCRIPTION = ?, START_TIME = ?, END_TIME = ?, COMMENTS = ? "
                    + "WHERE HASH = ? AND ID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            int index = 1;
            
            ps.setInt(index++, item.hashCode());
            ps.setInt(index++, item.getProjectPhaseId());
            ps.setInt(index++, item.getProjectId());
            ps.setString(index++, item.getUserLoginName());
            ps.setString(index++, item.getDescription());
            ZonedDateTime zdtStart = ZonedDateTime.ofInstant(item.getStart().toInstant(), ZoneId.of("UTC"));
            ps.setTimestamp(index++, Timestamp.from(zdtStart.toInstant()));
            ZonedDateTime zdtStop = ZonedDateTime.ofInstant(item.getStop().toInstant(), ZoneId.of("UTC"));
            ps.setTimestamp(index++, Timestamp.from(zdtStop.toInstant()));
            ps.setString(index++, item.getComments());

            ps.setInt(index++, item.getRemoteHash());
            ps.setInt(index++, item.getId());
            
            int numRowsAffected = ps.executeUpdate();
            if(numRowsAffected == 0)
                throw new ElementChangedException();
        }
    }

    @Override
    public void delete(Activity item) throws Exception
    {
        
        try(Connection con = db.getConnection())
        {
            String sql = "DELETE FROM ACTIVITY "
                    + "WHERE HASH = ? AND ID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            
            int index = 1;
            ps.setInt(index++, item.getRemoteHash());
            ps.setInt(index++, item.getId());
            
            
            int numRowsAffected = ps.executeUpdate();
            if(numRowsAffected == 0)
                throw new ElementChangedException();
        }
    }

    @Override
    public Activity getByPrimaryKey(int id) throws Exception
    {   
        try(Connection con = db.getConnection())
        {
            String sql = "SELECT HASH, ID, PROJECT_PHASE_ID, PROJECT_ID, USER_LOGIN_NAME, DESCRIPTION, "
                         + "START_TIME, END_TIME, COMMENTS FROM ACTIVITY ";
            PreparedStatement ps = con.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            if(rs.next() == false)
                throw new Exception("No such record");
            
            int hash = rs.getInt("HASH");
            int id_db = rs.getInt("ID");
            int projectPhaseId = rs.getInt("PROJECT_PHASE_ID");
            int projectId = rs.getInt("PROJECT_ID");
            String userLoginName = rs.getString("USER_LOGIN_NAME");
            String description = rs.getString("DESCRIPTION");
            ZonedDateTime start = rs.getObject("START_TIME", ZonedDateTime.class);
            ZonedDateTime end = rs.getObject("END_TIME", ZonedDateTime.class);
            String comments = rs.getString("COMMENTS");



            ProjectPhaseRepository pp = db.getProjectPhaseRepository();
            ProjectPhase phase = pp.getByPrimaryKey(projectPhaseId);

            UserRepository u = db.getUserRepository();
            User user = u.getByPrimaryKey(userLoginName);

            assert(id == id_db);
            assert(projectId == phase.getProjectId());

            Activity a = new Activity(hash, id, phase, user, description, start, end, comments);
            return a;
        }
    }

    @Override
    public void getProjectsAndWorkloadSince(String loginName, ZonedDateTime since, ArrayList<Project> projects, ArrayList<Duration> durations) throws Exception {
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


            ProjectRepository pr = db.getProjectRepository();
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                int id = rs.getInt(1);


                double seconds = rs.getDouble(2);
                Duration duration = Duration.ZERO;

                duration = duration.plusSeconds((long)seconds);
                duration = duration.plusMillis((long) ((seconds % 1) * 1000));

                Project p = pr.getByPrimaryKey(id);


                projects.add(p);
                durations.add(duration);
            }
        }
    }

    @Override
    public void getPhasesAndWorkloadSince(String loginName, int projectId, ZonedDateTime since, ArrayList<ProjectPhase> phases, ArrayList<Duration> durations) throws Exception {
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
            ZonedDateTime zdtSince = ZonedDateTime.ofInstant(since.toInstant(), ZoneId.of("UTC"));
            ps.setString(index++, loginName);
            ps.setTimestamp(index++, Timestamp.from(zdtSince.toInstant()));
            ps.setInt(index++, projectId);

            ProjectPhaseRepository ppr = db.getProjectPhaseRepository();
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                int id = rs.getInt(1);

                double seconds = rs.getDouble(2);
                Duration duration = Duration.ZERO;

                duration = duration.plusSeconds((long)seconds);
                duration = duration.plusMillis((long) ((seconds % 1) * 1000));

                ProjectPhase p = ppr.getByPrimaryKey(id);

                phases.add(p);
                durations.add(duration);
            }
        }
    }

    @Override
    public List<Activity> getAll() throws Exception
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
                int hash = rs.getInt("HASH");
                int id = rs.getInt("ID");
                int projectPhaseId = rs.getInt("PROJECT_PHASE_ID");
                int projectId = rs.getInt("PROJECT_ID");
                String userLoginName = rs.getString("USER_LOGIN_NAME");
                String description = rs.getString("DESCRIPTION");
                ZonedDateTime start = rs.getObject("START_TIME", ZonedDateTime.class);
                ZonedDateTime end = rs.getObject("END_TIME", ZonedDateTime.class);
                String comments = rs.getString("COMMENTS");
                
                
                ProjectPhaseRepository pp = db.getProjectPhaseRepository();
                ProjectPhase phase = pp.getByPrimaryKey(projectPhaseId);
                
                UserRepository u = db.getUserRepository();
                User user = u.getByPrimaryKey(userLoginName);

                assert(projectId == phase.getProjectId());
                
                Activity a = new Activity(hash, id, phase, user, description, start, end, comments);
                l.add(a);
            }
        }
        return l;
    }
}
