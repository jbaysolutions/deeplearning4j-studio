package com.jbaysolutions.ailabs.builder.nnwrapper.layer;

import org.deeplearning4j.nn.conf.layers.Layer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.lossfunctions.LossFunctions;

/**
 * (c) JBay Solutions 2010-2020 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 04-12-2019
 * Time: 17:15
 */
public class OutputLayerParams implements NNLayer {

    public Activation activation = Activation.SOFTMAX;
    public WeightInit weightInit = WeightInit.XAVIER;
    public LossFunctions.LossFunction lossFunction = LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD;

    public Layer generateLayer(long inputCount, long numNodes) {
        OutputLayer.Builder outputLayer = new OutputLayer.Builder();
        if (lossFunction!=null)
            outputLayer.lossFunction(lossFunction);

        outputLayer.nIn(inputCount);
        outputLayer.nOut(numNodes);

        if(activation!=null)
            outputLayer.activation(activation);
        if(weightInit!=null)
            outputLayer.weightInit(weightInit);

        return outputLayer.build();
    }

    public String generateLayerCode(long inputCount, long numNodes) {
        StringBuilder code = new StringBuilder();
        code.append("\t\t\tnew OutputLayer.Builder()\n")
                .append("\t\t\t\t.nIn(").append(inputCount).append(")\n")
                .append("\t\t\t\t.nOut(").append(numNodes).append(")\n");

        if (lossFunction!=null)
            code.append("\t\t\t\t.lossFunction(LossFunctions.LossFunction.").append(lossFunction.name()).append(")\n");
        if (activation != null)
            code.append("\t\t\t\t.activation(Activation.").append(activation.name()).append(")\n");
        if (weightInit != null)
            code.append("\t\t\t\t.weightInit(WeightInit.").append(weightInit.name()).append(")\n");
        code.append("\t\t\t.build()\n");
        return code.toString();
    }


}
