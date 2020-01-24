package com.jbaysolutions.framework.websockets.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 24-01-2020
 * Time: 18:08
 */
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = StartTrainingMessage.class, name = "START_TRAINING"),
})
public abstract class WSBaseMessage {

    public WSBaseMessageType type;
    public String uuid;

    public enum WSBaseMessageType {
        START_TRAINING;
    }

}
