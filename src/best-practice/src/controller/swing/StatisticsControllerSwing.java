package controller.swing;

import controller.interfaces.StatisticsController;
import data.Activity;
import data.Project;
import data.ProjectPhase;
import model.interfaces.StatisticsModel;
import view.interfaces.StatisticsView;

import java.time.*;
import java.util.ArrayList;

/**
 * Created by stephan on 17/07/17.
 */
public class StatisticsControllerSwing implements StatisticsController {
    private StatisticsModel model;
    private StatisticsView view;

    private ArrayList<Project> currentProjects;
    private ArrayList<ProjectPhase> currentPhases;
    private ArrayList<Activity> currentActivities;
    private Project detailProject;
    private ProjectPhase detailPhase;

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
    public void doubleClickOnProject(int index) {
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

        view.setDetailData(phaseNames, durations);
    }

    @Override
    public void showOverview() {
        view.showOverview();
    }

    @Override
    public void showProjectDetail() {
        view.showDetail();
    }

    @Override
    public void backToOverviewClicked() {
        showOverview();
    }

    @Override
    public void doubleClickOnPhase(int index) {
        detailPhase = currentPhases.get(index);
        try {
            model.requestedDetailFor(detailPhase);
        } catch (Exception e) {
            view.showError(e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void activityPeriodChanged(int selectedIndex) {
        try {
            assert(detailPhase != null);
            model.activityPeriodChanged(detailPhase.getId(), selectedIndex);
        } catch (Exception e) {
            view.showError(e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void setPhaseDetailData(ArrayList<Activity> activities)
    {
        currentActivities = activities;
        ArrayList<String> descriptions = new ArrayList<>();
        ArrayList<String> comments = new ArrayList<>();
        ArrayList<ZonedDateTime> startTimes = new ArrayList<>();
        ArrayList<ZonedDateTime> endTimes = new ArrayList<>();
        for(Activity a : activities)
        {
            descriptions.add(a.getDescription());
            comments.add(a.getComments());
            startTimes.add(a.getStart());
            endTimes.add(a.getStop());
        }
        view.setPhaseDetailData(descriptions, comments, startTimes, endTimes);


    }

    @Override
    public void showPhaseDetail() {
        view.showPhaseDetail();
    }

    @Override
    public void addActivityClicked() {
        view.showAddActivityDialog();
    }

    @Override
    public void deleteActivityClicked()
    {
        if(view.confirmDeletion())
        {
            Activity toDelete = currentActivities.get(view.getSelectedActivity());
            try {
                model.deleteActivity(toDelete);
            } catch (Exception e) {
                view.showError(e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateActivityClicked()
    {
        Activity a = currentActivities.get(view.getSelectedActivity());
        view.showUpdateActivityDialog(a.getDescription(), a.getComments(), a.getStart().toLocalDate(), a.getStop().toLocalDate());
    }

    @Override
    public void addActivity(String description, String comment, LocalDate start, LocalDate end)
    {
        ZonedDateTime zdtStart = start.atStartOfDay(ZoneOffset.UTC);
        ZonedDateTime zdtEnd = end.atStartOfDay(ZoneOffset.UTC);

        try {
            model.addActivity(detailPhase, description, comment, zdtStart, zdtEnd);
        } catch (Exception e) {
            view.showError(e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void updateActivity(String description, String comment, LocalDate start, LocalDate end) {
        Activity a = currentActivities.get(view.getSelectedActivity());

        ZonedDateTime zdtStart = start.atStartOfDay(ZoneOffset.UTC);
        ZonedDateTime zdtEnd = end.atStartOfDay(ZoneOffset.UTC);
        a.setDescription(description);
        a.setComments(comment);
        a.setStart(zdtStart);
        a.setStop(zdtEnd);
        try {
            model.updateActivity(a);
        } catch (Exception e) {
            view.showError(e.getLocalizedMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void backToProjectDetailClicked() {
        showProjectDetail();
    }
}
