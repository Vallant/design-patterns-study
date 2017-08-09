/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.swing.project;

import controller.interfaces.ProjectController;
import view.interfaces.ProjectView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * @author stephan
 */
public class ProjectViewSwing implements ProjectView
{
  private final JFrame            frame;
  private final ProjectViewPanel       pMain;
  private final ProjectDetailViewPanel pDetail;
  private       ProjectController controller;


  public ProjectViewSwing(JFrame frame)
  {
    this.frame = frame;
    pMain = new ProjectViewPanel();
    pDetail = new ProjectDetailViewPanel();
    setListener();
  }

  private void setListener()
  {
    pMain.btLeaveProject.addActionListener(actionEvent -> controller.leaveProjectClicked());
    pMain.btAddProject.addActionListener(actionEvent -> controller.addProjectClicked());
    pMain.btDeleteProject.addActionListener(actionEvent -> controller.deleteProjectClicked());

    pMain.lstOwned.addListSelectionListener(listSelectionEvent ->
    {
      int index = listSelectionEvent.getFirstIndex();
      controller.ownedProjectsHasSelection(index != -1);
    });

    pMain.lstInvolved.addListSelectionListener(listSelectionEvent ->
    {
      int index = listSelectionEvent.getFirstIndex();
      controller.involvedProjectsHasSelection(index != -1);
    });

    pMain.lstOwned.addMouseListener(new MouseAdapter()
    {
      @Override
      public void mouseClicked(MouseEvent evt)
      {
        JList list = (JList) evt.getSource();
        if(evt.getClickCount() == 2)
        {
          int index = list.locationToIndex(evt.getPoint());
          controller.doubleClickedOn(index);
        }
      }
    });

    pDetail.btBack.addActionListener(actionEvent -> controller.backClicked());

    pDetail.btAddPhase.addActionListener(actionEvent -> controller.addPhaseClicked());
    pDetail.btDeletePhase.addActionListener(actionEvent -> controller.deletePhaseClicked());

    pDetail.btAddMember.addActionListener(actionEvent -> controller.addMemberClicked());

    pDetail.btDeleteMember.addActionListener(actionEvent -> controller.deleteMemberClicked());


    pDetail.btPromoteToAdmin.addActionListener(actionEvent -> controller.promoteToAdminClicked());

    pDetail.btDegradeToMember.addActionListener(actionEvent -> controller.degradeToMemberClicked());

    pDetail.btUpdateDescription.addActionListener(actionEvent -> controller.updateDescriptionClicked());

    pDetail.lstPhases.addListSelectionListener(
      listSelectionEvent -> controller.projectPhaseHasSelection(listSelectionEvent.getFirstIndex() != -1));

    pDetail.lstMembers.getSelectionModel().addListSelectionListener(
      listSelectionEvent -> controller.memberTableHasSelection(listSelectionEvent.getFirstIndex() != -1));
  }

  @Override
  public void setController(ProjectController controller)
  {
    this.controller = controller;
  }

  @Override
  public void showOverview()
  {
    frame.remove(pDetail);
    frame.add(pMain, BorderLayout.CENTER);
    if(pMain.lstInvolved.getSelectedIndex() == -1)
      pMain.btLeaveProject.setEnabled(false);
    if(pMain.lstOwned.getSelectedIndex() == -1)
      pMain.btDeleteProject.setEnabled(false);
    frame.revalidate();
    frame.repaint();
  }

  @Override
  public void showDetail(String projectName, ArrayList<String> phases, ArrayList<String> members,
                         ArrayList<String> roles, String description)
  {
    frame.remove(pMain);
    pDetail.setProjectName(projectName);
    pDetail.setPhases(phases);
    pDetail.setMemberInformation(members, roles);
    pDetail.setDescription(description);
    frame.add(pDetail, BorderLayout.CENTER);
    setMemberListButtonsEnabled(pDetail.lstMembers.getSelectedIndex() != -1);
    setProjectPhaseButtonsEnabled(pDetail.lstPhases.getSelectedIndex() != -1);


    frame.pack();
    frame.revalidate();
    frame.repaint();

  }

