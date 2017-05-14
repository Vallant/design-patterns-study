/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.postgres.query;

import db.interfaces.ConnectionBuilder;
import db.interfaces.PredicateBuilder;
import java.sql.PreparedStatement;
import java.util.ArrayList;
/**
 *
 * @author stephan
 */
public class PredicateBuilderPostgres<T> implements PredicateBuilder<T>, PostgresQueryItem
{
    private String parameterName;
    private final ArrayList<PostgresQueryItem> items;
    private T value;
    private Predicate predicate;

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
    
    private enum Predicate
    {
        EQUALS,
        CONTAINS,
        GREATER,
        GREATEREQUALS,
        LESS,
        LESSEQUALS,
        NOT
    }
    
    public PredicateBuilderPostgres(String parameterName, ArrayList<PostgresQueryItem> items)
    {
        this.parameterName = parameterName;
        this.items = items;
        items.add(this);
    }
    
    public PredicateBuilderPostgres(ArrayList<PostgresQueryItem> items)
    {
        predicate = Predicate.NOT;
        this.items = items;
        items.add(this);
    }

    @Override
    public ConnectionBuilder Equals(T value)
    {
        this.value = value;
        predicate = Predicate.EQUALS;
        return new ConnectionBuilderPostgres(items);
    }

    @Override
    public ConnectionBuilder Contains(T value)
    {
        this.value = value;
        predicate = Predicate.EQUALS;
        return new ConnectionBuilderPostgres(items);
    }

    @Override
    public ConnectionBuilder Less(T value)
    {
        this.value = value;
        predicate = Predicate.EQUALS;
        return new ConnectionBuilderPostgres(items);
    }

    @Override
    public ConnectionBuilder Greater(T value)
    {
        this.value = value;
        predicate = Predicate.EQUALS;
        return new ConnectionBuilderPostgres(items);
    }

    @Override
    public ConnectionBuilder GreaterLess(T value)
    {
        this.value = value;
        predicate = Predicate.EQUALS;
        return new ConnectionBuilderPostgres(items);
    }

    @Override
    public PredicateBuilder not()
    {
        this.value = value;
        predicate = Predicate.EQUALS;
        return new PredicateBuilderPostgres(items);
    }

   
}
