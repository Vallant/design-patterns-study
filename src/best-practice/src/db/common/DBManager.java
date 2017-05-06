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

import static com.sun.org.apache.xerces.internal.util.DraconianErrorHandler.theInstance;
import data.Activity;
import data.Project;
import data.ProjectMember;
import data.ProjectPhase;
import data.User;
import db.interfaces.Criteria;
import db.interfaces.Repository;
import db.interfaces.RepositoryManager;
import db.postgres.RepositoryManagerPostgres;
import java.sql.Connection;
import javax.activation.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author stephan
 */
public class DBManager implements RepositoryManager
{
    private static final String DRIVER_POSTGRES = "org.postgresql.Driver";
    private static DBManager theInstance;
    private RepositoryManager repositoryManager;
    private BasicDataSource pool;
    
    public static void initInstance(String driver, String url, String username, String password) throws Exception
    {
        
        theInstance = new DBManager();
        //theInstance.pool = new DBConnectionPool(driver, url, username, password);
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(driver);
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setMaxTotal(50);
        ds.setMinIdle(5);
        //TODO optimize parameters
        theInstance.pool = ds;
        
        switch(driver)
        {
            case DRIVER_POSTGRES: theInstance.repositoryManager = new RepositoryManagerPostgres(); break;
            default: throw new UnsupportedOperationException("Not yet implemented");
        }
    }

    public static DBManager getInstance()
    {
        assert(theInstance != null);
        return theInstance;
    }

    private DBManager()
    {
        
    }

    @Override
    public Repository<User> getUserRepository()
    {
        return repositoryManager.getUserRepository();
    }

    @Override
    public Repository<Project> getProjectRepository()
    {
        return repositoryManager.getProjectRepository();
    }

    @Override
    public Repository<ProjectMember> getProjectMemberRepository()
    {
        return repositoryManager.getProjectMemberRepository();
    }

    @Override
    public Repository<ProjectPhase> getProjectPhaseRepository()
    {
        return repositoryManager.getProjectPhaseRepository();
    }

    @Override
    public Repository<Activity> getActivityRepository()
    {
        return repositoryManager.getActivityRepository();
    }    

    public synchronized Connection getConnection() throws Exception
    {
        return pool.getConnection();
    }    

    @Override
    public Criteria getAndCriteria(Criteria left, Criteria right)
    {
        return repositoryManager.getAndCriteria(left, right);
    }

    @Override
    public Criteria getOrCriteria(Criteria left, Criteria right)
    {
        return repositoryManager.getOrCriteria(left, right);
    }

    @Override
    public Criteria getIdAndHashCriteria(int id, int hash)
    {
        return repositoryManager.getIdAndHashCriteria(id, hash);
    }

    @Override
    public Criteria createIdCriteria(int id)
    {
        return repositoryManager.createIdCriteria(id);
    }

    @Override
    public Criteria createHashCriteria(int hash)
    {
        return repositoryManager.createHashCriteria(hash);
    }
    
    
}
