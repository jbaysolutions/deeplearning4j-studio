package com.jbaysolutions.ailabs.builder.testing.local.trainer.helper;

/**
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 21-01-2020
 * Time: 15:17
 */
public class MaxEpochsTerminationConditionWrapper extends EpochTerminationConditionWrapper {

    public int maxEpochs = 1;

    public MaxEpochsTerminationConditionWrapper() {
        this.type = EpochTerminationConditionType.MAX_EPOCHS;
    }

}
