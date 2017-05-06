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
public class ProjectPhase implements DBEntity
{
    private int hash;
    private int id;
    private Project project;
    private String name;

    public ProjectPhase(int hash, int id, Project project, String name)
    {
        this.hash = hash;
        this.id = id;
        this.project = project;
        this.name = name;
    }

    public ProjectPhase(Project project, String name)
    {
        this.project = project;
        this.name = name;
    }
    
    
    
    @Override
    public boolean isChanged()
    {
        return getLocalHash() != getRemoteHash();
    }

    @Override
    public int getLocalHash()
    {
        return new HashCodeBuilder().
                append(project.getLocalHash()).
                append(name).
                hashCode();
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public Project getProject()
    {
        return project;
    }

    public void setProject(Project project)
    {
        this.project = project;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public int getRemoteHash()
    {
        return hash;
    }

}
