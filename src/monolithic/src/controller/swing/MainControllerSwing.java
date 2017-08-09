/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.swing;

import data.User;
import model.impl.*;
import view.common.ViewManager;
import view.swing.MainViewSwing;

/**
 *
 * @author stephan
 */
public class MainControllerSwing
{
    private MainViewSwing mainView;
    private MainModelImpl mainModel;
    
    private final LoginControllerSwing login;
    private final ActivityBarControllerSwing activityBar;
    private final ProjectControllerSwing project;
    private final SideBarControllerSwing sideBar;
    private final PersonalStatisticControllerSwing personalStatistic;
    private final ProjectStatisticControllerSwing projectStatistic;
    private final SettingsControllerSwing settings;

    public MainControllerSwing()
    {
        login = new LoginControllerSwing();
        project = new ProjectControllerSwing();
        activityBar = new ActivityBarControllerSwing();
        sideBar = new SideBarControllerSwing();
        personalStatistic = new PersonalStatisticControllerSwing();
        projectStatistic = new ProjectStatisticControllerSwing();
        settings = new SettingsControllerSwing();
    }
    
    
    public void init(String frontend)
    {
        ViewManager.initInstance(frontend);
        mainView = ViewManager.getInstance();
    }

    
    public void setModel(MainModelImpl model)
    {
        mainModel = model;
    }

    
    public void switchToLogin()
    {
        assert(mainView != null);
        mainView.hideAll();

        mainView.showLoginView();
    }

    
    public void switchToProjectView()
    {
        mainView.hideCenterContent();
        mainView.showProjectView();
    }

    
    public void switchToAdminView()
    {
        mainView.hideCenterContent();
        mainView.showAdminView();
    }

    
    public void switchToPersonalStatisticView()
    {
        mainView.hideCenterContent();
        mainView.showPersonalStatisticView();
        personalStatistic.refresh();
    }

    
    public void showActivityBar()
    {
        mainView.showActivityBar();
    }

    
    public void pairLogin(LoginModelImpl model)
    {
        login.setModel(model);
        model.setController(login);
        mainView.pairLogin(login);
    }

    
    public void pairProject(ProjectModelImpl model)
    {
        project.setModel(model);
        model.setController(project);
        mainView.pairProject(project);
    }

    
    public void pairActivityBar(ActivityBarModelImpl model)
    {
        activityBar.setModel(model);
        model.setController(activityBar);
        mainView.pairActivityBar(activityBar);
    }

    
    public void showError(Exception ex) {
        mainView.showError(ex.getLocalizedMessage());
    }

    
    public void showSideBar(User.ROLE role) {
        mainView.showSideBar(role);
    }

    
    public void pairSideBar(SideBarModelImpl model) {
        sideBar.setModel(model);
        model.setController(sideBar);
        mainView.pairSideBar(sideBar);
    }

    
    public void pairPersonalStatistic(PersonalStatisticModelImpl model) {
        personalStatistic.setModel(model);
        model.setController(personalStatistic);
        mainView.pairPersonalStatistic(personalStatistic);
    }

    
    public void pairProjectStatistic(ProjectStatisticModelImpl model) {
        projectStatistic.setModel(model);
        model.setController(projectStatistic);
        mainView.pairProjectStatistic(projectStatistic);
    }

    
    public void pairSettings(SettingsModelImpl model) {
        settings.setModel(model);
        model.setController(settings);
        mainView.pairSettings(settings);
    }

    
    public void switchToSettingsView() {
        mainView.hideCenterContent();
        mainView.showSettingsView();
    }

    
    public void switchToProjectStatisticView() {
        mainView.hideCenterContent();
        mainView.showProjectStatisticView();
        projectStatistic.refresh();
    }


}
