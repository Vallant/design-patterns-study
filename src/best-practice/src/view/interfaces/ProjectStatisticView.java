package view.interfaces;

import controller.interfaces.ProjectStatisticController;

import java.lang.reflect.Array;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public interface ProjectStatisticView {
    void setController(ProjectStatisticController controller);
    void hide();
    void removeAllComponents();

    void showProjectView();
    void setProjectData(ArrayList<String> projectNames, ArrayList<Duration> durations);

    void showPhaseView();
    void setPhaseData(ArrayList<String> phaseNames, ArrayList<Duration> durations, ArrayList<String> memberNames);

    void showActivityView();
    void setActivityData(ArrayList<String> users,
                         ArrayList<String> descriptions,
                         ArrayList<String> comments,
                         ArrayList<ZonedDateTime> startTimes,
                         ArrayList<ZonedDateTime> endTimes,
                         ArrayList<String> memberNames);


    int getSelectedPhasePeriod();
    int getSelectedActivityPeriod();
    int getSelectedProjectPeriod();
    int getSelectedUserPeriod();

    boolean confirmDeletion();
    void showError(String error);

}
