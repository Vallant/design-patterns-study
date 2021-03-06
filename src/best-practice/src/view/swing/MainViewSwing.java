/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.swing;

import controller.interfaces.*;
import data.User;
import view.interfaces.*;
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
public class MainViewSwing implements MainView
{

  private final JFrame                frame;
  private final LoginView             login;
  private final ProjectView           project;
  private final SettingsView          settings;
  private final PersonalStatisticView personalStatistic;
  //private final AdministrationView administration;
  private final ActivityBarView       activityBar;
  private final SideBarView           sideBar;
  private final ProjectStatisticView  projectStatistic;
  private       JPanel                loginPanel;
  private       JMenuBar              menuBar;
  private       JMenu                 file;
  private       JMenuItem             logout;


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


  @Override
  public void showLoginView()
  {
    hideCenterContent();
    login.switchToLogin();
    frame.pack();
  }

  @Override
  public void showProjectView()
  {
    login.removeAllComponents();
    personalStatistic.RemoveAllComponents();
    projectStatistic.hide();
    project.showOverview();
    frame.pack();
  }

  @Override
  public void setMainController(MainController controller)
  {
  }

  @Override
  public void pairLogin(LoginController controller)
  {
    controller.setView(login);
    login.setController(controller);
  }

  @Override
  public void pairProject(ProjectController controller)
  {
    controller.setView(project);
    project.setController(controller);
  }

  @Override
  public void pairActivityBar(ActivityBarController controller)
  {
    activityBar.setController(controller);
    controller.setView(activityBar);
  }

  @Override
  public void showError(String message)
  {
    JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void showActivityBar()
  {
    activityBar.show();
  }

  @Override
  public void pairSideBar(SideBarController controller)
  {
    sideBar.setController(controller);
    controller.setView(sideBar);
  }

  @Override
  public void showSideBar(User.ROLE role)
  {
    sideBar.show(role);
  }

  @Override
  public void hideCenterContent()
  {
    project.hide();
    settings.hide();
    personalStatistic.hide();
    projectStatistic.hide();
    //administration.hide();
  }

  @Override
  public void showAdminView()
  {

  }

  @Override
  public void showPersonalStatisticView()
  {
    personalStatistic.showProjectView();

  }

  @Override
  public void showProjectStatisticView()
  {
    projectStatistic.showProjectView();
  }

  @Override
  public void showSettingsView()
  {
    hideCenterContent();
    settings.show();
  }

  @Override
  public void pairPersonalStatistic(PersonalStatisticController controller)
  {
    controller.setView(personalStatistic);
    personalStatistic.setController(controller);
  }

  @Override
  public void pairProjectStatistic(ProjectStatisticController controller)
  {
    controller.setView(projectStatistic);
    projectStatistic.setController(controller);
  }

  @Override
  public void hideAll()
  {
    hideCenterContent();
    sideBar.hide();
    activityBar.hide();
  }

  @Override
  public void pairSettings(SettingsController controller)
  {
    controller.setView(settings);
    settings.setController(controller);
  }


}
