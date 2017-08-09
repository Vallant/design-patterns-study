/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.swing;

import model.impl.ActivityBarModelImpl;
import view.swing.activitybar.ActivityBarViewSwing;

import java.util.ArrayList;

/**
 * @author stephan
 */
public class ActivityBarControllerSwing
{
  private ActivityBarModelImpl model;
  private ActivityBarViewSwing view;


  public ActivityBarControllerSwing()
  {
  }

  public void setModel(ActivityBarModelImpl model)
  {
    this.model = model;
  }

  public void setView(ActivityBarViewSwing view)
  {
    this.view = view;
  }

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

  public void startClicked()
  {
    model.startClicked();
  }

  public void stopClicked()
  {
    model.stopClicked();
  }

  public void projectSelected(String project)
  {
    ArrayList<String> phases = null;
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

  public void phaseSelected(String projectPhase)
  {
    if(projectPhase == null || projectPhase.isEmpty())
      view.disableStart();
    else
      view.enableStart();
  }

  public void disableComboBoxes()
  {
    view.disableComboBoxes();
  }

  public void showCommentDescriptionDialog()
  {
    view.showCommentDescriptionDialog();
  }

  public void startTimer()
  {
    view.startTimer();
  }


  public void stopTimer()
  {
    view.stopTimer();
  }


  public void disableStartButton()
  {
    view.disableStart();
  }


  public void disableStopButton()
  {
    view.disableStop();
  }


  public void enableStartButton()
  {
    view.enableStart();
  }


  public void enableStopButton()
  {
    view.enableStop();
  }


  public void enableComboBoxes()
  {
    view.enableComboBoxes();
  }


  public void discardActivity()
  {
    model.discardActivity();
  }


  public void finishActivity()
  {
    view.showFinishActivityDialog();
  }


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
