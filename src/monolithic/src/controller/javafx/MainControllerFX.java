package controller.javafx;
import data.User;
import model.impl.*;
import view.common.ViewManager;
import view.javafx.MainViewFX;

public class MainControllerFX
{
  private final LoginControllerFX             login;
  private final ActivityBarControllerFX       activityBar;
  private final ProjectControllerFX           project;
  private final SideBarControllerFX           sideBar;
  private final PersonalStatisticControllerFX personalStatistic;
  private final ProjectStatisticControllerFX  projectStatistic;
  private final SettingsControllerFX          settings;
  private       MainViewFX                    mainView;
  private       MainModelImpl                 mainModel;

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

  
  public void init(String frontend)
  {
    ViewManager.initInstance(frontend);
    mainView = ViewManager.getInstanceFX();
  }

  
  public void setModel(MainModelImpl model)
  {
    mainModel = model;
  }

  
  public void switchToLogin()
  {
    assert (mainView != null);
    mainView.hideAll();

    mainView.showLoginView();
  }

  
  public void switchToProjectView()
  {
    mainView.hideCenterContent();
    mainView.showProjectView();
  }

  
  public void switchToAdminView()
  {
    mainView.hideCenterContent();
    mainView.showAdminView();
  }

  
  public void switchToPersonalStatisticView()
  {
    mainView.hideCenterContent();
    mainView.showPersonalStatisticView();
    personalStatistic.refresh();
  }

  
  public void showActivityBar()
  {
    mainView.showActivityBar();
  }

  
  public void pairLogin(LoginModelImpl model)
  {
    login.setModel(model);
    model.setController(login);
    mainView.pairLogin(login);
  }

  
  public void pairProject(ProjectModelImpl model)
  {
    project.setModel(model);
    model.setController(project);
    mainView.pairProject(project);
  }

  
  public void pairActivityBar(ActivityBarModelImpl model)
  {
    activityBar.setModel(model);
    model.setController(activityBar);
    mainView.pairActivityBar(activityBar);
  }

  
  public void showError(Exception ex)
  {
    mainView.showError(ex.getLocalizedMessage());
  }

  
  public void showSideBar(User.ROLE role)
  {
    mainView.showSideBar(role);
  }

  
  public void pairSideBar(SideBarModelImpl model)
  {
    sideBar.setModel(model);
    model.setController(sideBar);
    mainView.pairSideBar(sideBar);
  }

  
  public void pairPersonalStatistic(PersonalStatisticModelImpl model)
  {
    personalStatistic.setModel(model);
    model.setController(personalStatistic);
    mainView.pairPersonalStatistic(personalStatistic);
  }

  
  public void pairProjectStatistic(ProjectStatisticModelImpl model)
  {
    projectStatistic.setModel(model);
    model.setController(projectStatistic);
    mainView.pairProjectStatistic(projectStatistic);
  }

  
  public void pairSettings(SettingsModelImpl model)
  {
    settings.setModel(model);
    model.setController(settings);
    mainView.pairSettings(settings);
  }

  
  public void switchToSettingsView()
  {
    mainView.hideCenterContent();
    mainView.showSettingsView();
  }

  
  public void switchToProjectStatisticView()
  {
    mainView.hideCenterContent();
    mainView.showProjectStatisticView();
    projectStatistic.refresh();
  }
}
