package controller.cached;

import controller.interfaces.SettingsController;
import data.User;
import model.interfaces.SettingsModel;
import view.interfaces.SettingsView;

import java.util.Arrays;


public class SettingsControllerCached implements SettingsController
{
  private SettingsModel model;
  private SettingsView  view;
  private User          user;

  private final ControllerCache cache;

  public SettingsControllerCached(ControllerCache cache)
  {
    this.cache = cache;
  }

  @Override
  public void setModel(SettingsModel model)
  {
    this.model = model;
  }

  @Override
  public void setView(SettingsView view)
  {
    this.view = view;
  }

  @Override
  public void refresh()
  {
    view.setData(user.getFirstName(), user.getLastName(), user.getEmail());
  }

  @Override
  public void show(User user)
  {
    this.user = user;
    view.show();
  }

  @Override
  public void resetClicked()
  {
    view.setData(user.getFirstName(), user.getLastName(), user.getEmail());
  }

  @Override
  public void applyClicked(String first, String last, String email, char[] old, char[] newPw, char[] newPwAgain)
  {

    if(first.isEmpty() || last.isEmpty() || email.isEmpty())
      view.showError("Please fill out all fields");
    else if(old.length != 0 && (newPw.length == 0 || newPwAgain.length == 0))
      view.showError("Please fill out all fields");
    else if(!Arrays.equals(newPw, newPwAgain))
      view.showError("The passwords do not match");
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
        view.showError(e.getLocalizedMessage());
        e.printStackTrace();
      }
    }
  }

  @Override
  public void updateSuccessful()
  {
    view.updateSuccessful();
  }
}
