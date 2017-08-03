/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.swing;

import controller.interfaces.*;
import data.User;
import model.interfaces.*;
import view.common.ViewManager;
import view.interfaces.MainView;

/**
 *
 * @author stephan
 */
public class MainControllerSwing implements MainController
{
    private MainView mainView;
    private MainModel mainModel;
    
    private final LoginController login;
    private final ActivityBarController activityBar;
    private final ProjectController project;
    private final SideBarController sideBar;
    private final PersonalStatisticController personalStatistic;
    private final ProjectStatisticController projectStatistic;

    public MainControllerSwing()
    {
        login = new LoginControllerSwing();
        project = new ProjectControllerSwing();
        activityBar = new ActivityBarControllerSwing();
        sideBar = new SideBarControllerSwing();
        personalStatistic = new PersonalStatisticControllerSwing();
        projectStatistic = new ProjectStatisticControllerSwing();
    }
    
    @Override
    public void init(String frontend)
    {
        ViewManager.InitInstance(frontend);
        mainView = ViewManager.GetInstance();
    }

    @Override
    public void setModel(MainModel model)
    {
        mainModel = model;
    }

    @Override
    public void switchToLogin()
    {
        assert(mainView != null);
        mainView.showLoginView();
    }

    @Override
    public void switchToProjectView()
    {
        mainView.hideCenterContent();
        mainView.showProjectView();
    }

    @Override
    public void switchToAdminView()
    {
        mainView.hideCenterContent();
        mainView.showAdminView();
    }

    @Override
    public void switchToPersonalStatisticView()
    {
        mainView.hideCenterContent();
        mainView.showPersonalStatisticView();
        personalStatistic.refresh();
    }

    @Override
    public void showActivityBar()
    {
        mainView.showActivityBar();
    }

    @Override
    public void pairLogin(LoginModel model)
    {
        login.SetModel(model);
        model.setController(login);
        mainView.pairLogin(login);
    }

    @Override
    public void pairProject(ProjectModel model)
    {
        project.setModel(model);
        model.setController(project);
        mainView.pairProject(project);
    }

    @Override
    public void pairActivityBar(ActivityBarModel model)
    {
        activityBar.setModel(model);
        model.setController(activityBar);
        mainView.pairActivityBar(activityBar);
    }

    @Override
    public void showError(Exception ex) {
        mainView.showError(ex.getLocalizedMessage());
    }

    @Override
    public void showSideBar(User.ROLE role) {
        mainView.showSideBar(role);
    }

    @Override
    public void pairSideBar(SideBarModel model) {
        sideBar.setModel(model);
        model.setController(sideBar);
        mainView.pairSideBar(sideBar);
    }

    @Override
    public void pairPersonalStatistic(PersonalStatisticModel model) {
        personalStatistic.setModel(model);
        model.setController(personalStatistic);
        mainView.pairPersonalStatistic(personalStatistic);
    }

    @Override
    public void pairProjectStatistic(ProjectStatisticModel model) {
        projectStatistic.setModel(model);
        model.setController(projectStatistic);
        mainView.pairProjectStatistic(projectStatistic);
    }

    @Override
    public void switchToSettingsView() {
        mainView.hideCenterContent();
        mainView.showSettingsView();
    }

    @Override
    public void switchToProjectStatisticView() {
        mainView.hideCenterContent();
        mainView.showProjectStatisticView();
    }


}
