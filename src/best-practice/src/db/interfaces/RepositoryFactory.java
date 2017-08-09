/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

/**
 * @author stephan
 */
public interface RepositoryFactory
{
  UserRepository getUserRepository();

  ProjectRepository getProjectRepository();

  ProjectMemberRepository getProjectMemberRepository();

  ProjectPhaseRepository getProjectPhaseRepository();

  ActivityRepository getActivityRepository();
}
