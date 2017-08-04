package controller.interfaces;

import data.*;
import model.interfaces.PersonalStatisticModel;
import model.interfaces.ProjectStatisticModel;
import view.interfaces.ProjectStatisticView;

import java.time.Duration;
import java.util.ArrayList;

public interface ProjectStatisticController {

    void setModel(ProjectStatisticModel model);
    void setView(ProjectStatisticView view);
    void refresh();

    void phaseDropDownChanged(int selectedPeriodIndex, int selectedUserIndex);
    void projectPeriodChanged(int selectedPeriodIndex);
    void activityDropDownChanged(int selectedPeriodIndex, int selectedUserIndex);

    void showProjectView();
    void setProjectData(ArrayList<Project> projects, ArrayList<Duration> durations);

    void showPhaseView();
    void setPhaseData(ArrayList<ProjectMember> members, ArrayList<ProjectPhase> phases, ArrayList<Duration> durations);

    void showActivityView();
    void setActivityData(ArrayList<Activity> activities);

    void doubleClickOnProject(int index);

    void backToProjectClicked();

    void doubleClickOnPhase(int index);

    void backToPhaseClicked();
}
