package model.impl;

import controller.interfaces.SettingsController;
import data.User;
import db.interfaces.UserRepository;
import model.interfaces.MainModel;
import model.interfaces.SettingsModel;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Random;


public class SettingsModelImpl implements SettingsModel
{
  private User               user;
  private MainModel          mainModel;
  private SettingsController controller;

  @Override
  public void setUser(User user)
  {
    this.user = user;
  }

  @Override
  public void setMainModel(MainModel mainModel)
  {
    this.mainModel = mainModel;
  }

  @Override
  public void setController(SettingsController controller)
  {
    this.controller = controller;
  }

  @Override
  public void refresh()
  {
    controller.show(user);
    controller.refresh();
  }

  @Override
  public void saveUser(User user) throws Exception
  {

    UserRepository ur = mainModel.db().getUserRepository();
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


    ur.update(user);
    controller.updateSuccessful();

    controller.refresh();
  }
}
