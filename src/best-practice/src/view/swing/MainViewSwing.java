/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.swing;

import controller.interfaces.ActivityBarController;
import controller.interfaces.LoginController;
import controller.interfaces.MainController;
import controller.interfaces.ProjectController;
import data.User;

import javax.swing.*;

import view.interfaces.ActivityBarView;
import view.interfaces.LoginView;
import view.interfaces.MainView;
import view.interfaces.ProjectView;

/**
 *
 * @author stephan
 */
public class MainViewSwing implements MainView
{
    
    private final JFrame frame;
    private JPanel loginPanel;
    
    private final LoginView login;
    private final ProjectView project;
    private final ActivityBarView activityBar;

    private MainController controller;
    
    
    
    public MainViewSwing()
    {
        this.frame = new JFrame("Design Pattern Case Study");
        frame.setSize(350,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        login = new LoginViewSwing(frame);
        project = new ProjectViewSwing(frame);
        activityBar = new ActivityBarViewSwing(frame);
    }

    @Override
    public void showLoginView()
    {
        login.SwitchToLogin();
    }

    @Override
    public void showProjectView()
    {
        login.RemoveAllComponents();
        project.show();
    }

    @Override
    public void setMainController(MainController controller)
    {
        this.controller = controller;
    }

    @Override
    public void pairLogin(LoginController controller)
    {
        controller.SetView(login);
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
        activityBar.setActivityBarController(controller);
        controller.setView(activityBar);
    }

    @Override
    public void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void showActivityBar() {
        activityBar.show();
    }


}
