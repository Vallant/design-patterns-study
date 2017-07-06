/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.impl;

import controller.interfaces.ActivityBarController;
import data.User;
import db.interfaces.ProjectPhaseRepository;
import db.interfaces.ProjectRepository;
import model.interfaces.ActivityBarModel;
import model.interfaces.MainModel;

import java.util.ArrayList;

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

    @Override
    public void startStopClicked() {

    }

    @Override
    public ArrayList<String> getProjectPhasesFor(String project) throws Exception{
        ProjectPhaseRepository r = mainModel.DB().getProjectPhaseRepository();
        return r.getNamesByProjectName(project);

    }

    @Override
    public void refresh() {
        controller.refresh();
    }

    @Override
    public ArrayList<String> getProjects() throws Exception {
        ProjectRepository r = mainModel.DB().getProjectRepository();
        return r.getPhaseNamesByUserName(user.getLoginName());
    }

}
