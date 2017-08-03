package view.swing.projectstatistic;

import controller.interfaces.ProjectStatisticController;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import view.interfaces.ProjectStatisticView;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class ProjectStatisticViewSwing implements ProjectStatisticView {
    private ProjectStatisticController controller;


    private final JFrame frame;
    private final ProjectStatisticProjectPanel pProject;
    private final ProjectStatisticPhasePanel pPhase;
    private final ProjectStatisticActivityPanel pActivity;

    public ProjectStatisticViewSwing(JFrame frame) {
        this.frame = frame;

        pProject = new ProjectStatisticProjectPanel();
        pPhase = new ProjectStatisticPhasePanel();
        pActivity = new ProjectStatisticActivityPanel();
    }

    @Override
    public void setController(ProjectStatisticController controller) {
        this.controller = controller;
    }

    @Override
    public void hide() {
        frame.remove(pActivity);
        frame.remove(pPhase);
        frame.remove(pProject);
    }

    @Override
    public void removeAllComponents() {
        frame.remove(pActivity);
        frame.remove(pPhase);
        frame.remove(pProject);
    }

    @Override
    public void showProjectView() {
        frame.remove(pActivity);
        frame.remove(pPhase);
        frame.add(pProject, BorderLayout.CENTER);
    }

    @Override
    public void setProjectData(ArrayList<String> projectNames, ArrayList<Duration> durations) {
        throw new NotImplementedException();
    }

    @Override
    public void showPhaseView() {
        frame.remove(pActivity);
        frame.add(pPhase, BorderLayout.CENTER);
        frame.remove(pProject);
    }

    @Override
    public void setPhaseData(ArrayList<String> phaseNames, ArrayList<Duration> durations) {
        throw new NotImplementedException();
    }

    @Override
    public void showActivityView() {
        frame.add(pActivity, BorderLayout.CENTER);
        frame.remove(pPhase);
        frame.remove(pProject);
    }

    @Override
    public void setActivityData(ArrayList<String> users, ArrayList<String> descriptions, ArrayList<String> comments, ArrayList<ZonedDateTime> startTimes, ArrayList<ZonedDateTime> endTimes) {
        throw new NotImplementedException();
    }

    @Override
    public int getSelectedPhasePeriod() {
        return pPhase.cbPeriod.getSelectedIndex();
    }

    @Override
    public int getSelectedActivityPeriod() {
        return pActivity.cbPeriod.getSelectedIndex();
    }

    @Override
    public int getSelectedProjectPeriod() {
        return pProject.cbPeriod.getSelectedIndex();
    }

    @Override
    public int getSelectedUserPeriod() {
        throw new NotImplementedException();
    }

    @Override
    public boolean confirmDeletion() {
        throw new NotImplementedException();
    }

    @Override
    public void showError(String error) {
        throw new NotImplementedException();
    }
}
