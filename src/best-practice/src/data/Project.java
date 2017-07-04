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

package data;

import db.interfaces.DBEntity;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @created $date
 * @author stephan
 */
public class Project implements DBEntity
{
    
    private int remoteHash;
    private int id;
    private String name;
    private String description;

    @Override
    public int getLocalHash()
    {
        return new HashCodeBuilder()
                .append(name.hashCode())
                .append(description.hashCode()).hashCode();
    }
    @Override
    public int getRemoteHash()
    {
        return remoteHash;
    }

    public Project(int hash, int id, String name, String description)
    {
        this.remoteHash = hash;
        this.name = name;
        this.description = description;
        this.id = id;
    }

    public Project(String name, String description)
    {
        this.name = name;
        this.description = description;
    }
    
    
    public int getHash()
    {
        return remoteHash;
    }

    public void setHash(int hash)
    {
        this.remoteHash = hash;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Override
    public boolean isChanged()
    {
        return getLocalHash() != getRemoteHash();
    }

    @Override
    public void setRemoteHash(int hash)
    {
        this.remoteHash = hash; 
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }
    
    
    
}


