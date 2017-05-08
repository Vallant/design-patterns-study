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
import db.interfaces.Criteria;
import db.interfaces.ProjectPhaseRepository;
import db.interfaces.ProjectRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import db.interfaces.SQLCriteria;
import db.interfaces.UserRepository;

/**
 * @created $date
 * @author stephan
 */
public class ActivityRepositoryPostgres implements ActivityRepository
{
    @Override
    public void add(Activity item) throws Exception
    {
        DBManagerPostgres db = (DBManagerPostgres) DBManager.getInstance();
        try(Connection con = db.getConnection())
        {
            String sql = "INSERT INTO ACTIVITY(HASH, PROJECT_NAME, PROJECT_PHASE_NAME, USER_LOGIN_NAME "
                            + "DESCRIPTION, START_TIME, END_TIME, COMMENTS) "
                            + "VALUES "
                            + "(?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            int index = 1;
            ps.setInt(index++, item.getRemoteHash());
            ps.setString(index++, item.getProjectName());
            ps.setString(index++, item.getProjectPhaseName());
            ps.setString(index++, item.getUserLoginName());
            ps.setString(index++, item.getDescription());
            ZonedDateTime zdtStart = ZonedDateTime.ofInstant(item.getStart().toInstant(), ZoneId.of("UTC"));
            ps.setObject(index++, zdtStart);
            ZonedDateTime zdtStop = ZonedDateTime.ofInstant(item.getStop().toInstant(), ZoneId.of("UTC"));
            ps.setObject(index++, zdtStop);
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
        DBManagerPostgres db = (DBManagerPostgres) DBManager.getInstance();
        try(Connection con = db.getConnection())
        {
            SQLCriteria c = (SQLCriteria) db.getIdAndHashCriteria(item.getId(), item.getRemoteHash());
            
            String sql = "UPDATE ACTIVITY SET HASH = ?, PROJECT_NAME = ?, PROJECT_PHASE_NAME = ?, USER_LOGIN_NAME = ? "
                    + "DESCRIPTION = ?, START_TIME = ?, END_TIME = ?, COMMENTS = ? "
                    + "WHERE " + c.toSqlClause();
            PreparedStatement ps = con.prepareStatement(sql);
            int index = 1;
            
            ps.setInt(index++, item.hashCode());
            ps.setString(index++, item.getProjectName());
            ps.setString(index++, item.getProjectPhaseName());
            ps.setString(index++, item.getUserLoginName());
            ps.setString(index++, item.getDescription());
            ps.setObject(index++, item.getStart());
            ps.setObject(index++, item.getStop());
            ps.setString(index++, item.getComments());
            c.prepareStatement(ps, index);
            
            int numRowsAffected = ps.executeUpdate();
            if(numRowsAffected == 0)
                throw new Exception("Record has changed or was not found!");
        }
    }

    @Override
    public void remove(Activity item) throws Exception
    {
        DBManagerPostgres db = (DBManagerPostgres) DBManager.getInstance();
        try(Connection con = db.getConnection())
        {
            SQLCriteria c = (SQLCriteria) getPrimaryKeyAndHashCriteria(item);
            String sql = "DELETE FROM ACTIVITY "
                    + "WHERE " + c.toSqlClause();
            PreparedStatement ps = con.prepareStatement(sql);
            
            int index = 1;
            c.prepareStatement(ps, index);
            
            int numRowsAffected = ps.executeUpdate();
            if(numRowsAffected == 0)
                throw new Exception("Record has changed or was not found!");
        }
    }

    @Override
    public Activity getByPrimaryKey(Criteria c) throws Exception
    {
        DBManager db = DBManager.getInstance();
        ArrayList<Activity> l = getByCriteria(c);
        if(l.size() == 0)
            throw new Exception("Record was not found!");
        assert(l.size() == 1);
        return l.get(0);
    }

    @Override
    public ArrayList<Activity> getByCriteria(Criteria criterias) throws Exception
    {
        ArrayList<Activity> l = new ArrayList<>();
        SQLCriteria sc = (SQLCriteria) criterias;
        DBManagerPostgres db = (DBManagerPostgres) DBManager.getInstance();
        try(Connection con = db.getConnection())
        {
            String sql = "SELECT HASH, ID, PROJECT_NAME, PROJECT_PHASE_NAME, USER_LOGIN_NAME, DESCRIPTION, "
                         + "START_TIME, END_TIME, COMMENTS FROM ACTIVITY "
                         + "WHERE " + sc.toSqlClause();
            PreparedStatement ps = con.prepareStatement(sql);
            
            int index = 1;
            sc.prepareStatement(ps, index);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                int hash = rs.getInt("HASH");
                int id = rs.getInt("ID");
                String projectName = rs.getString("PROJECT_NAME");
                String projectPhaseName = rs.getString("PROJECT_PHASE_NAME");
                String userLoginName = rs.getString("USER_LOGIN_NAME");
                String description = rs.getString("DESCRIPTION");
                ZonedDateTime start = rs.getObject("START_TIME", ZonedDateTime.class);
                ZonedDateTime end = rs.getObject("END_TIME", ZonedDateTime.class);
                String comments = rs.getString("COMMENTS");
                
                ProjectRepository p = db.getProjectRepository();
                Criteria c = db.getNameCriteria(projectName);
                Project project = p.getByPrimaryKey(c);
                ProjectPhaseRepository pp = db.getProjectPhaseRepository();
                ProjectPhase phase = pp.getByPrimaryKey(projectName, projectPhaseName);
                UserRepository u = db.getUserRepository();
                User user = u.getByPrimaryKey(userLoginName);
                
                
                Activity a = new Activity(hash, id, project, phase, user, description, start, start, comments);
                l.add(a);
            }
        }
        return l;
    }

    @Override
    public Criteria getPrimaryKeyCriteria(Activity item)
    {
     return DBManager.getInstance().getIdCriteria(item.getId());
    }

    @Override
    public Criteria getPrimaryKeyAndHashCriteria(Activity item)
    {
        return DBManager.getInstance().getIdAndHashCriteria(item.getId(), item.getRemoteHash());
    }

    @Override
    public Criteria getProjectNameCriteria(String projectName)
    {
        return DBManager.getInstance().getStringCriteria("PROJECT_NAME", projectName);
    }

    @Override
    public Criteria getUserLoginNameCriteria(String userLoginName)
    {
        return DBManager.getInstance().getStringCriteria("USER_LOGIN_NAME", userLoginName);
    }

}
