/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.swing;

import controller.interfaces.ProjectController;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import view.interfaces.ProjectView;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 *
 * @author stephan
 */
public class ProjectViewSwing implements ProjectView
{
    private final JFrame frame;
    private ProjectController controller;
    
    private final ProjectViewPanel pMain;
    private final ProjectDetailViewPanel pDetail;

    

    ProjectViewSwing(JFrame frame)
    {
        this.frame = frame;
        pMain = new ProjectViewPanel();
        pDetail = new ProjectDetailViewPanel();
        setListener();
    }

    private void setListener() {
        pMain.btLeaveProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.leaveClicked();
            }
        });
        pMain.btAddProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.addClicked();
            }
        });
        pMain.btDeleteProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.deleteClicked();
            }
        });

        pMain.lstOwned.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                int index = listSelectionEvent.getFirstIndex();
                controller.ownedProjectsHasSelection(index != -1);
            }
        });

        pMain.lstInvolved.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                int index = listSelectionEvent.getFirstIndex();
                controller.involvedProjectsHasSelection(index != -1);
            }
        });

        pMain.lstOwned.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList)evt.getSource();
                if (evt.getClickCount() == 2) {
                    int index = list.locationToIndex(evt.getPoint());
                    controller.doubleClickedOn(index);
                }
            }
        });
    }

    @Override
    public void setController(ProjectController controller)
    {
        this.controller = controller;
    }

    @Override
    public void showOverview() {
        frame.remove(pDetail);
        frame.add(pMain, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    @Override
    public void showDetail(String projectName, ArrayList<String> phases, ArrayList<String> members, ArrayList<String> roles, String description) {
        frame.remove(pMain);
        pDetail.setProjectName(projectName);
        pDetail.setPhases(phases);
        pDetail.setMembers(members);
        pDetail.setRoles(roles);
        pDetail.setDescription(description);
        frame.add(pDetail, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    @Override
    public void hide() {
        frame.remove(pMain);
    }

    @Override
    public void setParticipatingProjects(ArrayList<String> participatingProjects) {
        pMain.lstInvolvedModel.clear();
        for(String s : participatingProjects)
            pMain.lstInvolvedModel.addElement(s);
    }

    @Override
    public void setOwnedProjects(ArrayList<String> ownedProjects) {
        pMain.lstOwnedModel.clear();
        for(String s : ownedProjects)
            pMain.lstOwnedModel.addElement(s);
    }

    @Override
    public void showError(String localizedMessage) {
        JOptionPane.showMessageDialog(frame, localizedMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public String getSelectedOwnedProject() {
        return pMain.lstOwned.getSelectedValue();
    }

    @Override
    public void showProjectCreationDialog() {

    }

    @Override
    public void setOwnedProjectsButtonsEnabled(boolean enabled) {
        pMain.btDeleteProject.setEnabled(enabled);
        pMain.btLeaveProject.setEnabled(enabled);
    }

    @Override
    public void setInvolvedProjectsButtonsEnabled(boolean enabled)
    {
        pMain.btLeaveProject.setEnabled(enabled);
    }

    @Override
    public String getSelectedInvolvedProject() {
        return pMain.lstInvolved.getSelectedValue();
    }

    @Override
    public int getSelectedOwnedProjectIndex() {
        return pMain.lstOwned.getSelectedIndex();
    }

    @Override
    public int getSelectedInvolvedProjectIndex() {
        return pMain.lstInvolved.getSelectedIndex();
    }

}
