/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import data.Activity;
import data.Project;
import data.ProjectMember;
import data.ProjectPhase;
import data.User;

/**
 *
 * @author stephan
 */
public interface RepositoryFactory
{
    public Repository<User> getUserRepository();
    public Repository<Project> getProjectRepository();
    public Repository<ProjectMember> getProjectMemberRepository();
    public Repository<ProjectPhase> getProjectPhaseRepository();
    public Repository<Activity> getActivityRepository();
    
    public Criteria getAndCriteria(Criteria left, Criteria right);
    public Criteria getOrCriteria(Criteria left, Criteria right);
    public Criteria getIdAndHashCriteria(int id, int hash);
    public Criteria getIdCriteria(int id);
    public Criteria getHashCriteria(int hash);
}
