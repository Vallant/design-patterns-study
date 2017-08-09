package model.impl;

import controller.swing.ProjectStatisticControllerSwing;
import data.*;
import db.interfaces.ActivityRepository;
import db.interfaces.ProjectMemberRepository;


import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class ProjectStatisticModelImpl{

    public enum PERIOD
    {
        ALLTIME,
        YEAR,
        MONTH,
        WEEK,
        DAY
    };

    private User user;
    private MainModelImpl mainModel;
    private ProjectStatisticControllerSwing controller;


    public void projectPeriodChanged(int selectedPeriodIndex) throws Exception {
        PERIOD period = PERIOD.values()[selectedPeriodIndex];
        ArrayList<Project> projects = new ArrayList<>();
        ArrayList<Duration> durations = new ArrayList<>();

        ZonedDateTime since = subtract(period);

        ActivityRepository ar = mainModel.db().getActivityRepository();
        ar.getOwnedProjectsAndWorkloadSince(user.getLoginName(), since, projects, durations);
        controller.setProjectData(projects, durations);
    }


    public void activityDropDownChanged(int phaseId, int selectedPeriodIndex, boolean showAll, ProjectMember selectedUser) throws Exception {
        PERIOD period = PERIOD.values()[selectedPeriodIndex];
        ZonedDateTime since = subtract(period);


        ActivityRepository ar = mainModel.db().getActivityRepository();
        ArrayList<Activity> activities;

        if(selectedUser == null)
            activities = ar.getActivitiesByUserForPhaseSince("", phaseId, since);
        else
            activities = ar.getActivitiesByUserForPhaseSince(selectedUser.getUserLoginName(), phaseId, since);
        controller.setActivityData(activities);
    }


    public void phaseDropDownChanged(int projectId, int selectedPeriodIndex, boolean showAll, ProjectMember selectedUser) throws Exception {
        PERIOD period = PERIOD.values()[selectedPeriodIndex];
        ArrayList<ProjectPhase> phases = new ArrayList<>();
        ArrayList<Duration> durations = new ArrayList<>();
        ArrayList<ProjectMember> members = new ArrayList<>();


        ZonedDateTime since = subtract(period);

        ActivityRepository ar = mainModel.db().getActivityRepository();
        if(selectedUser == null)
            ar.getPhasesAndWorkloadForUserSince("", projectId, since, phases, durations);
        else
            ar.getPhasesAndWorkloadForUserSince(selectedUser.getUserLoginName(), projectId, since, phases, durations);

        ProjectMemberRepository pmr = mainModel.db().getProjectMemberRepository();
        members = pmr.getMembersByProjectId(projectId);
        controller.setPhaseData(members, phases, durations);

    }


    public void setUser(User user) {
        this.user = user;
    }


    public void setMainModel(MainModelImpl mainModel) {
        this.mainModel = mainModel;
    }


    public void setController(ProjectStatisticControllerSwing controller) {
        this.controller = controller;
    }


    public void refresh() {
        controller.refresh();
    }


    public void requestedDetailFor(Project project) throws Exception {
        phaseDropDownChanged(project.getId(), PERIOD.ALLTIME.ordinal(), true, null);
        controller.showPhaseView();
    }


    public void requestedDetailFor(ProjectPhase projectPhase) throws Exception {
        activityDropDownChanged(projectPhase.getId(), PERIOD.ALLTIME.ordinal(), true, null);
        controller.showActivityView();
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
