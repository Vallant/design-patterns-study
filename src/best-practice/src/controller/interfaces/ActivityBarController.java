/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.interfaces;

import model.interfaces.ActivityBarModel;
import view.interfaces.ActivityBarView;

/**
 * @author stephan
 */
public interface ActivityBarController
{
  //basic
  void setModel(ActivityBarModel model);

  void setView(ActivityBarView view);

  void refresh();

  //callbacks
  void startClicked();

  void stopClicked();


  void projectSelected(String project);

  void phaseSelected(String projectPhase);

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

  void finishActivity();

  void activityFinished(String project, String projectPhase, String description, String comment);
}
