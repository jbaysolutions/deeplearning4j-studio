package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.jbaysolutions.ailabs.ModelHelperData;
import com.jbaysolutions.ailabs.builder.MultiLayerConfigurationBuilder;
import com.jbaysolutions.ailabs.builder.nnwrapper.LayerWrapper;
import com.jbaysolutions.ailabs.builder.nnwrapper.MultiLayerWrapper;
import com.jbaysolutions.ailabs.builder.testing.general.iterator.DataIteratorWrapper;
import com.jbaysolutions.ailabs.builder.testing.local.recordreader.RecordReaderWrapper;
import com.jbaysolutions.ailabs.builder.testing.TrainingStrategyWrapper;
import com.jbaysolutions.ailabs.builder.testing.local.split.InputSplitWrapper;
import com.jbaysolutions.ailabs.builder.testing.local.trainer.helper.EpochTerminationConditionWrapper;
import com.jbaysolutions.ailabs.builder.testing.local.trainer.helper.IterationTerminationConditionWrapper;
import com.jbaysolutions.ailabs.builder.testing.local.trainer.helper.ScoreCalculatorWrapper;
import controllers.request.CreateModelRequest;
import controllers.request.CreateTrainingRequest;
import controllers.response.GeneralResponse;
import controllers.response.ModelDataReponse;
import controllers.response.TrainingStrategyResponse;
import lombok.extern.slf4j.Slf4j;
import model.NNModel;
import model.NNTrainingStrategy;
import org.deeplearning4j.exception.DL4JInvalidConfigException;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

/**
 * (c) JBay Solutions 2010-2020 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 12-12-2019
 * Time: 10:37
 */
@Slf4j
public class NNModelController extends Controller {

