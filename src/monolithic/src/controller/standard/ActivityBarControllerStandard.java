/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.standard;

import model.impl.ActivityBarModelImpl;
import view.javafx.activitybar.ActivityBarViewFX;
import view.swing.activitybar.ActivityBarViewSwing;

import java.util.ArrayList;

/**
 * @author stephan
 */
public class ActivityBarControllerStandard
{
  private ActivityBarModelImpl model;
  private ActivityBarViewSwing viewSwing;
  private ActivityBarViewFX    viewFX;


  public ActivityBarControllerStandard()
  {
  }

  public void setModel(ActivityBarModelImpl model)
  {
    this.model = model;
  }

  public void setViewSwing(ActivityBarViewSwing viewSwing)
  {
    this.viewSwing = viewSwing;
  }
  public void setViewFX(ActivityBarViewFX viewFX)
  {
    this.viewFX = viewFX;
  }

  public void refresh()
  {
    try
    {
      if(viewSwing != null)
        viewSwing.setProjects(model.getProjects());
      else
        viewFX.setProjects(model.getProjects());
    }
    catch(Exception e)
    {
      e.printStackTrace();
      if(viewSwing != null)
        viewSwing.showError(e.getLocalizedMessage());
      else
        viewFX.showError(e.getLocalizedMessage());
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
      if(viewSwing != null)
        viewSwing.setProjectPhases(phases);
      else
        viewFX.setProjectPhases(phases);
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
      if(viewSwing != null)
        viewSwing.showError(ex.getLocalizedMessage());
      else
        viewFX.showError(ex.getLocalizedMessage());
    }
  }

  public void phaseSelected(String projectPhase)
  {
    if(projectPhase == null || projectPhase.isEmpty())
    {
      if(viewSwing != null)
        viewSwing.disableStart();
      else
        viewFX.disableStart();
    }
    else
    {
      if(viewSwing != null)
        viewSwing.enableStart();
      else
        viewFX.enableStart();
    }

  }

  public void disableComboBoxes()
  {
    if(viewSwing != null)
      viewSwing.disableComboBoxes();
    else
      viewFX.disableComboBoxes();
  }

  public void showCommentDescriptionDialog()
  {
    if(viewSwing != null)
      viewSwing.showCommentDescriptionDialog();
    else
      viewFX.showCommentDescriptionDialog();
  }

  public void startTimer()
  {
    if(viewSwing != null)
      viewSwing.startTimer();
    else
      viewFX.startTimer();
  }


  public void stopTimer()
  {
    if(viewSwing != null)
      viewSwing.stopTimer();
    else
      viewFX.stopTimer();
  }


  public void disableStartButton()
  {
    if(viewSwing != null)
      viewSwing.disableStart();
    else
      viewFX.disableStart();
  }


  public void disableStopButton()
  {
    if(viewSwing != null)
      viewSwing.disableStop();
    else
      viewFX.disableStop();
  }


  public void enableStartButton()
  {
    if(viewSwing != null)
      viewSwing.enableStart();
    else
      viewFX.enableStart();
  }


  public void enableStopButton()
  {
    if(viewSwing != null)
      viewSwing.enableStop();
    else
      viewFX.enableStop();
  }

  public void enableComboBoxes()
  {
    if(viewSwing != null)
      viewSwing.enableComboBoxes();
    else
      viewFX.enableComboBoxes();
  }


  public void discardActivity()
  {
    model.discardActivity();
  }


  public void finishActivity()
  {
    if(viewSwing != null)
      viewSwing.showFinishActivityDialog();
    else
      viewFX.showFinishActivityDialog();
  }


  public void activityFinished(String project, String projectPhase, String description, String comment)
  {
    try
    {
      if(description.isEmpty())
      {
        if(viewSwing != null)
        {
          viewSwing.showError("Activity Description cannot be empty");
          viewSwing.showCommentDescriptionDialog();
        }
        else
        {
          viewFX.showError("Activity Description cannot be empty");
          viewFX.showCommentDescriptionDialog();
        }

      }
      else
        model.activityFinished(project, projectPhase, description, comment);
    }
    catch(Exception e)
    {
      e.printStackTrace();
      if(viewSwing != null)
        viewSwing.showError(e.getLocalizedMessage());
      else
        viewFX.showError(e.getLocalizedMessage());
    }
  }

}
