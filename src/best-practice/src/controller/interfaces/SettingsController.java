package controller.interfaces;

import data.User;
import model.interfaces.SettingsModel;
import view.interfaces.SettingsView;

public interface SettingsController
{
  void setModel(SettingsModel model);

  void setView(SettingsView view);

  void refresh();

  void resetClicked();

  void applyClicked(String first, String last, String email, char[] old, char[] newPw, char[] newPwAgain);

  void show(User user);

  void updateSuccessful();
}
