/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.impl;

import controller.javafx.LoginControllerFX;
import controller.swing.LoginControllerSwing;
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
  private LoginControllerSwing controllerSwing;
  private LoginControllerFX    controllerFX;
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
    User u = null;
    if(mainModel.db() != null)
      u = User.getByPrimaryKey(username, mainModel.db());
    else
      u = User.getByPrimaryKey(username, mainModel.dbMongo());

    SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
    Random r = new SecureRandom();
    KeySpec ks = new PBEKeySpec(password, u.getSalt(), 1000, 512);
    SecretKey generateSecret = skf.generateSecret(ks);

    if(!Arrays.equals(generateSecret.getEncoded(), u.getPassword()))
    {
      if(controllerSwing != null)
        controllerSwing.loginFailed();
      else
        controllerFX.loginFailed();
    }
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

    if(mainModel.db() != null)
      user.insertIntoDb(mainModel.db());
    else
      user.insertIntoDb(mainModel.dbMongo());

    if(controllerSwing != null)
    {
      controllerSwing.showDialog("User creation successful");
      controllerSwing.backToLoginClicked();
    }
    else
    {
      controllerFX.showDialog("User creation successful");
      controllerFX.backToLoginClicked();
    }

  }


  public void setController(LoginControllerSwing controllerSwing)
  {
    this.controllerSwing = controllerSwing;
  }

  public void setController(LoginControllerFX controllerFX)
  {
    this.controllerFX = controllerFX;
  }

  public void setMainModel(MainModelImpl mainModel)
  {
    this.mainModel = mainModel;
  }

}
