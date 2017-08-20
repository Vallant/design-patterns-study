package db.mongo.repository;

import data.Project;
import db.interfaces.ProjectRepository;

import java.util.ArrayList;
import java.util.List;

public class ProjectRepositoryMongo implements ProjectRepository
{
  @Override
  public Project getByPrimaryKey(int projectId) throws Exception
  {
    return null;
  }

  @Override
  public ArrayList<String> getProjectsByUserName(String loginName) throws Exception
  {
    return null;
  }

  @Override
  public String getDescriptionByProjectName(String projectName) throws Exception
  {
    return null;
  }

  @Override
  public void add(Project item) throws Exception
  {

  }

  @Override
  public void update(Project item) throws Exception
  {

  }

  @Override
  public void delete(Project item) throws Exception
  {

  }

  @Override
  public List<Project> getAll() throws Exception
  {
    return null;
  }
}
