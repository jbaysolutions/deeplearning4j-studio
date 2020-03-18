package com.jbaysolutions.ailabs.builder.testing.general.preprocessor;

/**
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 18-03-2020
 * Time: 15:04
 */
public class NormalizerStandardizeWrapper extends PreprocessorNormalizerWrapper{

    public NormalizerStandardizeWrapper() {
        this.type = PreprocessorNormalizerType.NORMALIZER_STANDARDIZE;
    }

    public NormalizerStandardizeTraining useTrainingDataForFit = null;

    public enum NormalizerStandardizeTraining {
        TRAINING_DS,
        TESTING_DS,
        BOTH
    }

}
