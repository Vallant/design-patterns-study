/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.impl;

import controller.common.ControllerManager;
import controller.standard.MainControllerStandard;
import data.User;
import db.common.DBManagerPostgres;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author stephan
 */
public class MainModelImpl
{
  private final MainControllerStandard controller;

  private final LoginModelImpl             login;
  private final ProjectModelImpl           project;
  private final ActivityBarModelImpl       activityBar;
  private final SideBarModelImpl           sideBar;
  private final PersonalStatisticModelImpl personalStatistic;
  private final ProjectStatisticModelImpl  projectStatistic;
  private final SettingsModelImpl          settings;

  private       User              user;
  private final DBManagerPostgres db;


  private MainModelImpl(String driver, String url, String username, String password, String controllerImpl, String
                        frontend) throws Exception
  {

    db = new DBManagerPostgres(driver, url, username, password);
    ControllerManager.initInstance(controllerImpl, frontend);

    controller = ControllerManager.getInstance();
    controller.setModel(this);

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

    controller.switchToLogin();
  }


  public static void main(String[] args)
  {
    try
    {
      new MainModelImpl(args[0], args[1], args[2], args[3], args[4], args[5]);
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

    controller.switchToProjectView();
    controller.showActivityBar();
    controller.showSideBar(user.getRole());


    project.refresh();
    activityBar.refresh();
    personalStatistic.refresh();
    sideBar.refresh();
  }


  public void showError(Exception ex)
  {
    controller.showError(ex);
  }

  private void pairLogin()
  {
    login.setMainModel(this);
    controller.pairLogin(login);
  }

  private void pairActivityBar()
  {
    activityBar.setMainModel(this);
    controller.pairActivityBar(activityBar);
  }

  private void pairProject()
  {
    project.setMainModel(this);
    controller.pairProject(project);
  }

  private void pairSideBar()
  {
    sideBar.setMainModel(this);
    controller.pairSideBar(sideBar);
  }

  private void pairPersonalStatistic()
  {
    personalStatistic.setMainModel(this);
    controller.pairPersonalStatistic(personalStatistic);
  }

  private void pairProjectStatistic()
  {
    projectStatistic.setMainModel(this);
    controller.pairProjectStatistic(projectStatistic);
  }

  private void pairSettings()
  {
    settings.setMainModel(this);
    controller.pairSettings(settings);
  }


  public DBManagerPostgres db()
  {
    return db;
  }


  public void switchToPersonalStatistics()
  {
    controller.switchToPersonalStatisticView();
  }


  public void switchToProjects()
  {
    controller.switchToProjectView();
  }


  public void switchToAdministration()
  {
    controller.switchToAdminView();
  }


  public void switchToSettings()
  {
    controller.switchToSettingsView();
    settings.setUser(user);
    settings.refresh();
  }


  public void refreshActivityBar()
  {
    activityBar.refresh();
  }


  public void switchToProjectStatistic()
  {
    controller.switchToProjectStatisticView();
  }


  public void logout()
  {
    activityBar.finishActivity();
    controller.switchToLogin();
  }
}

