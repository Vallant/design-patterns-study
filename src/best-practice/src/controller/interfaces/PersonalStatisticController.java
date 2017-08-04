package controller.interfaces;

import data.Activity;
import data.Project;
import data.ProjectPhase;
import model.interfaces.PersonalStatisticModel;
import view.interfaces.PersonalStatisticView;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by stephan on 17/07/17.
 */
public interface PersonalStatisticController {

    void setModel(PersonalStatisticModel model);
    void setView(PersonalStatisticView view);
    void refresh();

    void phasePeriodChanged(int selectedIndex);
    void projectPeriodChanged(int selectedIndex);

    void doubleClickOnProject(int index);

    void showProjectView();
    void setProjectData(ArrayList<Project> projects, ArrayList<Duration> durations);
    void showPhaseView();
    void setPhaseData(ArrayList<ProjectPhase> phases, ArrayList<Duration> durations);

    void backToOverviewClicked();

    void doubleClickOnPhase(int index);

    void activityPeriodChanged(int selectedIndex);

    void setActivityData(ArrayList<Activity> activities);

    void showActivityView();

    void addActivityClicked();

    void deleteActivityClicked();

    void updateActivityClicked();

    void addActivity(String description, String comment, LocalDate start, LocalDate end);

    void updateActivity(String description, String comment, LocalDate start, LocalDate end);

    void backToPhaseViewClicked();
}
