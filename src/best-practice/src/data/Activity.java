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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @created $date
 * @author stephan
 */
public class Activity implements DBEntity
{
    private int remoteHash;
    private int id;
    private Project project;
    private ProjectPhase phase;
    private User user;
    private String description;
    private ZonedDateTime start;
    private ZonedDateTime stop;
    private String comments;


    public Activity(int hash, int id, Project project, ProjectPhase phase, User user, String description, ZonedDateTime start, ZonedDateTime stop, String comments)
    {
        this.remoteHash = hash;
        this.id = id;
        this.project = project;
        this.phase = phase;
        this.user = user;
        this.description = description;
        this.start = ZonedDateTime.ofInstant(start.toInstant(), ZoneId.systemDefault());
        this.stop = ZonedDateTime.ofInstant(stop.toInstant(), ZoneId.systemDefault());
        this.comments = comments;
    }
    
    
    public Activity(Project project, ProjectPhase phase, User user, String description, ZonedDateTime start, ZonedDateTime stop, String comments)
    {
        this.project = project;
        this.phase = phase;
        this.user = user;
        this.description = description;
        this.start = ZonedDateTime.ofInstant(start.toInstant(), ZoneId.systemDefault());
        this.stop = ZonedDateTime.ofInstant(stop.toInstant(), ZoneId.systemDefault());
        this.comments = comments;
    }
    
    
    
    
    @Override
    public int getLocalHash()
    {
        return new HashCodeBuilder()
                .append(id)
                .append(project.getRemoteHash())
                .append(phase.getRemoteHash())
                .append(user.getRemoteHash())
                .append(description)
                .append(start)
                .append(stop)
                .append(comments).hashCode();
    }
    
    @Override
    public int getRemoteHash()
    {
        return remoteHash;
    }

    public void setHash(Integer hash)
    {
        this.remoteHash = hash;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
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

    public ProjectPhase getPhase()
    {
        return phase;
    }

    public void setPhase(ProjectPhase phase)
    {
        this.phase = phase;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }


    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public ZonedDateTime getStart()
    {
        return start;
    }

    public void setStart(ZonedDateTime start)
    {
        this.start = start;
    }

    public ZonedDateTime getStop()
    {
        return stop;
    }

    public void setStop(ZonedDateTime stop)
    {
        this.stop = stop;
    }

    public String getComments()
    {
        return comments;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }

    @Override
    public boolean isChanged()
    {
        return getLocalHash() != getRemoteHash();
    }

    public String getProjectName()
    {
        return project.getName();
    }
    
    public String getProjectPhaseName()
    {
        return phase.getName();
    }
    
    public String getUserLoginName()
    {
        return user.getLoginName();
    }

    @Override
    public void setRemoteHash(int hash)
    {
        this.remoteHash = hash;
    }
    
    
}
