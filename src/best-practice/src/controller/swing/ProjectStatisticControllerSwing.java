package controller.swing;

import controller.interfaces.ProjectStatisticController;
import model.interfaces.ProjectStatisticModel;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import view.interfaces.ProjectStatisticView;

public class ProjectStatisticControllerSwing implements ProjectStatisticController {

    private ProjectStatisticModel model;
    private ProjectStatisticView view;

    @Override
    public void setModel(ProjectStatisticModel model) {
        this.model = model;
    }

    @Override
    public void setView(ProjectStatisticView view) {
        this.view = view;
    }

    @Override
    public void refresh() {
        throw new NotImplementedException();
    }

    @Override
    public void phasePeriodChanged(int selectedPeriodIndex, int selectedUserIndex) {
        throw new NotImplementedException();
    }

    @Override
    public void projectPeriodChanged(int selectedPeriodIndex, int selectedUserIndex) {
        throw new NotImplementedException();
    }

    @Override
    public void activityPeriodChanged(int selectedPeriodIndex, int selectedUserIndex) {
        throw new NotImplementedException();
    }

    @Override
    public void showProjectView() {
        throw new NotImplementedException();
    }

    @Override
    public void showPhaseView() {
        throw new NotImplementedException();
    }

    @Override
    public void showActivityView() {
        throw new NotImplementedException();
    }
}
