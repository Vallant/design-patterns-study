/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.swing;

import controller.standard.*;
import data.User;
import view.swing.activitybar.ActivityBarViewSwing;
import view.swing.login.LoginViewSwing;
import view.swing.personalstatistic.PersonalStatisticViewSwing;
import view.swing.project.ProjectViewSwing;
import view.swing.projectstatistic.ProjectStatisticViewSwing;
import view.swing.settings.SettingsViewSwing;

import javax.swing.*;

/**
 * @author stephan
 */
public class MainViewSwing
{

  private final JFrame frame;
  private       JPanel loginPanel;

  private final LoginViewSwing             login;
  private final ProjectViewSwing           project;
  private final SettingsViewSwing          settings;
  private final PersonalStatisticViewSwing personalStatistic;
  //private final AdministrationView administration;
  private final ActivityBarViewSwing       activityBar;
  private final SideBarViewSwing           sideBar;
  private final ProjectStatisticViewSwing  projectStatistic;
  private       JMenuBar                   menuBar;
  private       JMenu                      file;
  private       JMenuItem                  logout;
  private       MainControllerStandard     controller;


  public MainViewSwing()
  {
    this.frame = new JFrame("Design Pattern Case Study");
    frame.setLocationRelativeTo(null);
    frame.getRootPane().setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    //frame.setSize(500,500);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);

    login = new LoginViewSwing(frame);
    project = new ProjectViewSwing(frame);
    activityBar = new ActivityBarViewSwing(frame);
    sideBar = new SideBarViewSwing(frame);
    personalStatistic = new PersonalStatisticViewSwing(frame);
    projectStatistic = new ProjectStatisticViewSwing(frame);
    settings = new SettingsViewSwing(frame);
  }


  public void showLoginView()
  {
    hideCenterContent();
    login.switchToLogin();
    frame.pack();
  }


  public void showProjectView()
  {
    login.removeAllComponents();
    personalStatistic.RemoveAllComponents();
    projectStatistic.hide();
    project.showOverview();
    frame.pack();
  }


  public void setMainController(MainControllerStandard controller)
  {
    this.controller = controller;
  }


  public void pairLogin(LoginControllerStandard controller)
  {
    controller.setViewSwing(login);
    login.setController(controller);
  }


  public void pairProject(ProjectControllerStandard controller)
  {
    controller.setViewSwing(project);
    project.setController(controller);
  }


  public void pairActivityBar(ActivityBarControllerStandard controller)
  {
    activityBar.setActivityBarController(controller);
    controller.setViewSwing(activityBar);
  }


  public void showError(String message)
  {
    JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
  }


  public void showActivityBar()
  {
    activityBar.show();
  }


  public void pairSideBar(SideBarControllerStandard controller)
  {
    sideBar.setController(controller);
    controller.setViewSwing(sideBar);
  }


  public void showSideBar(User.ROLE role)
  {
    sideBar.show(role);
  }


  public void hideCenterContent()
  {
    project.hide();
    settings.hide();
    personalStatistic.hide();
    projectStatistic.hide();
    //administration.hide();
  }


  public void showAdminView()
  {

  }


  public void showPersonalStatisticView()
  {
    personalStatistic.showProjectView();

  }


  public void showProjectStatisticView()
  {
    projectStatistic.showProjectView();
  }


  public void showSettingsView()
  {
    hideCenterContent();
    settings.show();
  }


  public void pairPersonalStatistic(PersonalStatisticControllerStandard controller)
  {
    controller.setViewSwing(personalStatistic);
    personalStatistic.setController(controller);
  }


  public void pairProjectStatistic(ProjectStatisticControllerStandard controller)
  {
    controller.setViewSwing(projectStatistic);
    projectStatistic.setController(controller);
  }


  public void hideAll()
  {
    hideCenterContent();
    sideBar.hide();
    activityBar.hide();
  }


  public void pairSettings(SettingsControllerStandard controller)
  {
    controller.setViewSwing(settings);
    settings.setController(controller);
  }


}
