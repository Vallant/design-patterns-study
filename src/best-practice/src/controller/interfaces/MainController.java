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
    public void init(String frontend);
    
    public void setModel(MainModel model);
    
    public void switchToLogin();
    public void switchToProjectView();
    public void switchToAdminView();
    public void switchToStatisticView();
    
    public void showActivityBar();
    
    public void pairLogin(LoginModel model);
    public void pairProject(ProjectModel model);
    public void pairActivityBar(ActivityBarModel model);
}
