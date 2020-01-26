package com.jbaysolutions.framework.websockets.response;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.jbaysolutions.framework.websockets.request.StartTrainingMessage;

/**
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 24-01-2020
 * Time: 18:08
 */
public abstract class WSBaseResponse {

    public WSBaseResponseType type;

    public enum WSBaseResponseType {
        TRAINING_STARTED,
        TRAINING_UPDATE,
        TRAINING_FINISHED;
    }

}
