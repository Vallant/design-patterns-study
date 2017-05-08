/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import data.Project;
import db.common.DBManager;
import db.interfaces.Criteria;
import db.interfaces.Repository;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author stephan
 */
public class Main
{
    public static void main(String[] args)
    {
        try
        {
            DBManager.initInstance("org.postgresql.Driver", "jdbc:postgresql://localhost/casestudy", "postgres", "postgres");
            
            Repository<Project> r = DBManager.getInstance().getProjectRepository();
            
            Criteria c = DBManager.getInstance().getIdCriteria(20);
            
            Project p2 = r.getByPrimaryKey(c);
            
            System.out.println(p2.getName());
        }
        catch (Exception ex)
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
