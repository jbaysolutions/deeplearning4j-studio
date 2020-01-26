package com.jbaysolutions.framework.websockets.response;

/**
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 24-01-2020
 * Time: 19:30
 */
public class TrainingUpdateMessage extends WSBaseResponse {

    public String uuid;
    public String message;
    public int epoch;
    public double score;

    public double f1;
    public double accuracy;
    public double precision;
    public double recall;

    public TrainingUpdateMessage() {
        this.type = WSBaseResponseType.TRAINING_UPDATE;
    }

    public TrainingUpdateMessage(
            String uuid,
            String message,
            int epoch,
            double score
    ) {
        this();
        this.uuid = uuid;
        this.message = message;
        this.epoch = epoch;
        this.score = score;
    }

    public TrainingUpdateMessage(String uuid, String message, int epoch, double score, double f1, double accuracy, double precision, double recall) {
        this();
        this.uuid = uuid;
        this.message = message;
        this.epoch = epoch;
        this.score = score;
        this.f1 = f1;
        this.accuracy = accuracy;
        this.precision = precision;
        this.recall = recall;
    }
}
