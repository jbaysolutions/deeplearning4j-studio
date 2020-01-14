package com.jbaysolutions.ailabs.builder.nnwrapper.layer;
import org.deeplearning4j.nn.conf.layers.Layer;

/**
 * (c) JBay Solutions 2010-2020 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 05-12-2019
 * Time: 10:34
 */
public interface NNLayer {

    public Layer generateLayer(long inputCount, long numNodes) ;
}
