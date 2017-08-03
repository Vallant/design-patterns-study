package view.swing.personalstatistic;

import controller.interfaces.PersonalStatisticController;
import view.interfaces.PersonalStatisticView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;

/**
 * Created by stephan on 17/07/17.
 */
public class PersonalStatisticViewSwing implements PersonalStatisticView {

    private final JFrame frame;
    private PersonalStatisticController controller;
    private final PersonalStatisticProjectPanel pProject;
    private final PersonalStatisticPhasePanel pPhase;
    private final PersonalStatisticActivityPanel pActivity;

    public PersonalStatisticViewSwing(JFrame frame) {
        this.frame = frame;
        pProject = new PersonalStatisticProjectPanel();
        pPhase = new PersonalStatisticPhasePanel();
        pActivity = new PersonalStatisticActivityPanel();

        setListeners();
    }

    private void setListeners() {
        pProject.cbPeriod.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.projectPeriodChanged(pProject.cbPeriod.getSelectedIndex());
            }
        });
        pPhase.cbPeriod.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.phasePeriodChanged(pProject.cbPeriod.getSelectedIndex());
            }
        });

        pActivity.cbPeriod.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.activityPeriodChanged(pProject.cbPeriod.getSelectedIndex());
            }
        });

        pProject.tblProjects.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                JTable tbl = (JTable) mouseEvent.getSource();
                if(mouseEvent.getClickCount() == 2)
                {
                    int index = tbl.rowAtPoint(mouseEvent.getPoint());
                    controller.doubleClickOnProject(index);
                }
            }
        });

        pPhase.btBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.backToOverviewClicked();
            }
        });
        pPhase.tblPhases.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                JTable tbl = (JTable) mouseEvent.getSource();
                if(mouseEvent.getClickCount() == 2)
                {
                    int index = tbl.rowAtPoint(mouseEvent.getPoint());
                    controller.doubleClickOnPhase(index);
                }
            }
        });

        pActivity.btAddActivity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.addActivityClicked();
            }
        });
        pActivity.btDeleteActivity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.deleteActivityClicked();
            }
        });

        pActivity.btUpdateActivity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.updateActivityClicked();
            }
        });

        pActivity.btBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.backToProjectDetailClicked();
            }
        });
    }

    @Override
    public void setProjectData(ArrayList<String> projectNames, ArrayList<Duration> durations) {

        pProject.tblProjectsModel.setFirstColumnContent(projectNames);
        pProject.tblProjectsModel.setWorkloadContent(durations);
        pProject.tblProjects.updateUI();
    }

    @Override
    public void setPhaseData(ArrayList<String> phaseNames, ArrayList<Duration> durations) {
        pPhase.tblProjectsModel.setFirstColumnContent(phaseNames);
        pPhase.tblProjectsModel.setWorkloadContent(durations);
        pPhase.tblPhases.updateUI();
    }

    @Override
    public void showActivityView() {
        frame.remove(pPhase);
        frame.remove(pProject);
        frame.add(pActivity);
        frame.revalidate();
        frame.repaint();

    }

    @Override
    public void setActivityData(ArrayList<String> descriptions, ArrayList<String> comments, ArrayList<ZonedDateTime> startTimes, ArrayList<ZonedDateTime> endTimes) {
        pActivity.tblActivityModel.setValues(descriptions, comments, startTimes, endTimes);
    }

    @Override
    public void setController(PersonalStatisticController controller) {
        this.controller = controller;
    }

    @Override
    public void RemoveAllComponents() {
        frame.remove(pPhase);
        frame.remove(pProject);
        frame.remove(pActivity);
        frame.revalidate();
        frame.repaint();
    }

    @Override
    public void showError(String localizedMessage) {
        JOptionPane.showMessageDialog(frame, localizedMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }


    @Override
    public void showProjectView() {

        frame.remove(pPhase);
        frame.remove(pActivity);
        frame.add(pProject, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    @Override
    public void showPhaseView() {
        frame.remove(pProject);
        frame.remove(pActivity);
        frame.add(pPhase, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    @Override
    public int getSelectedProjectPeriod() {
        return pProject.cbPeriod.getSelectedIndex();
    }

    @Override
    public int getSelectedActivity() {
        return pActivity.tblActivity.getSelectedRow();
    }

    @Override
    public void hide() {
        frame.remove(pPhase);
        frame.remove(pProject);
    }

    @Override
    public void showAddActivityDialog()
    {
        PersonalStatisticUpdateActivityDialogPanel dialogPanel = new PersonalStatisticUpdateActivityDialogPanel();

        int selection = JOptionPane.showConfirmDialog(
                null, dialogPanel, "Input Form : "
                , JOptionPane.OK_CANCEL_OPTION
                , JOptionPane.PLAIN_MESSAGE);

        if (selection == JOptionPane.OK_OPTION)
        {
            controller.addActivity(dialogPanel.tfDescription.getText(), dialogPanel.tfComment.getText(),
                    dialogPanel.dpStartTime.getDatePicker().getDate(),
                    dialogPanel.dpEndTime.getDatePicker().getDate());
        }
    }

    @Override
    public boolean confirmDeletion() {

        return JOptionPane.showConfirmDialog(null, "Are you sure to delete this activity?","Confirmation",
                JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION;
    }

    @Override
    public void showUpdateActivityDialog(String description, String comment, LocalDate start, LocalDate end)
    {
        PersonalStatisticUpdateActivityDialogPanel dialogPanel = new PersonalStatisticUpdateActivityDialogPanel(description, comment, start, end);

        int selection = JOptionPane.showConfirmDialog(
                null, dialogPanel, "Input Form : "
                , JOptionPane.OK_CANCEL_OPTION
                , JOptionPane.PLAIN_MESSAGE);

        if (selection == JOptionPane.OK_OPTION)
        {
            controller.updateActivity(dialogPanel.tfDescription.getText(), dialogPanel.tfComment.getText(),
                    dialogPanel.dpStartTime.getDatePicker().getDate(),
                    dialogPanel.dpEndTime.getDatePicker().getDate());
        }
    }

    @Override
    public int getSelectedPhasePeriod() {
        return pPhase.cbPeriod.getSelectedIndex();
    }

    @Override
    public int getSelectedActivityPeriod() {
        return pActivity.cbPeriod.getSelectedIndex();
    }
}
