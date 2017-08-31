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
    return ProjectMember.getOwnedProject(user.getLoginName(), mainModel.db());
  }


  public ArrayList<ProjectMember> getInvolvedProjects() throws Exception
  {
    return ProjectMember.getInvolvedProjects(user.getLoginName(), mainModel.db());
  }


  public void leaveProject(ProjectMember member) throws Exception
  {
    member.deleteInDb(mainModel.db());
    if(controllerSwing != null)
      controllerSwing.refresh();
    else
      controllerFX.refresh();
  }


  public void deleteProject(Project selectedProject) throws Exception
  {
    selectedProject.deleteFromDb(mainModel.db());
    if(controllerSwing != null)
      controllerSwing.refresh();
    else
      controllerFX.refresh();
    mainModel.refreshActivityBar();

  }


  public void requestedDetailForProject(Project project) throws Exception
  {
    ArrayList<ProjectMember> members = ProjectMember.getMembersByProjectId(project.getId(), mainModel.db());

    ArrayList<ProjectPhase> phases = ProjectPhase.getByProjectId(project.getId(), mainModel.db());

    if(controllerSwing != null)
      controllerSwing.showDetail(project, phases, members);
    else
      controllerFX.showDetail(project, phases, members);
  }


  public void addProject(String name, String description) throws Exception
  {
    Project project = new Project(name, description);
    project.insertIntoDb(mainModel.db());
    ProjectMember pm = new ProjectMember(user, project, ProjectMember.ROLE.LEADER);
    pm.insertIntoDb(mainModel.db());
    if(controllerSwing != null)
      controllerSwing.refresh();
    else
      controllerFX.refresh();
    mainModel.refreshActivityBar();
  }


  public void addPhase(Project project, String phaseName) throws Exception
  {
    ProjectPhase phase = new ProjectPhase(project, phaseName);
    phase.insertIntoDb(mainModel.db());
    if(controllerSwing != null)
      controllerSwing.refresh();
    else
      controllerFX.refresh();
    mainModel.refreshActivityBar();
  }


  public void deletePhase(ProjectPhase projectPhase) throws Exception
  {
    projectPhase.deleteFromDb(mainModel.db());
    if(controllerSwing != null)
      controllerSwing.refresh();
    else
      controllerFX.refresh();
    mainModel.refreshActivityBar();
  }


  public void promoteToLeader(ProjectMember projectMember) throws Exception
  {
    projectMember.setRole(ProjectMember.ROLE.LEADER);
    projectMember.updateInDb(mainModel.db());
    if(controllerSwing != null)
      controllerSwing.refresh();
    else
      controllerFX.refresh();
  }


  public void degradeToMember(ProjectMember projectMember) throws Exception
  {
    projectMember.setRole(ProjectMember.ROLE.MEMBER);
    projectMember.updateInDb(mainModel.db());
    if(controllerSwing != null)
      controllerSwing.refresh();
    else
      controllerFX.refresh();
  }


  public void deleteMember(ProjectMember projectMember) throws Exception
  {
    projectMember.deleteInDb(mainModel.db());
    if(controllerSwing != null)
      controllerSwing.refresh();
    else
      controllerFX.refresh();
  }


  public ArrayList<User> getAvailableUsersFor(int projectId) throws Exception
  {
    return User.getAvailableUsersFor(projectId, mainModel.db());
  }


  public void addMembersToProject(ArrayList<User> toAdd, Project currentProject) throws Exception
  {
    for(User u : toAdd)
    {
      ProjectMember member = new ProjectMember(u, currentProject, ProjectMember.ROLE.MEMBER);
      member.insertIntoDb(mainModel.db());
    }
    if(controllerSwing != null)
      controllerSwing.refresh();
    else
      controllerFX.refresh();
  }


  public void updateProject(Project project) throws Exception
  {
    project.updateInDb(mainModel.db());
    if(controllerSwing != null)
      controllerSwing.refresh();
    else
      controllerFX.refresh();
  }


}
