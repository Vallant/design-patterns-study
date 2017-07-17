package model.interfaces;

import controller.interfaces.StatisticsController;
import data.Project;
import data.User;
import model.impl.MainModelImpl;

import java.time.Duration;
import java.util.ArrayList;

/**
 * Created by stephan on 17/07/17.
 */
public interface StatisticsModel {

    void setUser(User user);
    void setMainModel(MainModel mainModel);

    void setController(StatisticsController statistics);

    void getProjectsAndWorkload(ArrayList<Project> projects, ArrayList<Duration> durations) throws Exception;

    void refresh();
}
