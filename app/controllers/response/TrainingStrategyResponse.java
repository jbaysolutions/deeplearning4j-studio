package controllers.response;

import com.fasterxml.jackson.databind.JsonNode;
import model.NNTrainingStrategy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List; /**
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 27-12-2019
 * Time: 18:18
 */
public class TrainingStrategyResponse {

    public long strategyId;
    public String name;
    public Date creationDate;
    public JsonNode rawStrategy;
    public NNTrainingStrategy.TrainingStatus status = NNTrainingStrategy.TrainingStatus.DRAFT;

    public TrainingStrategyResponse() {
    }

    public TrainingStrategyResponse(NNTrainingStrategy entry) {
        this.strategyId = entry.strategyId;
        this.name = entry.name;
        this.creationDate = entry.creationDate;
        this.rawStrategy = entry.rawStrategy;
        this.status = entry.status;
    }

    public static List<TrainingStrategyResponse> generate(List<NNTrainingStrategy> strategies) {
        List<TrainingStrategyResponse> list = new ArrayList<>();
        for (NNTrainingStrategy tStrat : strategies)
            list.add(
                    new TrainingStrategyResponse(tStrat)
            );
        return list;
    }
}
