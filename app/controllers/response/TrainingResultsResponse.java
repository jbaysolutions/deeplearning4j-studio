package controllers.response;

import controllers.response.pojo.TrainingResultPojo;
import model.TrainingStrategyResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * (c) JBay Solutions 2010-2020 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 28-12-2019
 * Time: 19:11
 */
public class TrainingResultsResponse {

    public List<TrainingResultPojo> executions = new ArrayList<>();

    public TrainingResultsResponse() {
    }

    public TrainingResultsResponse(List<TrainingStrategyResult> resultList) {
        for (TrainingStrategyResult item : resultList) {
            executions.add(
                    new TrainingResultPojo(
                            item
                    )
            );
        }
    }

}
