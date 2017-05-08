/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

/**
 *
 * @author stephan
 */
public interface RepositoryFactory
{
    public UserRepository getUserRepository();
    public ProjectRepository getProjectRepository();
    public ProjectMemberRepository getProjectMemberRepository();
    public ProjectPhaseRepository getProjectPhaseRepository();
    public ActivityRepository getActivityRepository();
    
    public Criteria getAndCriteria(Criteria left, Criteria right);
    public Criteria getOrCriteria(Criteria left, Criteria right);
    public Criteria getIdAndHashCriteria(int id, int hash);
    public Criteria getIdCriteria(int id);
    public Criteria getHashCriteria(int hash);
    
    public Criteria getNameAndHashCriteria(String name, int hash);
    public Criteria getNameCriteria(String name);
    
    public Criteria getIntCriteria(String columnName, int value);
    public Criteria getStringCriteria(String columnName, String value);
}
