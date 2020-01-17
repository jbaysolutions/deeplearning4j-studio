package com.jbaysolutions.ailabs.builder.testing.spark;

import com.jbaysolutions.ailabs.builder.testing.TrainingStrategyWrapper;
import com.jbaysolutions.ailabs.builder.testing.local.recordreader.RecordReaderWrapper;

/**
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 17-01-2020
 * Time: 14:46
 */
public class SparkTrainingStrategyWrapper extends TrainingStrategyWrapper {

    public RecordReaderWrapper recordReader = null;

    public SparkTrainingStrategyWrapper() {
        this.trainingType = TrainingType.SPARK;
    }
}
