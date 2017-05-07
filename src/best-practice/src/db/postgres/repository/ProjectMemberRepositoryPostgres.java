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

import data.ProjectMember;
import db.common.DBManager;
import db.common.DBManagerPostgres;
import db.interfaces.Criteria;
import java.util.ArrayList;
import db.interfaces.ProjectMemberRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import db.interfaces.SQLCriteria;


/**
 * @created $date
 * @author stephan
 */
public class ProjectMemberRepositoryPostgres implements ProjectMemberRepository
{
    @Override
    public void add(ProjectMember item) throws Exception
    {
        DBManagerPostgres db = (DBManagerPostgres) DBManager.getInstance();
        SQLCriteria c = (SQLCriteria) getPrimaryKeyCriteria(item.getProject().getId(), item.getUser().getId());
        SQLCriteria h = (SQLCriteria) db.getHashCriteria(item.getRemoteHash());
        SQLCriteria a = (SQLCriteria) db.getAndCriteria(c, h);
        try(Connection con = db.getConnection())
        {
            String sql = "INSERT INTO PROJECT_MEMBER(HASH, USER_ID, PROJECT_ID, ROLE) "
                            + "VALUES "
                            + "(?, ?, ?, ?) "
                            + "WHERE "
                            + a.toSqlClause();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            int index = 1;
            ps.setInt(index++, item.getLocalHash());
            ps.setInt(index++, item.getUser().getId());
            ps.setInt(index++, item.getProject().getId());
            ps.setString(index++, item.getRole().name());
            a.prepareStatement(ps, index);
            
            int numRowsAffected = ps.executeUpdate();
            if(numRowsAffected == 0)
                throw new Exception("Update failed!");
            item.setRemoteHash(item.getLocalHash());
            
        }
    }

    @Override
    public void update(ProjectMember item) throws Exception
    {
        DBManagerPostgres db = (DBManagerPostgres) DBManager.getInstance();
        SQLCriteria c = (SQLCriteria) getPrimaryKeyCriteria(item.getProject().getId(), item.getUser().getId());
        SQLCriteria h = (SQLCriteria) db.getHashCriteria(item.getRemoteHash());
        SQLCriteria a = (SQLCriteria) db.getAndCriteria(c, h);
        try(Connection con = db.getConnection())
        {
            String sql = "UPDATE PROJECT_MEMBER SET HASH = ?, USER_ID = ?, PROJECT_ID = ?, ROLE = ? "
                            + "WHERE "
                            + a.toSqlClause();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            int index = 1;
            ps.setInt(index++, item.getLocalHash());
            ps.setInt(index++, item.getUser().getId());
            ps.setInt(index++, item.getProject().getId());
            ps.setString(index++, item.getRole().name());
            a.prepareStatement(ps, index);
            
            int numRowsAffected = ps.executeUpdate();
            if(numRowsAffected == 0)
                throw new Exception("Record has changed or was not found!");
            item.setRemoteHash(item.getLocalHash());
            
        }
    }

    @Override
    public void remove(ProjectMember item) throws Exception
    {
        DBManagerPostgres db = (DBManagerPostgres) DBManager.getInstance();
        SQLCriteria c = (SQLCriteria) getPrimaryKeyCriteria(item.getProject().getId(), item.getUser().getId());
        SQLCriteria h = (SQLCriteria) db.getHashCriteria(item.getRemoteHash());
        SQLCriteria a = (SQLCriteria) db.getAndCriteria(c, h);
        try(Connection con = db.getConnection())
        {
            String sql = "DELETE FROM PROJECT_MEMBER "
                            + "WHERE "
                            + a.toSqlClause();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            int index = 1;
            ps.setInt(index++, item.getRemoteHash());
            ps.setInt(index++, item.getUser().getId());
            ps.setInt(index++, item.getProject().getId());
            ps.setString(index++, item.getRole().name());
            a.prepareStatement(ps, index);
            
            int numRowsAffected = ps.executeUpdate();
            if(numRowsAffected == 0)
                throw new Exception("Record has changed or was not found!");
        }
    }

    @Override
    public ProjectMember getByPrimaryKey(Criteria c) throws Exception
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<ProjectMember> getByCriteria(Criteria criterias) throws Exception
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public Criteria getPrimaryKeyCriteria(int projectId, int userId)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
  

}
