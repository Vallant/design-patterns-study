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
package db.interfaces;

import db.postgres.DatabaseEnginePostgres;

/**
 *
 * @author stephan
 */
public abstract class DatabaseEngine 
{
    private static String theBackend;
    
    protected DatabaseEngine()
    {
        
    }
    
    public static void setFactory(String backend)
    {
        assert(theBackend == null);
        theBackend = backend;
    }
    
    public static DatabaseEngine getInstance() throws UnsupportedOperationException
    {
        assert(theBackend != null);
        switch(theBackend)
        {
            case "postgres": return DatabaseEnginePostgres.getInstance();
            default: throw new UnsupportedOperationException("Not supported yet."); 
        }
    }
    
    
    public abstract boolean connect();
    public abstract boolean disconnect();
    
    public abstract UserRepository getUserRepository();
    public abstract ProjectRepository getProjectRepository();
    public abstract ProjectMemberRepository getProjectMemberRepository();
    public abstract ProjectPhaseRepository getProjectPhaseRepository();
    public abstract ActivityRepository getActivityRepository();
}
