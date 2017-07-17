package model.impl;

import controller.interfaces.StatisticsController;
import data.Project;
import data.User;
import db.interfaces.ActivityRepository;
import model.interfaces.MainModel;
import model.interfaces.StatisticsModel;

import java.time.Duration;
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
    public void getProjectsAndWorkload(ArrayList<Project> projects, ArrayList<Duration> durations) throws Exception {
        ActivityRepository ar = mainModel.DB().getActivityRepository();
        ar.getProjectsAndWorkload(user.getLoginName(), projects, durations);
    }

    @Override
    public void refresh() {
        controller.refresh();
    }
}
