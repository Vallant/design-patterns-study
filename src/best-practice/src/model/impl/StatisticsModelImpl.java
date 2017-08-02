package model.impl;

import controller.interfaces.StatisticsController;
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
        PERIOD period = PERIOD.ALLTIME;
        ArrayList<ProjectPhase> phases = new ArrayList<>();
        ArrayList<Duration> durations = new ArrayList<>();


        ZonedDateTime since = ZonedDateTime.now();
        switch(period)
        {
            case ALLTIME: since = ZonedDateTime.ofInstant(Instant.EPOCH, ZoneId.systemDefault()); break;
            case YEAR: since = ZonedDateTime.now().minusYears(1); break;
            case MONTH: since = ZonedDateTime.now().minusMonths(1); break;
            case WEEK: since = ZonedDateTime.now().minusWeeks(1); break;
            case DAY: since = ZonedDateTime.now().minusDays(1); break;
        }

        ActivityRepository ar = mainModel.DB().getActivityRepository();
        ar.getPhasesAndWorkloadSince(user.getLoginName(), project.getId(), since, phases, durations);
        controller.showDetail();
        controller.setDetailData(phases, durations);
    }

    @Override
    public void phasePeriodChanged(int projectId, int selectedIndex) throws Exception {
        PERIOD period = PERIOD.values()[selectedIndex];
        ArrayList<ProjectPhase> phases = new ArrayList<>();
        ArrayList<Duration> durations = new ArrayList<>();


        ZonedDateTime since = ZonedDateTime.now();
        switch(period)
        {
            case ALLTIME: since = ZonedDateTime.ofInstant(Instant.EPOCH, ZoneId.systemDefault()); break;
            case YEAR: since = ZonedDateTime.now().minusYears(1); break;
            case MONTH: since = ZonedDateTime.now().minusMonths(1); break;
            case WEEK: since = ZonedDateTime.now().minusWeeks(1); break;
            case DAY: since = ZonedDateTime.now().minusDays(1); break;
        }

        ActivityRepository ar = mainModel.DB().getActivityRepository();
        ar.getPhasesAndWorkloadSince(user.getLoginName(), projectId, since, phases, durations);
        controller.setDetailData(phases, durations);
    }

    @Override
    public void projectPeriodChanged(int selectedIndex) throws Exception {
        PERIOD period = PERIOD.values()[selectedIndex];
        ArrayList<Project> projects = new ArrayList<>();
        ArrayList<Duration> durations = new ArrayList<>();


        ZonedDateTime since = ZonedDateTime.now();
        switch(period)
        {
            case ALLTIME: since = ZonedDateTime.ofInstant(Instant.EPOCH, ZoneId.systemDefault()); break;
            case YEAR: since = ZonedDateTime.now().minusYears(1); break;
            case MONTH: since = ZonedDateTime.now().minusMonths(1); break;
            case WEEK: since = ZonedDateTime.now().minusWeeks(1); break;
            case DAY: since = ZonedDateTime.now().minusDays(1); break;
        }

        ActivityRepository ar = mainModel.DB().getActivityRepository();
        ar.getProjectsAndWorkloadSince(user.getLoginName(), since, projects, durations);
        controller.setOverviewData(projects, durations);
    }
}
