package view.javafx.personalstatistic;

import java.time.Duration;

public class PersonalStatisticTableData
{
  String projectName;
  String duration;
  String share;


  public PersonalStatisticTableData(String projectName, Duration duration, Duration total)
  {
    this.projectName = projectName;
    this.duration = String.format("%02d:%02d", duration.getSeconds() / 60, duration.getSeconds() % 60);
    this.share = String.format("%.2f", duration.toMillis() * 100 / (double) (total.toMillis()));
  }

  public String getProjectName()
  {
    return projectName;
  }

  public String getDuration()
  {
    return duration;
  }

  public String getShare()
  {
    return share + " % ";
  }
}
