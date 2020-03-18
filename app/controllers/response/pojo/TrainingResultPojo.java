package controllers.response.pojo;

import model.TrainingStrategyResult;

import java.text.SimpleDateFormat;

/**
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 28-01-2020
 * Time: 18:52
 */
public class TrainingResultPojo {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm");

    public long resultId;
    public int version;
    public String executionDate;
    public int bestEpoch;
    public double bestScore;

    public TrainingResultPojo(TrainingStrategyResult item) {
        this.resultId = item.trainingStrategyResultId;
        this.version = item.version;
        this.executionDate = sdf.format(item.executionDate);
        this.bestEpoch = item.bestEpoch;
        this.bestScore = item.bestScore;
    }

}
