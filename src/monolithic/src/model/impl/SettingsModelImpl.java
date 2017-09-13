package model.impl;

import controller.standard.SettingsControllerStandard;
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
  private User                       user;
  private MainModelImpl              mainModel;
  private SettingsControllerStandard controller;

  public void setUser(User user)
  {
    this.user = user;
  }


  public void setMainModel(MainModelImpl mainModel)
  {
    this.mainModel = mainModel;
  }


  public void setController(SettingsControllerStandard controller)
  {
    this.controller = controller;
  }


  public void refresh()
  {
    controller.show(user);
    controller.refresh();
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


    if(mainModel.dbPostgres() != null)
      user.updateInDb(mainModel.dbPostgres());
    else
      user.updateInDb(mainModel.dbMongo());

    controller.updateSuccessful();

    controller.refresh();
  }
}
