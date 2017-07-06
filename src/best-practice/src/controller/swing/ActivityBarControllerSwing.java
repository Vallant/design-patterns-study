/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.swing;

import controller.interfaces.ActivityBarController;
import data.ProjectPhase;
import exception.ElementNotFoundException;
import model.interfaces.ActivityBarModel;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import view.interfaces.ActivityBarView;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 *
 * @author stephan
 */
public class ActivityBarControllerSwing implements ActivityBarController
{
    private ActivityBarModel model;
    private ActivityBarView view;
    
    @Override
    public void setModel(ActivityBarModel model)
    {
        this.model = model;
    }

    @Override
    public void setView(ActivityBarView view)
    {
        this.view = view;
    }

    @Override
    public void StartStopClicked() {
        model.startStopClicked();
    }

    @Override
    public void ProjectSelected(String project) {
        ArrayList<String> phases = null;
        try {
            phases = model.getProjectPhasesFor(project);
            view.setProjectPhases(phases);
        } catch (Exception ex)
        {
            view.showError(ex.getLocalizedMessage());
        }


    }

    @Override
    public void PhaseSelected(String projectPhase)
    {
        view.enableStartStop();
    }

    @Override
    public void refresh() {
        try {
            view.setProjects(model.getProjects());
        } catch (Exception e) {
            view.showError(e.getLocalizedMessage());
        }
    }

}
