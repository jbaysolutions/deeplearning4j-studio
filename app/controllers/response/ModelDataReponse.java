package controllers.response;

import com.fasterxml.jackson.databind.JsonNode;
import model.NNModel;
import model.NNTrainingStrategy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 26-12-2019
 * Time: 11:39
 */
public class ModelDataReponse {

    public long modelId;
    public String name;
    public NNModel.Status status;
    public Date creationDate;
    public NNModel.NNType modelType ;
    public JsonNode rawModel;
    public List<TrainingStrategyResponse> trainingStrategyList = new ArrayList<>();

    public ModelDataReponse(NNModel model) {
        this.modelId = model.modelId;
        this.name = model.name;
        this.status = model.status;
        this.creationDate = model.creationDate;
        this.modelType = model.modelType;
        this.rawModel = model.rawModel;

        this.trainingStrategyList = TrainingStrategyResponse.generate(model.strategies);

    }
}
