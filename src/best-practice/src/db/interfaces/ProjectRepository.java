/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import data.Project;

/**
 *
 * @author stephan
 */
public interface ProjectRepository extends Repository<Project>
{
    Criteria createIdCriteria(int id);
    Criteria createHashCriteria(int hash);
}
