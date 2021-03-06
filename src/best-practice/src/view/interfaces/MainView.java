/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.interfaces;

import controller.interfaces.*;
import data.User;

/**
 * @author stephan
 */
public interface MainView
{
  void setMainController(MainController controller);

  void showLoginView();

  void showProjectView();

  void pairLogin(LoginController controller);

  void pairProject(ProjectController controller);

  void pairActivityBar(ActivityBarController controller);

  void showError(String message);

  void showActivityBar();

  void pairSideBar(SideBarController sideBar);

  void showSideBar(User.ROLE role);

  void hideCenterContent();

  void showAdminView();

  void showPersonalStatisticView();

  void showProjectStatisticView();

  void showSettingsView();

  void pairPersonalStatistic(PersonalStatisticController statistics);

  void pairProjectStatistic(ProjectStatisticController projectStatistic);

  void hideAll();

  void pairSettings(SettingsController settings);
}
