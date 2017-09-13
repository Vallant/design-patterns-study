/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.impl;

import controller.standard.ProjectControllerStandard;
import data.Project;
import data.ProjectMember;
import data.ProjectPhase;
import data.User;

import java.util.ArrayList;

/**
 * @author stephan
 */
public class ProjectModelImpl
{
  private MainModelImpl             mainModel;
  private User                      user;
  private ProjectControllerStandard controller;

  public ProjectModelImpl()
  {

  }


  public void setMainModel(MainModelImpl mainModel)
  {
    this.mainModel = mainModel;
  }


  public void setUser(User user)
  {
    this.user = user;
  }


  public void setController(ProjectControllerStandard controller)
  {
    this.controller = controller;
  }


  public void refresh()
  {
    controller.refresh();
  }


  public ArrayList<ProjectMember> getOwnedProjects() throws Exception
  {
    if(mainModel.dbPostgres() != null)
      return ProjectMember.getOwnedProject(user.getLoginName(), mainModel.dbPostgres());
    else
      return ProjectMember.getOwnedProject(user.getLoginName(), mainModel.dbMongo());
  }


  public ArrayList<ProjectMember> getInvolvedProjects() throws Exception
  {
    if(mainModel.dbPostgres() != null)
      return ProjectMember.getInvolvedProjects(user.getLoginName(), mainModel.dbPostgres());
    else
      return ProjectMember.getInvolvedProjects(user.getLoginName(), mainModel.dbMongo());
  }


  public void leaveProject(ProjectMember member) throws Exception
  {
    if(mainModel.dbPostgres() != null)
      member.deleteFromDb(mainModel.dbPostgres());
    else
      member.deleteFromDb(mainModel.dbMongo());
    controller.refresh();
  }


  public void deleteProject(Project selectedProject) throws Exception
  {
    if(mainModel.dbPostgres() != null)
      selectedProject.deleteFromDb(mainModel.dbPostgres());
    else
      selectedProject.deleteFromDb(mainModel.dbMongo());
    controller.refresh();
    mainModel.refreshActivityBar();

  }


  public void requestedDetailForProject(Project project) throws Exception
  {
    ArrayList<ProjectMember> members = null;
    if(mainModel.dbPostgres() != null)
      members = ProjectMember.getMembersByProjectId(project.getId(), mainModel.dbPostgres());
    else
      members = ProjectMember.getMembersByProjectId(project.getId(), mainModel.dbMongo());

    ArrayList<ProjectPhase> phases = null;
    if(mainModel.dbPostgres() != null)
      phases = ProjectPhase.getByProjectId(project.getId(), mainModel.dbPostgres());
    else
      phases = ProjectPhase.getByProjectId(project.getId(), mainModel.dbMongo());

    controller.showDetail(project, phases, members);
  }


  public void addProject(String name, String description) throws Exception
  {
    Project project = new Project(name, description);
    if(mainModel.dbPostgres() != null)
      project.insertIntoDb(mainModel.dbPostgres());
    else
      project.insertIntoDb(mainModel.dbMongo());

    ProjectMember pm = new ProjectMember(user, project, ProjectMember.ROLE.LEADER);
    if(mainModel.dbPostgres() != null)
      pm.insertIntoDb(mainModel.dbPostgres());
    else
      pm.insertIntoDb(mainModel.dbMongo());
    controller.refresh();
    mainModel.refreshActivityBar();
  }


  public void addPhase(Project project, String phaseName) throws Exception
  {
    ProjectPhase phase = new ProjectPhase(project, phaseName);
    if(mainModel.dbPostgres() != null)
      phase.insertIntoDb(mainModel.dbPostgres());
    else
      phase.insertIntoDb(mainModel.dbMongo());
    controller.refresh();
    mainModel.refreshActivityBar();
  }


  public void deletePhase(ProjectPhase projectPhase) throws Exception
  {
    if(mainModel.dbPostgres() != null)
      projectPhase.deleteFromDb(mainModel.dbPostgres());
    else
      projectPhase.deleteFromDb(mainModel.dbMongo());
    controller.refresh();
    mainModel.refreshActivityBar();
  }


  public void promoteToLeader(ProjectMember projectMember) throws Exception
  {
    projectMember.setRole(ProjectMember.ROLE.LEADER);
    if(mainModel.dbPostgres() != null)
      projectMember.updateInDb(mainModel.dbPostgres());
    else
      projectMember.updateInDb(mainModel.dbMongo());
    controller.refresh();
  }


  public void degradeToMember(ProjectMember projectMember) throws Exception
  {
    projectMember.setRole(ProjectMember.ROLE.MEMBER);
    if(mainModel.dbPostgres() != null)
      projectMember.updateInDb(mainModel.dbPostgres());
    else
      projectMember.updateInDb(mainModel.dbMongo());
    controller.refresh();
  }


  public void deleteMember(ProjectMember projectMember) throws Exception
  {
    if(mainModel.dbPostgres() != null)
      projectMember.deleteFromDb(mainModel.dbPostgres());
    else
      projectMember.deleteFromDb(mainModel.dbMongo());
    controller.refresh();
  }


  public ArrayList<User> getAvailableUsersFor(int projectId) throws Exception
  {
    if(mainModel.dbPostgres() != null)
      return User.getAvailableUsersFor(projectId, mainModel.dbPostgres());
    else
      return User.getAvailableUsersFor(projectId, mainModel.dbMongo());
  }


  public void addMembersToProject(ArrayList<User> toAdd, Project currentProject) throws Exception
  {
    for(User u : toAdd)
    {
      ProjectMember member = new ProjectMember(u, currentProject, ProjectMember.ROLE.MEMBER);
      if(mainModel.dbPostgres() != null)
        member.insertIntoDb(mainModel.dbPostgres());
      else
        member.insertIntoDb(mainModel.dbMongo());
    }
    controller.refresh();
  }


  public void updateProject(Project project) throws Exception
  {
    if(mainModel.dbPostgres() != null)
      project.updateInDb(mainModel.dbPostgres());
    else
      project.updateInDb(mainModel.dbMongo());
    controller.refresh();
  }


}
