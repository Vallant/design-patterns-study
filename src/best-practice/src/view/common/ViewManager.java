/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.common;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import view.interfaces.MainView;
import view.javafx.MainViewFX;
import view.swing.MainViewSwing;

/**
 * @author stephan
 */
public class ViewManager
{
  private static MainView view;

  public static void initInstance(String frontend)
  {
    assert (view == null);
    switch(frontend)
    {
      case "swing":
        view = new MainViewSwing();
        break;
      case "javafx":
        MainViewFX fx = MainViewFX.getInstance();
        launchFx(fx);
        break;
      default:
        throw new UnsupportedOperationException();
    }
  }

  private static void launchFx(MainViewFX fx)
  {
    new Thread(() -> fx.launchThis()).start();
    view = fx;
    while(MainViewFX.getInstance().getStage() == null)
      try
      {
        Thread.sleep(100);
      }
      catch(InterruptedException e)
      {
        e.printStackTrace();
      }
  }

  public static MainView getInstance()
  {
    assert (view != null);
    return view;
  }
}
