/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.interfaces;

import controller.interfaces.ActivityBarController;
import controller.interfaces.LoginController;
import controller.interfaces.MainController;
import controller.interfaces.ProjectController;

/**
 *
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
    
}
