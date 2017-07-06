/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.interfaces;

import model.interfaces.ProjectModel;
import view.interfaces.ProjectView;

/**
 *
 * @author stephan
 */
public interface ProjectController
{
    void setModel(ProjectModel model);
    void setView(ProjectView view);

    void refresh();
}
