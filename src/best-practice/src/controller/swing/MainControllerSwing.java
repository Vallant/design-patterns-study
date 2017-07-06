/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.swing;

import controller.interfaces.ActivityBarController;
import controller.interfaces.LoginController;
import controller.interfaces.MainController;
import controller.interfaces.ProjectController;
import model.interfaces.ActivityBarModel;
import model.interfaces.LoginModel;
import model.interfaces.MainModel;
import model.interfaces.ProjectModel;
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

    public MainControllerSwing()
    {
        login = new LoginControllerSwing();
        project = new ProjectControllerSwing();
        activityBar = new ActivityBarControllerSwing();
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
        mainView.showProjectView();
    }

    @Override
    public void switchToAdminView()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void switchToStatisticView()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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


}
