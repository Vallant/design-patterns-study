/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.interfaces;

import controller.interfaces.ProjectController;
import data.Project;
import data.ProjectMember;
import data.ProjectPhase;
import data.User;

import java.util.ArrayList;

/**
 * @author stephan
 */
public interface ProjectModel
{
  void setMainModel(MainModel mainModel);

  void setUser(User user);

  void setController(ProjectController controller);

  void refresh();

  ArrayList<ProjectMember> getOwnedProjects() throws Exception;

  ArrayList<ProjectMember> getInvolvedProjects() throws Exception;

  void leaveProject(ProjectMember selectedProject) throws Exception;

  void deleteProject(Project selectedProject) throws Exception;

  void requestedDetailForProject(Project project) throws Exception;

  void addProject(String name, String description) throws Exception;

  void addPhase(Project project, String phaseName) throws Exception;

  void deletePhase(ProjectPhase projectPhase) throws Exception;

  void promoteToLeader(ProjectMember projectMember) throws Exception;

  void degradeToMember(ProjectMember projectMember) throws Exception;

  void deleteMember(ProjectMember projectMember) throws Exception;

  ArrayList<User> getAvailableUsersFor(int projectId) throws Exception;

  void addMembersToProject(ArrayList<User> toAdd, Project currentProject) throws Exception;

  void updateProject(Project project) throws Exception;
}
