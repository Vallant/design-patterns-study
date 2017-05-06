/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.postgres.specification;

import java.sql.PreparedStatement;
import db.interfaces.Criteria;

/**
 *
 * @author stephan
 */
public class IdCriteriaPostgres implements Criteria
{

    private final int id;

    public IdCriteriaPostgres(int id)
    {
        this.id = id;
    }
    
    @Override
    public String toSqlClause()
    {
        return " ID = ? ";
    }

    @Override
    public int prepareStatement(PreparedStatement ps, int startIndex) throws Exception
    {
        ps.setInt(startIndex++, id);
        return startIndex;
    }
    
}
