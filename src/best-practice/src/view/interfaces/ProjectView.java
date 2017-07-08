/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.interfaces;

import controller.interfaces.ProjectController;

import java.util.ArrayList;

/**
 *
 * @author stephan
 */
public interface ProjectView
{
    void setController(ProjectController controller);
    void showOverview();
    void showDetail(String projectName, ArrayList<String> phases, ArrayList<String> members, ArrayList<String> roles, String description);
    void hide();

    void setParticipatingProjects(ArrayList<String> participatingProjects);

    void setOwnedProjects(ArrayList<String> ownedProjects);

    void showError(String localizedMessage);

    String getSelectedOwnedProject();

    void showProjectCreationDialog();

    void setOwnedProjectsButtonsEnabled(boolean enabled);

    void setInvolvedProjectsButtonsEnabled(boolean enabled);

    String getSelectedInvolvedProject();

    int getSelectedOwnedProjectIndex();

    int getSelectedInvolvedProjectIndex();
}
