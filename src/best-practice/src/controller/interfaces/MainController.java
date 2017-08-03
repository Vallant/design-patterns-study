/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.interfaces;

import data.User;
import model.interfaces.*;

/**
 *
 * @author stephan
 */
public interface MainController
{
    void init(String frontend);
    
    void setModel(MainModel model);
    
    void switchToLogin();
    void switchToProjectView();
    void switchToAdminView();
    void switchToPersonalStatisticView();
    void switchToSettingsView();
    void switchToProjectStatisticView();
    
    void showActivityBar();
    void showSideBar(User.ROLE role);
    
    void pairLogin(LoginModel model);
    void pairProject(ProjectModel model);
    void pairActivityBar(ActivityBarModel model);
    void pairSideBar(SideBarModel sideBar);
    void pairPersonalStatistic(PersonalStatisticModel model);
    void pairProjectStatistic(ProjectStatisticModel projectStatistic);
    
    void showError(Exception ex);



}
