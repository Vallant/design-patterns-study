package model.interfaces;

import controller.interfaces.ProjectStatisticController;
import data.Project;
import data.ProjectPhase;
import data.User;

public interface ProjectStatisticModel
{
    void setUser(User user);
    void setMainModel(MainModel mainModel);
    void setController(ProjectStatisticController controller);
    void refresh();
    void requestedDetailFor(Project project);
    void requestedDetailFor(ProjectPhase projectPhase);
}
