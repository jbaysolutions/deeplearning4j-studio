package com.jbaysolutions.framework.websockets;

import akka.actor.ActorRef;
import com.fasterxml.jackson.databind.JsonNode;
import com.jbaysolutions.ailabs.builder.BadConfigurationException;
import com.jbaysolutions.ailabs.builder.LocalTestingConfigurationBuilder;
import com.jbaysolutions.ailabs.builder.MultiLayerConfigurationBuilder;
import com.jbaysolutions.ailabs.builder.bundle.EarlyStoppingTrainerBundle;
import com.jbaysolutions.ailabs.builder.nnwrapper.MultiLayerWrapper;
import com.jbaysolutions.ailabs.builder.testing.TrainingStrategyWrapper;
import com.jbaysolutions.ailabs.builder.testing.local.LocalTrainingStrategyWrapper;
import com.jbaysolutions.framework.websockets.request.StartTrainingMessage;
import com.jbaysolutions.framework.websockets.response.TrainingFinishedMessage;
import com.jbaysolutions.framework.websockets.response.TrainingStartedMessage;
import com.jbaysolutions.framework.websockets.response.TrainingUpdateMessage;
import io.ebean.Ebean;
import io.ebean.Transaction;
import lombok.extern.slf4j.Slf4j;
import model.EpochResult;
import model.NNModel;
import model.NNTrainingStrategy;
import model.TrainingStrategyResult;
import org.deeplearning4j.earlystopping.EarlyStoppingConfiguration;
import org.deeplearning4j.earlystopping.EarlyStoppingResult;
import org.deeplearning4j.earlystopping.listener.EarlyStoppingListener;
import org.deeplearning4j.earlystopping.trainer.EarlyStoppingTrainer;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.api.ndarray.INDArray;
import play.libs.Json;

import java.io.File;
import java.io.IOException;

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


        Transaction transaction = Ebean.beginTransaction();

        NNTrainingStrategy item =  NNTrainingStrategy.findStrategyById(message.strategyId);

        TrainingStrategyWrapper trainingStrategyWrapper = Json.fromJson(item.rawStrategy, TrainingStrategyWrapper.class);
        MultiLayerWrapper mmW = Json.fromJson(item.model.rawModel, MultiLayerWrapper.class);

        MultiLayerConfiguration conf = MultiLayerConfigurationBuilder.buildConfiguration(mmW);

        if (trainingStrategyWrapper.trainingType == TrainingStrategyWrapper.TrainingType.LOCAL) {

            EarlyStoppingTrainerBundle trainer = null;
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


            EarlyStoppingTrainerBundle finalTrainer = trainer;
            trainer.getEst().setListener(new EarlyStoppingListener<MultiLayerNetwork>() {

                TrainingStrategyResult trainingResult;

                @Override
                public void onStart(EarlyStoppingConfiguration<MultiLayerNetwork> esConfig, MultiLayerNetwork net) {
                    log.info("Training is Starting");
                    out.tell(
                            Json.toJson(
                                    new TrainingStartedMessage(message.uuid)
                            ),
                            out
                    );

                    trainingResult = TrainingStrategyResult.createStrategyResult(
                            item,
                            item.version,
                            item.rawStrategy
                    );
                    item.version++;
                    item.update();
                }

                @Override
                public void onEpoch(int epochNum, double score, EarlyStoppingConfiguration<MultiLayerNetwork> esConfig, MultiLayerNetwork net) {

                    Evaluation eval = net.evaluate(finalTrainer.getTestingDataSetIterator());
                    log.info("Training in Progress - Epoch {} ; F1: {} ; Accuracy: {} ; Precision: {} ; Recall: {} - Score: {}",
                            epochNum,
                            eval.f1(),
                            eval.accuracy(),
                            eval.precision(),
                            eval.recall(),
                            score
                    );

                    EpochResult res = EpochResult.createEpochResult(
                            trainingResult, epochNum, score,
                            eval.f1(),
                            eval.accuracy(),
                            eval.precision(),
                            eval.recall()
                    );
                    trainingResult.epochResults.add(res);

                    String messageOut = "Epoch " + epochNum + " - Score : " + score;
                    out.tell(
                            Json.toJson(
                                    new TrainingUpdateMessage(
                                            message.uuid,
                                            messageOut,
                                            epochNum,
                                            score,

                                            eval.f1(),
                                            eval.accuracy(),
                                            eval.precision(),
                                            eval.recall()
                                    )
                            ),
                            out
                    );
                }

                @Override
                public void onCompletion(EarlyStoppingResult<MultiLayerNetwork> esResult) {
                    log.info("Training is Finished - Best Epoch: {} with Score: {}", esResult.getBestModelEpoch(), esResult.getBestModelScore() );
                    String messageOut = "BEST MODEL Epoch " + esResult.getBestModelEpoch();
                    out.tell(
                            Json.toJson(
                                    new TrainingFinishedMessage(message.uuid, messageOut, esResult.getBestModelEpoch(), esResult.getBestModelScore())
                            ),
                            out
                    );

                    try {
                        File tempFile = File.createTempFile(item.strategyId+ "-final-"+esResult.getBestModelEpoch(),"zip");
                        esResult.getBestModel().save(
                                tempFile , true
                        );
                        trainingResult.bestEpoch = esResult.getBestModelEpoch();
                        trainingResult.bestScore = esResult.getBestModelScore();
                        trainingResult.savedfile = tempFile;
                        trainingResult.update();

                    } catch (IOException e) {
                        e.printStackTrace();
                        log.error("Could not Save");
                    }

                    NNTrainingStrategy.updateToExecuted(item);
                    /*try {

                    } catch (Exception e) {
                        e.printStackTrace();
                        // TODO SEND ERROR BACK
                        transaction.rollback();
                    }*/
                }
            });


            try {
                EarlyStoppingResult<MultiLayerNetwork> resultEWET = trainer.getEst().fit();
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                // TODO SEND ERROR BACK
                e.printStackTrace();
            }
        }
    }
}
