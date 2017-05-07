/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.postgres.criteria;

import java.sql.PreparedStatement;
import db.interfaces.SQLCriteria;

/**
 *
 * @author stephan
 */
public class HashCriteriaPostgres implements SQLCriteria
{
    private final int hash;

    public HashCriteriaPostgres(int hash)
    {
        this.hash = hash;
    }
    
    @Override
    public String toSqlClause()
    {
        return " HASH = ? ";
    }

    @Override
    public int prepareStatement(PreparedStatement ps, int startIndex) throws Exception
    {
        ps.setInt(startIndex++, hash);
        return startIndex;
    }
    
}
