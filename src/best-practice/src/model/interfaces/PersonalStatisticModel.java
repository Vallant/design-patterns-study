package model.interfaces;

import controller.interfaces.PersonalStatisticController;
import data.Activity;
import data.Project;
import data.ProjectPhase;
import data.User;

import java.time.ZonedDateTime;

/**
 * Created by stephan on 17/07/17.
 */
public interface PersonalStatisticModel
{


  void deleteActivity(Activity toDelete) throws Exception;

  void addActivity(ProjectPhase detailPhase, String description, String comment, ZonedDateTime zdtStart,
                   ZonedDateTime zdtEnd) throws Exception;

  void updateActivity(Activity a) throws Exception;

  void setUser(User user);

  void setMainModel(MainModel mainModel);

  void setController(PersonalStatisticController statistics);

  void refresh();

  void requestedDetailFor(Project project) throws Exception;

  void requestedDetailFor(ProjectPhase detailPhase) throws Exception;

  void phasePeriodChanged(int projectId, int selectedIndex) throws Exception;

  void projectPeriodChanged(int selectedIndex) throws Exception;

  void activityPeriodChanged(int id, int selectedIndex) throws Exception;

  enum PERIOD
  {
    ALLTIME,
    YEAR,
    MONTH,
    WEEK,
    DAY
  }
}
