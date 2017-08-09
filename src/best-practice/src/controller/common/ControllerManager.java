/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.common;

import controller.interfaces.MainController;
import controller.swing.MainControllerSwing;

/**
 * @author stephan
 */
public class ControllerManager
{
  private static MainController controller;

  public static void initInstance(String frontend)
  {
    assert (controller == null);
    switch(frontend)
    {
      case "swing":
        controller = new MainControllerSwing();
        break;
      default:
        throw new UnsupportedOperationException();
    }

    controller.init(frontend);
  }

  public static MainController getInstance()
  {
    assert (controller != null);
    return controller;
  }
}
