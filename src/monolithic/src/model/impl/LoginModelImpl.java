/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.impl;

import controller.swing.LoginControllerSwing;
import data.User;
import db.interfaces.UserRepository;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Random;

/**
 * @author stephan
 */
public class LoginModelImpl
{
  private LoginControllerSwing controller;
  private MainModelImpl        mainModel;

  public LoginModelImpl()
  {

  }


  public void resetPassword(String email)
  {
    throw new UnsupportedOperationException(
      "Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }


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


  public void setController(LoginControllerSwing controller)
  {
    this.controller = controller;
  }


  public void setMainModel(MainModelImpl mainModel)
  {
    this.mainModel = mainModel;
  }

}
