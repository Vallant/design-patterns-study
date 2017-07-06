/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.interfaces;

import controller.interfaces.ProjectController;

/**
 *
 * @author stephan
 */
public interface ProjectView
{
    void setController(ProjectController controller);
    void show();
}
