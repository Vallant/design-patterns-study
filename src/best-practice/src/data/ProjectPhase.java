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
 * @author stephan
 * @created $date
 */
public class ProjectPhase implements DBEntity
{
  private int     remoteHash;
  private Project project;
  private String  name;
  private int     id;

  public ProjectPhase(int hash, Project project, String name, int id)
  {
    this.remoteHash = hash;
    this.project = project;
    this.name = name;
    this.id = id;
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
      append(project.getId()).
      append(name).
      append(id).
      hashCode();
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
    return remoteHash;
  }

  @Override
  public void setRemoteHash(int hash)
  {
    remoteHash = hash;
  }

  public String getProjectName()
  {
    return project.getName();
  }

  public int getProjectId()
  {
    return project.getId();
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
