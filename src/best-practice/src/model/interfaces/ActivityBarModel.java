/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.interfaces;

import controller.interfaces.ActivityBarController;
import data.User;

import java.util.ArrayList;

/**
 * @author stephan
 */
public interface ActivityBarModel
{
  void setMainModel(MainModel mainModel);

  void setController(ActivityBarController controller);

  void setUser(User user);

  void startClicked();

  void stopClicked();

  ArrayList<String> getProjectPhasesFor(String project) throws Exception;

  void refresh();

  ArrayList<String> getProjects() throws Exception;

  void activityFinished(String project, String projectPhase, String description, String comment) throws Exception;

  void discardActivity();

  void finishActivity();
}
