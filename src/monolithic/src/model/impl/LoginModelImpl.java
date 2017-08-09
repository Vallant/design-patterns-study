/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.impl;

import controller.interfaces.LoginController;
import data.User;
import db.interfaces.UserRepository;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Random;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import model.interfaces.LoginModel;
import model.interfaces.MainModel;

/**
 *
 * @author stephan
 */
public class LoginModelImpl implements LoginModel
{
    private LoginController controller;
    private MainModel mainModel;

    public LoginModelImpl()
    {

    }



    @Override
    public void resetPassword(String email)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void login(String username, char[] password) throws Exception
    {
        UserRepository repo = mainModel.db().getUserRepository();

        User u = repo.getByPrimaryKey(username);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        Random r = new SecureRandom();
        KeySpec ks = new PBEKeySpec(password, u.getSalt(), 1000, 512);
        SecretKey generateSecret = skf.generateSecret(ks);

        if(!Arrays.equals(generateSecret.getEncoded(), u.getPassword()))
            controller.loginFailed();
        else
            mainModel.loginSuccessfulFor(u);
    }

    @Override
    public void saveNewUser(User user) throws Exception
    {
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        Random r = new SecureRandom();
        byte[] salt = new byte[128];
        r.nextBytes(salt);
        KeySpec ks = new PBEKeySpec(user.getNewPassword(), salt, 1000, 512);
        SecretKey generateSecret = skf.generateSecret(ks);
        user.setPassword(generateSecret.getEncoded());
        user.setSalt(salt);

        UserRepository repo = mainModel.db().getUserRepository();
        repo.add(user);

        controller.showDialog("User creation successful");
        controller.backToLoginClicked();
    }

    @Override
    public void setController(LoginController controller)
    {
        this.controller = controller;
    }

    @Override
    public void setMainModel(MainModel mainModel)
    {
        this.mainModel = mainModel;
    }
    
}
