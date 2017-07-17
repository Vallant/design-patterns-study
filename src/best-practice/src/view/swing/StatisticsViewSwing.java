package view.swing;

import controller.interfaces.StatisticsController;
import view.interfaces.StatisticsView;

import javax.swing.*;
import java.awt.*;
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
    }

    @Override
    public void showOverview() {
        frame.remove(pDetail);
        frame.add(pMain, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
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
    public void setProjectList(ArrayList<String> projectNames, ArrayList<Duration> durations) {
        pMain.tblProjectsModel.setFirstColumnContent(projectNames);
        pMain.tblProjectsModel.setWorkloadContent(durations);
    }
}
