package view.swing;

import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class StatisticsActivityTableModel extends AbstractTableModel {

    static private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm");

    String[] columnNames = {"Description", "Comment", "Start", "End"};
    ArrayList<String> descriptions;
    ArrayList<String> comments;
    ArrayList<ZonedDateTime> startTimes;
    ArrayList<ZonedDateTime> endTimes;

    public StatisticsActivityTableModel() {
        descriptions = new ArrayList<>();
        comments = new ArrayList<>();
        startTimes = new ArrayList<>();
        endTimes = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return descriptions.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int row, int column) {
        switch (column)
        {
            case 0: return startTimes.get(row).format(formatter);
            case 1: return endTimes.get(row).format(formatter);
            case 2: return descriptions.get(row);
            case 3: return comments.get(row);
            default: return "ERROR";
        }
    }

    public void setValues(ArrayList<String> descriptions,
                   ArrayList<String> comments,
                   ArrayList<ZonedDateTime> startTimes,
                   ArrayList<ZonedDateTime> endTimes)
    {
        this.descriptions = descriptions;
        this.comments = comments;
        this.startTimes = startTimes;
        this.endTimes = endTimes;
    }

    @Override
    public String getColumnName(int i) {
        return columnNames[i];
    }
}
