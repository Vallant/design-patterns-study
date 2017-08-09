package controller.swing;

import model.impl.SideBarModelImpl;
import view.swing.SideBarViewSwing;

/**
 * Created by stephan on 08.07.17.
 */
public class SideBarControllerSwing
{
  private SideBarViewSwing view;
  private SideBarModelImpl model;


  public void setView(SideBarViewSwing view)
  {
    this.view = view;
  }


  public void setModel(SideBarModelImpl model)
  {
    this.model = model;
  }


  public void projectsClicked()
  {
    model.projectsClicked();
  }


  public void personalStatisticClicked()
  {
    model.personalStatisticClicked();
  }


  public void administrationClicked()
  {
    model.administrationClicked();
  }


  public void settingsClicked()
  {
    model.settingsClicked();
  }


  public void projectStatisticClicked()
  {
    model.projectStatisticClicked();
  }


  public void logoutClicked()
  {
    model.logoutClicked();
  }
}
