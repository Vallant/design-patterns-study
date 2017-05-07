/*
 * Copyright (C) 2017 stephan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package db.postgres.criteria;

import java.sql.PreparedStatement;
import db.interfaces.SQLCriteria;

/**
 * @created $date
 * @author stephan
 */
public class ProjectIdCriteriaPostgres implements SQLCriteria
{
    private final int projectId;

    public ProjectIdCriteriaPostgres(int projectId)
    {
        this.projectId = projectId;
    }
    
    
    @Override
    public String toSqlClause()
    {
        return " PROJECT_ID = ? ";
    }

    @Override
    public int prepareStatement(PreparedStatement ps, int startIndex) throws Exception
    {
        ps.setInt(startIndex++, projectId);
        return startIndex;
    }

}
