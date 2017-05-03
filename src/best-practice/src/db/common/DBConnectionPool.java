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
package db.common;

import db.postgres.DBConnectionPostgres;
import java.util.ArrayList;

/**
 *
 * @author stephan
 */
public class DBConnectionPool 
{
    private static DBConnectionPool theInstance;
    
    private static final int MAX_CONNECTIONS = 100;
    private static final int INIT_CONNECTIONS = 5;
    
    private static final String POSTGRES_DRIVER = "org.postgresql.Driver";
    private static String DRIVER;
    private static String URL;
    private static String USERNAME;
    private static String PASSWORD;
    private int createdConnections;

    private ArrayList<DBConnection> connectionPool;
    private final ArrayList<String> errors;
    
    
    public static void setConfig(String driver, String url, String username, String password)
    {
        assert(DRIVER == null);
        assert(URL == null);
        assert(USERNAME == null);
        assert(PASSWORD == null);
        DRIVER = driver;
        URL = url;
        USERNAME = username;
        PASSWORD = password;
    }

    public static DBConnectionPool getInstance() throws Exception
    {
        assert(DRIVER != null);
        assert(URL != null);
        assert(USERNAME != null);
        assert(PASSWORD != null);
        
        if (theInstance == null)
        {
            theInstance = new DBConnectionPool();
        }
        return theInstance;
    }

    private DBConnectionPool() throws Exception
    {
        errors = new ArrayList<>();
        initializeConnectionPool();
    }

    private void initializeConnectionPool() throws Exception
    {
        connectionPool = new ArrayList<>();
        for(int i = 0; i < INIT_CONNECTIONS; ++i)
        {
            returnConnection(createNewConnection());
        }
    }

    private synchronized DBConnection createNewConnection() throws Exception
    {
        if (!maxConnectionsCreated())
        {
            createdConnections++;
            DBConnection con;
            switch(DRIVER)
            {
                case POSTGRES_DRIVER: con = new DBConnectionPostgres(DRIVER, URL, USERNAME, PASSWORD); break;
                default: throw new UnsupportedOperationException("Not yet implemented");
            }
            return con;
        }

        return null;
    }

    private synchronized boolean maxConnectionsCreated()
    {
        return createdConnections == MAX_CONNECTIONS;
    }

    public synchronized DBConnection getConnection() throws Exception
    {
        DBConnection con = null;

        if (connectionPool.size() > 0)
        {
            con = connectionPool.get(0);
            connectionPool.remove(0);
        }
        else
        {
            con = createNewConnection();
        }
        return con;
    }

    public synchronized void returnConnection(DBConnection con)
    {
        if(con == null)
            return;
        String msg = con.getErrorMessage();
        if (msg.isEmpty())
        {
            errors.add(msg);
        }
        connectionPool.add(con);
    }

}
