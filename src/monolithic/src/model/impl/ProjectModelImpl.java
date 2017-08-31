/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.impl;

import controller.javafx.ProjectControllerFX;
import controller.swing.ProjectControllerSwing;
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
  private MainModelImpl          mainModel;
  private User                   user;
  private ProjectControllerSwing controllerSwing;
  private ProjectControllerFX    controllerFX;


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


  public void setController(ProjectControllerSwing controller)
  {
    this.controllerSwing = controller;
  }

  public void setController(ProjectControllerFX controller)
  {
    controllerFX = controller;
  }

  public void refresh()
  {
    if(controllerSwing != null)
      controllerSwing.refresh();
    else
      controllerFX.refresh();
  }


  public ArrayList<ProjectMember> getOwnedProjects() throws Exception
  {
    if(mainModel.db() != null)
      return ProjectMember.getOwnedProject(user.getLoginName(), mainModel.db());
    else
      return ProjectMember.getOwnedProject(user.getLoginName(), mainModel.dbMongo());

  }


  public ArrayList<ProjectMember> getInvolvedProjects() throws Exception
  {
    if(mainModel.db() != null)
      return ProjectMember.getInvolvedProjects(user.getLoginName(), mainModel.db());
    else
      return ProjectMember.getInvolvedProjects(user.getLoginName(), mainModel.dbMongo());
  }


  public void leaveProject(ProjectMember member) throws Exception
  {
    if(mainModel.db() != null)
      member.deleteFromDb(mainModel.db());
    else
      member.deleteFromDb(mainModel.dbMongo());

    if(controllerSwing != null)
      controllerSwing.refresh();
    else
      controllerFX.refresh();
  }


  public void deleteProject(Project selectedProject) throws Exception
  {
    if(mainModel.db() != null)
      selectedProject.deleteFromDb(mainModel.db());
    else
      selectedProject.deleteFromDb(mainModel.dbMongo());

    if(controllerSwing != null)
      controllerSwing.refresh();
    else
      controllerFX.refresh();
    mainModel.refreshActivityBar();

  }


  public void requestedDetailForProject(Project project) throws Exception
  {
    ArrayList<ProjectMember> members = null;
    if(mainModel.db() != null)
      members = ProjectMember.getMembersByProjectId(project.getId(), mainModel.db());
    else
      members = ProjectMember.getMembersByProjectId(project.getId(), mainModel.dbMongo());

    ArrayList<ProjectPhase> phases = null;
    if(mainModel.db() != null)
      phases = ProjectPhase.getByProjectId(project.getId(), mainModel.db());
    else
      phases = ProjectPhase.getByProjectId(project.getId(), mainModel.dbMongo());

    if(controllerSwing != null)
      controllerSwing.showDetail(project, phases, members);
    else
      controllerFX.showDetail(project, phases, members);
  }


  public void addProject(String name, String description) throws Exception
  {
    Project project = new Project(name, description);

    if(mainModel.db() != null)
      project.insertIntoDb(mainModel.db());
    else
      project.insertIntoDb(mainModel.dbMongo());

    ProjectMember pm = new ProjectMember(user, project, ProjectMember.ROLE.LEADER);
    if(mainModel.db() != null)
      pm.insertIntoDb(mainModel.db());
    else
      pm.insertIntoDb(mainModel.dbMongo());

    if(controllerSwing != null)
      controllerSwing.refresh();
    else
      controllerFX.refresh();
    mainModel.refreshActivityBar();
  }


  public void addPhase(Project project, String phaseName) throws Exception
  {
    ProjectPhase phase = new ProjectPhase(project, phaseName);
    if(mainModel.db() != null)
      phase.insertIntoDb(mainModel.db());
    else
      phase.insertIntoDb(mainModel.dbMongo());
    if(controllerSwing != null)
      controllerSwing.refresh();
    else
      controllerFX.refresh();
    mainModel.refreshActivityBar();
  }


  public void deletePhase(ProjectPhase projectPhase) throws Exception
  {
    if(mainModel.db() != null)
      projectPhase.deleteFromDb(mainModel.db());
    else
      projectPhase.deleteFromDb(mainModel.dbMongo());

    if(controllerSwing != null)
      controllerSwing.refresh();
    else
      controllerFX.refresh();
    mainModel.refreshActivityBar();
  }


  public void promoteToLeader(ProjectMember projectMember) throws Exception
  {
    projectMember.setRole(ProjectMember.ROLE.LEADER);
    if(mainModel.db() != null)
      projectMember.updateInDb(mainModel.db());
    else
      projectMember.updateInDb(mainModel.dbMongo());

    if(controllerSwing != null)
      controllerSwing.refresh();
    else
      controllerFX.refresh();
  }


  public void degradeToMember(ProjectMember projectMember) throws Exception
  {
    projectMember.setRole(ProjectMember.ROLE.MEMBER);
    if(mainModel.db() != null)
      projectMember.updateInDb(mainModel.db());
    else
      projectMember.updateInDb(mainModel.dbMongo());

    if(controllerSwing != null)
      controllerSwing.refresh();
    else
      controllerFX.refresh();
  }


  public void deleteMember(ProjectMember projectMember) throws Exception
  {
    if(mainModel.db() != null)
      projectMember.deleteFromDb(mainModel.db());
    else
      projectMember.deleteFromDb(mainModel.dbMongo());

    if(controllerSwing != null)
      controllerSwing.refresh();
    else
      controllerFX.refresh();
  }


  public ArrayList<User> getAvailableUsersFor(int projectId) throws Exception
  {
    if(mainModel.db() != null)
      return User.getAvailableUsersFor(projectId, mainModel.db());
    else
      return User.getAvailableUsersFor(projectId, mainModel.dbMongo());
  }


  public void addMembersToProject(ArrayList<User> toAdd, Project currentProject) throws Exception
  {
    for(User u : toAdd)
    {
      ProjectMember member = new ProjectMember(u, currentProject, ProjectMember.ROLE.MEMBER);

      if(mainModel.db() != null)
        member.insertIntoDb(mainModel.db());
      else
        member.insertIntoDb(mainModel.dbMongo());
    }
    if(controllerSwing != null)
      controllerSwing.refresh();
    else
      controllerFX.refresh();
  }


  public void updateProject(Project project) throws Exception
  {
    if(mainModel.db() != null)
      project.updateInDb(mainModel.db());
    else
      project.updateInDb(mainModel.dbMongo());

    if(controllerSwing != null)
      controllerSwing.refresh();
    else
      controllerFX.refresh();
  }


}
