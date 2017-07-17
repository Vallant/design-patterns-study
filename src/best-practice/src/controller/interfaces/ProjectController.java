/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.interfaces;

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
public interface ProjectController
{
    void setModel(ProjectModel model);
    void setView(ProjectView view);

    void refresh();

    void leaveProjectClicked();

    void addProjectClicked();

    void deleteProjectClicked();

    void ownedProjectsHasSelection(boolean hasSelection);

    void involvedProjectsHasSelection(boolean hasSelection);

    void doubleClickedOn(int index);

    void showDetail(Project project, ArrayList<ProjectPhase> phases, ArrayList<ProjectMember> members);

    void backClicked();

    void addProject(String text, String text1);

    void deletePhaseClicked();
    void addPhaseClicked();

    void deleteMemberClicked();

    void promoteToAdminClicked();

    void degradeToMemberClicked();

    void addMemberClicked();

    void addPhase(String phaseName);

    void addMembers(int[] selectedIndices);

    void updateDescriptionClicked();
}
