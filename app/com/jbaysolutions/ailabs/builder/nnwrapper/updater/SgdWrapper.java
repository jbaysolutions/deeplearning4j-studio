package com.jbaysolutions.ailabs.builder.nnwrapper.updater;

import org.nd4j.linalg.learning.config.IUpdater;
import org.nd4j.linalg.learning.config.Sgd;

/**
 * (c) JBay Solutions 2010-2020 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 04-12-2019
 * Time: 16:41
 */
public class SgdWrapper {

    public Double learningRate;

    public IUpdater generateIUpdater() {
        Sgd sgd = new Sgd();
        if (learningRate != null)
            sgd.setLearningRate(learningRate);
        return sgd;
    }

    public String generateIUpdaterCode() {
        StringBuilder code = new StringBuilder();
        code.append("new Sgd(\n")
                .append("\t\t").append(learningRate).append(")\n");

        return code.toString();
    }
}
