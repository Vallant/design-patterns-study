package view.interfaces;

import controller.interfaces.SideBarController;
import data.User;

/**
 * Created by stephan on 08.07.17.
 */
public interface SideBarView {

    void setController(SideBarController controller);

    void show(User.ROLE role);

    void hide();
}
