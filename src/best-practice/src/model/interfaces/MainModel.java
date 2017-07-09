/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.interfaces;

import data.User;
import db.common.DBManager;

/**
 *
 * @author stephan
 */
public interface MainModel
{
    void loginSuccessfulFor(User user);
    void showError(Exception ex);
    DBManager DB();

    void switchedToStatistics();
    void switchedToProjects();
    void switchedToAdministration();
    void switchedToSettings();

    void refreshActivityBar();
    //void switchedToHelp();
}
