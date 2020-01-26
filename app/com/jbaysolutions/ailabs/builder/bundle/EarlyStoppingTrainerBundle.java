package com.jbaysolutions.ailabs.builder.bundle;

import org.deeplearning4j.earlystopping.trainer.EarlyStoppingTrainer;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

/**
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 26-01-2020
 * Time: 14:43
 */
public class EarlyStoppingTrainerBundle {

    private EarlyStoppingTrainer est;
    private DataSetIterator testingDataSetIterator;

    public EarlyStoppingTrainerBundle(EarlyStoppingTrainer est, DataSetIterator testingDataSetIterator) {
        this.est = est;
        this.testingDataSetIterator = testingDataSetIterator;
    }

    public EarlyStoppingTrainer getEst() {
        return est;
    }

    public DataSetIterator getTestingDataSetIterator() {
        return testingDataSetIterator;
    }
}
