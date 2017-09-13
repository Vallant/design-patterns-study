package controller.javafx;

import model.impl.SideBarModelImpl;
import view.javafx.SideBarViewFX;

public class SideBarControllerFX
{
  private SideBarModelImpl model;


  public void setView(SideBarViewFX view)
  {
    SideBarViewFX view1 = view;
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
