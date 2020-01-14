package com.jbaysolutions.ailabs.builder.nnwrapper.updater;

import org.nd4j.linalg.learning.config.IUpdater;
import org.nd4j.linalg.learning.config.Nesterovs;

/**
 * (c) JBay Solutions 2010-2020 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 04-12-2019
 * Time: 16:34
 */
public class NesterovsWrapper {

    public Double learningRate = Nesterovs.DEFAULT_NESTEROV_LEARNING_RATE;
    public Double momentum = Nesterovs.DEFAULT_NESTEROV_MOMENTUM;

    public IUpdater generateIUpdater() {
        Nesterovs nesterovs = new Nesterovs();
        if (learningRate != null)
            nesterovs.setLearningRate(learningRate);
        if (momentum != null)
            nesterovs.setMomentum(momentum);
        return nesterovs;
    }

    public String generateIUpdaterCode() {
        StringBuilder code = new StringBuilder();
        code.append("new Nesterovs(\n")
                .append("\t\t").append(learningRate).append(",\n")
                .append("\t\t").append(momentum).append(")\n");

        return code.toString();
    }
}
