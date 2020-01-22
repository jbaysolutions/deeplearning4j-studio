package com.jbaysolutions.ailabs.builder.testing.local.trainer.helper;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 21-01-2020
 * Time: 15:14
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "serializableHelperData")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MaxEpochsTerminationConditionWrapper.class, name = "MAX_EPOCHS"),
        @JsonSubTypes.Type(value = ScoreImprovementEpochsTerminationConditionWrapper.class, name = "SCORE_IMPROVEMENT"),
        @JsonSubTypes.Type(value = BestScoreEpochsTerminationConditionWrapper.class, name = "BEST_SCORE"),
})
public abstract class EpochTerminationConditionWrapper {

    public EpochTerminationConditionType type;

    public static EpochTerminationConditionWrapper generate(EpochTerminationConditionType epochTerminationConditionType) {
        if (epochTerminationConditionType == EpochTerminationConditionType.MAX_EPOCHS)
            return new MaxEpochsTerminationConditionWrapper();
        if (epochTerminationConditionType == EpochTerminationConditionType.SCORE_IMPROVEMENT)
            return new ScoreImprovementEpochsTerminationConditionWrapper();
        if (epochTerminationConditionType == EpochTerminationConditionType.BEST_SCORE)
            return new BestScoreEpochsTerminationConditionWrapper();

        throw new RuntimeException("not implemented yet");
    }

    public enum EpochTerminationConditionType {
        MAX_EPOCHS,
        SCORE_IMPROVEMENT,
        BEST_SCORE;
    }

}
