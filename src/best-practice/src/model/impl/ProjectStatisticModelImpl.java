package model.impl;

import controller.interfaces.ProjectStatisticController;
import data.Project;
import data.ProjectPhase;
import data.User;
import model.interfaces.MainModel;
import model.interfaces.ProjectStatisticModel;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ProjectStatisticModelImpl implements ProjectStatisticModel {
    private User user;
    private MainModel mainModel;
    private ProjectStatisticController controller;

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void setMainModel(MainModel mainModel) {
        this.mainModel = mainModel;
    }

    @Override
    public void setController(ProjectStatisticController controller) {
        this.controller = controller;
    }

    @Override
    public void refresh() {
        controller.refresh();
    }

    @Override
    public void requestedDetailFor(Project project) {
        throw new NotImplementedException();
    }

    @Override
    public void requestedDetailFor(ProjectPhase projectPhase) {
        throw new NotImplementedException();
    }
}
