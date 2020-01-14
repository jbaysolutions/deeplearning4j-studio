package com.jbaysolutions.ailabs.builder.nnwrapper.updater;

import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.learning.config.IUpdater;

/**
 * (c) JBay Solutions 2010-2020 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 04-12-2019
 * Time: 16:24
 */
public class AdamWrapper {
    public Double learningRate = Adam.DEFAULT_ADAM_LEARNING_RATE;
    public Double beta1 = Adam.DEFAULT_ADAM_BETA1_MEAN_DECAY;
    public Double beta2 = Adam.DEFAULT_ADAM_BETA2_VAR_DECAY;
    public Double epsilon = Adam.DEFAULT_ADAM_EPSILON;

    public IUpdater generateIUpdater() {
        Adam adam = new Adam();
        if (learningRate != null)
            adam.setLearningRate(learningRate);
        if (beta1 != null)
            adam.setBeta1(beta1);
        if (beta2 != null)
            adam.setBeta2(beta2);
        if (epsilon != null)
            adam.setEpsilon(epsilon);
        return adam;
    }

    public String generateIUpdaterCode() {
        StringBuilder code = new StringBuilder();
        code.append("new Adam(\n")
                .append("\t\t").append(learningRate).append(",\n")
                .append("\t\t").append(beta1).append(",\n")
                .append("\t\t").append(beta2).append(",\n")
                .append("\t\t").append(epsilon).append(")\n");

        return code.toString();
    }
}
