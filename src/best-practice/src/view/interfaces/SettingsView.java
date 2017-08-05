package view.interfaces;

import controller.interfaces.SettingsController;

/**
 * Created by stephan on 08.07.17.
 */
public interface SettingsView {
    void setController(SettingsController controller);
    void hide();
    void show();
    void showError(String error);

    void setData(String firstName, String lastName, String email);

    void updateSuccessful();
}
