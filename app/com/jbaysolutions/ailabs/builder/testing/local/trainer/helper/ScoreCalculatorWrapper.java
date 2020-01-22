package com.jbaysolutions.ailabs.builder.testing.local.trainer.helper;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 21-01-2020
 * Time: 16:25
 */
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "serializableHelperData")
@JsonSubTypes({
  @JsonSubTypes.Type(value = ClassificationScoreCalculatorWrapper.class, name = "CLASSIFICATION_SCORE"),
})
public abstract class ScoreCalculatorWrapper {

    public ScoreCalculatorType type;

    public static ScoreCalculatorWrapper generate(ScoreCalculatorType epochTerminationConditionType) {
        if (epochTerminationConditionType == ScoreCalculatorType.CLASSIFICATION_SCORE)
            return new ClassificationScoreCalculatorWrapper();

        throw new RuntimeException("not implemented yet");
    }

    public enum ScoreCalculatorType {
        CLASSIFICATION_SCORE;
    }

}

