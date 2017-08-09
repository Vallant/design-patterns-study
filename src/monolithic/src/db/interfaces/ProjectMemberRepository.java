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

import data.ProjectMember;

import java.util.ArrayList;

/**
 *
 * @author stephan
 */
public interface ProjectMemberRepository extends Repository<ProjectMember>
{
    ProjectMember getByPrimaryKey(String userLoginName, int projectId) throws Exception;

    ArrayList<ProjectMember> getMembersByProjectId(int projectId) throws Exception;

    ArrayList<ProjectMember> getInvolvedProjects(String loginName) throws Exception;
    ArrayList<ProjectMember> getOwnedProject(String loginName) throws Exception;
}
