/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.swing;

import controller.interfaces.ProjectController;
import javax.swing.JFrame;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import view.interfaces.ProjectView;

/**
 *
 * @author stephan
 */
public class ProjectViewSwing implements ProjectView
{
    private final JFrame frame;
    private ProjectController controller;
    
    ProjectViewSwing(JFrame frame)
    {
        this.frame = frame;
    }

    @Override
    public void setController(ProjectController controller)
    {
        this.controller = controller;
    }

    @Override
    public void show() {
        //throw new NotImplementedException();
    }

}
