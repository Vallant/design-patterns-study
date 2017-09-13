/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.common;

import view.javafx.MainViewFX;
import view.swing.MainViewSwing;

/**
 * @author stephan
 */
public class ViewManager
{
  private static MainViewSwing viewSwing;
  private static MainViewFX    viewFX;

  public static void initInstance(String frontend)
  {
    assert (viewSwing == null && viewFX == null);
    switch(frontend)
    {
      case "swing":
        viewSwing = new MainViewSwing();
        break;
      case "javafx":
        viewFX = MainViewFX.getInstance();
        launchFx(viewFX);
        break;
      default:
        throw new UnsupportedOperationException();
    }
  }

  public static MainViewSwing getInstanceSwing()
  {
    assert (viewSwing != null);
    return viewSwing;
  }

  public static MainViewFX getInstanceFX()
  {
    assert (viewFX != null);
    return viewFX;
  }

  private static void launchFx(MainViewFX fx)
  {
    new Thread(fx::launchThis).start();
    while(MainViewFX.getInstance().getMainStage() == null)
    {
      try
      {
        Thread.sleep(100);
      }
      catch(InterruptedException e)
      {
        e.printStackTrace();
      }
    }
  }
}
