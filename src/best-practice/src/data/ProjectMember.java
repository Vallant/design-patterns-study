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
public class ProjectMember implements DBEntity
{

    public static enum ROLE
    {
        MEMBER,
        LEADER
    }
    
    private int id;
    private User user;
    private Project project;
    private int remoteHash;
    private ROLE role;

    public User getUser()
    {
        return user;
    }

    public Project getProject()
    {
        return project;
    }

    public ROLE getRole()
    {
        return role;
    }

    public void setRole(ROLE role)
    {
        this.role = role;
    }

    public ProjectMember(User user, Project project)
    {
        this.user = user;
        this.project = project;
    }

    public ProjectMember(User user, Project project, int hash, ROLE role)
    {
        this.user = user;
        this.project = project;
        this.remoteHash = hash;
        this.role = role;
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
                append(user).
                append(project.getLocalHash()).
                append(role).hashCode();
                
    }

    @Override
    public int getRemoteHash()
    {
        return remoteHash;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public void setProject(Project project)
    {
        this.project = project;
    }

    public void setRemoteHash(int remoteHash)
    {
        this.remoteHash = remoteHash;
    }
    
    
    
    
    
    

}
