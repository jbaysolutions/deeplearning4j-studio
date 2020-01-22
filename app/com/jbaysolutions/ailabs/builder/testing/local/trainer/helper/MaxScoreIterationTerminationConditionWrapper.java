package com.jbaysolutions.ailabs.builder.testing.local.trainer.helper;

/**
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 21-01-2020
 * Time: 16:17
 */
public class MaxScoreIterationTerminationConditionWrapper extends IterationTerminationConditionWrapper{

    public double score;

    public MaxScoreIterationTerminationConditionWrapper() {
        this.type = IterationTerminationConditionType.MAX_SCORE;
    }
}
