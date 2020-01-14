package controllers;

import play.mvc.*;
import play.routing.JavaScriptReverseRouter;

public class HomeController extends Controller {

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
                        routes.javascript.NNModelController.deleteTrainingStrategy()

                )  + "\nwindow.jsRoutes = jsRoutes;"
        ).as("text/javascript");
    }
}
