/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.impl;

import controller.common.ControllerManager;
import controller.interfaces.MainController;
import data.User;
import db.common.DBManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.interfaces.ActivityBarModel;
import model.interfaces.LoginModel;
import model.interfaces.MainModel;
import model.interfaces.ProjectModel;

/**
 *
 * @author stephan
 */
public class MainModelImpl implements MainModel
{
    private final MainController controller;
    
    private final LoginModel login;
    private final ProjectModel project;
    private final ActivityBarModel activityBar;
    
    private User user;
    private final DBManager db;
    
    
    MainModelImpl(String driver, String url, String username, String password, String frontend) throws Exception
    {
        
        db = DBManager.get(driver, url, username, password);
        ControllerManager.initInstance(frontend);
        
        controller = ControllerManager.getInstance();
        controller.setModel(this);
        
        login = new LoginModelImpl();
        pairLogin();
        project = new ProjectModelImpl();
        pairProject();
        activityBar = new ActivityBarModelImpl();
        pairActivityBar();
        
        controller.switchToLogin();
    }
    
    public static void main(String[] args)
    {
        try
        {
            new MainModelImpl(args[0], args[1], args[2], args[3], args[4]);
        }
        catch (Exception ex)
        {
            Logger.getLogger(MainModelImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @Override
    public void loginSuccessfulFor(User user)
    {
        this.user = user;
        activityBar.setUser(user);
        project.setUser(user);
        controller.switchToProjectView();
    }

    private void pairLogin()
    {
        login.setMainModel(this);
        controller.pairLogin(login);
    }

    private void pairActivityBar()
    {
        activityBar.setMainModel(this);
        controller.pairActivityBar(activityBar);
    }

    private void pairProject()
    {
        project.setMainModel(this);
        controller.pairProject(project);   
    }

    @Override
    public DBManager DB()
    {
        return DB();
    }
}

