package model.impl;

import controller.interfaces.PersonalStatisticController;
import data.Activity;
import data.Project;
import data.ProjectPhase;
import data.User;
import db.interfaces.ActivityRepository;
import model.interfaces.MainModel;
import model.interfaces.PersonalStatisticModel;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

/**
 * Created by stephan on 17/07/17.
 */
public class PersonalStatisticModelImpl implements PersonalStatisticModel
{

    private MainModel mainModel;
    private PersonalStatisticController controller;
    private User user;

    @Override
    public void deleteActivity(Activity toDelete) throws Exception {
     ActivityRepository ar = mainModel.DB().getActivityRepository();
     ar.delete(toDelete);
     controller.refresh();
    }

    @Override
    public void addActivity(ProjectPhase detailPhase, String description, String comment, ZonedDateTime zdtStart, ZonedDateTime zdtEnd) throws Exception {
        if(zdtEnd.isBefore(zdtStart))
            throw new Exception("Start date has to be before End date");
        Activity a = new Activity(detailPhase, user, description, zdtStart, zdtEnd, comment);

        ActivityRepository ar = mainModel.DB().getActivityRepository();
        ar.add(a);
        controller.refresh();
    }

    @Override
    public void updateActivity(Activity a) throws Exception {
        ActivityRepository ar  =mainModel.DB().getActivityRepository();
        ar.update(a);
        controller.refresh();
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void setMainModel(MainModel mainModel) {
        this.mainModel = mainModel;
    }

    @Override
    public void setController(PersonalStatisticController controller) {
        this.controller = controller;
    }

    @Override
    public void refresh() {
        controller.refresh();
    }

    @Override
    public void requestedDetailFor(Project project) throws Exception {

        phasePeriodChanged(project.getId(), PERIOD.ALLTIME.ordinal());
        controller.showPhaseView();
    }

    @Override
    public void requestedDetailFor(ProjectPhase detailPhase) throws Exception {
        activityPeriodChanged(detailPhase.getId(), PERIOD.ALLTIME.ordinal());
        controller.showActivityView();
    }

    @Override
    public void phasePeriodChanged(int projectId, int selectedIndex) throws Exception {
        PERIOD period = PERIOD.values()[selectedIndex];
        ArrayList<ProjectPhase> phases = new ArrayList<>();
        ArrayList<Duration> durations = new ArrayList<>();


        ZonedDateTime since = subtract(period);

        ActivityRepository ar = mainModel.DB().getActivityRepository();
        ar.getPhasesAndWorkloadSince(user.getLoginName(), projectId, since, phases, durations);
        controller.setPhaseData(phases, durations);
    }

    @Override
    public void projectPeriodChanged(int selectedIndex) throws Exception {
        PERIOD period = PERIOD.values()[selectedIndex];
        ArrayList<Project> projects = new ArrayList<>();
        ArrayList<Duration> durations = new ArrayList<>();


        ZonedDateTime since = subtract(period);


        ActivityRepository ar = mainModel.DB().getActivityRepository();
        ar.getParticipatingProjectsAndWorkloadSince(user.getLoginName(), since, projects, durations);
        controller.setProjectData(projects, durations);
    }

    @Override
    public void activityPeriodChanged(int phaseId, int selectedIndex) throws Exception {
        PERIOD period = PERIOD.values()[selectedIndex];
        ZonedDateTime since = subtract(period);


        ActivityRepository ar = mainModel.DB().getActivityRepository();
        ArrayList<Activity> activities = ar.getActivitiesForPhaseSince(user.getLoginName(), phaseId, since);
        controller.showActivityView();
        controller.setActivityData(activities);
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
