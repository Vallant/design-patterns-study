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
import db.postgres.repository.ActivityRepositoryPostgres;
import db.postgres.repository.ProjectMemberRepositoryPostgres;
import db.postgres.repository.ProjectPhaseRepositoryPostgres;
import db.postgres.repository.ProjectRepositoryPostgres;
import db.postgres.repository.UserRepositoryPostgres;
import java.sql.Connection;

import db.interfaces.ProjectMemberRepository;
import db.interfaces.ProjectPhaseRepository;
import db.interfaces.ProjectRepository;
import db.interfaces.UserRepository;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 * @created $date
 * @author stephan
 */
public class DBManagerPostgres extends DBManager
{
    private final BasicDataSource pool;
    
    public DBManagerPostgres(String driver, String url, String username, String password)
    {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(driver);
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setMaxTotal(50);
        ds.setMinIdle(5);
        //TODO optimize parameters
        pool = ds;
    }
    
    public Connection getConnection() throws Exception
    {
        return pool.getConnection();
    }  
    

    public UserRepository getUserRepository()
    {
        return new UserRepositoryPostgres(this);
    }


    public ProjectRepository getProjectRepository()
    {
        return new ProjectRepositoryPostgres(this);
    }


    public ProjectMemberRepository getProjectMemberRepository()
    {
        return new ProjectMemberRepositoryPostgres(this);
    }


    public ProjectPhaseRepository getProjectPhaseRepository()
    {
        return new ProjectPhaseRepositoryPostgres(this);
    }


    public ActivityRepository getActivityRepository()
    {
        return new ActivityRepositoryPostgres(this);
    }

}
