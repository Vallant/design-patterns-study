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

import data.Activity;
import data.Project;
import data.ProjectPhase;
import data.User;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;

/**
 *
 * @author stephan
 */
public interface ActivityRepository extends Repository<Activity>
{
    Activity getByPrimaryKey(int id) throws Exception;

    void getParticipatingProjectsAndWorkloadSince(String loginName, ZonedDateTime since, ArrayList<Project> projects, ArrayList<Duration> durations) throws Exception;

    void getPhasesAndWorkloadSince(String loginName, int projectId, ZonedDateTime since, ArrayList<ProjectPhase> phases, ArrayList<Duration> durations) throws Exception;

    ArrayList<Activity> getActivitiesForPhaseSince(String loginName, int id, ZonedDateTime since) throws Exception;
    ArrayList<Activity> getActivitiesByUserForPhaseSince(String s, int phaseId, ZonedDateTime since) throws Exception;

    void getOwnedProjectsAndWorkloadSince(String loginName, ZonedDateTime since, ArrayList<Project> projects, ArrayList<Duration> durations) throws Exception;

    void getPhasesAndWorkloadForUserSince(String loginName, int projectId, ZonedDateTime since, ArrayList<ProjectPhase> phases, ArrayList<Duration> durations) throws Exception;


}
