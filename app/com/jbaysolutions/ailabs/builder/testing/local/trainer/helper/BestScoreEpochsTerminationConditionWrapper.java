package com.jbaysolutions.ailabs.builder.testing.local.trainer.helper;

/**
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 21-01-2020
 * Time: 15:17
 */
public class BestScoreEpochsTerminationConditionWrapper extends EpochTerminationConditionWrapper {

    public double bestExpectedScore = 0.0;

    public BestScoreEpochsTerminationConditionWrapper() {
        this.type = EpochTerminationConditionType.BEST_SCORE;
    }

}
