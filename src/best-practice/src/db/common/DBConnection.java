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

package db.common;

import db.interfaces.ActivityRepository;
import db.interfaces.ProjectMemberRepository;
import db.interfaces.ProjectPhaseRepository;
import db.interfaces.ProjectRepository;
import db.interfaces.UserRepository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 * @created $date
 * @author stephan
 */
public abstract class DBConnection
{

    private Connection con = null;
    private String ErrorMessage;
    private static final SimpleDateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public DBConnection(String driver_, String url_, String username_, String password_) throws Exception
    {
        Class.forName(driver_);
        con = (Connection) DriverManager.getConnection(url_, username_, password_);
        con.setAutoCommit(false);
        ErrorMessage = new String();
    }

    public String getErrorMessage()
    {
        String msg = new String(ErrorMessage);
        ErrorMessage = new String();
        return msg;
    }

    public void close() throws SQLException
    {
        if (con != null)
        {
            con.close();
            con = null;
        }
    }
    
    public abstract UserRepository getUserRepository();
    public abstract ProjectRepository getProjectRepository();
    public abstract ProjectMemberRepository getProjectMemberRepository();
    public abstract ProjectPhaseRepository getProjectPhaseRepository();
    public abstract ActivityRepository getActivityRepository();
}

