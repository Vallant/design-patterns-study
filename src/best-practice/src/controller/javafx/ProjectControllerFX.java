package controller.javafx;

import controller.interfaces.ProjectController;
import data.Project;
import data.ProjectMember;
import data.ProjectPhase;
import model.interfaces.ProjectModel;
import view.interfaces.ProjectView;

import java.util.ArrayList;

public class ProjectControllerFX implements ProjectController
{
  @Override
  public void setModel(ProjectModel model)
  {

  }

  @Override
  public void setView(ProjectView view)
  {

  }

  @Override
  public void refresh()
  {

  }

  @Override
  public void leaveProjectClicked()
  {

  }

  @Override
  public void addProjectClicked()
  {

  }

  @Override
  public void deleteProjectClicked()
  {

  }

  @Override
  public void doubleClickedOn(int index)
  {

  }

  @Override
  public void backClicked()
  {

  }

  @Override
  public void deletePhaseClicked()
  {

  }

  @Override
  public void addPhaseClicked()
  {

  }

  @Override
  public void deleteMemberClicked()
  {

  }

  @Override
  public void promoteToAdminClicked()
  {

  }

  @Override
  public void degradeToMemberClicked()
  {

  }

  @Override
  public void addMemberClicked()
  {

  }

  @Override
  public void updateDescriptionClicked()
  {

  }

  @Override
  public void ownedProjectsHasSelection(boolean hasSelection)
  {

  }

  @Override
  public void involvedProjectsHasSelection(boolean hasSelection)
  {

  }

  @Override
  public void projectPhaseHasSelection(boolean hasSelection)
  {

  }

  @Override
  public void memberTableHasSelection(boolean hasSelection)
  {

  }

  @Override
  public void showDetail(Project project, ArrayList<ProjectPhase> phases, ArrayList<ProjectMember> members)
  {

  }

  @Override
  public void addPhase(String phaseName)
  {

  }

  @Override
  public void addMembers(int[] selectedIndices)
  {

  }

  @Override
  public void addProject(String name, String description)
  {

  }
}
