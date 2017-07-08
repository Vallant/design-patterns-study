/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.interfaces;

import data.Project;
import data.ProjectPhase;
import model.interfaces.ActivityBarModel;
import view.interfaces.ActivityBarView;

/**
 *
 * @author stephan
 */
public interface ActivityBarController
{
    void setModel(ActivityBarModel model);
    void setView(ActivityBarView view);

    void StartClicked();
    void StopClicked();

    void ActivityFinished(String project, String projectPhase, String description, String comment);
    void ProjectSelected(String project);
    void PhaseSelected(String projectPhase);

    void refresh();

    void disableComboBoxes();

    void showCommentDescriptionDialog();


    void startTimer();
    void stopTimer();

    void disableStartButton();
    void disableStopButton();

    void enableStartButton();
    void enableStopButton();

    void enableComboBoxes();

    void discardActivity();
}
