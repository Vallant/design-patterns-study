/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.postgres.query;

import java.sql.PreparedStatement;

/**
 *
 * @author stephan
 */
public interface PostgresQueryItem
{
    String toSql();
    public int prepareStatement(PreparedStatement ps, int startIndex) throws Exception;
}
