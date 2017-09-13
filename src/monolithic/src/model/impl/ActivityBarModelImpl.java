/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.impl;

import controller.standard.ActivityBarControllerStandard;
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
  private ActivityBarControllerStandard controller;
  private MainModelImpl                 mainModel;
  private User                          user;

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


  public void setController(ActivityBarControllerStandard controller)
  {
    this.controller = controller;
  }


  public void setUser(User user)
  {
    this.user = user;
  }


  public void startClicked()
  {
    ongoingActivity = true;
    start = ZonedDateTime.now();
    controller.startTimer();
    controller.disableStartButton();
    controller.enableStopButton();
  }


  public void stopClicked()
  {
    assert (!ongoingActivity);
    stop = ZonedDateTime.now();
    controller.disableComboBoxes();
    controller.showCommentDescriptionDialog();
    ongoingActivity = false;
  }


  public ArrayList<String> getProjectPhasesFor(String project) throws Exception
  {
    return ProjectPhase.getNamesByProjectName(project, mainModel.db());

  }


  public void refresh()
  {
    controller.refresh();
  }


  public ArrayList<String> getProjects() throws Exception
  {
    return Project.getProjectsByUserName(user.getLoginName(), mainModel.db());
  }


  public void activityFinished(String projectName, String projectPhaseName, String description, String comment)
    throws Exception
  {
    controller.disableStopButton();
    controller.enableStartButton();
    controller.stopTimer();
    controller.enableComboBoxes();

    ProjectPhase projectPhase = ProjectPhase.getByProjectAndPhaseName(projectName, projectPhaseName, mainModel.db());
    Activity activity = new Activity(projectPhase, user, description, start, stop, comment);
    activity.insertIntoDb(mainModel.db());
  }


  public void discardActivity()
  {
    controller.disableStopButton();
    controller.enableStartButton();
    controller.stopTimer();
    controller.enableComboBoxes();
    ongoingActivity = false;

  }


  public void finishActivity()
  {
    if(ongoingActivity)
      controller.finishActivity();
  }

}
