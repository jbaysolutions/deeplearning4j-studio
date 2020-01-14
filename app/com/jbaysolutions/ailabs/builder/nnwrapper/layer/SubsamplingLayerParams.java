package com.jbaysolutions.ailabs.builder.nnwrapper.layer;

import org.deeplearning4j.nn.conf.layers.Layer;
import org.deeplearning4j.nn.conf.layers.SubsamplingLayer;

/**
 * (c) JBay Solutions 2010-2020 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 05-12-2019
 * Time: 10:42
 */
public class SubsamplingLayerParams implements NNLayer {

    public SubsamplingLayer.PoolingType poolingType =  SubsamplingLayer.PoolingType.MAX;
    public int[] kernerSize;
    public int[] stride;

    @Override
    public Layer generateLayer(long inputCount, long numNodes) {

        SubsamplingLayer.Builder builder = new SubsamplingLayer.Builder();

        if (poolingType != null)
            builder.poolingType(poolingType);

        if (kernerSize != null)
            builder.kernelSize(kernerSize);

        if (stride != null)
            builder.stride(2, 2);

        return builder.build();
    }
}
