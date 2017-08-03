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
    private final PersonalStatisticProjectPanel pMain;
    private final PersonalStatisticPhasePanel pProjectDetail;
    private final PersonalStatisticActivityPanel pPhaseDetail;

    public PersonalStatisticViewSwing(JFrame frame) {
        this.frame = frame;
        pMain = new PersonalStatisticProjectPanel();
        pProjectDetail = new PersonalStatisticPhasePanel();
        pPhaseDetail = new PersonalStatisticActivityPanel();

        setListeners();
    }

    private void setListeners() {
        pMain.cbPeriod.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.projectPeriodChanged(pMain.cbPeriod.getSelectedIndex());
            }
        });
        pProjectDetail.cbPeriod.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.phasePeriodChanged(pMain.cbPeriod.getSelectedIndex());
            }
        });

        pPhaseDetail.cbPeriod.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.activityPeriodChanged(pMain.cbPeriod.getSelectedIndex());
            }
        });

        pMain.tblProjects.addMouseListener(new MouseAdapter() {
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

        pProjectDetail.btBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.backToOverviewClicked();
            }
        });
        pProjectDetail.tblPhases.addMouseListener(new MouseAdapter() {
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

        pPhaseDetail.btAddActivity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.addActivityClicked();
            }
        });
        pPhaseDetail.btDeleteActivity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.deleteActivityClicked();
            }
        });

        pPhaseDetail.btUpdateActivity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.updateActivityClicked();
            }
        });

        pPhaseDetail.btBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.backToProjectDetailClicked();
            }
        });
    }

    @Override
    public void setProjectData(ArrayList<String> projectNames, ArrayList<Duration> durations) {

        pMain.tblProjectsModel.setFirstColumnContent(projectNames);
        pMain.tblProjectsModel.setWorkloadContent(durations);
        pMain.tblProjects.updateUI();
    }

    @Override
    public void setPhaseData(ArrayList<String> phaseNames, ArrayList<Duration> durations) {
        pProjectDetail.tblProjectsModel.setFirstColumnContent(phaseNames);
        pProjectDetail.tblProjectsModel.setWorkloadContent(durations);
        pProjectDetail.tblPhases.updateUI();
    }

    @Override
    public void showActivityView() {
        frame.remove(pProjectDetail);
        frame.remove(pMain);
        frame.add(pPhaseDetail);
        frame.revalidate();
        frame.repaint();

    }

    @Override
    public void setActivityData(ArrayList<String> descriptions, ArrayList<String> comments, ArrayList<ZonedDateTime> startTimes, ArrayList<ZonedDateTime> endTimes) {
        pPhaseDetail.tblActivityModel.setValues(descriptions, comments, startTimes, endTimes);
    }

    @Override
    public void setController(PersonalStatisticController controller) {
        this.controller = controller;
    }

    @Override
    public void RemoveAllComponents() {
        frame.remove(pProjectDetail);
        frame.remove(pMain);
        frame.remove(pPhaseDetail);
        frame.revalidate();
        frame.repaint();
    }

    @Override
    public void showError(String localizedMessage) {
        JOptionPane.showMessageDialog(frame, localizedMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }


    @Override
    public void showProjectView() {

        frame.remove(pProjectDetail);
        frame.remove(pPhaseDetail);
        frame.add(pMain, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    @Override
    public void showPhaseView() {
        frame.remove(pMain);
        frame.remove(pPhaseDetail);
        frame.add(pProjectDetail, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    @Override
    public int getSelectedProjectPeriod() {
        return pMain.cbPeriod.getSelectedIndex();
    }

    @Override
    public int getSelectedActivity() {
        return pPhaseDetail.tblActivity.getSelectedRow();
    }

    @Override
    public void hide() {
        frame.remove(pProjectDetail);
        frame.remove(pMain);
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
        return pProjectDetail.cbPeriod.getSelectedIndex();
    }

    @Override
    public int getSelectedActivityPeriod() {
        return pPhaseDetail.cbPeriod.getSelectedIndex();
    }
}
