/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.standard;

import data.Project;
import data.ProjectMember;
import data.ProjectPhase;
import data.User;
import model.impl.ProjectModelImpl;
import view.javafx.project.ProjectViewFX;
import view.swing.project.ProjectViewSwing;

import java.util.ArrayList;

/**
 * @author stephan
 */
public class ProjectControllerStandard
{
  private ProjectModelImpl model;
  private ProjectViewSwing viewSwing;
  private ProjectViewFX viewFX;

  private ArrayList<ProjectMember> ownedProjects;
  private ArrayList<ProjectMember> involvedProjects;

  private ArrayList<ProjectMember> projectMembers;
  private ArrayList<ProjectPhase>  projectPhases;

  private ArrayList<User> availableMembers;

  private Project detailProject;


  public void setModel(ProjectModelImpl model)
  {
    this.model = model;
  }


  public void setViewSwing(ProjectViewSwing viewSwing)
  {
    this.viewSwing = viewSwing;
  }

  public void setViewFX(ProjectViewFX viewFX)
  {
    this.viewFX = viewFX;
  }

  public void refresh()
  {
    ArrayList<String> owned = new ArrayList<>();
    ArrayList<String> involved = new ArrayList<>();
    try
    {
      ownedProjects = model.getOwnedProjects();
      involvedProjects = model.getInvolvedProjects();
      for(ProjectMember m : ownedProjects)
      {
        owned.add(m.getProjectName());
      }
      for(ProjectMember m : involvedProjects)
      {
        involved.add(m.getProjectName());
      }
      if(viewSwing != null)
      {
        viewSwing.setOwnedProjects(owned);
        viewSwing.setParticipatingProjects(involved);
      }
      else
      {
        viewFX.setOwnedProjects(owned);
        viewFX.setParticipatingProjects(involved);
      }


      if(detailProject != null)
        model.requestedDetailForProject(detailProject);

    }
    catch(Exception e)
    {
      e.printStackTrace();
      if(viewSwing != null)
        viewSwing.showError(e.getLocalizedMessage());
      else
        viewFX.showError(e.getLocalizedMessage());
    }

  }


  public void leaveProjectClicked()
  {
    int index;
    if(viewSwing != null)
      index = viewSwing.getSelectedInvolvedProjectIndex();
    else
      index = viewFX.getSelectedInvolvedProjectIndex();
    try
    {
      model.leaveProject(involvedProjects.get(index));
    }
    catch(Exception e)
    {
      e.printStackTrace();
      if(viewSwing != null)
        viewSwing.showError(e.getLocalizedMessage());
      else
        viewFX.showError(e.getLocalizedMessage());
    }
  }


  public void addProjectClicked()
  {
    if(viewSwing != null)
      viewSwing.showProjectCreationDialog();
    else
      viewFX.showProjectCreationDialog();
  }


  public void deleteProjectClicked()
  {
    int index;
    if(viewSwing != null)
      index = viewSwing.getSelectedOwnedProjectIndex();
    else
      index = viewFX.getSelectedOwnedProjectIndex();
    try
    {
      model.deleteProject(ownedProjects.get(index).getProject());
    }
    catch(Exception e)
    {
      e.printStackTrace();
      if(viewSwing != null)
        viewSwing.showError(e.getLocalizedMessage());
      else
        viewFX.showError(e.getLocalizedMessage());
    }
  }


  public void ownedProjectsHasSelection(boolean hasSelection)
  {
    if(viewSwing != null)
      viewSwing.setOwnedProjectsButtonsEnabled(hasSelection);
    else
      viewFX.setOwnedProjectsButtonsEnabled(hasSelection);
  }


  public void involvedProjectsHasSelection(boolean hasSelection)
  {
    if(viewSwing != null)
      viewSwing.setInvolvedProjectsButtonsEnabled(hasSelection);
    else
      viewFX.setInvolvedProjectsButtonsEnabled(hasSelection);
  }


  public void doubleClickedOn(int index)
  {
    try
    {
      model.requestedDetailForProject(ownedProjects.get(index).getProject());
    }
    catch(Exception e)
    {
      e.printStackTrace();
      if(viewSwing != null)
        viewSwing.showError(e.getLocalizedMessage());
      else
        viewFX.showError(e.getLocalizedMessage());
    }
  }


  public void showDetail(Project project, ArrayList<ProjectPhase> phases, ArrayList<ProjectMember> members)
  {

    detailProject = project;
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
    if(viewSwing != null)
      viewSwing.showDetail(project.getName(), phaseNames, memberNames, memberRoles, project.getDescription());
    else
      viewFX.showDetail(project.getName(), phaseNames, memberNames, memberRoles, project.getDescription());
  }


  public void backClicked()
  {
    detailProject = null;
    if(viewSwing != null)
      viewSwing.showOverview();
    else
      viewFX.showOverview();
  }


