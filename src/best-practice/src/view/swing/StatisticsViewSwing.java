package view.swing;

import controller.interfaces.StatisticsController;
import view.interfaces.StatisticsView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;

/**
 * Created by stephan on 17/07/17.
 */
public class StatisticsViewSwing implements StatisticsView {

    private final JFrame frame;
    private StatisticsController controller;
    private final StatisticsPanel pMain;
    private final StatisticsProjectDetailPanel pDetail;
    private final StatisticsPhaseDetailPanel pPhaseDetail;

    public StatisticsViewSwing(JFrame frame) {
        this.frame = frame;
        pMain = new StatisticsPanel();
        pDetail = new StatisticsProjectDetailPanel();
        pPhaseDetail = new StatisticsPhaseDetailPanel();

        setListeners();
    }

    private void setListeners() {
        pMain.cbPeriod.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.projectPeriodChanged(pMain.cbPeriod.getSelectedIndex());
            }
        });
        pDetail.cbPeriod.addActionListener(new ActionListener() {
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

        pDetail.btBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.backClicked();
            }
        });
        pDetail.tblPhases.addMouseListener(new MouseAdapter() {
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
    }

    @Override
    public void setOverviewData(ArrayList<String> projectNames, ArrayList<Duration> durations) {

        pMain.tblProjectsModel.setFirstColumnContent(projectNames);
        pMain.tblProjectsModel.setWorkloadContent(durations);
        pMain.tblProjects.updateUI();
    }

    @Override
    public void setDetailData(ArrayList<String> phaseNames, ArrayList<Duration> durations) {
        pDetail.tblProjectsModel.setFirstColumnContent(phaseNames);
        pDetail.tblProjectsModel.setWorkloadContent(durations);
        pDetail.tblPhases.updateUI();
    }

    @Override
    public void showPhaseDetail() {
        frame.remove(pDetail);
        frame.remove(pMain);
        frame.add(pPhaseDetail);
        frame.revalidate();
        frame.repaint();

    }

    @Override
    public void setPhaseDetailData(ArrayList<String> descriptions, ArrayList<String> comments, ArrayList<ZonedDateTime> startTimes, ArrayList<ZonedDateTime> endTimes) {
        pPhaseDetail.tblActivityModel.setValues(descriptions, comments, startTimes, endTimes);
    }

    @Override
    public void setController(StatisticsController controller) {
        this.controller = controller;
    }

    @Override
    public void RemoveAllComponents() {
        frame.remove(pDetail);
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
    public void showOverview() {

        frame.remove(pDetail);
        frame.remove(pPhaseDetail);
        frame.add(pMain, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    @Override
    public void showDetail() {
        frame.remove(pMain);
        frame.remove(pPhaseDetail);
        frame.add(pDetail, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    @Override
    public int getSelectedProjectPeriod() {
        return pMain.cbPeriod.getSelectedIndex();
    }

    @Override
    public void hide() {
        frame.remove(pDetail);
        frame.remove(pMain);
    }
}
