package model.interfaces;

import controller.interfaces.StatisticsController;
import data.Project;
import data.ProjectPhase;
import data.User;
import model.impl.MainModelImpl;
import model.impl.StatisticsModelImpl;

import java.time.Duration;
import java.util.ArrayList;

/**
 * Created by stephan on 17/07/17.
 */
public interface StatisticsModel {




    enum PERIOD
    {
        ALLTIME,
        YEAR,
        MONTH,
        WEEK,
        DAY
    };

    void setUser(User user);
    void setMainModel(MainModel mainModel);

    void setController(StatisticsController statistics);

    void refresh();

    void requestedDetailFor(Project project) throws Exception;
    void requestedDetailFor(ProjectPhase detailPhase) throws Exception;

    void phasePeriodChanged(int projectId, int selectedIndex) throws Exception;
    void projectPeriodChanged(int selectedIndex) throws Exception;
    void activityPeriodChanged(int id, int selectedIndex) throws Exception;
}
