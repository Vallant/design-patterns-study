package view.swing.projectstatistic;

import controller.standard.ProjectStatisticControllerStandard;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class ProjectStatisticViewSwing
{
  private ProjectStatisticControllerStandard controller;


  private final JFrame                        frame;
  private final ProjectStatisticProjectPanel  pProject;
  private final ProjectStatisticPhasePanel    pPhase;
  private final ProjectStatisticActivityPanel pActivity;

  public ProjectStatisticViewSwing(JFrame frame)
  {
    this.frame = frame;

    pProject = new ProjectStatisticProjectPanel();
    pPhase = new ProjectStatisticPhasePanel();
    pActivity = new ProjectStatisticActivityPanel();

    setListener();
  }

  private void setListener()
  {
    pProject.cbPeriod.addActionListener(
      actionEvent -> controller.projectPeriodChanged(pProject.cbPeriod.getSelectedIndex()));
    pPhase.cbPeriod.addActionListener(actionEvent -> controller.phaseDropDownChanged(pPhase.cbPeriod.getSelectedIndex(),
      pPhase.cbMembers.getSelectedIndex()));
    pPhase.cbMembers.addActionListener(actionEvent -> controller.phaseDropDownChanged(pPhase.cbPeriod.getSelectedIndex(),
      pPhase.cbMembers.getSelectedIndex()));

    pActivity.cbPeriod.addActionListener(actionEvent -> controller.activityDropDownChanged(
      pActivity.cbPeriod.getSelectedIndex(),
      pActivity.cbMembers.getSelectedIndex()));
    pActivity.cbMembers.addActionListener(actionEvent -> controller.activityDropDownChanged(
      pActivity.cbPeriod.getSelectedIndex(),
      pActivity.cbMembers.getSelectedIndex()));

    pProject.tblProjects.addMouseListener(new MouseAdapter()
    {

      public void mouseClicked(MouseEvent mouseEvent)
      {
        JTable tbl = (JTable) mouseEvent.getSource();
        if(mouseEvent.getClickCount() == 2)
        {
          int index = tbl.rowAtPoint(mouseEvent.getPoint());
          controller.doubleClickOnProject(index);
        }
      }
    });

    pPhase.btBack.addActionListener(actionEvent -> controller.backToProjectClicked());
    pPhase.tblPhases.addMouseListener(new MouseAdapter()
    {

      public void mouseClicked(MouseEvent mouseEvent)
      {
        JTable tbl = (JTable) mouseEvent.getSource();
        if(mouseEvent.getClickCount() == 2)
        {
          int index = tbl.rowAtPoint(mouseEvent.getPoint());
          controller.doubleClickOnPhase(index);
        }
      }
    });

    pActivity.btBack.addActionListener(actionEvent -> controller.backToPhaseClicked());
  }


  public void setController(ProjectStatisticControllerStandard controller)
  {
    this.controller = controller;
  }


  public void hide()
  {
    frame.remove(pActivity);
    frame.remove(pPhase);
    frame.remove(pProject);
  }


  public void removeAllComponents()
  {
    frame.remove(pActivity);
    frame.remove(pPhase);
    frame.remove(pProject);
  }


  public void showProjectView()
  {
    frame.remove(pActivity);
    frame.remove(pPhase);
    frame.add(pProject, BorderLayout.CENTER);
    frame.revalidate();
    frame.repaint();
  }


  public void setProjectData(ArrayList<String> projectNames, ArrayList<Duration> durations)
  {
    pProject.tblProjectsModel.setFirstColumnContent(projectNames);
    pProject.tblProjectsModel.setWorkloadContent(durations);
    pProject.tblProjects.updateUI();
  }


  public void showPhaseView()
  {
    frame.remove(pActivity);
    frame.add(pPhase, BorderLayout.CENTER);
    frame.remove(pProject);
    frame.revalidate();
    frame.repaint();
  }


  public void setPhaseData(ArrayList<String> phaseNames, ArrayList<Duration> durations, ArrayList<String> memberNames)
  {
    pPhase.tblPhaseModel.setFirstColumnContent(phaseNames);
    pPhase.tblPhaseModel.setWorkloadContent(durations);
    pPhase.setMemberNames(memberNames);
    frame.revalidate();
    frame.repaint();
  }


  public void showActivityView()
  {
    frame.add(pActivity, BorderLayout.CENTER);
    frame.remove(pPhase);
    frame.remove(pProject);
    frame.revalidate();
    frame.repaint();
  }


  public void setActivityData(ArrayList<String> users,
                              ArrayList<String> descriptions,
                              ArrayList<String> comments,
                              ArrayList<ZonedDateTime> startTimes,
                              ArrayList<ZonedDateTime> endTimes,
                              ArrayList<String> memberNames)
  {
    pActivity.tblActivityModel.setValues(users, descriptions, comments, startTimes, endTimes);
    pActivity.setMembers(memberNames);
  }


  public int getSelectedPhasePeriod()
  {
    return pPhase.cbPeriod.getSelectedIndex();
  }


  public int getSelectedActivityPeriod()
  {
    return pActivity.cbPeriod.getSelectedIndex();
  }


  public int getSelectedProjectPeriod()
  {
    return pProject.cbPeriod.getSelectedIndex();
  }


  public int getSelectedUser()
  {
    throw new NotImplementedException();
  }


  public boolean confirmDeletion()
  {
    throw new NotImplementedException();
  }


  public void showError(String error)
  {
    JOptionPane.showMessageDialog(frame, error, "Error", JOptionPane.ERROR_MESSAGE);
  }
}
