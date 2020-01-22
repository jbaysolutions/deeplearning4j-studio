package com.jbaysolutions.ailabs.builder.testing.local.trainer.helper;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 21-01-2020
 * Time: 16:16
 */
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "serializableHelperData")
@JsonSubTypes({
  @JsonSubTypes.Type(value = MaxScoreIterationTerminationConditionWrapper.class, name = "MAX_SCORE"),
})
public abstract class IterationTerminationConditionWrapper {

    public IterationTerminationConditionType type;

    public static IterationTerminationConditionWrapper generate(IterationTerminationConditionType epochTerminationConditionType) {
        if (epochTerminationConditionType == IterationTerminationConditionType.MAX_SCORE)
            return new MaxScoreIterationTerminationConditionWrapper();

        throw new RuntimeException("not implemented yet");
    }

    public enum IterationTerminationConditionType {
        MAX_SCORE;
    }

}
