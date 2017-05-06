/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.postgres;

import java.sql.PreparedStatement;
import db.interfaces.Criteria;

/**
 *
 * @author stephan
 */
public class OrCriteriaPostgres implements Criteria
{
    private final Criteria left;
    private final Criteria right;

    public OrCriteriaPostgres(Criteria left, Criteria right)
    {
        this.left = left;
        this.right = right;
    }
    
    @Override
    public String toSqlClause()
    {
        return " ( " + left.toSqlClause() + " ) " + " OR " + "( " + right.toSqlClause() + " ) ";
    }

    @Override
    public int prepareStatement(PreparedStatement ps, int startIndex) throws Exception
    {
        int nextIndex = left.prepareStatement(ps, startIndex);
        return right.prepareStatement(ps, nextIndex);
    }
    
}
