/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.interfaces;

import data.User;
import model.interfaces.ActivityBarModel;
import model.interfaces.LoginModel;
import model.interfaces.MainModel;
import model.interfaces.ProjectModel;

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
    void switchToStatisticView();
    
    void showActivityBar();
    
    void pairLogin(LoginModel model);
    void pairProject(ProjectModel model);
    void pairActivityBar(ActivityBarModel model);
    
    void showError(Exception ex);
}
