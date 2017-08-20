package db.mongo.repository;

import data.ProjectMember;
import db.interfaces.ProjectMemberRepository;

import java.util.ArrayList;
import java.util.List;

public class ProjectMemberRepositoryMongo implements ProjectMemberRepository
{
  @Override
  public ProjectMember getByPrimaryKey(String userLoginName, int projectId) throws Exception
  {
    return null;
  }

  @Override
  public ArrayList<ProjectMember> getMembersByProjectId(int projectId) throws Exception
  {
    return null;
  }

  @Override
  public ArrayList<ProjectMember> getInvolvedProjects(String loginName) throws Exception
  {
    return null;
  }

  @Override
  public ArrayList<ProjectMember> getOwnedProject(String loginName) throws Exception
  {
    return null;
  }

  @Override
  public void add(ProjectMember item) throws Exception
  {

  }

  @Override
  public void update(ProjectMember item) throws Exception
  {

  }

  @Override
  public void delete(ProjectMember item) throws Exception
  {

  }

  @Override
  public List<ProjectMember> getAll() throws Exception
  {
    return null;
  }
}
