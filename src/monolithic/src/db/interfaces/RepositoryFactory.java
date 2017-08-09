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
}
