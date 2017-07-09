/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.swing;

import controller.interfaces.*;
import data.User;

import javax.swing.*;

import view.interfaces.*;

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
    //private final SettingsView settings;
    //private final StatisticsView statistics;
    //private final AdministrationView administration;
    private final ActivityBarView activityBar;
    private final SideBarView sideBar;

    private MainController controller;
    
    
    
    public MainViewSwing()
    {
        this.frame = new JFrame("Design Pattern Case Study");
        frame.setLocationRelativeTo(null);
        frame.getRootPane().setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        frame.setSize(350,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        login = new LoginViewSwing(frame);
        project = new ProjectViewSwing(frame);
        activityBar = new ActivityBarViewSwing(frame);
        sideBar = new SideBarViewSwing(frame);
    }

    @Override
    public void showLoginView()
    {
        login.SwitchToLogin();
        frame.pack();
    }

    @Override
    public void showProjectView()
    {
        login.RemoveAllComponents();
        project.showOverview();
        frame.pack();
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

    @Override
    public void pairSideBar(SideBarController controller) {
        sideBar.setController(controller);
        controller.setView(sideBar);
    }

    @Override
    public void showSideBar(User.ROLE role) {
        sideBar.show(role);
    }

    @Override
    public void hideCenterContent() {
        project.hide();
        //settings.hide();
        //statistics.hide();
        //administration.hide();
    }

    @Override
    public void showAdminView() {

    }

    @Override
    public void showStatisticsView() {

    }

    @Override
    public void showSettingsView() {

    }


}
