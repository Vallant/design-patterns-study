package exception;

import java.util.ArrayList;

/**
 * Created by stephan on 06/07/17.
 */
public class ElementNotFoundException extends Exception
{
    private final String table;
    private final ArrayList<String> columns;
    private final ArrayList<String> values;


    public ElementNotFoundException(String table, String column, String value)
    {
        this.table = table;
        this.columns = new ArrayList<>();
        this.columns.add(column);
        this.values = new ArrayList<>();
        this.values.add(value);
    }

    public ElementNotFoundException(String table, ArrayList<String> columns, ArrayList<String> values) {
        assert(columns.size() == values.size());
        assert(columns.size() > 0);

        this.table = table;
        this.columns = columns;
        this.values = values;
    }

    @Override
    public String getLocalizedMessage() {
        String message = "Element from table " + table + " not found that satifies conditions:";
        for(int i = 0; i < columns.size(); ++i)
        {
            String column = columns.get(i);
            String value = values.get(i);
            message += "\ncolumn " + column + " = " + value;
        }
        return message;
    }
}
