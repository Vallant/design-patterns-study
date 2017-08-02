package view.interfaces;

import controller.interfaces.StatisticsController;

import java.time.Duration;
import java.util.ArrayList;

/**
 * Created by stephan on 08.07.17.
 */
public interface StatisticsView {
    void showOverview();

    void setOverviewData(ArrayList<String> projectNames, ArrayList<Duration> durations);

    void showDetail();
    void setDetailData(ArrayList<String> phaseNames, ArrayList<Duration> durations);

    void setController(StatisticsController controller);

    void RemoveAllComponents();

    void showError(String localizedMessage);

    int getSelectedProjectPeriod();

    void hide();
}
