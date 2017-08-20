/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.common;

import controller.javafx.MainControllerFX;
import controller.swing.MainControllerSwing;

/**
 * @author stephan
 */
public class ControllerManager
{
  private static MainControllerSwing controllerSwing;
  private static MainControllerFX    controllerFX;

  public static void initInstance(String frontend)
  {
    assert (controllerSwing == null);
    switch(frontend)
    {
      case "swing":
        controllerSwing = new MainControllerSwing();
        break;
      case "javafx":
        controllerFX = new MainControllerFX();
        break;
      default:
        throw new UnsupportedOperationException();
    }

    if(controllerSwing != null)
      controllerSwing.init(frontend);
    else
      controllerFX.init(frontend);
  }

  public static MainControllerSwing getInstanceSwing()
  {
    assert (controllerSwing != null);
    return controllerSwing;
  }

  public static MainControllerFX getInstanceFX()
  {
    assert (controllerFX != null);
    return controllerFX;
  }
}
