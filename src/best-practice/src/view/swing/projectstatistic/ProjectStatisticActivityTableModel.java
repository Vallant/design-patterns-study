package view.swing.projectstatistic;

import javax.swing.table.AbstractTableModel;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

class ProjectStatisticActivityTableModel extends AbstractTableModel
{

  static private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm");

  private final String[] columnNames = {"Member", "Start", "End", "Description", "Comment"};
  private ArrayList<String>        descriptions;
  private ArrayList<String>        comments;
  private ArrayList<ZonedDateTime> startTimes;
  private ArrayList<ZonedDateTime> endTimes;
  private ArrayList<String>        users;

  public ProjectStatisticActivityTableModel()
  {
    descriptions = new ArrayList<>();
    comments = new ArrayList<>();
    startTimes = new ArrayList<>();
    endTimes = new ArrayList<>();
  }

  @Override
  public int getRowCount()
  {
    return descriptions.size();
  }

  @Override
  public int getColumnCount()
  {
    return columnNames.length;
  }

  @Override
  public Object getValueAt(int row, int column)
  {
    switch(column)
    {
      case 0:
        return users.get(row);
      case 1:
        return startTimes.get(row).format(formatter);
      case 2:
        return endTimes.get(row).format(formatter);
      case 3:
        return descriptions.get(row);
      case 4:
        return comments.get(row);
      default:
        return "ERROR";
    }
  }

  public void setValues(ArrayList<String> users, ArrayList<String> descriptions,
                        ArrayList<String> comments,
                        ArrayList<ZonedDateTime> startTimes,
                        ArrayList<ZonedDateTime> endTimes)
  {
    this.descriptions = descriptions;
    this.comments = comments;
    this.startTimes = startTimes;
    this.endTimes = endTimes;
    this.users = users;
  }

  @Override
  public String getColumnName(int i)
  {
    return columnNames[i];
  }
}
