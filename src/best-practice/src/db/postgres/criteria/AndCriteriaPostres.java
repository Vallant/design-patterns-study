/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.postgres.criteria;

import db.interfaces.Criteria;
import java.sql.PreparedStatement;
import db.interfaces.SQLCriteria;

/**
 *
 * @author stephan
 */
public class AndCriteriaPostres implements SQLCriteria
{

    private final SQLCriteria left;
    private final SQLCriteria right;

    public AndCriteriaPostres(Criteria left, Criteria right)
    {
        this.left = (SQLCriteria) left;
        this.right = (SQLCriteria) right;
    }
    
    @Override
    public String toSqlClause()
    {
        return " ( " + left.toSqlClause() + " ) " + " AND " + "( " + right.toSqlClause() + " ) ";
    }

    @Override
    public int prepareStatement(PreparedStatement ps, int startIndex) throws Exception
    {
        int nextIndex = left.prepareStatement(ps, startIndex);
        return right.prepareStatement(ps, nextIndex);
    }
}
