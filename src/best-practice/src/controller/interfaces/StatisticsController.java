package controller.interfaces;

import data.Activity;
import data.Project;
import data.ProjectPhase;
import model.interfaces.StatisticsModel;
import view.interfaces.StatisticsView;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by stephan on 17/07/17.
 */
public interface StatisticsController {

    void setModel(StatisticsModel model);
    void setView(StatisticsView view);
    void refresh();

    void phasePeriodChanged(int selectedIndex);
    void projectPeriodChanged(int selectedIndex);

    void doubleClickOnProject(int index);

    void showOverview();
    void setOverviewData(ArrayList<Project> projects, ArrayList<Duration> durations);
    void showProjectDetail();
    void setDetailData(ArrayList<ProjectPhase> phases, ArrayList<Duration> durations);

    void backToOverviewClicked();

    void doubleClickOnPhase(int index);

    void activityPeriodChanged(int selectedIndex);

    void setPhaseDetailData(ArrayList<Activity> activities);

    void showPhaseDetail();

    void addActivityClicked();

    void deleteActivityClicked();

    void updateActivityClicked();

    void addActivity(String description, String comment, LocalDate start, LocalDate end);

    void updateActivity(String description, String comment, LocalDate start, LocalDate end);

    void backToProjectDetailClicked();
}
