/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.impl;

import controller.standard.LoginControllerStandard;
import data.User;

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
  private LoginControllerStandard controller;
  private MainModelImpl           mainModel;

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
    User u = null;
    if(mainModel.dbPostgres() != null)
      u = User.getByPrimaryKey(username, mainModel.dbPostgres());
    else
      u = User.getByPrimaryKey(username, mainModel.dbMongo());

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

    if(mainModel.dbPostgres() != null)
      user.insertIntoDb(mainModel.dbPostgres());
    else
      user.insertIntoDb(mainModel.dbMongo());

    controller.showDialog("User creation successful");
    controller.backToLoginClicked();
  }


  public void setController(LoginControllerStandard controller)
  {
    this.controller = controller;
  }


  public void setMainModel(MainModelImpl mainModel)
  {
    this.mainModel = mainModel;
  }

}
