package model.interfaces;

import controller.interfaces.SettingsController;
import data.User;

public interface SettingsModel
{

  void setUser(User user);

  void setMainModel(MainModel mainModel);

  void setController(SettingsController controller);

  void refresh();

  void saveUser(User user) throws Exception;

}
