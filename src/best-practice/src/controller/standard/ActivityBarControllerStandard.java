/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.standard;

import controller.interfaces.ActivityBarController;
import model.interfaces.ActivityBarModel;
import view.interfaces.ActivityBarView;

import java.util.ArrayList;

/**
 * @author stephan
 */
public class ActivityBarControllerStandard implements ActivityBarController
{
  private ActivityBarModel model;
  private ActivityBarView  view;


  public ActivityBarControllerStandard()
  {
  }


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
  public void refresh()
  {
    try
    {
      view.setProjects(model.getProjects());
    }
    catch(Exception e)
    {
      e.printStackTrace();
      view.showError(e.getLocalizedMessage());
    }
  }

  @Override
  public void startClicked()
  {
    model.startClicked();
  }

  @Override
  public void stopClicked()
  {
    model.stopClicked();
  }


  @Override
  public void projectSelected(String project)
  {
    ArrayList<String> phases;
    try
    {
      phases = model.getProjectPhasesFor(project);
      view.setProjectPhases(phases);
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
      view.showError(ex.getLocalizedMessage());
    }
  }

  @Override
  public void phaseSelected(String projectPhase)
  {
    if(projectPhase == null || projectPhase.isEmpty())
      view.disableStart();
    else
      view.enableStart();
  }

  @Override
  public void disableComboBoxes()
  {
    view.disableComboBoxes();
  }

  @Override
  public void showCommentDescriptionDialog()
  {
    view.showCommentDescriptionDialog();
  }

  @Override
  public void startTimer()
  {
    view.startTimer();
  }

  @Override
  public void stopTimer()
  {
    view.stopTimer();
  }

  @Override
  public void disableStartButton()
  {
    view.disableStart();
  }

  @Override
  public void disableStopButton()
  {
    view.disableStop();
  }

  @Override
  public void enableStartButton()
  {
    view.enableStart();
  }

  @Override
  public void enableStopButton()
  {
    view.enableStop();
  }

  @Override
  public void enableComboBoxes()
  {
    view.enableComboBoxes();
  }

  @Override
  public void discardActivity()
  {
    model.discardActivity();
  }

  @Override
  public void finishActivity()
  {
    view.showFinishActivityDialog();
  }

  @Override
  public void activityFinished(String project, String projectPhase, String description, String comment)
  {
    try
    {
      if(description.isEmpty())
      {
        view.showError("Activity Description cannot be empty");
        view.showCommentDescriptionDialog();
      }
      else
        model.activityFinished(project, projectPhase, description, comment);
    }
    catch(Exception e)
    {
      e.printStackTrace();
      view.showError(e.getLocalizedMessage());
    }
  }

}
