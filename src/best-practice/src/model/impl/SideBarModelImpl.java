package model.impl;

import controller.interfaces.MainController;
import controller.interfaces.SideBarController;
import model.interfaces.MainModel;
import model.interfaces.SideBarModel;

/**
 * Created by stephan on 08.07.17.
 */
public class SideBarModelImpl implements SideBarModel {
    private MainModel mainModel;
    private SideBarController controller;
    @Override
    public void setMainModel(MainModel model) {
        mainModel = model;
    }

    @Override
    public void setController(SideBarController controller) {
        this.controller = controller;
    }

    @Override
    public void refresh() {

    }

    @Override
    public void projectsClicked() {
        mainModel.switchedToProjects();
    }

    @Override
    public void statisticsClicked() {
        mainModel.switchedToStatistics();
    }

    @Override
    public void administrationClicked() {
        mainModel.switchedToAdministration();
    }

    @Override
    public void settingsClicked() {
        mainModel.switchedToSettings();
    }
}
