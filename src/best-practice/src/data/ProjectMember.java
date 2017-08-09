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
public class ProjectMember implements DBEntity
{


  private User    user;
  private Project project;
  private int     remoteHash;
  private ROLE    role;
  public ProjectMember(User user, Project project, ROLE role)
  {
    this.user = user;
    this.project = project;
    this.role = role;
  }

  public ProjectMember(User user, Project project, int hash, ROLE role)
  {
    this.user = user;
    this.project = project;
    this.remoteHash = hash;
    this.role = role;
  }

  public User getUser()
  {
    return user;
  }

  public void setUser(User user)
  {
    this.user = user;
  }

  public Project getProject()
  {
    return project;
  }

  public void setProject(Project project)
  {
    this.project = project;
  }

  public ROLE getRole()
  {
    return role;
  }

  public void setRole(ROLE role)
  {
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
      append(user.getLoginName()).
      append(project.getId()).
      append(role).
      hashCode();

  }

  @Override
  public int getRemoteHash()
  {
    return remoteHash;
  }

  public void setRemoteHash(int remoteHash)
  {
    this.remoteHash = remoteHash;
  }

  public String getUserLoginName()
  {
    return user.getLoginName();
  }

  public int getProjectId()
  {
    return project.getId();
  }

  public String getProjectName()
  {
    return project.getName();
  }

  public enum ROLE
  {
    MEMBER,
    LEADER
  }


}
