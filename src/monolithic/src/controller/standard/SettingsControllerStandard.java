package controller.standard;

import data.User;
import model.impl.SettingsModelImpl;
import view.javafx.settings.SettingsViewFX;
import view.swing.settings.SettingsViewSwing;

import java.util.Arrays;


public class SettingsControllerStandard
{
  private SettingsModelImpl model;
  private SettingsViewSwing viewSwing;
  private SettingsViewFX viewFX;
  private User              user;

  public void setModel(SettingsModelImpl model)
  {
    this.model = model;
  }


  public void setViewSwing(SettingsViewSwing viewSwing)
  {
    this.viewSwing = viewSwing;
  }

  public void setViewFX(SettingsViewFX viewFX)
  {
    this.viewFX = viewFX;
  }

  public void refresh()
  {

    if(viewSwing != null)
      viewSwing.setData(user.getFirstName(), user.getLastName(), user.getEmail());
    else
      viewFX.setData(user.getFirstName(), user.getLastName(), user.getEmail());
  }


  public void show(User user)
  {
    this.user = user;
    if(viewSwing != null)
      viewSwing.show();
    else
      viewFX.show();
  }


  public void resetClicked()
  {
    if(viewSwing != null)
      viewSwing.setData(user.getFirstName(), user.getLastName(), user.getEmail());
    else
      viewFX.setData(user.getFirstName(), user.getLastName(), user.getEmail());
  }


  public void applyClicked(String first, String last, String email, char[] old, char[] newPw, char[] newPwAgain)
  {

    if(first.isEmpty() || last.isEmpty() || email.isEmpty())
    {
      if(viewSwing != null)
        viewSwing.showError("Please fill out all fields");
      else
        viewFX.showError("Please fill out all fields");
    }
    else if(old.length != 0 && (newPw.length == 0 || newPwAgain.length == 0))
    {
      if(viewSwing != null)
        viewSwing.showError("Please fill out all fields");
      else
        viewFX.showError("Please fill out all fields");
    }
    else if(!Arrays.equals(newPw, newPwAgain))
    {
      if(viewSwing != null)
        viewSwing.showError("The passwords do not match");
      else
        viewFX.showError("The passwords do not match");
    }
    else
    {
      user.setFirstName(first);
      user.setLastName(last);
      user.setEmail(email);
      if(old.length != 0)
      {
        user.setNewPassword(newPw);
        user.setOldPassword(old);
      }

      try
      {
        model.saveUser(user);
      }
      catch(Exception e)
      {
        if(viewSwing != null)
          viewSwing.showError(e.getLocalizedMessage());
        else
          viewFX.showError(e.getLocalizedMessage());
        e.printStackTrace();
      }
    }
  }


  public void updateSuccessful()
  {
    if(viewSwing != null)
      viewSwing.updateSuccessful();
    else
      viewFX.updateSuccessful();
  }
}
