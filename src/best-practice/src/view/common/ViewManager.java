/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.common;

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
        view = new MainViewFX();
        MainViewFX.launchThis();
        break;
      default:
        throw new UnsupportedOperationException();
    }
  }

  public static MainView getInstance()
  {
    assert (view != null);
    return view;
  }
}
