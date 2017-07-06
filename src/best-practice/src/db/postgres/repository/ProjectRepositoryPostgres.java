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

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import db.interfaces.ProjectPhaseRepository;
import db.interfaces.ProjectRepository;
import java.util.List;

/**
 *
 * @author stephan
 */
public class ProjectRepositoryPostgres implements ProjectRepository
{
    private final DBManagerPostgres db;

    public ProjectRepositoryPostgres(DBManagerPostgres db)
    {
        this.db = db;
    }
    
    
    @Override
    public void add(Project item) throws Exception
    {
        assert(item != null);
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
        
        try(Connection con = db.getConnection())
        {
            
            String sql = "UPDATE PROJECT SET HASH = ?, NAME = ?, DESCRIPTION = ? "
                    + "WHERE ID = ? AND HASH = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            
            int index = 1;
            ps.setInt(index++, item.getLocalHash());
            ps.setString(index++, item.getName());
            ps.setString(index++, item.getDescription());

            ps.setInt(index++, item.getId());
            ps.setInt(index++, item.getRemoteHash());
            
            int numRowsAffected = ps.executeUpdate();
            if(numRowsAffected == 0)
                throw new Exception("Record has changed or was not found!");
            
        }
    }

    @Override
    public void remove(Project item) throws Exception
    {
        
        try(Connection con = db.getConnection())
        {
            
            String sql = "DELETE FROM PROJECT "
                    + "WHERE ID = ? AND HASH = ?";
            PreparedStatement ps = con.prepareStatement(sql);
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
    public Project getByPrimaryKey(int projectId) throws Exception
    {
       try(Connection con = db.getConnection())
        {
            String sql = "SELECT HASH,  ID, NAME, DESCRIPTION FROM PROJECT "
                    + "WHERE ID = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            
            int index = 1;
            ps.setInt(index++, projectId);
            
            ResultSet rs = ps.executeQuery();
            if(rs.next() == false)
                throw new Exception("No such record");
            
            int hash = rs.getInt("HASH");
            int id = rs.getInt("ID");
            String name = rs.getString("NAME");
            String description = rs.getString("DESCRIPTION");
            ProjectPhaseRepository ppr = db.getProjectPhaseRepository();
            assert(projectId == id);

            return new Project(hash, projectId, name, description);
        }
    }

    @Override
    public ArrayList<String> getPhaseNamesByUserName(String loginName) throws Exception{
        ArrayList<String> list = new ArrayList<>();

        try(Connection con = db.getConnection())
        {
            String sql = "SELECT HASH, ID, NAME, DESCRIPTION FROM PROJECT " +
                    "JOIN PROJECT_MEMBERS ON PROJECT_MEMBER.USER_LOGIN_NAME = ? " +
                    "WHERE 1 = 1";
            PreparedStatement ps = con.prepareStatement(sql);
            int index = 1;
            ps.setString(index++, loginName);


            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                String name = rs.getString("NAME");
                list.add(name);
            }
        }
        return list;
    }

    @Override
    public List<Project> getAll() throws Exception
    {
         ArrayList<Project> list = new ArrayList<>();
        
        try(Connection con = db.getConnection())
        {
            String sql = "SELECT HASH, ID, NAME, DESCRIPTION FROM PROJECT ";
            PreparedStatement ps = con.prepareStatement(sql);

            
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                int hash = rs.getInt("HASH");
                int id = rs.getInt("ID");
                String name = rs.getString("NAME");
                String description = rs.getString("DESCRIPTION");

                
                list.add(new Project(hash, id, name, description));
            }
        }
        return list;
    }
}
