/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.swing;

import controller.interfaces.ActivityBarController;
import model.interfaces.ActivityBarModel;
import view.interfaces.ActivityBarView;

import javax.swing.*;
import java.util.ArrayList;

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
    public void StartClicked() {
        model.startClicked();
    }

    @Override
    public void StopClicked() {
        model.stopClicked();
    }

    @Override
    public void ActivityFinished(String project, String projectPhase, String description, String comment) {
        try {
            model.activityFinished(project, projectPhase, description, comment);
        } catch (Exception e) {
            e.printStackTrace();
            view.showError(e.getLocalizedMessage());
        }
    }

    @Override
    public void ProjectSelected(String project) {
        ArrayList<String> phases = null;
        try {
            phases = model.getProjectPhasesFor(project);
            view.setProjectPhases(phases);
        } catch (Exception ex)
        {
            ex.printStackTrace();
            view.showError(ex.getLocalizedMessage());
        }
    }

    @Override
    public void PhaseSelected(String projectPhase)
    {
        if(projectPhase == null || projectPhase.isEmpty())
            view.disableStart();
        else
            view.enableStart();
    }

    @Override
    public void refresh() {
        try {
            view.setProjects(model.getProjects());
        } catch (Exception e) {
            e.printStackTrace();
            view.showError(e.getLocalizedMessage());
        }
    }

    @Override
    public void disableComboBoxes() {
        view.disableComboBoxes();
    }

    @Override
    public void showCommentDescriptionDialog() {
        view.showCommentDescriptionDialog();
    }

    @Override
    public void startTimer() {
        view.startTimer();
    }

    @Override
    public void stopTimer() {
        view.stopTimer();
    }

    @Override
    public void disableStartButton() {
        view.disableStart();
    }

    @Override
    public void disableStopButton() {
        view.disableStop();
    }

    @Override
    public void enableStartButton() {
        view.enableStart();
    }


    @Override
    public void enableStopButton() {
        view.enableStop();
    }

    @Override
    public void enableComboBoxes() {
        view.enableComboBoxes();
    }

    @Override
    public void discardActivity() {
        model.discardActivity();
    }

}
