package view.javafx.personalstatistic;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

class PersonalStatisticActivityTableData
{

  static private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm");
  private final ZonedDateTime start;
  private final ZonedDateTime end;
  private final String        description;
  private final String        comment;


  public PersonalStatisticActivityTableData(ZonedDateTime start, ZonedDateTime end, String description,
                                            String comment)
  {

    this.start = start;
    this.end = end;
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

  public String getDescription()
  {
    return description;
  }

  public String getComment()
  {
    return comment;
  }
}