    public Result getAllModels() {
        List<NNModel> models = NNModel.findAllModels();
        JsonNode jsonNode = Json.toJson(models);

        return ok(jsonNode).as("application/json");
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result createNewModel() {
        CreateModelRequest req = Json.fromJson(request().body().asJson(), CreateModelRequest.class);

        long id = NNModel.createNewModel(req);
        return ok(
                Json.toJson(
                        new GeneralResponse(id)
                )
        );
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result persistCurrewntModelData(long id) {
        JsonNode rawData = request().body().asJson();

        MultiLayerWrapper mmW = Json.fromJson(rawData, MultiLayerWrapper.class);
        try {
            MultiLayerConfiguration conf = MultiLayerConfigurationBuilder.buildConfiguration(mmW);
        } catch (DL4JInvalidConfigException e) {
            log.error("Error with the Configuration: " + e.getMessage());
            return internalServerError(
                e.getMessage()
            );
        }

        NNModel model  = NNModel.updateWithRawData(
                id,
                rawData
        );

        return ok(
                Json.toJson(
                    new ModelDataReponse(model)
                )
        );
    }

    public Result getModelData(long id) {
        NNModel model = NNModel.findModelById(id);
        return ok(
                Json.toJson(
                    new ModelDataReponse(model)
                )
        );
    }

    public Result getDL4JConfigForModel(long id) {
        NNModel model = NNModel.findModelById(id);
        if (model == null) {
            return internalServerError(
                    "Model does not exist"
            );
        }

        MultiLayerWrapper mmW = Json.fromJson(model.rawModel, MultiLayerWrapper.class);
        try {
            MultiLayerConfiguration conf = MultiLayerConfigurationBuilder.buildConfiguration(mmW);
            return ok (
                    Json.parse(
                            conf.toJson()
                    )
            );
        } catch (DL4JInvalidConfigException e) {
            log.error("Error with the Configuration: " + e.getMessage());
            return internalServerError(
                e.getMessage()
            );
        }
    }

    public Result getDL4JCodeForModel(long id) {
        NNModel model = NNModel.findModelById(id);
        if (model == null) {
            return internalServerError(
                    "Model does not exist"
            );
        }

        MultiLayerWrapper mmW = Json.fromJson(model.rawModel, MultiLayerWrapper.class);
        try {
            String conf = MultiLayerConfigurationBuilder.buildJavaCode(mmW);
            System.out.println(conf);
            return ok (
                conf
            );
        } catch (DL4JInvalidConfigException e) {
            log.error("Error with the Configuration: " + e.getMessage());
            return internalServerError(
                e.getMessage()
            );
        }
    }

    public Result getNNModelHelper() {
        return ok(
                Json.toJson(new ModelHelperData())
        );
    }

    public Result generateCleanLayer(String layerType) {
        return ok(
                Json.toJson(
                    new LayerWrapper(LayerWrapper.LayerType.valueOf(layerType))
                )
        );
    }

    public Result deleteNNModel(long id) {

        NNModel.deleteModelById(id);

        return ok();
    }

    /* TRAINING STRATEGIES */

    public Result createTrainingStrategy() {
        CreateTrainingRequest req = Json.fromJson(request().body().asJson(), CreateTrainingRequest.class);

        NNModel model = NNModel.findModelById(req.modelId);
        NNTrainingStrategy strategy = new NNTrainingStrategy();
        strategy.name = req.name;
        strategy.model = model;
        strategy.rawStrategy = Json.toJson(TrainingStrategyWrapper.generate(req.type));
        strategy.save();
        return getAllTrainingStrategies(req.modelId);
    }

    public Result getAllTrainingStrategies(long modelId) {
        List<NNTrainingStrategy> list =  NNTrainingStrategy.findAllStrategiesForModel(modelId);
        return ok(
                Json.toJson(
                        TrainingStrategyResponse.generate(list)
                )
        );
    }

    public Result getTrainingStrategy(long strategyId) {
        NNTrainingStrategy item =  NNTrainingStrategy.findStrategyById(strategyId);
        return ok(
                Json.toJson(
                        new TrainingStrategyResponse(item)
                )
        );
    }

    public Result deleteTrainingStrategy(long id) {

        NNTrainingStrategy.deleteStrategyById(id);

        return ok();
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result persistCurrentTrainingStrategy(long id) {
        JsonNode rawData = request().body().asJson();

        TrainingStrategyWrapper trainingStrategyWrapper = Json.fromJson(rawData, TrainingStrategyWrapper.class);

        NNTrainingStrategy.updateWithRawData(
                id,
                rawData
        );

        return getTrainingStrategy(id);
    }

    public Result generateCleanRecordReader(String recordReaderType) {
        return ok(
                Json.toJson(
                        RecordReaderWrapper.generate(RecordReaderWrapper.RecordReaderType.valueOf(recordReaderType))
                )
        );
    }

    public Result generateCleanInputSplit(String inputSplitType) {
        return ok(
                Json.toJson(
                        InputSplitWrapper.generate(InputSplitWrapper.InputSplitType.valueOf(inputSplitType))
                )
        );
    }

    public Result generateCleanLocalEpochTermCodition(String conditionType) {
        return ok(
                Json.toJson(
                        EpochTerminationConditionWrapper.generate(EpochTerminationConditionWrapper.EpochTerminationConditionType.valueOf(conditionType))
                )
        );
    }

    public Result generateCleanLocalIterationTermCodition(String conditionType) {
        return ok(
                Json.toJson(
                        IterationTerminationConditionWrapper.generate(IterationTerminationConditionWrapper.IterationTerminationConditionType.valueOf(conditionType))
                )
        );
    }

    public Result generateCleanLocalScoreCalculator(String scoreType) {
        return ok(
                Json.toJson(
                        ScoreCalculatorWrapper.generate(ScoreCalculatorWrapper.ScoreCalculatorType.valueOf(scoreType))
                )
        );
    }

    public Result generateCleanLocalDatasetIterator(String iteratorType) {
        return ok(
                Json.toJson(
                        DataIteratorWrapper.generate(DataIteratorWrapper.DataIteratorType.valueOf(iteratorType))
                )
        );
    }

}
