package view.interfaces;

import controller.interfaces.PersonalStatisticController;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;

/**
 * Created by stephan on 08.07.17.
 */
public interface PersonalStatisticView {
    void showProjectView();

    void setProjectData(ArrayList<String> projectNames, ArrayList<Duration> durations);

    void showPhaseView();
    void setPhaseData(ArrayList<String> phaseNames, ArrayList<Duration> durations);

    void showActivityView();
    void setActivityData(ArrayList<String> descriptions,
                         ArrayList<String> comments,
                         ArrayList<ZonedDateTime> startTimes,
                         ArrayList<ZonedDateTime> endTimes);

    void setController(PersonalStatisticController controller);

    void RemoveAllComponents();

    void showError(String localizedMessage);

    int getSelectedProjectPeriod();

    int getSelectedActivity();

    void hide();

    void showAddActivityDialog();

    boolean confirmDeletion();

    void showUpdateActivityDialog(String description, String comment, LocalDate start, LocalDate end);

    int getSelectedPhasePeriod();

    int getSelectedActivityPeriod();
}
