/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.interfaces;

import model.interfaces.MainModel;

/**
 *
 * @author stephan
 */
public interface MainController
{
    public void init(String frontend);
    public void setModel(MainModel model);
    
    public void switchToLogin();
    public void switchToMainView();
    public void switchToAdminView();
    public void switchToStatisticView();
}
