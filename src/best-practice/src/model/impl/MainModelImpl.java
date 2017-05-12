/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.impl;

import controller.common.ControllerManager;
import controller.interfaces.MainController;
import db.common.DBManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.interfaces.LoginModel;
import model.interfaces.MainModel;

/**
 *
 * @author stephan
 */
public class MainModelImpl implements MainModel
{
    private final MainController controller;
    
    
    MainModelImpl(String driver, String url, String username, String password, String frontend) throws Exception
    {
        DBManager.initInstance(driver, url, username, password);
        ControllerManager.initInstance(frontend);
        controller = ControllerManager.getInstance();
        controller.setModel(this);
        
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
    public LoginModel getLoginModel()
    {
        return new LoginModelImpl();
    }
}

