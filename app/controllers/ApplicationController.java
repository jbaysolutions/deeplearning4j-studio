package controllers;

import play.mvc.*;
import play.routing.JavaScriptReverseRouter;

public class ApplicationController extends Controller {

    public Result javascriptRoutes() {
        return ok(
                JavaScriptReverseRouter.create(
                        "jsRoutes",
                        routes.javascript.NNModelController.getAllModels(),
                        routes.javascript.NNModelController.createNewModel(),
                        routes.javascript.NNModelController.deleteNNModel(),
                        routes.javascript.NNModelController.getModelData(),
                        routes.javascript.NNModelController.persistCurrewntModelData(),
                        routes.javascript.NNModelController.getNNModelHelper(),
                        routes.javascript.NNModelController.generateCleanLayer(),
                        routes.javascript.NNModelController.getDL4JConfigForModel(),
                        routes.javascript.NNModelController.getDL4JCodeForModel(),

                        routes.javascript.NNModelController.getAllTrainingStrategies(),
                        routes.javascript.NNModelController.createTrainingStrategy(),
                        routes.javascript.NNModelController.getTrainingStrategy(),
                        routes.javascript.NNModelController.deleteTrainingStrategy(),
                        routes.javascript.NNModelController.generateCleanRecordReader(),
                        routes.javascript.NNModelController.generateCleanInputSplit(),
                        routes.javascript.NNModelController.generateCleanLocalEpochTermCodition(),
                        routes.javascript.NNModelController.generateCleanLocalIterationTermCodition(),
                        routes.javascript.NNModelController.generateCleanLocalScoreCalculator(),
                        routes.javascript.NNModelController.generateCleanLocalDatasetIterator(),
                        routes.javascript.NNModelController.generateCleanPreprocessorNormalizer(),
                        routes.javascript.NNModelController.getTrainingStrategyResultsList(),

                        routes.javascript.NNModelController.executeTrainingStrategy(),
                        routes.javascript.NNModelController.downloadTrainingResultsFile(),
                        
                        routes.javascript.NNModelController.persistCurrentTrainingStrategy()

                )  + "\nwindow.jsRoutes = jsRoutes;"
        ).as("text/javascript");
    }
}
