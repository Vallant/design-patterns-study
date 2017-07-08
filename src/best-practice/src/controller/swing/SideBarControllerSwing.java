package controller.swing;

import controller.interfaces.SideBarController;
import model.interfaces.SideBarModel;
import view.interfaces.SideBarView;

/**
 * Created by stephan on 08.07.17.
 */
public class SideBarControllerSwing implements SideBarController
{
    SideBarView view;
    SideBarModel model;


    @Override
    public void setView(SideBarView view) {
        this.view = view;
    }

    @Override
    public void setModel(SideBarModel model) {
        this.model = model;
    }

    @Override
    public void projectsClicked() {
        model.projectsClicked();
    }

    @Override
    public void statisticsClicked() {
        model.statisticsClicked();
    }

    @Override
    public void administrationClicked() {
        model.administrationClicked();
    }

    @Override
    public void settingsClicked() {
        model.settingsClicked();
    }
}
