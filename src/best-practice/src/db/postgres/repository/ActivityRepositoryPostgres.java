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
import db.common.DBManager;
import db.common.DBManagerPostgres;
import db.interfaces.ActivityRepository;
import db.interfaces.Criteria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import db.interfaces.SQLCriteria;

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
            String sql = "INSERT INTO ACTIVITY(HASH, PROJECT_ID, PHASE_ID, USER_ID "
                            + "DESCRIPTION, START_TIME, END_TIME, COMMENTS) "
                            + "VALUES "
                            + "(?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            int index = 1;
            ps.setInt(index++, item.getRemoteHash());
            ps.setInt(index++, item.getProjectId());
            ps.setInt(index++, item.getPhaseId());
            ps.setInt(index++, item.getUserId());
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
            
            String sql = "UPDATE ACTIVITY SET HASH = ?, PROJECT_ID = ?, PHASE_ID = ?, USER_ID = ? "
                    + "DESCRIPTION = ?, START_TIME = ?, END_TIME = ?, COMMENTS = ? "
                    + "WHERE " + c.toSqlClause();
            PreparedStatement ps = con.prepareStatement(sql);
            int index = 1;
            
            ps.setInt(index++, item.hashCode());
            ps.setInt(index++, item.getProjectId());
            ps.setInt(index++, item.getPhaseId());
            ps.setInt(index++, item.getUserId());
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
            SQLCriteria c = (SQLCriteria) db.getIdAndHashCriteria(item.getId(), item.getRemoteHash());
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
            String sql = "SELECT HASH, ID, PROJECT_ID, PHASE_ID, USER_ID, DESCRIPTION, "
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
                int projectId = rs.getInt("PROJECT_ID");
                int phaseId = rs.getInt("PHASE_ID");
                int userId = rs.getInt("USER_ID");
                String description = rs.getString("DESCRIPTION");
                ZonedDateTime start = rs.getObject("START_TIME", ZonedDateTime.class);
                ZonedDateTime end = rs.getObject("END_TIME", ZonedDateTime.class);
                String comments = rs.getString("COMMENTS");
                
                Activity a = new Activity(hash, id, projectId, phaseId, userId, description, start, start, comments);
                l.add(a);
            }
        }
        return l;
    }

}
