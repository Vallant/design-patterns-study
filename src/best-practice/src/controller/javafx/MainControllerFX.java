package controller.javafx;

import controller.interfaces.*;
import data.User;
import model.interfaces.*;
import view.common.ViewManager;
import view.interfaces.MainView;

public class MainControllerFX implements MainController
{
  private final LoginController             login;
  private final ActivityBarController       activityBar;
  private final ProjectController           project;
  private final SideBarController           sideBar;
  private final PersonalStatisticController personalStatistic;
  private final ProjectStatisticController  projectStatistic;
  private final SettingsController          settings;
  private       MainView                    mainView;

  public MainControllerFX()
  {
    login = new LoginControllerFX();
    project = new ProjectControllerFX();
    activityBar = new ActivityBarControllerFX();
    sideBar = new SideBarControllerFX();
    personalStatistic = new PersonalStatisticControllerFX();
    projectStatistic = new ProjectStatisticControllerFX();
    settings = new SettingsControllerFX();
  }

  @Override
  public void init(String frontend)
  {
    ViewManager.initInstance(frontend);
    mainView = ViewManager.getInstance();
  }

  @Override
  public void setModel(MainModel model)
  {
  }

  @Override
  public void switchToLogin()
  {
    assert (mainView != null);
    mainView.hideAll();

    mainView.showLoginView();
  }

  @Override
  public void switchToProjectView()
  {
    mainView.hideCenterContent();
    mainView.showProjectView();
  }

  @Override
  public void switchToAdminView()
  {
    mainView.hideCenterContent();
    mainView.showAdminView();
  }

  @Override
  public void switchToPersonalStatisticView()
  {
    mainView.hideCenterContent();
    mainView.showPersonalStatisticView();
    personalStatistic.refresh();
  }

  @Override
  public void showActivityBar()
  {
    mainView.showActivityBar();
  }

  @Override
  public void pairLogin(LoginModel model)
  {
    login.setModel(model);
    model.setController(login);
    mainView.pairLogin(login);
  }

  @Override
  public void pairProject(ProjectModel model)
  {
    project.setModel(model);
    model.setController(project);
    mainView.pairProject(project);
  }

  @Override
  public void pairActivityBar(ActivityBarModel model)
  {
    activityBar.setModel(model);
    model.setController(activityBar);
    mainView.pairActivityBar(activityBar);
  }

  @Override
  public void showError(Exception ex)
  {
    mainView.showError(ex.getLocalizedMessage());
  }

  @Override
  public void showSideBar(User.ROLE role)
  {
    mainView.showSideBar(role);
  }

  @Override
  public void pairSideBar(SideBarModel model)
  {
    sideBar.setModel(model);
    model.setController(sideBar);
    mainView.pairSideBar(sideBar);
  }

  @Override
  public void pairPersonalStatistic(PersonalStatisticModel model)
  {
    personalStatistic.setModel(model);
    model.setController(personalStatistic);
    mainView.pairPersonalStatistic(personalStatistic);
  }

  @Override
  public void pairProjectStatistic(ProjectStatisticModel model)
  {
    projectStatistic.setModel(model);
    model.setController(projectStatistic);
    mainView.pairProjectStatistic(projectStatistic);
  }

  @Override
  public void pairSettings(SettingsModel model)
  {
    settings.setModel(model);
    model.setController(settings);
    mainView.pairSettings(settings);
  }

  @Override
  public void switchToSettingsView()
  {
    mainView.hideCenterContent();
    mainView.showSettingsView();
  }

  @Override
  public void switchToProjectStatisticView()
  {
    mainView.hideCenterContent();
    mainView.showProjectStatisticView();
    projectStatistic.refresh();
  }
}
