package com.jbaysolutions.ailabs.builder.nnwrapper.layer;

import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.Layer;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;

/**
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 04-12-2019
 * Time: 17:13
 */
public class DenseLayerParams implements NNLayer {

    public Activation activation = Activation.RELU;
    public WeightInit weightInit = WeightInit.XAVIER;
    public DropoutType dropoutType = null;

    public Layer generateLayer(long inputCount, long numNodes) {
        DenseLayer.Builder denseLayer = new DenseLayer.Builder();
        denseLayer.nIn(inputCount);
        denseLayer.nOut(numNodes);

        if (activation != null)
            denseLayer.activation(activation);
        if (weightInit != null)
            denseLayer.weightInit(weightInit);

        //denseLayer.dropOut(new Dropout())
        return denseLayer.build();
    }

    public String generateLayerCode(long inputCount, long numNodes) {
        StringBuilder code = new StringBuilder();
        code.append("\t\t\tnew DenseLayer.Builder()\n")
                .append("\t\t\t\t.nIn(").append(inputCount).append(")\n")
                .append("\t\t\t\t.nOut(").append(numNodes).append(")\n");

        if (activation != null)
            code.append("\t\t\t\t.activation(Activation.").append(activation.name()).append(")\n");
        if (weightInit != null)
            code.append("\t\t\t\t.weightInit(WeightInit.").append(weightInit.name()).append(")\n");
        code.append("\t\t\t.build()\n");
        return code.toString();
    }

    public enum DropoutType{
        DROPOUT, ALPHADROPOU, GAUSSIAN_DROUPOUT, GAUSSIAN_NOISE, SPACIALDROPOUT;
    }
}
