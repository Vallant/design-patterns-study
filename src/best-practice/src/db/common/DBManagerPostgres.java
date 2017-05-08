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
import db.postgres.criteria.AndCriteriaPostres;
import db.postgres.criteria.OrCriteriaPostgres;
import db.postgres.repository.ActivityRepositoryPostgres;
import db.postgres.repository.ProjectMemberRepositoryPostgres;
import db.postgres.repository.ProjectPhaseRepositoryPostres;
import db.postgres.repository.ProjectRepositoryPostgres;
import db.postgres.repository.UserRepositoryPostgres;
import db.postgres.criteria.HashCriteriaPostgres;
import db.postgres.criteria.IdCriteriaPostgres;
import java.sql.Connection;
import org.apache.commons.dbcp2.BasicDataSource;
import db.interfaces.Criteria;
import db.interfaces.ProjectMemberRepository;
import db.interfaces.ProjectPhaseRepository;
import db.interfaces.ProjectRepository;
import db.interfaces.UserRepository;
import db.postgres.criteria.IntCriteriaPostgres;
import db.postgres.criteria.NameCriteriaPostgres;
import db.postgres.criteria.StringCriteriaPostgres;

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
    
    @Override
    public UserRepository getUserRepository()
    {
        return new UserRepositoryPostgres();
    }

    @Override
    public ProjectRepository getProjectRepository()
    {
        return new ProjectRepositoryPostgres();
    }

    @Override
    public ProjectMemberRepository getProjectMemberRepository()
    {
        return new ProjectMemberRepositoryPostgres();
    }

    @Override
    public ProjectPhaseRepository getProjectPhaseRepository()
    {
        return new ProjectPhaseRepositoryPostres();
    }

    @Override
    public ActivityRepository getActivityRepository()
    {
        return new ActivityRepositoryPostgres();
    }

    @Override
    public Criteria getAndCriteria(Criteria left, Criteria right)
    {
        return new AndCriteriaPostres(left, right);
    }

    @Override
    public Criteria getOrCriteria(Criteria left, Criteria right)
    {
        return new OrCriteriaPostgres(left, right);
    }

    @Override
    public Criteria getIdAndHashCriteria(int id, int hash)
    {
        return new AndCriteriaPostres(new IdCriteriaPostgres(id), new HashCriteriaPostgres(hash));
    }

    @Override
    public Criteria getIdCriteria(int id)
    {
        return new IdCriteriaPostgres(id);
    }

    @Override
    public Criteria getHashCriteria(int hash)
    {
        return new HashCriteriaPostgres(hash);
    }

    @Override
    public Criteria getNameAndHashCriteria(String name, int hash)
    {
        return new AndCriteriaPostres(new NameCriteriaPostgres(name), new HashCriteriaPostgres(hash));
    }

    @Override
    public Criteria getNameCriteria(String name)
    {
        return new NameCriteriaPostgres(name);
    }

    @Override
    public Criteria getIntCriteria(String columnName, int value)
    {
        return new IntCriteriaPostgres(value, columnName);
    }

    @Override
    public Criteria getStringCriteria(String columnName, String value)
    {
        return new StringCriteriaPostgres(columnName, value);
    }
    

}
