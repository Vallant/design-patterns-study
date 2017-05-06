/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.postgres;

import data.Activity;
import data.Project;
import data.ProjectMember;
import data.ProjectPhase;
import data.User;
import db.interfaces.Criteria;
import db.interfaces.Repository;
import db.interfaces.RepositoryManager;
import db.postgres.repository.ActivityRepositoryPostgres;
import db.postgres.repository.ProjectMemberRepositoryPostgres;
import db.postgres.repository.ProjectPhaseRepositoryPostres;
import db.postgres.repository.ProjectRepositoryPostgres;
import db.postgres.repository.UserRepositoryPostgres;
import db.postgres.specification.HashCriteriaPostgres;
import db.postgres.specification.IdCriteriaPostgres;

/**
 *
 * @author stephan
 */
public class RepositoryManagerPostgres implements RepositoryManager
{
    @Override
    public Repository<User> getUserRepository()
    {
        return new UserRepositoryPostgres();
    }

    @Override
    public Repository<Project> getProjectRepository()
    {
        return new ProjectRepositoryPostgres();
    }

    @Override
    public Repository<ProjectMember> getProjectMemberRepository()
    {
        return new ProjectMemberRepositoryPostgres();
    }

    @Override
    public Repository<ProjectPhase> getProjectPhaseRepository()
    {
        return new ProjectPhaseRepositoryPostres();
    }

    @Override
    public Repository<Activity> getActivityRepository()
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
    
}
