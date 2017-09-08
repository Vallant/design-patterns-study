/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.common;

import view.swing.MainViewSwing;

/**
 * @author stephan
 */
public class ViewManager
{
  private static MainViewSwing view;

  public static void initInstance(String frontend)
  {
    assert (view == null);
    switch(frontend)
    {
      case "standard":
        view = new MainViewSwing();
        break;
      default:
        throw new UnsupportedOperationException();
    }
  }

  public static MainViewSwing getInstance()
  {
    assert (view != null);
    return view;
  }
}
