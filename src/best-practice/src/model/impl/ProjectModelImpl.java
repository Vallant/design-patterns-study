/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.impl;

import controller.interfaces.ProjectController;
import data.Project;
import data.ProjectMember;
import data.ProjectPhase;
import data.User;
import db.interfaces.ProjectMemberRepository;
import db.interfaces.ProjectPhaseRepository;
import db.interfaces.ProjectRepository;
import model.interfaces.MainModel;
import model.interfaces.ProjectModel;

import java.util.ArrayList;

/**
 *
 * @author stephan
 */
public class ProjectModelImpl implements ProjectModel
{
    private MainModel mainModel;
    private User user;
    private ProjectController controller;

    public ProjectModelImpl()
    {
        
    }

    @Override
    public void setMainModel(MainModel mainModel)
    {
        this.mainModel = mainModel;
    }

    @Override
    public void setUser(User user)
    {
        this.user = user;
    }

    @Override
    public void setController(ProjectController controller)
    {
        this.controller = controller;
    }

    @Override
    public void refresh() {
        controller.refresh();
    }

    @Override
    public ArrayList<ProjectMember> getOwnedProjects() throws Exception {

        ProjectMemberRepository pmr = mainModel.DB().getProjectMemberRepository();
        return pmr.getOwnedProject(user.getLoginName());
    }

    @Override
    public ArrayList<ProjectMember> getInvolvedProjects() throws Exception {
        ProjectMemberRepository pmr = mainModel.DB().getProjectMemberRepository();
        return pmr.getInvolvedProjects(user.getLoginName());
    }

    @Override
    public void leaveProject(ProjectMember member) throws Exception {
        ProjectMemberRepository pmr = mainModel.DB().getProjectMemberRepository();
        pmr.delete(member);
        controller.refresh();
    }

    @Override
    public void deleteProject(Project selectedProject) throws Exception {
        ProjectRepository pr = mainModel.DB().getProjectRepository();
        pr.delete(selectedProject);

    }

    @Override
    public void requestedDetailForProject(Project project) throws Exception {
        ProjectMemberRepository pmr = mainModel.DB().getProjectMemberRepository();
        ArrayList<ProjectMember> members = pmr.getMembersByProjectName (project.getName());

        ProjectPhaseRepository ppr = mainModel.DB().getProjectPhaseRepository();
        ArrayList<ProjectPhase> phases = ppr.getByProjectId(project.getId());

        controller.showDetail(project, phases, members);
    }


}
