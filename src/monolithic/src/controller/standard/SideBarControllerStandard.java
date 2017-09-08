package controller.standard;

import model.impl.SideBarModelImpl;
import view.javafx.SideBarViewFX;
import view.swing.SideBarViewSwing;

/**
 * Created by stephan on 08.07.17.
 */
public class SideBarControllerStandard
{
  private SideBarViewSwing viewSwing;
  private SideBarViewFX viewFX;
  private SideBarModelImpl model;


  public void setViewSwing(SideBarViewSwing viewSwing)
  {
    this.viewSwing = viewSwing;
  }

  public void setViewFX(SideBarViewFX viewFX)
  {
    this.viewFX = viewFX;
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
