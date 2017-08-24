/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.impl;

import controller.interfaces.ActivityBarController;
import data.Activity;
import data.Project;
import data.ProjectPhase;
import data.User;
import db.interfaces.ActivityRepository;
import db.interfaces.ProjectPhaseRepository;
import db.interfaces.ProjectRepository;
import model.interfaces.ActivityBarModel;
import model.interfaces.MainModel;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

/**
 * @author stephan
 */
public class ActivityBarModelImpl implements ActivityBarModel
{

  private ZonedDateTime         start;
  private ZonedDateTime         stop;
  private boolean               ongoingActivity;
  private ActivityBarController controller;
  private MainModel             mainModel;
  private User                  user;

  ActivityBarModelImpl()
  {
    ongoingActivity = false;
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
  public void startClicked()
  {
    ongoingActivity = true;
    start = ZonedDateTime.now(ZoneId.systemDefault());
    controller.startTimer();
    controller.disableStartButton();
    controller.enableStopButton();
  }

  @Override
  public void stopClicked()
  {
    assert (!ongoingActivity);
    stop = ZonedDateTime.now(ZoneId.systemDefault());
    controller.disableComboBoxes();
    controller.showCommentDescriptionDialog();
    ongoingActivity = false;
  }

  @Override
  public ArrayList<String> getProjectPhasesFor(String project) throws Exception
  {
    ProjectPhaseRepository r = mainModel.db().getProjectPhaseRepository();
    return r.getNamesByProjectName(project);

  }

  @Override
  public void refresh()
  {
    controller.refresh();
  }

  @Override
  public ArrayList<String> getProjects() throws Exception
  {
    ProjectRepository r = mainModel.db().getProjectRepository();
    ArrayList<Project> projects = r.getProjectsByUserName(user.getLoginName());
    ArrayList<String> list = new ArrayList<>();
    for(Project p : projects)
      list.add(p.getName());
    return list;
  }

  @Override
  public void activityFinished(String projectName, String projectPhaseName, String description, String comment)
    throws Exception
  {
    controller.disableStopButton();
    controller.enableStartButton();
    controller.stopTimer();
    controller.enableComboBoxes();
    ProjectPhaseRepository ppr = mainModel.db().getProjectPhaseRepository();
    ProjectPhase projectPhase = ppr.getByProjectAndPhaseName(projectName, projectPhaseName);
    Activity activity = new Activity(projectPhase, user, description, start, stop, comment);
    ActivityRepository ar = mainModel.db().getActivityRepository();
    ar.add(activity);
  }

  @Override
  public void discardActivity()
  {
    controller.disableStopButton();
    controller.enableStartButton();
    controller.stopTimer();
    controller.enableComboBoxes();
    ongoingActivity = false;

  }

  @Override
  public void finishActivity()
  {
    if(ongoingActivity)
      controller.finishActivity();
  }

}
