package controller.javafx;

import data.User;
import model.impl.SettingsModelImpl;
import view.javafx.settings.SettingsViewFX;

import java.util.Arrays;

public class SettingsControllerFX
{
  private SettingsModelImpl model;
  private SettingsViewFX    view;
  private User              user;


  public void setModel(SettingsModelImpl model)
  {
    this.model = model;
  }


  public void setView(SettingsViewFX view)
  {
    this.view = view;
  }


  public void refresh()
  {
    view.setData(user.getFirstName(), user.getLastName(), user.getEmail());
  }


  public void show(User user)
  {
    this.user = user;
    view.show();
  }


  public void resetClicked()
  {
    view.setData(user.getFirstName(), user.getLastName(), user.getEmail());
  }


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


  public void updateSuccessful()
  {
    view.updateSuccessful();
  }
}
