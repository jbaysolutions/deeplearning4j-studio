package com.jbaysolutions.ailabs.builder;

import com.jbaysolutions.ailabs.builder.nnwrapper.LayerWrapper;
import com.jbaysolutions.ailabs.builder.nnwrapper.MultiLayerWrapper;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.learning.config.Nesterovs;
import org.nd4j.linalg.learning.config.Sgd;

import java.util.Random;

/**
 * (c) JBay Solutions 2010-2020 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 04-12-2019
 * Time: 16:00
 */
public class MultiLayerConfigurationBuilder {

    private static final Random random = new Random();

    public static MultiLayerConfiguration buildConfiguration(MultiLayerWrapper def) {

        NeuralNetConfiguration.Builder builder = new NeuralNetConfiguration.Builder();
        if (def.randomSeed) {
            builder.seed(random.nextLong());
        } else {
            builder.seed(def.seed);
        }

        /*
            Setting the NN optimization Algo, which defaults to Stochastic GradientDescent
        */
        builder.optimizationAlgo(
            def.optimizationAlgo
        );

        /* Setting the default Updater for all Layers*/

        switch (def.defaultUpdater) {
            case ADAM:
                if ( def.isAdamWrapper!= null) {
                    builder.updater(def.isAdamWrapper.generateIUpdater());
                } else
                    builder.updater(new Adam());
                break;
            case NESTEROVS:
                if ( def.isNesterovsWrapper!= null) {
                    builder.updater(def.isNesterovsWrapper.generateIUpdater());
                } else
                    builder.updater(new Nesterovs());
                break;
            case SGD:
                if ( def.isSgdWrapper!= null) {
                    builder.updater(def.isSgdWrapper.generateIUpdater());
                } else
                    builder.updater(new Sgd());
                break;
        }

        long lastLayerSize = def.inputNodes;

        NeuralNetConfiguration.ListBuilder listBuilder = builder.list();
        int layerCount = 0;
        for (LayerWrapper layer : def.layers) {
            listBuilder.layer(
                    layerCount++,
                    layer.generateLayer(lastLayerSize)
            );
            lastLayerSize = layer.numNodes;
        }
        listBuilder.layer(
                layerCount,
                def.outputLayer.generateLayer(lastLayerSize)
        );

        return listBuilder.build();

    }

    public static String buildJavaCode(MultiLayerWrapper def) {

        StringBuilder rawJavaCode = new StringBuilder();

        rawJavaCode.append("MultiLayerConfiguration multiLayerConfiguration = new NeuralNetConfiguration.Builder()\n");
        if (def.randomSeed) {
            rawJavaCode.append("\t.seed(new Random().nextLong())\n");
        } else {
            rawJavaCode.append("\t.seed(").append(def.seed).append(")\n");
        }

        rawJavaCode.append("\t.optimizationAlgo(OptimizationAlgorithm.").append(def.optimizationAlgo).append(")\n");

        /* Setting the default Updater for all Layers*/

        switch (def.defaultUpdater) {
            case ADAM:
                if (def.isAdamWrapper != null) {
                    rawJavaCode.append("\t.updater(")
                            .append(def.isAdamWrapper.generateIUpdaterCode())
                            .append("\t)\n");
                } else
                    rawJavaCode.append("\t.updater(new Adam())\n");
                break;
            case NESTEROVS:
                if (def.isNesterovsWrapper != null) {
                    rawJavaCode.append("\t.updater(")
                            .append(def.isNesterovsWrapper.generateIUpdaterCode())
                            .append("\t)\n");
                } else
                    rawJavaCode.append("\t.updater(new Nesterovs())\n");
                break;
            case SGD:
                if (def.isSgdWrapper != null) {
                    rawJavaCode.append("\t.updater(")
                            .append(def.isSgdWrapper.generateIUpdaterCode())
                            .append("\t)\n");
                } else
                    rawJavaCode.append("\t.updater(new Sgd())\n");
                break;
        }

        long lastLayerSize = def.inputNodes;

        rawJavaCode.append("\t.list()\n");

        int layerCount = 0;
        for (LayerWrapper layer : def.layers) {

            rawJavaCode.append("\t\t.layer(\n")
                    .append("\t\t\t").append(layerCount++).append(",\n")
                    .append(layer.generateLayerCode(lastLayerSize))
                    .append("\t\t)\n");
            lastLayerSize = layer.numNodes;
        }

        rawJavaCode.append("\t\t.layer(\n")
                .append("\t\t\t").append(layerCount).append(",\n")
                .append(def.outputLayer.generateLayerCode(lastLayerSize))
                .append("\t\t)\n");

        rawJavaCode.append("\t.build();\n");

        return rawJavaCode.toString();
    }

}
