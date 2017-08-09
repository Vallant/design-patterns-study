/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.interfaces;

import controller.interfaces.ActivityBarController;
import data.ProjectPhase;

import java.util.ArrayList;

/**
 *
 * @author stephan
 */
public interface ActivityBarView
{
    void setActivityBarController(ActivityBarController controller);
    void show();

    void enableStart();
    void disableStart();
    void enableStop();
    void disableStop();

    void setProjectPhases(ArrayList<String> phases);
    void setProjects(ArrayList<String> projects);
    void startTimer();
    void stopTimer();

    void showError(String localizedMessage);


    void disableComboBoxes();

    void showCommentDescriptionDialog();

    void enableComboBoxes();

    void hide();

    void showFinishActivityDialog();
}
