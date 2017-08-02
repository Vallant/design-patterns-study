package view.swing;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.time.Duration;
import java.util.ArrayList;

/**
 * Created by stephan on 17/07/17.
 */
public class StatisticsPanel extends JPanel {
    final JPanel pHeader;

    final JComboBox<String> cbPeriod;

    final StatisticsTableModel tblProjectsModel;
    final JTable tblProjects;
    final JScrollPane scrpTable;

    public StatisticsPanel() {
        super(new BorderLayout(5,5));
        this.pHeader = new JPanel(new FlowLayout(5));

        this.cbPeriod = new JComboBox<>();
        cbPeriod.addItem("All Time");
        cbPeriod.addItem("Last Year");
        cbPeriod.addItem("Last Month");
        cbPeriod.addItem("Last Week");
        cbPeriod.addItem("Last Day");




        this.tblProjectsModel = new StatisticsTableModel("Projectname");
        this.tblProjects = new JTable(tblProjectsModel);
        scrpTable = new JScrollPane(tblProjects);

        tblProjects.setBorder(new LineBorder(Color.black, 1));
        pHeader.add(cbPeriod);
        add(pHeader, BorderLayout.NORTH);
        add(scrpTable, BorderLayout.CENTER);

    }


}
