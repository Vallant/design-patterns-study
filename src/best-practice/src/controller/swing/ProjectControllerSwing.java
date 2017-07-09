/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.swing;

import controller.interfaces.ProjectController;
import data.Project;
import data.ProjectMember;
import data.ProjectPhase;
import model.interfaces.ProjectModel;
import view.interfaces.ProjectView;

import java.util.ArrayList;

/**
 *
 * @author stephan
 */
public class ProjectControllerSwing implements ProjectController
{
    private ProjectModel model;
    private ProjectView view;

    private ArrayList<ProjectMember> ownedProjects;
    private ArrayList<ProjectMember> involvedProjects;

    private ArrayList<ProjectMember> projectMembers;
    private ArrayList<ProjectPhase> projectPhases;



    @Override
    public void setModel(ProjectModel model)
    {
        this.model = model;
    }

    @Override
    public void setView(ProjectView view)
    {
        this.view = view;
    }

    @Override
    public void refresh()
    {
        ArrayList<String> owned = new ArrayList<>();
        ArrayList<String> involved = new ArrayList<>();
        try {
            ownedProjects = model.getOwnedProjects();
            involvedProjects = model.getInvolvedProjects();
            for(ProjectMember m : ownedProjects)
                owned.add(m.getProjectName());
            for(ProjectMember m : involvedProjects)
                involved.add(m.getProjectName());
            view.setOwnedProjects(owned);
            view.setParticipatingProjects(involved);
        } catch (Exception e) {
            view.showError(e.getLocalizedMessage());
        }

    }

    @Override
    public void leaveProjectClicked()
    {
        int index = view.getSelectedInvolvedProjectIndex();
        try {
            model.leaveProject(involvedProjects.get(index));
        } catch (Exception e) {
            view.showError(e.getLocalizedMessage());
        }
    }

    @Override
    public void addProjectClicked()
    {
        view.showProjectCreationDialog();
    }

    @Override
    public void deleteProjectClicked() {
        int index = view.getSelectedOwnedProjectIndex();
        try {
            model.deleteProject(ownedProjects.get(index).getProject());
        } catch (Exception e) {
            view.showError(e.getLocalizedMessage());
        }
    }

    @Override
    public void ownedProjectsHasSelection(boolean hasSelection) {
        view.setOwnedProjectsButtonsEnabled(hasSelection);
    }

    @Override
    public void involvedProjectsHasSelection(boolean hasSelection) {
        view.setInvolvedProjectsButtonsEnabled(hasSelection);
    }

    @Override
    public void doubleClickedOn(int index)
    {
        try {
            model.requestedDetailForProject(ownedProjects.get(index).getProject());
        } catch (Exception e) {
            view.showError(e.getLocalizedMessage());
        }
    }

    @Override
    public void showDetail(Project project, ArrayList<ProjectPhase> phases, ArrayList<ProjectMember> members) {

        ArrayList<String> phaseNames = new ArrayList<>();
        ArrayList<String> memberNames = new ArrayList<>();
        ArrayList<String> memberRoles = new ArrayList<>();

        projectMembers = members;
        projectPhases = phases;

        for(ProjectPhase pp : phases)
        {
            phaseNames.add(pp.getName());
        }

        for(ProjectMember m : members)
        {
            memberNames.add(m.getUser().getFirstName() + " " + m.getUser().getLastName());
            memberRoles.add(m.getRole().name());
        }

        view.showDetail(project.getName(), phaseNames, memberNames, memberRoles, project.getDescription());
    }

    @Override
    public void backClicked() {
        view.showOverview();
    }

    @Override
    public void addProject(String name, String description) {
        try {
            model.addProject(name, description);
        } catch (Exception e) {
            view.showError(e.getLocalizedMessage());
        }
    }

    @Override
    public void deletePhaseClicked() {
        int index = view.getSelectedPhaseIndex();
    }

    @Override
    public void addMemberClicked() {
        view.showAddMemberDialog();
    }

    @Override
    public void deleteMemberClicked()
    {
        int index = view.getSelectedMemberIndex();
    }

    @Override
    public void promoteToAdminClicked()
    {
        int index = view.getSelectedMemberIndex();
    }

    @Override
    public void degradeToMemberClicked() {
        int index = view.getSelectedMemberIndex();
    }

    @Override
    public void addPhaseClicked() {
        view.showAddPhaseDialog();

    }

    @Override
    public void addPhase(int index, String phaseName) {
        try {
            model.addPhase(ownedProjects.get(index).getProject(), phaseName);
        } catch (Exception e) {
            view.showError(e.getLocalizedMessage());
        }
    }

}
