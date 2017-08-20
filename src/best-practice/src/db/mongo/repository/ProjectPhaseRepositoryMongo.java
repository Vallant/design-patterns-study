package db.mongo.repository;

import data.ProjectPhase;
import db.interfaces.ProjectPhaseRepository;

import java.util.ArrayList;
import java.util.List;

public class ProjectPhaseRepositoryMongo implements ProjectPhaseRepository
{
  @Override
  public ProjectPhase getByPrimaryKey(int id) throws Exception
  {
    return null;
  }

  @Override
  public ArrayList<ProjectPhase> getByProjectId(int projectId) throws Exception
  {
    return null;
  }

  @Override
  public ArrayList<String> getNamesByProjectName(String projectName) throws Exception
  {
    return null;
  }

  @Override
  public ProjectPhase getByProjectAndPhaseName(String projectName, String projectPhaseName) throws Exception
  {
    return null;
  }

  @Override
  public void add(ProjectPhase item) throws Exception
  {

  }

  @Override
  public void update(ProjectPhase item) throws Exception
  {

  }

  @Override
  public void delete(ProjectPhase item) throws Exception
  {

  }

  @Override
  public List<ProjectPhase> getAll() throws Exception
  {
    return null;
  }
}
