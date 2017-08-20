package view.javafx.projectstatistic;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ProjectStatisticActivityTableData
{

  static private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm");
  private final ZonedDateTime start;
  private final ZonedDateTime end;
  private final String        member;
  private final String        description;
  private final String        comment;


  public ProjectStatisticActivityTableData(ZonedDateTime start, ZonedDateTime end, String user, String description,
                                           String comment)
  {

    this.start = start;
    this.end = end;
    this.member = user;
    this.description = description;
    this.comment = comment;
  }

  public String getStart()
  {
    return formatter.format(start);
  }

  public String getEnd()
  {
    return formatter.format(end);
  }

  public String getMember()
  {
    return member;
  }

  public String getDescription()
  {
    return description;
  }

  public String getComment()
  {
    return comment;
  }
}
