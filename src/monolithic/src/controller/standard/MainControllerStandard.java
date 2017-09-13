/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.standard;

import data.User;
import model.impl.*;
import view.common.ViewManager;
import view.javafx.MainViewFX;
import view.swing.MainViewSwing;

/**
 * @author stephan
 */
public class MainControllerStandard
{
  private MainViewSwing mainViewSwing;
  private MainViewFX mainViewFX;

  private MainModelImpl mainModel;

  private final LoginControllerStandard             login;
  private final ActivityBarControllerStandard       activityBar;
  private final ProjectControllerStandard           project;
  private final SideBarControllerStandard           sideBar;
  private final PersonalStatisticControllerStandard personalStatistic;
  private final ProjectStatisticControllerStandard  projectStatistic;
  private final SettingsControllerStandard          settings;

  public MainControllerStandard()
  {
    login = new LoginControllerStandard();
    project = new ProjectControllerStandard();
    activityBar = new ActivityBarControllerStandard();
    sideBar = new SideBarControllerStandard();
    personalStatistic = new PersonalStatisticControllerStandard();
    projectStatistic = new ProjectStatisticControllerStandard();
    settings = new SettingsControllerStandard();
  }


  public void init(String frontend)
  {
    ViewManager.initInstance(frontend);
    if(frontend.equals("swing"))
    {
      mainViewSwing = ViewManager.getInstanceSwing();
      mainViewFX = null;
    }
    else
    {
      mainViewSwing = null;
      mainViewFX = ViewManager.getInstanceFX();
    }

  }


  public void setModel(MainModelImpl model)
  {
    mainModel = model;
  }


  public void switchToLogin()
  {
    assert (mainViewSwing != null || mainViewFX != null);
    if(mainViewSwing != null)
    {
      mainViewSwing.hideAll();
      mainViewSwing.showLoginView();
    }
    else
    {
      mainViewFX.hideAll();
      mainViewFX.showLoginView();
    }

  }


  public void switchToProjectView()
  {
    if(mainViewSwing != null)
    {
      mainViewSwing.hideCenterContent();
      mainViewSwing.showProjectView();
    }
    else
    {
      mainViewFX.hideCenterContent();
      mainViewFX.showProjectView();
    }

  }


  public void switchToAdminView()
  {
    if(mainViewSwing != null)
    {
      mainViewSwing.hideCenterContent();
      mainViewSwing.showAdminView();
    }
    else
    {
      mainViewFX.hideCenterContent();
      mainViewFX.showAdminView();
    }

  }


  public void switchToPersonalStatisticView()
  {
    if(mainViewSwing != null)
    {
      mainViewSwing.hideCenterContent();
      mainViewSwing.showPersonalStatisticView();
    }
    else
    {
      mainViewFX.hideCenterContent();
      mainViewFX.showPersonalStatisticView();
    }

    personalStatistic.refresh();
  }


  public void showActivityBar()
  {
    if(mainViewSwing != null)
      mainViewSwing.showActivityBar();
    else
      mainViewFX.showActivityBar();
  }


  public void pairLogin(LoginModelImpl model)
  {
    login.setModel(model);
    model.setController(login);
    if(mainViewSwing != null)
      mainViewSwing.pairLogin(login);
    else
      mainViewFX.pairLogin(login);
  }


  public void pairProject(ProjectModelImpl model)
  {
    project.setModel(model);
    model.setController(project);
    if(mainViewSwing != null)
      mainViewSwing.pairProject(project);
    else
      mainViewFX.pairProject(project);
  }


  public void pairActivityBar(ActivityBarModelImpl model)
  {
    activityBar.setModel(model);
    model.setController(activityBar);
    if(mainViewSwing != null)
      mainViewSwing.pairActivityBar(activityBar);
    else
      mainViewFX.pairActivityBar(activityBar);
  }


  public void showError(Exception ex)
  {
    if(mainViewSwing != null)
      mainViewSwing.showError(ex.getLocalizedMessage());
    else
      mainViewFX.showError(ex.getLocalizedMessage());
  }


  public void showSideBar(User.ROLE role)
  {
    if(mainViewSwing != null)
      mainViewSwing.showSideBar(role);
    else
      mainViewFX.showSideBar(role);
  }


  public void pairSideBar(SideBarModelImpl model)
  {
    sideBar.setModel(model);
    model.setController(sideBar);
    if(mainViewSwing != null)
      mainViewSwing.pairSideBar(sideBar);
    else
      mainViewFX.pairSideBar(sideBar);
  }


  public void pairPersonalStatistic(PersonalStatisticModelImpl model)
  {
    personalStatistic.setModel(model);
    model.setController(personalStatistic);
    if(mainViewSwing != null)
      mainViewSwing.pairPersonalStatistic(personalStatistic);
    else
      mainViewFX.pairPersonalStatistic(personalStatistic);
  }


  public void pairProjectStatistic(ProjectStatisticModelImpl model)
  {
    projectStatistic.setModel(model);
    model.setController(projectStatistic);
    if(mainViewSwing != null)
      mainViewSwing.pairProjectStatistic(projectStatistic);
    else
      mainViewFX.pairProjectStatistic(projectStatistic);
  }


  public void pairSettings(SettingsModelImpl model)
  {
    settings.setModel(model);
    model.setController(settings);
    if(mainViewSwing != null)
      mainViewSwing.pairSettings(settings);
    else
      mainViewFX.pairSettings(settings);
  }


  public void switchToSettingsView()
  {
    if(mainViewSwing != null)
    {
      mainViewSwing.hideCenterContent();
      mainViewSwing.showSettingsView();
    }
    else
    {
      mainViewFX.hideCenterContent();
      mainViewFX.showSettingsView();
    }

  }


  public void switchToProjectStatisticView()
  {
    if(mainViewSwing != null)
    {
      mainViewSwing.hideCenterContent();
      mainViewSwing.showProjectStatisticView();
    }
    else
    {
      mainViewFX.hideCenterContent();
      mainViewFX.showProjectStatisticView();
    }

    projectStatistic.refresh();
  }


}