  public void addProject(String name, String description)
  {
    try
    {
      model.addProject(name, description);
    }
    catch(Exception e)
    {
      e.printStackTrace();
      if(viewSwing != null)
        viewSwing.showError(e.getLocalizedMessage());
      else
        viewFX.showError(e.getLocalizedMessage());
    }
  }


  public void deletePhaseClicked()
  {
    int index;
    if(viewSwing != null)
      index = viewSwing.getSelectedPhaseIndex();
    else
      index = viewFX.getSelectedPhaseIndex();
    try
    {
      model.deletePhase(projectPhases.get(index));
    }
    catch(Exception e)
    {
      e.printStackTrace();
      if(viewSwing != null)
        viewSwing.showError(e.getLocalizedMessage());
      else
        viewFX.showError(e.getLocalizedMessage());
    }
  }


  public void addPhaseClicked()
  {
    if(viewSwing != null)
      viewSwing.showAddPhaseDialog();
    else
      viewFX.showAddPhaseDialog();
  }


  public void deleteMemberClicked()
  {
    int index;
    if(viewSwing != null)
      index = viewSwing.getSelectedMemberIndex();
    else
      index = viewFX.getSelectedMemberIndex();

    try
    {
      model.deleteMember(projectMembers.get(index));
    }
    catch(Exception e)
    {
      e.printStackTrace();
      if(viewSwing != null)
        viewSwing.showError(e.getLocalizedMessage());
      else
        viewFX.showError(e.getLocalizedMessage());
    }
  }


  public void promoteToAdminClicked()
  {
    int index;
    if(viewSwing != null)
      index = viewSwing.getSelectedMemberIndex();
    else
      index = viewFX.getSelectedMemberIndex();
    try
    {
      model.promoteToLeader(projectMembers.get(index));
    }
    catch(Exception e)
    {
      e.printStackTrace();
      if(viewSwing != null)
        viewSwing.showError(e.getLocalizedMessage());
      else
        viewFX.showError(e.getLocalizedMessage());
    }
  }


  public void degradeToMemberClicked()
  {

    try
    {
      int index;
      if(viewSwing != null)
        index = viewSwing.getSelectedMemberIndex();
      else
        index = viewFX.getSelectedMemberIndex();

      model.degradeToMember(projectMembers.get(index));
    }
    catch(Exception e)
    {
      e.printStackTrace();
      if(viewSwing != null)
        viewSwing.showError(e.getLocalizedMessage());
      else
        viewFX.showError(e.getLocalizedMessage());
    }
  }


  public void addMemberClicked()
  {
    try
    {
      availableMembers = model.getAvailableUsersFor(detailProject.getId());

      ArrayList<String> availableString = new ArrayList<>();

      for(User u : availableMembers)
      {
        availableString.add(u.getFirstName() + " " + u.getLastName());
      }
      if(viewSwing != null)
        viewSwing.showAddMemberDialog(availableString);
      else
        viewFX.showAddMemberDialog(availableString);

    }
    catch(Exception e)
    {
      e.printStackTrace();
      if(viewSwing != null)
        viewSwing.showError(e.getLocalizedMessage());
      else
        viewFX.showError(e.getLocalizedMessage());
    }

  }


  public void addPhase(String phaseName)
  {
    try
    {
      assert (detailProject != null);
      model.addPhase(detailProject, phaseName);
    }
    catch(Exception e)
    {
      e.printStackTrace();
      if(viewSwing != null)
        viewSwing.showError(e.getLocalizedMessage());
      else
        viewFX.showError(e.getLocalizedMessage());
    }
  }


  public void addMembers(int[] selectedIndices)
  {
    try
    {
      ArrayList<User> toAdd = new ArrayList<>();
      for(int i : selectedIndices)
      {
        toAdd.add(availableMembers.get(i));
      }
      availableMembers = null;

      model.addMembersToProject(toAdd, detailProject);
    }
    catch(Exception e)
    {
      e.printStackTrace();
      if(viewSwing != null)
        viewSwing.showError(e.getLocalizedMessage());
      else
        viewFX.showError(e.getLocalizedMessage());

    }

  }


  public void updateDescriptionClicked()
  {
    String newDescription;
    if(viewSwing != null)
      newDescription = viewSwing.getDescription();
    else
      newDescription = viewFX.getDescription();

    detailProject.setDescription(newDescription);
    try
    {
      model.updateProject(detailProject);
    }
    catch(Exception e)
    {
      e.printStackTrace();
      if(viewSwing != null)
        viewSwing.showError(e.getLocalizedMessage());
      else
        viewFX.showError(e.getLocalizedMessage());
    }
  }


  public void projectPhaseHasSelection(boolean hasSelection)
  {
    if(viewSwing != null)
      viewSwing.setProjectPhaseButtonsEnabled(hasSelection);
    else
      viewFX.setProjectPhaseButtonsEnabled(hasSelection);
  }


  public void memberTableHasSelection(boolean hasSelection)
  {
    if(viewSwing != null)
      viewSwing.setMemberListButtonsEnabled(hasSelection);
    else
      viewFX.setMemberListButtonsEnabled(hasSelection);
  }

}
