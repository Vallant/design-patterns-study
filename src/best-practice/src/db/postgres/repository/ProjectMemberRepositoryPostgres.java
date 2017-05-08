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
import data.ProjectMember;
import data.ProjectPhase;
import data.User;
import db.common.DBManager;
import db.common.DBManagerPostgres;
import db.interfaces.Criteria;
import java.util.ArrayList;
import db.interfaces.ProjectMemberRepository;
import db.interfaces.ProjectPhaseRepository;
import db.interfaces.ProjectRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import db.interfaces.SQLCriteria;
import db.interfaces.UserRepository;
import db.postgres.criteria.AndCriteriaPostres;
import db.postgres.criteria.StringCriteriaPostgres;
import java.sql.ResultSet;
import java.time.ZonedDateTime;


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
        try(Connection con = db.getConnection())
        {
            String sql = "INSERT INTO PROJECT_MEMBER(HASH, USER_LOGIN_NAME, PROJECT_NAME, ROLE) "
                            + "VALUES "
                            + "(?, ?, ?, ?) ";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            int index = 1;
            ps.setInt(index++, item.getLocalHash());
            ps.setString(index++, item.getUserLoginName());
            ps.setString(index++, item.getProjectName());
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
        DBManagerPostgres db = (DBManagerPostgres) DBManager.getInstance();
        SQLCriteria c = (SQLCriteria) getPrimaryKeyAndHashCriteria(item);
        try(Connection con = db.getConnection())
        {
            String sql = "UPDATE PROJECT_MEMBER SET HASH = ?, USER_LOGIN_NAME = ?, PROJECT_NAME = ?, ROLE = ? "
                            + "WHERE "
                            + c.toSqlClause();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            int index = 1;
            ps.setInt(index++, item.getLocalHash());
            ps.setString(index++, item.getUserLoginName());
            ps.setString(index++, item.getProjectName());
            ps.setString(index++, item.getRole().name());
            c.prepareStatement(ps, index);
            
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
        SQLCriteria c = (SQLCriteria) getPrimaryKeyAndHashCriteria(item);
        try(Connection con = db.getConnection())
        {
            String sql = "DELETE FROM PROJECT_MEMBER "
                            + "WHERE "
                            + c.toSqlClause();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            c.prepareStatement(ps, 1);
            
            int numRowsAffected = ps.executeUpdate();
            if(numRowsAffected == 0)
                throw new Exception("Record has changed or was not found!");
        }
    }

    @Override
    public ProjectMember getByPrimaryKey(Criteria c) throws Exception
    {
        ArrayList<ProjectMember> l = getByCriteria(c);
        if(l.isEmpty())
            throw new Exception("Record was not found!");
        return l.get(0);
            
    }

    @Override
    public ArrayList<ProjectMember> getByCriteria(Criteria criterias) throws Exception
    {
        ArrayList<ProjectMember> l = new ArrayList<>();
        SQLCriteria sc = (SQLCriteria) criterias;
        DBManagerPostgres db = (DBManagerPostgres) DBManager.getInstance();
        try(Connection con = db.getConnection())
        {
            String sql = "SELECT HASH, USER_LOGIN_NAME, PROJECT_NAME, ROLE FROM PROJECT_MEMBERS "
                         + "WHERE " + sc.toSqlClause();
            PreparedStatement ps = con.prepareStatement(sql);
            
            int index = 1;
            sc.prepareStatement(ps, index);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                int hash = rs.getInt("HASH");
                String projectName = rs.getString("PROJECT_NAME");
                String userLoginName = rs.getString("USER_LOGIN_NAME");
                String role = rs.getString("ROLE");
                
                ProjectRepository p = db.getProjectRepository();
                Criteria c = db.getNameCriteria(projectName);
                Project project = p.getByPrimaryKey(c);
                UserRepository u = db.getUserRepository();
                User user = u.getByPrimaryKey(userLoginName);
                
                ProjectMember m = new ProjectMember(user, project, hash, ProjectMember.ROLE.valueOf(role));
                l.add(m);
            }
        }
        return l;
    }


    @Override
    public Criteria getPrimaryKeyCriteria(String projectName, String userLoginName)
    {
        Criteria name = new StringCriteriaPostgres("PROJECT_NAME", projectName);
        Criteria login = new StringCriteriaPostgres("USER_LOGIN_NAME", userLoginName);
        return new AndCriteriaPostres(login, name);
    }

    @Override
    public Criteria getPrimaryKeyCriteria(ProjectMember item)
    {
        return getPrimaryKeyCriteria(item.getProjectName(), item.getUserLoginName());
    }

    @Override
    public Criteria getPrimaryKeyAndHashCriteria(ProjectMember item)
    {
        DBManager db = DBManager.getInstance();
        Criteria h = db.getHashCriteria(item.getRemoteHash());
        Criteria p = getPrimaryKeyCriteria(item);
        return db.getAndCriteria(p, h);
    }
    
  

}
