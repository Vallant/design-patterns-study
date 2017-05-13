/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.swing;

import controller.interfaces.ActivityBarController;
import javax.swing.JFrame;
import view.interfaces.ActivityBarView;

/**
 *
 * @author stephan
 */
public class ActivityBarViewSwing implements ActivityBarView
{
    private final JFrame frame;
    private ActivityBarController controller;

    public ActivityBarViewSwing(JFrame frame)
    {
        this.frame = frame;
    }

    @Override
    public void setActivityBarController(ActivityBarController controller)
    {
        this.controller = controller;
    }
    
    
}
