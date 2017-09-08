package view.swing.projectstatistic;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Created by stephan on 17/07/17.
 */
class ProjectStatisticProjectPanel extends JPanel
{

  final JComboBox<String> cbPeriod;

  final ProjectStatisticTableModel tblProjectsModel;
  final JTable                     tblProjects;

  public ProjectStatisticProjectPanel()
  {
    super(new BorderLayout(5, 5));
    setBorder(new EtchedBorder());
    JPanel pHeader = new JPanel(new FlowLayout(5));

    this.cbPeriod = new JComboBox<>();
    cbPeriod.addItem("All Time");
    cbPeriod.addItem("Last Year");
    cbPeriod.addItem("Last Month");
    cbPeriod.addItem("Last Week");
    cbPeriod.addItem("Last Day");
    cbPeriod.setSelectedIndex(0);


    this.tblProjectsModel = new ProjectStatisticTableModel("Projectname");
    this.tblProjects = new JTable(tblProjectsModel);
    JScrollPane scrpTable = new JScrollPane(tblProjects);

    tblProjects.setBorder(new LineBorder(Color.black, 1));
    pHeader.add(cbPeriod);
    add(pHeader, BorderLayout.NORTH);
    add(scrpTable, BorderLayout.CENTER);
  }


}
