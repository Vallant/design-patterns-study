package model.impl;


import controller.swing.SideBarControllerSwing;

/**
 * Created by stephan on 08.07.17.
 */
public class SideBarModelImpl
{
  private MainModelImpl          mainModel;
  private SideBarControllerSwing controller;

  public void setMainModel(MainModelImpl model)
  {
    mainModel = model;
  }


  public void setController(SideBarControllerSwing controller)
  {
    this.controller = controller;
  }


  public void refresh()
  {

  }


  public void projectsClicked()
  {
    mainModel.switchToProjects();
  }


  public void personalStatisticClicked()
  {
    mainModel.switchToPersonalStatistics();
  }


  public void administrationClicked()
  {
    mainModel.switchToAdministration();
  }


  public void settingsClicked()
  {
    mainModel.switchToSettings();
  }


  public void projectStatisticClicked()
  {
    mainModel.switchToProjectStatistic();
  }


  public void logoutClicked()
  {
    mainModel.logout();
  }
}