  @Override
  public void hide()
  {
    frame.remove(pMain);
    frame.remove(pDetail);
  }

  @Override
  public void setParticipatingProjects(ArrayList<String> participatingProjects)
  {
    pMain.lstInvolvedModel.clear();
    for(String s : participatingProjects)
    {
      pMain.lstInvolvedModel.addElement(s);
    }
  }

  @Override
  public void setOwnedProjects(ArrayList<String> ownedProjects)
  {
    pMain.lstOwnedModel.clear();
    for(String s : ownedProjects)
    {
      pMain.lstOwnedModel.addElement(s);
    }
  }

  @Override
  public void showError(String localizedMessage)
  {
    JOptionPane.showMessageDialog(frame, localizedMessage, "Error", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void showProjectCreationDialog()
  {
    ProjectAddDialogPanel dialogPanel = new ProjectAddDialogPanel();

    int selection = JOptionPane.showConfirmDialog(
      null, dialogPanel, "Create new Project: "
      , JOptionPane.OK_CANCEL_OPTION
      , JOptionPane.PLAIN_MESSAGE);

    if(selection == JOptionPane.OK_OPTION)
    {
      controller.addProject(dialogPanel.tfName.getText(), dialogPanel.tfDescription.getText());
    }
  }

  @Override
  public void setOwnedProjectsButtonsEnabled(boolean enabled)
  {
    pMain.btDeleteProject.setEnabled(enabled);
  }

  @Override
  public void setInvolvedProjectsButtonsEnabled(boolean enabled)
  {
    pMain.btLeaveProject.setEnabled(enabled);
  }


  @Override
  public int getSelectedOwnedProjectIndex()
  {
    return pMain.lstOwned.getSelectedIndex();
  }

  @Override
  public int getSelectedInvolvedProjectIndex()
  {
    return pMain.lstInvolved.getSelectedIndex();
  }

  @Override
  public int getSelectedPhaseIndex()
  {
    return pDetail.lstPhases.getSelectedIndex();
  }

  @Override
  public void showAddMemberDialog(ArrayList<String> names)
  {
    ProjectDetailAddMemberPanel dialogPanel = new ProjectDetailAddMemberPanel();
    dialogPanel.setAvailableNames(names);

    int selection = JOptionPane.showConfirmDialog(
      null, dialogPanel, "Add Members to Project"
      , JOptionPane.OK_CANCEL_OPTION
      , JOptionPane.PLAIN_MESSAGE);

    if(selection == JOptionPane.OK_OPTION)
    {
      controller.addMembers(dialogPanel.lstAvailableUsers.getSelectedIndices());
    }
  }

  @Override
  public String getDescription()
  {
    return pDetail.taDescription.getText();
  }

  @Override
  public void setProjectPhaseButtonsEnabled(boolean hasSelection)
  {
    pDetail.btDeletePhase.setEnabled(hasSelection);
  }

  @Override
  public void setMemberListButtonsEnabled(boolean hasSelection)
  {
    pDetail.btDegradeToMember.setEnabled(hasSelection);
    pDetail.btPromoteToAdmin.setEnabled(hasSelection);
    pDetail.btDeleteMember.setEnabled(hasSelection);
  }

  @Override
  public int getSelectedMemberIndex()
  {
    return pDetail.lstMembers.getSelectedIndex();
  }

  @Override
  public void showAddPhaseDialog()
  {
    ProjectAddPhasePanel dialogPanel = new ProjectAddPhasePanel();

    int selection = JOptionPane.showConfirmDialog(
      null, dialogPanel, "Add Phase"
      , JOptionPane.OK_CANCEL_OPTION
      , JOptionPane.PLAIN_MESSAGE);

    if(selection == JOptionPane.OK_OPTION)
    {
      controller.addPhase(dialogPanel.tfName.getText());
    }
  }

}
