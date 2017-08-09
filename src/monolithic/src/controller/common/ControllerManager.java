/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.common;

import controller.swing.MainControllerSwing;

/**
 * @author stephan
 */
public class ControllerManager
{
  private static MainControllerSwing controller;

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

  public static MainControllerSwing getInstance()
  {
    assert (controller != null);
    return controller;
  }
}
