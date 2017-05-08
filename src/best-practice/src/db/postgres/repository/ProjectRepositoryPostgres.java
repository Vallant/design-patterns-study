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
import db.common.DBManager;
import db.common.DBManagerPostgres;
import db.interfaces.Criteria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import db.interfaces.ProjectRepository;
import db.interfaces.SQLCriteria;

/**
 *
 * @author stephan
 */
public class ProjectRepositoryPostgres implements ProjectRepository
{

    @Override
    public void add(Project item) throws Exception
    {
        assert(item != null);
        DBManagerPostgres db = (DBManagerPostgres) DBManager.getInstance();
        try(Connection con = db.getConnection())
        {
            String sql = "INSERT INTO PROJECT(HASH, NAME, "
                            + "DESCRIPTION) "
                            + "VALUES "
                            + "(?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            
            int index = 1;
            ps.setInt(index++, item.getLocalHash());
            ps.setString(index++, item.getName());
            ps.setString(index++, item.getDescription());
            
            ps.executeUpdate();
            item.setRemoteHash(item.getLocalHash());
        }
    }

    @Override
    public void update(Project item) throws Exception
    {
        DBManagerPostgres db = (DBManagerPostgres) DBManager.getInstance();
        try(Connection con = db.getConnection())
        {
            SQLCriteria c = (SQLCriteria) db.getNameAndHashCriteria(item.getName(), item.getHash());
            
            String sql = "UPDATE PROJECT SET HASH = ?, NAME = ?, DESCRIPTION = ? "
                    + "WHERE " + c.toSqlClause();
            PreparedStatement ps = con.prepareStatement(sql);
            
            int index = 1;
            ps.setInt(index++, item.getLocalHash());
            ps.setString(index++, item.getName());
            ps.setString(index++, item.getDescription());
            
            c.prepareStatement(ps, index);
            
            int numRowsAffected = ps.executeUpdate();
            if(numRowsAffected == 0)
                throw new Exception("Record has changed or was not found!");
            
        }
    }

    @Override
    public void remove(Project item) throws Exception
    {
        DBManagerPostgres db = (DBManagerPostgres) DBManager.getInstance();
        try(Connection con = db.getConnection())
        {
            SQLCriteria sum = (SQLCriteria) db.getNameAndHashCriteria(item.getName(), item.getHash());
            
            String sql = "DELETE FROM PROJECT "
                    + "WHERE " + sum.toSqlClause();
            PreparedStatement ps = con.prepareStatement(sql);
            sum.prepareStatement(ps, 1);
            
            int numRowsAffected = ps.executeUpdate();                
            if(numRowsAffected == 0)
                throw new Exception("Record has changed or was not found!");
            
            item.setRemoteHash(item.getLocalHash());
            
        }
    }

    @Override
    public Project getByPrimaryKey(Criteria c) throws Exception
    {
        DBManager db = DBManager.getInstance();
        ArrayList<Project> l = getByCriteria(c);
        if(l.isEmpty())
            throw new Exception("No such item");
        return l.get(0);
    }

    @Override
    public ArrayList<Project> getByCriteria(Criteria criterias) throws Exception
    {
        ArrayList<Project> list = new ArrayList<>();
        DBManagerPostgres db = (DBManagerPostgres) DBManager.getInstance();
        SQLCriteria sc = (SQLCriteria) criterias;
        try(Connection con = db.getConnection())
        {
            String sql = "SELECT HASH,  NAME, DESCRIPTION FROM PROJECT "
                    + "WHERE " + sc.toSqlClause();
            PreparedStatement ps = con.prepareStatement(sql);
            sc.prepareStatement(ps, 1);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                int hash = rs.getInt("HASH");
                String name = rs.getString("NAME");
                String description = rs.getString("DESCRIPTION");
                
                list.add(new Project(hash, name, description));
            }
        }
        return list;
    }    

    @Override
    public Criteria getPrimaryKeyCriteria(Project item)
    {
        return DBManager.getInstance().getNameCriteria(item.getName());
    }

    @Override
    public Criteria getPrimaryKeyAndHashCriteria(Project item)
    {
        return DBManager.getInstance().getNameAndHashCriteria(item.getName(), item.getRemoteHash());
    }
}
