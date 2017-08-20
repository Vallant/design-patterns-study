/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.impl;

import controller.common.ControllerManager;
import controller.javafx.MainControllerFX;
import controller.swing.MainControllerSwing;
import data.User;
import db.common.DBManagerPostgres;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author stephan
 */
public class MainModelImpl
{
  private final MainControllerSwing controllerSwing;
  private final MainControllerFX    controllerFX;

  private final LoginModelImpl             login;
  private final ProjectModelImpl           project;
  private final ActivityBarModelImpl       activityBar;
  private final SideBarModelImpl           sideBar;
  private final PersonalStatisticModelImpl personalStatistic;
  private final ProjectStatisticModelImpl  projectStatistic;
  private final SettingsModelImpl          settings;
  private final DBManagerPostgres db;
  private       User              user;


  private MainModelImpl(String driver, String url, String username, String password, String frontend) throws Exception
  {

    db = new DBManagerPostgres(driver, url, username, password);

    ControllerManager.initInstance(frontend);

    if(Objects.equals(frontend, "swing"))
    {
      controllerSwing = ControllerManager.getInstanceSwing();
      controllerSwing.setModel(this);
      controllerFX = null;
    }
    else
    {
      controllerFX = ControllerManager.getInstanceFX();
      controllerFX.setModel(this);
      controllerSwing = null;
    }

    login = new LoginModelImpl();
    pairLogin();
    project = new ProjectModelImpl();
    pairProject();
    activityBar = new ActivityBarModelImpl();
    pairActivityBar();
    sideBar = new SideBarModelImpl();
    pairSideBar();
    personalStatistic = new PersonalStatisticModelImpl();
    pairPersonalStatistic();

    projectStatistic = new ProjectStatisticModelImpl();
    pairProjectStatistic();

    settings = new SettingsModelImpl();
    pairSettings();

    if(controllerSwing != null)
      controllerSwing.switchToLogin();
    else
      controllerFX.switchToLogin();
  }


  public static void main(String[] args)
  {
    try
    {
      new MainModelImpl(args[0], args[1], args[2], args[3], args[4]);
    }
    catch(Exception ex)
    {
      Logger.getLogger(MainModelImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
  }


  public void loginSuccessfulFor(User user)
  {
    this.user = user;
    activityBar.setUser(user);
    personalStatistic.setUser(user);
    project.setUser(user);
    projectStatistic.setUser(user);

    if(controllerSwing != null)
    {
      controllerSwing.switchToProjectView();
      controllerSwing.showActivityBar();
      controllerSwing.showSideBar(user.getRole());
    }
    else
    {
      controllerFX.switchToProjectView();
      controllerFX.showActivityBar();
      controllerFX.showSideBar(user.getRole());
    }


    project.refresh();
    activityBar.refresh();
    personalStatistic.refresh();
    sideBar.refresh();
  }


  public void showError(Exception ex)
  {

    if(controllerSwing != null)
      controllerSwing.showError(ex);
    else
      controllerFX.showError(ex);

  }

  private void pairLogin()
  {
    login.setMainModel(this);
    if(controllerSwing != null)
      controllerSwing.pairLogin(login);
    else
      controllerFX.pairLogin(login);
  }

  private void pairActivityBar()
  {
    activityBar.setMainModel(this);
    if(controllerSwing != null)
      controllerSwing.pairActivityBar(activityBar);
    else
      controllerFX.pairActivityBar(activityBar);
  }

  private void pairProject()
  {
    project.setMainModel(this);
    if(controllerSwing != null)
      controllerSwing.pairProject(project);
    else
      controllerFX.pairProject(project);
  }

  private void pairSideBar()
  {
    sideBar.setMainModel(this);
    if(controllerSwing != null)
      controllerSwing.pairSideBar(sideBar);
    else
      controllerFX.pairSideBar(sideBar);
  }

  private void pairPersonalStatistic()
  {
    personalStatistic.setMainModel(this);
    if(controllerSwing != null)
      controllerSwing.pairPersonalStatistic(personalStatistic);
    else
      controllerFX.pairPersonalStatistic(personalStatistic);
  }

  private void pairProjectStatistic()
  {
    projectStatistic.setMainModel(this);
    if(controllerSwing != null)
      controllerSwing.pairProjectStatistic(projectStatistic);
    else
      controllerFX.pairProjectStatistic(projectStatistic);
  }

  private void pairSettings()
  {
    settings.setMainModel(this);
    if(controllerSwing != null)
      controllerSwing.pairSettings(settings);
    else
      controllerFX.pairSettings(settings);
  }


  public DBManagerPostgres db()
  {
    return db;
  }


  public void switchToPersonalStatistics()
  {

    if(controllerSwing != null)
      controllerSwing.switchToPersonalStatisticView();
    else
      controllerFX.switchToPersonalStatisticView();
  }


  public void switchToProjects()
  {
    if(controllerSwing != null)
      controllerSwing.switchToProjectView();
    else
      controllerFX.switchToProjectView();
  }


  public void switchToAdministration()
  {
    if(controllerSwing != null)
      controllerSwing.switchToAdminView();
    else
      controllerFX.switchToAdminView();
  }


  public void switchToSettings()
  {
    if(controllerSwing != null)
      controllerSwing.switchToSettingsView();
    else
      controllerFX.switchToSettingsView();

    settings.setUser(user);
    settings.refresh();
  }


  public void refreshActivityBar()
  {
    activityBar.refresh();
  }


  public void switchToProjectStatistic()
  {
    if(controllerSwing != null)
      controllerSwing.switchToProjectStatisticView();
    else
      controllerFX.switchToProjectStatisticView();
  }


  public void logout()
  {
    activityBar.finishActivity();

    if(controllerSwing != null)
      controllerSwing.switchToLogin();
    else
      controllerFX.switchToLogin();
  }
}

