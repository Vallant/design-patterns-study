/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.postgres.query;

import db.interfaces.ConnectionBuilder;
import db.interfaces.PredicateBuilder;
import db.interfaces.WhereBuilder;
import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 *
 * @author stephan
 */
public class ConnectionBuilderPostgres implements ConnectionBuilder, PostgresQueryItem
{
    private final ArrayList<PostgresQueryItem> items;
    private Connection connection;

    public ConnectionBuilderPostgres(ArrayList<PostgresQueryItem> items)
    {
        this.items = items;
    }

    @Override
    public String toSql()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int prepareStatement(PreparedStatement ps, int startIndex) throws Exception
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private enum Connection
    {
        OR,
        AND
    }
    
    @Override
    public void toList(ArrayList<?> list)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PredicateBuilder orWhere(String parameterName)
    {
        connection = Connection.OR;
        return new WhereBuilderPostgres(items).where(parameterName);
    }

    @Override
    public PredicateBuilder andWhere(String parameterName)
    {
        connection = Connection.AND;
        return new WhereBuilderPostgres(items).where(parameterName);
    }
    
}
