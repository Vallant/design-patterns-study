/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.swing;

import controller.interfaces.ActivityBarController;
import model.interfaces.ActivityBarModel;
import view.interfaces.ActivityBarView;

/**
 *
 * @author stephan
 */
public class ActivityBarControllerSwing implements ActivityBarController
{
    private ActivityBarModel model;
    private ActivityBarView view;
    
    @Override
    public void setModel(ActivityBarModel model)
    {
        this.model = model;
    }

    @Override
    public void setView(ActivityBarView view)
    {
        this.view = view;
    }
    
}
