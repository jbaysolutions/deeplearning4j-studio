package com.jbaysolutions.framework.websockets.response;

/**
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 25-01-2020
 * Time: 19:42
 */
public class TrainingFinishedMessage extends WSBaseResponse {

    public String uuid;
    public String message;
    public int epoch;
    public double score;

    public TrainingFinishedMessage() {
        this.type = WSBaseResponseType.TRAINING_FINISHED;
    }

    public TrainingFinishedMessage(
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
}
