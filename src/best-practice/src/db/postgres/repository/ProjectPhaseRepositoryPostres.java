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
import data.ProjectPhase;
import db.common.DBManager;
import db.common.DBManagerPostgres;
import db.interfaces.Criteria;
import db.interfaces.ProjectPhaseRepository;
import db.interfaces.ProjectRepository;
import db.interfaces.SQLCriteria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author stephan
 */
public class ProjectPhaseRepositoryPostres implements ProjectPhaseRepository
{
    private final DBManagerPostgres db;

    public ProjectPhaseRepositoryPostres(DBManagerPostgres db)
    {
        this.db = db;
    }
    
    
    @Override
    public void add(ProjectPhase item) throws Exception
    {
        
        
        try(Connection con = db.getConnection())
        {
            String sql = "INSERT INTO PROJECT_PHASES(HASH, PROJECT_NAME, NAME) "
                            + "VALUES "
                            + "(?, ?, ?) ";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            int index = 1;
            ps.setInt(index++, item.getLocalHash());
            ps.setString(index++, item.getProjectName());
            ps.setString(index++, item.getName());
            
            int numRowsAffected = ps.executeUpdate();
            if(numRowsAffected == 0)
                throw new Exception("Insert failed!");
            item.setRemoteHash(item.getLocalHash());
            
        }
    }

    @Override
    public void update(ProjectPhase item) throws Exception
    {
        
        SQLCriteria c = (SQLCriteria) getPrimaryKeyAndHashCriteria(item);
        try(Connection con = db.getConnection())
        {
            String sql = "UPDATE PROJECT_PHASES SET HASH = ?, PROJECT_NAME = ?, NAME = ? "
                            + "WHERE "
                            + c.toSqlClause();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            int index = 1;
            ps.setInt(index++, item.getLocalHash());
            ps.setString(index++, item.getProjectName());
            ps.setString(index++, item.getName());
            c.prepareStatement(ps, index);
            
            int numRowsAffected = ps.executeUpdate();
            if(numRowsAffected == 0)
                throw new Exception("Record has changed or was not found!");
            item.setRemoteHash(item.getLocalHash());
            
        }
    }

    @Override
    public void remove(ProjectPhase item) throws Exception
    {
        
        SQLCriteria c = (SQLCriteria) getPrimaryKeyAndHashCriteria(item);
        try(Connection con = db.getConnection())
        {
            String sql = "DELETE FROM PROJECT_PHASES "
                            + "WHERE "
                            + c.toSqlClause();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            c.prepareStatement(ps, 1);
            
            int numRowsAffected = ps.executeUpdate();
            if(numRowsAffected == 0)
                throw new Exception("Record has changed or was not found!");
            item.setRemoteHash(item.getLocalHash());
            
        }
    }

    @Override
    public ProjectPhase getByPrimaryKey(Criteria c) throws Exception
    {
        ArrayList<ProjectPhase> l = getByCriteria(c);
        if(l.isEmpty())
            throw new Exception("Record was not found!");
        return l.get(0);
    }

    @Override
    public ArrayList<ProjectPhase> getByCriteria(Criteria criterias) throws Exception
    {
        ArrayList<ProjectPhase> l = new ArrayList<>();
        
        SQLCriteria c = (SQLCriteria) criterias;
        try(Connection con = db.getConnection())
        {
            String sql = "SELECT HASH, PROJECT_NAME, NAME FROM PROJECT_PHASES "
                            + "WHERE "
                            + c.toSqlClause();
            
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                int hash = rs.getInt("HASH");
                String projectName = rs.getString("PROJECT_NAME");
                String name = rs.getString("NAME");
                
                ProjectRepository p = db.getProjectRepository();
                Criteria pc = db.getNameCriteria(projectName);
                Project project = p.getByPrimaryKey(pc);
                
                ProjectPhase phase = new ProjectPhase(hash, project, name);
                l.add(phase);                
            }
        }
        return l;
    }

    @Override
    public Criteria getPrimaryKeyCriteria(ProjectPhase item)
    {
        
        Criteria left = db.getStringCriteria("PROJECT_NAME", item.getProjectName());
        Criteria right = db.getStringCriteria("NAME", item.getName());
        return db.getAndCriteria(left, right);
    }

    @Override
    public Criteria getPrimaryKeyAndHashCriteria(ProjectPhase item)
    {
        
        return db.getAndCriteria(getPrimaryKeyCriteria(item), db.getHashCriteria(item.getRemoteHash()));
    }

    @Override
    public ProjectPhase getByPrimaryKey(String projectName, String projectPhaseName) throws Exception
    {
        
        Criteria left = db.getStringCriteria("PROJECT_NAME", projectName);
        Criteria right = db.getStringCriteria("NAME", projectPhaseName);
        Criteria a = db.getAndCriteria(left, right);
        return getByPrimaryKey(a);
    }

    
}
