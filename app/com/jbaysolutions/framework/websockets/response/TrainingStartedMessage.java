package com.jbaysolutions.framework.websockets.response;

/**
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 24-01-2020
 * Time: 19:30
 */
public class TrainingStartedMessage extends WSBaseResponse {

    public String uuid;

    public TrainingStartedMessage() {
        this.type = WSBaseResponseType.TRAINING_STARTED;
    }

    public TrainingStartedMessage(String uuid) {
        this();
        this.uuid = uuid;
    }
}
