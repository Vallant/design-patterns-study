package model.impl;

import controller.interfaces.StatisticsController;
import data.Activity;
import data.Project;
import data.ProjectPhase;
import data.User;
import db.interfaces.ActivityRepository;
import model.interfaces.MainModel;
import model.interfaces.StatisticsModel;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

/**
 * Created by stephan on 17/07/17.
 */
public class StatisticsModelImpl implements StatisticsModel
{

    private MainModel mainModel;
    private StatisticsController controller;
    private User user;

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void setMainModel(MainModel mainModel) {
        this.mainModel = mainModel;
    }

    @Override
    public void setController(StatisticsController controller) {
        this.controller = controller;
    }

    @Override
    public void refresh() {
        controller.refresh();
    }

    @Override
    public void requestedDetailFor(Project project) throws Exception {

        phasePeriodChanged(project.getId(), PERIOD.ALLTIME.ordinal());
        controller.showDetail();
    }

    @Override
    public void requestedDetailFor(ProjectPhase detailPhase) throws Exception {
        activityPeriodChanged(detailPhase.getId(), PERIOD.ALLTIME.ordinal());
        controller.showPhaseDetail();
    }

    @Override
    public void phasePeriodChanged(int projectId, int selectedIndex) throws Exception {
        PERIOD period = PERIOD.values()[selectedIndex];
        ArrayList<ProjectPhase> phases = new ArrayList<>();
        ArrayList<Duration> durations = new ArrayList<>();


        ZonedDateTime since = subtract(period);

        ActivityRepository ar = mainModel.DB().getActivityRepository();
        ar.getPhasesAndWorkloadSince(user.getLoginName(), projectId, since, phases, durations);
        controller.setDetailData(phases, durations);
    }

    @Override
    public void projectPeriodChanged(int selectedIndex) throws Exception {
        PERIOD period = PERIOD.values()[selectedIndex];
        ArrayList<Project> projects = new ArrayList<>();
        ArrayList<Duration> durations = new ArrayList<>();


        ZonedDateTime since = subtract(period);


        ActivityRepository ar = mainModel.DB().getActivityRepository();
        ar.getProjectsAndWorkloadSince(user.getLoginName(), since, projects, durations);
        controller.setOverviewData(projects, durations);
    }

    @Override
    public void activityPeriodChanged(int phaseId, int selectedIndex) throws Exception {
        PERIOD period = PERIOD.values()[selectedIndex];
        ZonedDateTime since = subtract(period);


        ActivityRepository ar = mainModel.DB().getActivityRepository();
        ArrayList<Activity> activities = ar.getActivitiesForPhaseSince(user.getLoginName(), phaseId, since);
        controller.showPhaseDetail();
        controller.setPhaseDetailData(activities);
    }

    private ZonedDateTime subtract(PERIOD period)
    {
        switch(period)
        {
            case ALLTIME: return ZonedDateTime.ofInstant(Instant.EPOCH, ZoneId.systemDefault());
            case YEAR: return ZonedDateTime.now().minusYears(1);
            case MONTH: return ZonedDateTime.now().minusMonths(1);
            case WEEK: return ZonedDateTime.now().minusWeeks(1);
            case DAY: return ZonedDateTime.now().minusDays(1);
            default: return ZonedDateTime.ofInstant(Instant.EPOCH, ZoneId.systemDefault());
        }
    }
}
