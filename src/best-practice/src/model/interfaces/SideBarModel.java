package model.interfaces;

import controller.interfaces.SideBarController;

/**
 * Created by stephan on 08.07.17.
 */
public interface SideBarModel
{

    void setMainModel(MainModel model);
    void setController(SideBarController controller);

    void refresh();

    void projectsClicked();

    void statisticsClicked();

    void administrationClicked();

    void settingsClicked();
}
