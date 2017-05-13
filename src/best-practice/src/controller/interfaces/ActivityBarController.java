/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.interfaces;

import model.interfaces.ActivityBarModel;
import view.interfaces.ActivityBarView;

/**
 *
 * @author stephan
 */
public interface ActivityBarController
{
    void setModel(ActivityBarModel model);
    void setView(ActivityBarView view);
}
