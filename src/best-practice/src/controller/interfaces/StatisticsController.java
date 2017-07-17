package controller.interfaces;

import model.interfaces.StatisticsModel;
import view.interfaces.StatisticsView;

/**
 * Created by stephan on 17/07/17.
 */
public interface StatisticsController {

    void setModel(StatisticsModel model);
    void setView(StatisticsView view);
    void refresh();

}
