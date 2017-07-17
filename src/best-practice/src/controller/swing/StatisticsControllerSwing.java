package controller.swing;

import controller.interfaces.StatisticsController;
import data.Project;
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
        ArrayList<Duration> durations = new ArrayList<>();
        ArrayList<Project> projects= new ArrayList<>();

        try {
            model.getProjectsAndWorkload(projects, durations);
            currentProjects = projects;
            ArrayList<String> projectNames = new ArrayList<>();
            for(Project p : projects)
                projectNames.add(p.getName());

            view.setProjectList(projectNames, durations);
        } catch (Exception e) {
            view.showError(e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
}
