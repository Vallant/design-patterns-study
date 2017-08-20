package view.javafx.projectstatistic;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

class ProjectStatisticActivityPane extends BorderPane
{

  final ComboBox<String> cbPeriod;
  final ComboBox<String> cbMembers;

  final TableView tblActivity;
  final Button    btBack;

  public ProjectStatisticActivityPane()
  {
    BorderPane pHeader = new BorderPane();
    GridPane pHeaderGrid = new GridPane();


    cbPeriod = new ComboBox<>();
    cbPeriod.getItems().add("All Time");
    cbPeriod.getItems().add("Last Year");
    cbPeriod.getItems().add("Last Month");
    cbPeriod.getItems().add("Last Week");
    cbPeriod.getItems().add("Last Day");
    cbPeriod.getSelectionModel().selectFirst();

    cbMembers = new ComboBox<>();
    cbMembers.getItems().add("All Members");
    cbMembers.getSelectionModel().selectFirst();


    btBack = new Button("Back");

    tblActivity = new TableView();
    ScrollPane scrpTable = new ScrollPane(tblActivity);


    pHeaderGrid.add(btBack, 0, 0);
    pHeaderGrid.add(cbPeriod, 1, 0);
    pHeaderGrid.add(cbMembers, 2, 0);
    pHeader.setLeft(pHeaderGrid);
    setTop(pHeader);
    setCenter(scrpTable);

    TableColumn<ProjectStatisticActivityTableData, String> startColumn = new TableColumn<>("Start");
    startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));

    TableColumn<ProjectStatisticActivityTableData, String> userColumn = new TableColumn<>("User");
    userColumn.setCellValueFactory(new PropertyValueFactory<>("member"));

    TableColumn<ProjectStatisticActivityTableData, String> endColumn = new TableColumn<>("End");
    endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));

    TableColumn<ProjectStatisticActivityTableData, String> commentColumn = new TableColumn<>("Comment");
    commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));

    TableColumn<ProjectStatisticActivityTableData, String> descriptionColumn = new TableColumn<>("Description");
    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

    tblActivity.getColumns().addAll(userColumn, startColumn, endColumn, descriptionColumn, commentColumn);
    tblActivity.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


    scrpTable.setFitToWidth(true);
    scrpTable.setFitToHeight(true);

    scrpTable.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    scrpTable.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
  }


  public void setMembers(ArrayList<String> members)
  {
    int index = cbMembers.getSelectionModel().getSelectedIndex();
    cbMembers.getItems().clear();
    cbMembers.getItems().add("All Members");
    cbMembers.getItems().addAll(members);
    if(index < cbMembers.getItems().size())
      cbMembers.getSelectionModel().clearAndSelect(index);
    else
      cbMembers.getSelectionModel().clearAndSelect(0);
  }
}
