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
import db.interfaces.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.spi.DirStateFactory;
import db.interfaces.Criteria;

/**
 * @created $date
 * @author stephan
 */
public class ActivityRepositoryPostgres implements Repository<Activity>
{
    @Override
    public void add(Activity item) throws Exception
    {
        try(Connection con = DBManager.getInstance().getConnection())
        {
            String sql = "INSERT INTO ACTIVITY(HASH, PROJECT_ID, PHASE_ID, USER_ID "
                            + "DESCRIPTION, START_TIME, END_TIME, COMMENTS) "
                            + "VALUES "
                            + "(?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            ps.setInt(1, item.getHash());
            ps.setInt(2, item.getProjectId());
            ps.setInt(3, item.getPhaseId());
            ps.setInt(4, item.getUserId());
            ps.setString(5, item.getDescription());
            ps.setObject(6, item.getStart());
            ps.setObject(7, item.getStop());
            ps.setString(8, item.getComments());
            
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next())
            {
                int id = rs.getInt(1);
                item.setId(id);
            }
        }
    }

    @Override
    public void update(Activity item) throws Exception
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(Activity item) throws Exception
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Activity getByID(int ID) throws Exception
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Activity> getBySpecification(Criteria criterias) throws Exception
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
