/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.interfaces;

import controller.interfaces.ActivityBarController;
import data.User;

/**
 *
 * @author stephan
 */
public interface ActivityBarModel
{
    void setMainModel(MainModel mainModel);
    void setController(ActivityBarController controller);
    void setUser(User user);
}
