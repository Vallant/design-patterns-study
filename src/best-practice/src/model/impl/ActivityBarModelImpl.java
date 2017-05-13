/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.impl;

import controller.interfaces.ActivityBarController;
import data.User;
import model.interfaces.ActivityBarModel;
import model.interfaces.MainModel;

/**
 *
 * @author stephan
 */
public class ActivityBarModelImpl implements ActivityBarModel
{
    
    private ActivityBarController controller;
    private MainModel mainModel;
    private User user;
    
    ActivityBarModelImpl()
    {
        
    }

    @Override
    public void setMainModel(MainModel mainModel)
    {
        this.mainModel = mainModel;
    }

    @Override
    public void setController(ActivityBarController controller)
    {
        this.controller = controller;
    }

    @Override
    public void setUser(User user)
    {
        this.user = user;
    }
    
}
