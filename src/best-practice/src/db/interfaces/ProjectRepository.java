/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import data.Project;

import java.util.ArrayList;

/**
 *
 * @author stephan
 */
public interface ProjectRepository extends Repository<Project>
{
    Project getByPrimaryKey(int projectId) throws Exception;

    ArrayList<String> getProjectsByUserName(String loginName) throws Exception;
}
