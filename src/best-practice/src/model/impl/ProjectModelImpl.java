/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.impl;

import controller.interfaces.ProjectController;
import data.User;
import model.interfaces.MainModel;
import model.interfaces.ProjectModel;

/**
 *
 * @author stephan
 */
public class ProjectModelImpl implements ProjectModel
{
    private MainModel mainModel;
    private User user;
    private ProjectController controller;

    public ProjectModelImpl()
    {
        
    }

    @Override
    public void setMainModel(MainModel mainModel)
    {
        this.mainModel = mainModel;
    }

    @Override
    public void setUser(User user)
    {
        this.user = user;
    }

    @Override
    public void setController(ProjectController controller)
    {
        this.controller = controller;
    }

    @Override
    public void refresh() {
        controller.refresh();
    }


}
