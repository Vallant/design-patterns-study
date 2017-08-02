package controller.swing;

import controller.interfaces.StatisticsController;
import data.Project;
import data.ProjectPhase;
import model.interfaces.StatisticsModel;
import view.interfaces.StatisticsView;

import java.time.Duration;
import java.util.ArrayList;

/**
 * Created by stephan on 17/07/17.
 */
public class StatisticsControllerSwing implements StatisticsController {
    private StatisticsModel model;
    private StatisticsView view;

    private ArrayList<Project> currentProjects;
    private ArrayList<ProjectPhase> currentPhases;
    private Project detailProject;

    public StatisticsControllerSwing() {

    }

    @Override
    public void setModel(StatisticsModel model) {
        this.model = model;
    }

    @Override
    public void setView(StatisticsView view) {
        this.view = view;
    }

    @Override
    public void refresh() {
        try {
            if(detailProject != null)
                model.phasePeriodChanged(detailProject.getId(), view.getSelectedProjectPeriod());
            else
                model.projectPeriodChanged(view.getSelectedProjectPeriod());

        } catch (Exception e) {
            view.showError(e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void phasePeriodChanged(int selectedIndex) {
        try {
            assert(detailProject != null);
            model.phasePeriodChanged(detailProject.getId(), selectedIndex);
        } catch (Exception e) {
            view.showError(e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void projectPeriodChanged(int selectedIndex) {
        try {
            model.projectPeriodChanged(selectedIndex);
        } catch (Exception e) {
            view.showError(e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void doubleClickOnRow(int index) {
        detailProject = currentProjects.get(index);
        try {
            model.requestedDetailFor(detailProject);
        } catch (Exception e) {
            view.showError(e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void setOverviewData(ArrayList<Project> projects, ArrayList<Duration> durations) {
        currentProjects = projects;
        detailProject = null;
        currentPhases = null;
        ArrayList<String> projectNames = new ArrayList<>();
        for(Project p : projects)
            projectNames.add(p.getName());
        view.setOverviewData(projectNames, durations);
    }

    @Override
    public void setDetailData(ArrayList<ProjectPhase> phases, ArrayList<Duration> durations) {
        currentPhases = phases;
        ArrayList<String> phaseNames = new ArrayList<>();
        for(ProjectPhase pp : phases)
            phaseNames.add(pp.getName());

        view.setOverviewData(phaseNames, durations);
    }

    @Override
    public void showOverview() {
        view.showOverview();
    }

    @Override
    public void showDetail() {
        view.showDetail();
    }

    @Override
    public void backClicked() {
        showOverview();
    }
}
