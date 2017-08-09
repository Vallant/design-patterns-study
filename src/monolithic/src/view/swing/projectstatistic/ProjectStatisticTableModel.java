package view.swing.projectstatistic;

import javax.swing.table.AbstractTableModel;
import java.time.Duration;
import java.util.ArrayList;

/**
 * Created by stephan on 17/07/17.
 */
class ProjectStatisticTableModel extends AbstractTableModel
{
  private       ArrayList<String>   firstColumn;
  private       ArrayList<Duration> durations;
  private final String[]            columnNames;

  public ProjectStatisticTableModel(String firstColumnName)
  {
    columnNames = new String[3];
    columnNames[0] = firstColumnName;
    columnNames[1] = "Share [%]";
    columnNames[2] = "Workload";
    firstColumn = new ArrayList<>();
    durations = new ArrayList<>();
  }


  public int getRowCount()
  {
    assert (firstColumn.size() == durations.size());
    return firstColumn.size();
  }


  public int getColumnCount()
  {
    return columnNames.length;
  }


  public Object getValueAt(int row, int column)
  {
    Duration current = durations.get(row);
    switch(column)
    {
      case 0:
        return firstColumn.get(row);
      case 1:
        return String.format("%.2f", current.toMillis() * 100 / (double) (getTotalDuration().toMillis()));
      case 2:
        return String.format("%02d:%02d", current.getSeconds() / 60, current.getSeconds() % 60);
    }

    return null;
  }

  private Duration getTotalDuration()
  {
    Duration total = Duration.ZERO;
    for(Duration d : durations)
    {
      total = total.plus(d);
    }
    return total;
  }


  public String getColumnName(int i)
  {
    return columnNames[i];
  }

  public void setFirstColumnContent(ArrayList<String> firstColumn)
  {
    this.firstColumn = firstColumn;
  }

  public void setWorkloadContent(ArrayList<Duration> durations)
  {
    this.durations = durations;
  }
}