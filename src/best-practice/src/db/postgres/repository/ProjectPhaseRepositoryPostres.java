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
import db.interfaces.ProjectPhaseRepository;
import db.interfaces.ProjectRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
        try(Connection con = db.getConnection())
        {
            String sql = "UPDATE PROJECT_PHASES SET HASH = ?, PROJECT_NAME = ?, NAME = ? "
                            + "WHERE ID = ? AND HASH = ?";
            
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            int index = 1;
            ps.setInt(index++, item.getLocalHash());
            ps.setString(index++, item.getProjectName());
            ps.setString(index++, item.getName());
            ps.setInt(index++, item.getId());
            ps.setInt(index++, item.getRemoteHash());
            
            
            int numRowsAffected = ps.executeUpdate();
            if(numRowsAffected == 0)
                throw new Exception("Record has changed or was not found!");
            item.setRemoteHash(item.getLocalHash());
            
        }
    }

    @Override
    public void remove(ProjectPhase item) throws Exception
    {
        try(Connection con = db.getConnection())
        {
            String sql = "DELETE FROM PROJECT_PHASES "
                            + "WHERE ID = ? AND HASH = ?";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            int index = 1;
            ps.setInt(index++, item.getId());
            ps.setInt(index++, item.getRemoteHash());
            
            int numRowsAffected = ps.executeUpdate();
            if(numRowsAffected == 0)
                throw new Exception("Record has changed or was not found!");
            item.setRemoteHash(item.getLocalHash());
            
        }
    }


   
    @Override
    public ProjectPhase getByPrimaryKey(int id) throws Exception
    {
        try(Connection con = db.getConnection())
        {
            String sql = "SELECT HASH, ID, PROJECT_ID, NAME FROM PROJECT_PHASES "
                    + "WHERE ID = ?";
            
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int index = 1;
            ps.setInt(index++, id);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next() == false)
                throw new Exception("No such item");
            
            int hash = rs.getInt("HASH");
            int idDb = rs.getInt("ID");
            int projectId = rs.getInt("PROJECT_ID");
            String name = rs.getString("NAME");

            ProjectRepository p = db.getProjectRepository();
            Project project = p.getByPrimaryKey(projectId);

            return new ProjectPhase(hash, project, name, id);
        }
    }

    @Override
    public List<ProjectPhase> getAll() throws Exception
    {
        ArrayList<ProjectPhase> l = new ArrayList<>();
        
        try(Connection con = db.getConnection())
        {
            String sql = "SELECT HASH, ID, PROJECT_ID, NAME FROM PROJECT_PHASES ";
            
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                int hash = rs.getInt("HASH");
                int id = rs.getInt("ID");
                int projectId = rs.getInt("PROJECT_ID");
                String name = rs.getString("NAME");
                
                ProjectRepository p = db.getProjectRepository();
                Project project = p.getByPrimaryKey(projectId);
                
                ProjectPhase phase = new ProjectPhase(hash, project, name, id);
                l.add(phase);                
            }
        }
        return l;
    }

}
