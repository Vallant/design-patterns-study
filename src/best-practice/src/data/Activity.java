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

import java.time.ZonedDateTime;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @created $date
 * @author stephan
 */
public class Activity 
{
    private Integer hash;
    private Integer id;
    private Integer projectId;
    private Integer phaseId;
    private Integer userId;
    private String description;
    private ZonedDateTime start;
    private ZonedDateTime stop;
    private String comments;

    public Activity(Integer hash, Integer id, Integer projectId, Integer phaseId, Integer userId, String description, ZonedDateTime start, ZonedDateTime stop, String comments)
    {
        this.hash = hash;
        this.id = id;
        this.projectId = projectId;
        this.phaseId = phaseId;
        this.userId = userId;
        this.description = description;
        this.start = start;
        this.stop = stop;
        this.comments = comments;
    }

    public Activity(Integer projectId, Integer phaseId, Integer userId, String description, ZonedDateTime start, ZonedDateTime stop, String comments)
    {
        this.projectId = projectId;
        this.phaseId = phaseId;
        this.userId = userId;
        this.description = description;
        this.start = start;
        this.stop = stop;
        this.comments = comments;
    }
    
    

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder()
                .append(id)
                .append(projectId)
                .append(phaseId)
                .append(userId)
                .append(description)
                .append(start)
                .append(stop)
                .append(comments).hashCode();
    }
    
    

    public Integer getHash()
    {
        return hash;
    }

    public void setHash(Integer hash)
    {
        this.hash = hash;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getProjectId()
    {
        return projectId;
    }

    public void setProjectId(Integer projectId)
    {
        this.projectId = projectId;
    }

    public Integer getPhaseId()
    {
        return phaseId;
    }

    public void setPhaseId(Integer phaseId)
    {
        this.phaseId = phaseId;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId(Integer userId)
    {
        this.userId = userId;
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
    
    
}
