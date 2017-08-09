/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.impl;

import controller.swing.ProjectControllerSwing;
import data.Project;
import data.ProjectMember;
import data.ProjectPhase;
import data.User;
import db.interfaces.ProjectMemberRepository;
import db.interfaces.ProjectPhaseRepository;
import db.interfaces.ProjectRepository;
import db.interfaces.UserRepository;

import java.util.ArrayList;

/**
 * @author stephan
 */
public class ProjectModelImpl
{
  private MainModelImpl          mainModel;
  private User                   user;
  private ProjectControllerSwing controller;

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
    this.controller = controller;
  }


  public void refresh()
  {
    controller.refresh();
  }


  public ArrayList<ProjectMember> getOwnedProjects() throws Exception
  {

    ProjectMemberRepository pmr = mainModel.db().getProjectMemberRepository();
    return pmr.getOwnedProject(user.getLoginName());
  }


  public ArrayList<ProjectMember> getInvolvedProjects() throws Exception
  {
    ProjectMemberRepository pmr = mainModel.db().getProjectMemberRepository();
    return pmr.getInvolvedProjects(user.getLoginName());
  }


  public void leaveProject(ProjectMember member) throws Exception
  {
    ProjectMemberRepository pmr = mainModel.db().getProjectMemberRepository();
    pmr.delete(member);
    controller.refresh();
  }


  public void deleteProject(Project selectedProject) throws Exception
  {

    ProjectRepository pr = mainModel.db().getProjectRepository();
    pr.delete(selectedProject);
    controller.refresh();
    mainModel.refreshActivityBar();

  }


  public void requestedDetailForProject(Project project) throws Exception
  {
    ProjectMemberRepository pmr = mainModel.db().getProjectMemberRepository();
    ArrayList<ProjectMember> members = pmr.getMembersByProjectId(project.getId());

    ProjectPhaseRepository ppr = mainModel.db().getProjectPhaseRepository();
    ArrayList<ProjectPhase> phases = ppr.getByProjectId(project.getId());

    controller.showDetail(project, phases, members);
  }


  public void addProject(String name, String description) throws Exception
  {
    ProjectRepository pr = mainModel.db().getProjectRepository();
    Project project = new Project(name, description);
    pr.add(project);
    ProjectMemberRepository pmr = mainModel.db().getProjectMemberRepository();
    ProjectMember pm = new ProjectMember(user, project, ProjectMember.ROLE.LEADER);
    pmr.add(pm);
    controller.refresh();
    mainModel.refreshActivityBar();
  }


  public void addPhase(Project project, String phaseName) throws Exception
  {
    ProjectPhase phase = new ProjectPhase(project, phaseName);
    ProjectPhaseRepository ppr = mainModel.db().getProjectPhaseRepository();
    ppr.add(phase);
    controller.refresh();
    mainModel.refreshActivityBar();
  }


  public void deletePhase(ProjectPhase projectPhase) throws Exception
  {
    ProjectPhaseRepository ppr = mainModel.db().getProjectPhaseRepository();
    ppr.delete(projectPhase);
    controller.refresh();
    mainModel.refreshActivityBar();
  }


  public void promoteToLeader(ProjectMember projectMember) throws Exception
  {
    projectMember.setRole(ProjectMember.ROLE.LEADER);
    ProjectMemberRepository pmr = mainModel.db().getProjectMemberRepository();
    pmr.update(projectMember);
    controller.refresh();
  }


  public void degradeToMember(ProjectMember projectMember) throws Exception
  {
    projectMember.setRole(ProjectMember.ROLE.MEMBER);
    ProjectMemberRepository pmr = mainModel.db().getProjectMemberRepository();
    pmr.update(projectMember);
    controller.refresh();
  }


  public void deleteMember(ProjectMember projectMember) throws Exception
  {
    ProjectMemberRepository pmr = mainModel.db().getProjectMemberRepository();
    pmr.delete(projectMember);
    controller.refresh();
  }


  public ArrayList<User> getAvailableUsersFor(int projectId) throws Exception
  {
    UserRepository ur = mainModel.db().getUserRepository();
    return ur.getAvailableUsersFor(projectId);
  }


  public void addMembersToProject(ArrayList<User> toAdd, Project currentProject) throws Exception
  {
    ProjectMemberRepository pmr = mainModel.db().getProjectMemberRepository();
    for(User u : toAdd)
    {
      ProjectMember member = new ProjectMember(u, currentProject, ProjectMember.ROLE.MEMBER);
      pmr.add(member);
    }
    controller.refresh();
  }


  public void updateProject(Project project) throws Exception
  {
    ProjectRepository pr = mainModel.db().getProjectRepository();
    pr.update(project);
    controller.refresh();
  }


}
