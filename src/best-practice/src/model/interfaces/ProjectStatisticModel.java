package model.interfaces;

import controller.interfaces.ProjectStatisticController;
import data.Project;
import data.ProjectMember;
import data.ProjectPhase;
import data.User;

public interface ProjectStatisticModel
{

  void projectPeriodChanged(int selectedPeriodIndex) throws Exception;

  void activityDropDownChanged(int phaseId, int selectedPeriodIndex, boolean showAll, ProjectMember selectedUser)
    throws Exception;

  void phaseDropDownChanged(int phaseId, int selectedPeriodIndex, boolean showAll, ProjectMember selectedUser)
    throws Exception;

  void setUser(User user);

  void setMainModel(MainModel mainModel);

  void setController(ProjectStatisticController controller);

  void refresh();

  void requestedDetailFor(Project project) throws Exception;

  void requestedDetailFor(ProjectPhase projectPhase) throws Exception;

  enum PERIOD
  {
    ALLTIME,
    YEAR,
    MONTH,
    WEEK,
    DAY
  }
}
