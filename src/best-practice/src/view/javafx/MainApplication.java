package view.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application
{
  public MainApplication()
  {

  }

  public Stage getStage()
  {
    return stage;
  }

  private Stage stage;

  @Override
  public void start(Stage stage) throws Exception
  {
    this.stage = stage;
  }

  public static void main()
  {
    launch();
  }
}
