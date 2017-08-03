package controller.interfaces;

import model.interfaces.PersonalStatisticModel;
import model.interfaces.ProjectStatisticModel;
import view.interfaces.ProjectStatisticView;

public interface ProjectStatisticController {

    void setModel(ProjectStatisticModel model);
    void setView(ProjectStatisticView view);
    void refresh();

    void phasePeriodChanged(int selectedPeriodIndex, int selectedUserIndex);
    void projectPeriodChanged(int selectedPeriodIndex, int selectedUserIndex);
    void activityPeriodChanged(int selectedPeriodIndex, int selectedUserIndex);

    void showProjectView();
    void showPhaseView();
    void showActivityView();

}
