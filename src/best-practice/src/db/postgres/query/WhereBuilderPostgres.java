/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.postgres.query;

import db.interfaces.PredicateBuilder;
import db.interfaces.WhereBuilder;
import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 *
 * @author stephan
 */
public class WhereBuilderPostgres implements WhereBuilder, PostgresQueryItem
{
    private final ArrayList<PostgresQueryItem> items;

    public WhereBuilderPostgres(ArrayList<PostgresQueryItem> items)
    {
        this.items = items;
        items.add(this);
    }
    
    @Override
    public PredicateBuilder where(String parameterName)
    {
        return new PredicateBuilderPostgres(parameterName, items);
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
    
}
