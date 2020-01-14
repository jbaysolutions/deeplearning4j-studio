package com.jbaysolutions.ailabs.builder.testing;

/**
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 27-12-2019
 * Time: 13:24
 */
public class TrainingStrategyWrapper {

    public TrainingType trainingType = null;
    public NNRecordReaderWrapper recordReader = new NNRecordReaderWrapper();


    public TrainingStrategyWrapper() {
    }

    public TrainingStrategyWrapper(TrainingType trainingType) {
        this.trainingType = trainingType;
    }

    public enum TrainingType {
        LOCAL, SPARK;
    }
}