package com.jbaysolutions.ailabs.builder.testing.local.trainer.helper;

/**
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 21-01-2020
 * Time: 15:17
 */
public class ScoreImprovementEpochsTerminationConditionWrapper extends EpochTerminationConditionWrapper {

    public int maxEpochsWithNoImprovement = 1;
    public double minImprovement = 0.0;

    public ScoreImprovementEpochsTerminationConditionWrapper() {
        this.type = EpochTerminationConditionType.SCORE_IMPROVEMENT;
    }

}
