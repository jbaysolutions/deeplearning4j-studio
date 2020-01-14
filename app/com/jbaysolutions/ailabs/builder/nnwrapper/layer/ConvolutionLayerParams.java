package com.jbaysolutions.ailabs.builder.nnwrapper.layer;

import org.deeplearning4j.nn.conf.ConvolutionMode;
import org.deeplearning4j.nn.conf.layers.ConvolutionLayer;
import org.deeplearning4j.nn.conf.layers.Layer;
import org.nd4j.linalg.activations.Activation;

/**
 * (c) JBay Solutions 2010-2020 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 05-12-2019
 * Time: 10:34
 */
public class ConvolutionLayerParams implements NNLayer {

    public int[] kernelSize;
    public int channels = 1;
    public int[] strideSize;
    public int[] padding;
    public int[] dilation;
    public Double dropOutRetainProbability;
    public Double gainInit;

    public Double l1;
    public Double l1Bias;
    public Double l2;
    public Double l2Bias;

    public Activation activation = Activation.IDENTITY;
    public ConvolutionMode convolutionMode = ConvolutionMode.Truncate;

    @Override
    public Layer generateLayer(long inputCount, long numNodes) {
        ConvolutionLayer.Builder builder = new ConvolutionLayer.Builder(kernelSize);

        //nIn and nOut specify depth. nIn here is the nChannels and
        //nOut is the number of filters to be applied
        builder.nIn(channels);
        builder.nOut(numNodes);

        if (strideSize!= null)
            builder.stride(strideSize);

        if (activation!=null)
            builder.activation(activation);

        if (padding != null)
            builder.padding(padding);

        if (dilation != null)
            builder.dilation(dilation);

        if (dropOutRetainProbability != null)
            builder.dropOut(dropOutRetainProbability);

        if (convolutionMode != null)
            builder.convolutionMode(ConvolutionMode.Same);

        if (gainInit!=null)
            builder.gainInit(gainInit);

        if (l1!=null)
            builder.l1(l1);

        if (l1Bias!=null)
            builder.l1Bias(l1Bias);

        if (l2!=null)
            builder.l2(l2);

        if (l2Bias!=null)
            builder.l2Bias(l2Bias);

        return builder.build();
    }
}
