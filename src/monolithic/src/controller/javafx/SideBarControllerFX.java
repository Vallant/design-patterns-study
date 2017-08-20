package controller.javafx;

import model.impl.SideBarModelImpl;
import view.javafx.SideBarViewFX;

public class SideBarControllerFX
{
  private SideBarViewFX    view;
  private SideBarModelImpl model;


  
  public void setView(SideBarViewFX view)
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
