/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.impl;

import controller.javafx.ActivityBarControllerFX;
import controller.swing.ActivityBarControllerSwing;
import data.Activity;
import data.Project;
import data.ProjectPhase;
import data.User;

import java.time.ZonedDateTime;
import java.util.ArrayList;

/**
 * @author stephan
 */
public class ActivityBarModelImpl
{
  private ActivityBarControllerSwing controllerSwing;
  private ActivityBarControllerFX    controllerFX;
  private MainModelImpl              mainModel;
  private User                       user;

  private ZonedDateTime start;
  private ZonedDateTime stop;
  private boolean       ongoingActivity;

  ActivityBarModelImpl()
  {
    ongoingActivity = false;
  }


  public void setMainModel(MainModelImpl mainModel)
  {
    this.mainModel = mainModel;
  }


  public void setController(ActivityBarControllerSwing controller)
  {
    this.controllerSwing = controller;
  }

  public void setController(ActivityBarControllerFX controller)
  {
    this.controllerFX = controller;
  }


  public void setUser(User user)
  {
    this.user = user;
  }


  public void startClicked()
  {
    ongoingActivity = true;
    start = ZonedDateTime.now();
    if(controllerSwing != null)
    {
      controllerSwing.startTimer();
      controllerSwing.disableStartButton();
      controllerSwing.enableStopButton();
    }
    else
    {
      controllerFX.startTimer();
      controllerFX.disableStartButton();
      controllerFX.enableStopButton();
    }

  }


  public void stopClicked()
  {
    assert (!ongoingActivity);
    stop = ZonedDateTime.now();
    if(controllerSwing != null)
    {
      controllerSwing.disableComboBoxes();
      controllerSwing.showCommentDescriptionDialog();
    }
    else
    {
      controllerFX.disableComboBoxes();
      controllerFX.showCommentDescriptionDialog();
    }

    ongoingActivity = false;
  }


  public ArrayList<String> getProjectPhasesFor(String project) throws Exception
  {
    return ProjectPhase.getNamesByProjectName(project, mainModel.db());

  }


  public void refresh()
  {
    if(controllerSwing != null)
      controllerSwing.refresh();
    else
      controllerFX.refresh();
  }


  public ArrayList<String> getProjects() throws Exception
  {
    return Project.getProjectsByUserName(user.getLoginName(), mainModel.db());
  }


  public void activityFinished(String projectName, String projectPhaseName, String description, String comment)
    throws Exception
  {
    if(controllerSwing != null)
    {
      controllerSwing.disableStopButton();
      controllerSwing.enableStartButton();
      controllerSwing.stopTimer();
      controllerSwing.enableComboBoxes();
    }
    else
    {
      controllerFX.disableStopButton();
      controllerFX.enableStartButton();
      controllerFX.stopTimer();
      controllerFX.enableComboBoxes();
    }


    ProjectPhase projectPhase = ProjectPhase.getByProjectAndPhaseName(projectName, projectPhaseName, mainModel.db());
    Activity activity = new Activity(projectPhase, user, description, start, stop, comment);
    activity.insertIntoDb(mainModel.db());
  }


  public void discardActivity()
  {
    if(controllerSwing != null)
    {
      controllerSwing.disableStopButton();
      controllerSwing.enableStartButton();
      controllerSwing.stopTimer();
      controllerSwing.enableComboBoxes();
    }
    else
    {
      controllerFX.disableStopButton();
      controllerFX.enableStartButton();
      controllerFX.stopTimer();
      controllerFX.enableComboBoxes();
    }

    ongoingActivity = false;

  }


  public void finishActivity()
  {
    if(ongoingActivity)
    {
      if(controllerSwing != null)
        controllerSwing.finishActivity();
      else
        controllerFX.finishActivity();
    }
  }

}
