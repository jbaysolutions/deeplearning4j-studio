package com.jbaysolutions.framework.websockets;

import akka.actor.ActorRef;
import com.fasterxml.jackson.databind.JsonNode;
import com.jbaysolutions.ailabs.builder.BadConfigurationException;
import com.jbaysolutions.ailabs.builder.LocalTestingConfigurationBuilder;
import com.jbaysolutions.ailabs.builder.MultiLayerConfigurationBuilder;
import com.jbaysolutions.ailabs.builder.nnwrapper.MultiLayerWrapper;
import com.jbaysolutions.ailabs.builder.testing.TrainingStrategyWrapper;
import com.jbaysolutions.ailabs.builder.testing.local.LocalTrainingStrategyWrapper;
import com.jbaysolutions.framework.websockets.request.StartTrainingMessage;
import com.jbaysolutions.framework.websockets.response.TrainingStartedMessage;
import com.jbaysolutions.framework.websockets.response.TrainingUpdateMessage;
import lombok.extern.slf4j.Slf4j;
import model.NNModel;
import model.NNTrainingStrategy;
import org.deeplearning4j.earlystopping.EarlyStoppingConfiguration;
import org.deeplearning4j.earlystopping.EarlyStoppingResult;
import org.deeplearning4j.earlystopping.listener.EarlyStoppingListener;
import org.deeplearning4j.earlystopping.trainer.EarlyStoppingTrainer;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import play.libs.Json;

/**
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 24-01-2020
 * Time: 17:23
 */
@Slf4j
public class WebSocketMessageHandler {

    private ActorRef out;

    public WebSocketMessageHandler(ActorRef out) {
        this.out = out;
    }

    public void handleMessage(String message) {
        out.tell("I received your message: " + message, out);
    }

    public void handleMessage(JsonNode message) {
        log.error("----------------- " + message.getClass());
        out.tell(
                Json.toJson(new NNModel()),
                out
        );
    }

    public void handleMessage(StartTrainingMessage message) {
        out.tell(
                Json.toJson(
                        new TrainingStartedMessage(message.uuid)
                ),
                out
        );

        NNTrainingStrategy item =  NNTrainingStrategy.findStrategyById(message.strategyId);

        TrainingStrategyWrapper trainingStrategyWrapper = Json.fromJson(item.rawStrategy, TrainingStrategyWrapper.class);
        MultiLayerWrapper mmW = Json.fromJson(item.model.rawModel, MultiLayerWrapper.class);

        MultiLayerConfiguration conf = MultiLayerConfigurationBuilder.buildConfiguration(mmW);
        if (trainingStrategyWrapper.trainingType == TrainingStrategyWrapper.TrainingType.LOCAL) {

            EarlyStoppingTrainer trainer = null;
            try {
                trainer = LocalTestingConfigurationBuilder.buildConfiguration(
                        (LocalTrainingStrategyWrapper) trainingStrategyWrapper,
                        conf
                );
            } catch (BadConfigurationException e) {
                // return internalServerError(e.getMessage());
                // TODO SEND ERROR
                return;
            }


            trainer.setListener(new EarlyStoppingListener<MultiLayerNetwork>() {
                @Override
                public void onStart(EarlyStoppingConfiguration<MultiLayerNetwork> esConfig, MultiLayerNetwork net) {

                }

                @Override
                public void onEpoch(int epochNum, double score, EarlyStoppingConfiguration<MultiLayerNetwork> esConfig, MultiLayerNetwork net) {
                            /*INDArray output = net.output(testData.getFeatures());
                            Evaluation eval = new Evaluation(CLASSES_COUNT);
                            eval.eval(testData.getLabels(), output);
                            */

                    String messageOut = "Epoch " + epochNum + " - Score : " + score;
                    out.tell(
                            Json.toJson(
                                    new TrainingUpdateMessage(message.uuid, messageOut)
                            ),
                            out
                    );
                }

                @Override
                public void onCompletion(EarlyStoppingResult<MultiLayerNetwork> esResult) {
                    String messageOut = "BEST MODEL Epoch " + esResult.getBestModelEpoch();
                    out.tell(
                            Json.toJson(
                                    new TrainingUpdateMessage(message.uuid, messageOut)
                            ),
                            out
                    );
                }
            });
            EarlyStoppingResult<MultiLayerNetwork> resultEWET = trainer.fit();
        }


    }
}
