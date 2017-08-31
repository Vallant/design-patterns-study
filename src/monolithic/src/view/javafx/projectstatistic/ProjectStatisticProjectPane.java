package view.javafx.projectstatistic;

import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

class ProjectStatisticProjectPane extends BorderPane
{
  final ComboBox<String> cbPeriod;

  final         TableView  tblProjects;

  public ProjectStatisticProjectPane()
  {
    cbPeriod = new ComboBox<>();

    cbPeriod.getItems().add("All Time");
    cbPeriod.getItems().add("Last Year");
    cbPeriod.getItems().add("Last Month");
    cbPeriod.getItems().add("Last Week");
    cbPeriod.getItems().add("Last Day");
    cbPeriod.getSelectionModel().clearAndSelect(0);

    tblProjects = new TableView();
    ScrollPane scrpTable = new ScrollPane(tblProjects);

    BorderPane pHeader = new BorderPane();
    pHeader.setPadding(new Insets(0, 0, 5, 0));
    pHeader.setLeft(cbPeriod);
    setTop(pHeader);
    setCenter(scrpTable);

    setPadding(new Insets(5, 5, 5, 5));

    TableColumn<ProjectStatisticTableData, String> nameColumn = new TableColumn<>("Project Name");
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("projectName"));
    TableColumn<ProjectStatisticTableData, String> durationColumn = new TableColumn<>("Duration");
    durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
    TableColumn<ProjectStatisticTableData, String> shareColumn = new TableColumn<>("Share");
    shareColumn.setCellValueFactory(new PropertyValueFactory<>("share"));

    tblProjects.getColumns().add(nameColumn);
    tblProjects.getColumns().add(shareColumn);
    tblProjects.getColumns().add(durationColumn);

    tblProjects.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


    scrpTable.setFitToWidth(true);
    scrpTable.setFitToHeight(true);

    scrpTable.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    scrpTable.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

  }
}


