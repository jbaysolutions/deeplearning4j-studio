package controllers.response;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.response.pojo.TrainingResultPojo;
import model.NNTrainingStrategy;
import model.TrainingStrategyResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * (c) JBay Solutions 2010-2020 All rights reserved.
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
    public int version;
    public NNTrainingStrategy.TrainingStatus status = NNTrainingStrategy.TrainingStatus.DRAFT;
    public List<TrainingResultPojo> results = new ArrayList<>();

    public TrainingStrategyResponse() {
    }

    public TrainingStrategyResponse(NNTrainingStrategy entry) {
        this.strategyId = entry.strategyId;
        this.name = entry.name;
        this.creationDate = entry.creationDate;
        this.rawStrategy = entry.rawStrategy;
        this.status = entry.status;
        this.version = entry.version;
        for (TrainingStrategyResult item : entry.results) {
            results.add(
                    new TrainingResultPojo(
                            item
                    )
            );
        }
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
