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
import java.util.ArrayList;

/**
 * Created by stephan on 17/07/17.
 */
public class StatisticsViewSwing implements StatisticsView {

    private final JFrame frame;
    private StatisticsController controller;
    private final StatisticsPanel pMain;
    private final StatisticsDetailPanel pDetail;

    public StatisticsViewSwing(JFrame frame) {
        this.frame = frame;
        pMain = new StatisticsPanel();
        pDetail = new StatisticsDetailPanel();

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

        pMain.tblProjects.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                JTable tbl = (JTable) mouseEvent.getSource();
                if(mouseEvent.getClickCount() == 2)
                {
                    int index = tbl.rowAtPoint(mouseEvent.getPoint());
                    controller.doubleClickOnRow(index);
                }
            }
        });

        pDetail.btBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.backClicked();
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
        pDetail.tblProjects.updateUI();
    }

    @Override
    public void setController(StatisticsController controller) {
        this.controller = controller;
    }

    @Override
    public void RemoveAllComponents() {
        frame.remove(pDetail);
        frame.remove(pMain);
    }

    @Override
    public void showError(String localizedMessage) {
        JOptionPane.showMessageDialog(frame, localizedMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }


    @Override
    public void showOverview() {

        frame.remove(pDetail);
        frame.add(pMain, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    @Override
    public void showDetail() {
        frame.remove(pMain);
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
