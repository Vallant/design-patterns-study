/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.interfaces;

import controller.interfaces.LoginController;
import data.User;

/**
 *
 * @author stephan
 */
public interface LoginModel
{
    void resetPassword(String email);
    void login(String username, char[] password);
    void addUser(User user);
    
    void setController(LoginController controller);
}
