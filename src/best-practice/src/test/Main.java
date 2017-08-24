/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import data.Project;
import db.common.DBManager;
import db.interfaces.ProjectRepository;

import java.time.ZonedDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author stephan
 */
class Main
{
  public static void main(String[] args)
  {
    System.out.println(ZonedDateTime.now().toInstant().toString());
  }
}
