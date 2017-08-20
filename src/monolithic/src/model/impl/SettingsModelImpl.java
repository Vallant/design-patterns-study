package model.impl;

import controller.javafx.SettingsControllerFX;
import controller.swing.SettingsControllerSwing;
import data.User;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Random;


public class SettingsModelImpl
{
  private User                    user;
  private MainModelImpl           mainModel;
  private SettingsControllerSwing controllerSwing;
  private SettingsControllerFX    controllerFX;

  public void setUser(User user)
  {
    this.user = user;
  }


  public void setMainModel(MainModelImpl mainModel)
  {
    this.mainModel = mainModel;
  }


  public void setController(SettingsControllerSwing controller)
  {
    this.controllerSwing = controller;
  }

  public void setController(SettingsControllerFX controller)
  {
    controllerFX = controller;
  }


  public void refresh()
  {
    if(controllerSwing != null)
    {
      controllerSwing.show(user);
      controllerSwing.refresh();
    }
    else
    {
      controllerFX.show(user);
      controllerFX.refresh();
    }

  }


  public void saveUser(User user) throws Exception
  {

    if(user.getNewPassword() != null)
    {
      SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
      Random r = new SecureRandom();
      KeySpec ks = new PBEKeySpec(user.getOldPassword(), user.getSalt(), 1000, 512);
      SecretKey generateSecret = skf.generateSecret(ks);

      if(!Arrays.equals(generateSecret.getEncoded(), user.getPassword()))
        throw new Exception("Old password is not correct");


      r = new SecureRandom();
      byte[] salt = new byte[128];
      r.nextBytes(salt);
      ks = new PBEKeySpec(user.getNewPassword(), salt, 1000, 512);
      generateSecret = skf.generateSecret(ks);
      user.setPassword(generateSecret.getEncoded());
      user.setSalt(salt);
    }
    user.setNewPassword(null);


    user.updateInDb(mainModel.db());

    if(controllerSwing != null)
    {
      controllerSwing.updateSuccessful();
      controllerSwing.refresh();
    }
    else
    {
      controllerFX.updateSuccessful();
      controllerFX.refresh();
    }

  }
}
