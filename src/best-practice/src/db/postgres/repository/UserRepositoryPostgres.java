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

import data.User;
import db.common.DBManager;
import db.common.DBManagerPostgres;
import java.util.ArrayList;
import db.interfaces.UserRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * @created $date
 * @author stephan
 */
public class UserRepositoryPostgres implements UserRepository
{
    private final DBManagerPostgres db;

    public UserRepositoryPostgres(DBManagerPostgres db)
    {
        this.db = db;
    }
    
    
    @Override
    public void add(User item) throws Exception
    {
        assert(item != null);
        try(Connection con = db.getConnection())
        {
            String sql = "INSERT INTO USERS(HASH, FIRST_NAME, LAST_NAME, ROLE, "
                    + "SALT, PASSWORD, LOGIN_NAME, EMAIL) "
                    + "VALUES "
                    + "(?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            
            int index = 1;
            ps.setInt(index++, item.getLocalHash());
            ps.setString(index++, item.getFirstName());
            ps.setString(index++, item.getLastName());
            ps.setString(index++, item.getRole().name());
            ps.setBytes(index++, item.getSalt());
            ps.setBytes(index++, item.getPassword());
            ps.setString(index++, item.getLoginName());
            ps.setString(index++, item.getEmail());
            
            ps.executeUpdate();
            item.setRemoteHash(item.getLocalHash());
        }
    }

    @Override
    public void update(User item) throws Exception
    {
        assert(item != null);
        
        try(Connection con = db.getConnection())
        {
            String sql = "UPDATE USERS SET HASH = ?, FIRST_NAME = ?, LAST_NAME = ?, ROLE = ?, "
                    + "SALT = ?, PASSWORD = ?, LOGIN_NAME = ?, EMAIL = ? "
                    + "WHERE LOGIN_NAME = ? AND HASH = ?";
            
            PreparedStatement ps = con.prepareStatement(sql);
            
            
            int index = 1;
            ps.setInt(index++, item.getLocalHash());
            ps.setString(index++, item.getFirstName());
            ps.setString(index++, item.getLastName());
            ps.setString(index++, item.getRole().name());
            ps.setBytes(index++, item.getSalt());
            ps.setBytes(index++, item.getPassword());
            ps.setString(index++, item.getLoginName());
            ps.setString(index++, item.getEmail());
            
            ps.setString(index++, item.getLoginName());
            ps.setInt(index++, item.getRemoteHash());
            
            int numRowsAffected = ps.executeUpdate();
            if(numRowsAffected == 0)
                throw new Exception("Record has changed or was not found!");
        }
    }

    @Override
    public void delete(User item) throws Exception
    {
        assert(item != null);
       
        try(Connection con = db.getConnection())
        {
            String sql = "DELETE FROM USERS "
                    + "WHERE LOGIN_NAME = ? AND HASH = ?";
            
            PreparedStatement ps = con.prepareStatement(sql);
            int index = 1;
            ps.setString(index++, item.getLoginName());
            ps.setInt(index++, item.getRemoteHash());
            
            
            int numRowsAffected = ps.executeUpdate();
            if(numRowsAffected == 0)
                throw new Exception("Record has changed or was not found!");
        }
    }

  
    @Override
    public User getByPrimaryKey(String loginName) throws Exception
    { 
        try(Connection con = db.getConnection())
        {
            String sql = "SELECT HASH, FIRST_NAME, LAST_NAME, ROLE, "
                    + "SALT, PASSWORD, LOGIN_NAME, EMAIL "
                    + "FROM USERS "
                    + "WHERE LOGIN_NAME = ?";

            int index = 1;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(index++, loginName);
            
            ResultSet rs = ps.executeQuery();
            if(rs.next() == false)
                throw new Exception("No such record");
            
            int hash = rs.getInt("HASH");
            String firstName = rs.getString("FIRST_NAME");
            String lastName = rs.getString("LAST_NAME");
            String role = rs.getString("ROLE");
            byte[] salt = rs.getBytes("SALT");
            byte[] password = rs.getBytes("PASSWORD");
            String loginNameDb = rs.getString("LOGIN_NAME");
            String email = rs.getString("EMAIL");

            return new User(hash, loginName, firstName, lastName, 
                    User.ROLE.valueOf(role), email, password, salt);
        }
    }

    @Override
    public List<User> getAll() throws Exception
    {
         ArrayList<User> l = new ArrayList<>();
        
        try(Connection con = db.getConnection())
        {
            String sql = "SELECT HASH, FIRST_NAME, LAST_NAME, ROLE, "
                    + "SALT, PASSWORD, LOGIN_NAME, EMAIL "
                    + "FROM USERS ";
            PreparedStatement ps = con.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                int hash = rs.getInt("HASH");
                String firstName = rs.getString("FIRST_NAME");
                String lastName = rs.getString("LAST_NAME");
                String role = rs.getString("ROLE");
                byte[] salt = rs.getBytes("SALT");
                byte[] password = rs.getBytes("PASSWORD");
                String loginName = rs.getString("LOGIN_NAME");
                String email = rs.getString("EMAIL");
                
                l.add(new User(hash, loginName, firstName, lastName, 
                        User.ROLE.valueOf(role), email, password, salt));
            }
        }
        return l;
    }


}
