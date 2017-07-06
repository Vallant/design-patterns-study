/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.swing;

import controller.interfaces.ProjectController;
import model.interfaces.ProjectModel;
import view.interfaces.ProjectView;

/**
 *
 * @author stephan
 */
public class ProjectControllerSwing implements ProjectController
{
    private ProjectModel model;
    private ProjectView view;
    
    @Override
    public void setModel(ProjectModel model)
    {
        this.model = model;
    }

    @Override
    public void setView(ProjectView view)
    {
        this.view = view;
    }

    @Override
    public void refresh()
    {

    }

}
