/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.interfaces;

import controller.interfaces.ProjectController;
import data.User;

/**
 *
 * @author stephan
 */
public interface ProjectModel
{
    void setMainModel(MainModel mainModel);
    void setUser(User user);
    void setController(ProjectController controller);

    void refresh();
}
