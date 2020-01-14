package com.jbaysolutions.ailabs.builder.nnwrapper;

import com.jbaysolutions.ailabs.builder.nnwrapper.layer.ConvolutionLayerParams;
import com.jbaysolutions.ailabs.builder.nnwrapper.layer.DenseLayerParams;
import com.jbaysolutions.ailabs.builder.nnwrapper.layer.OutputLayerParams;
import com.jbaysolutions.ailabs.builder.nnwrapper.layer.SubsamplingLayerParams;
import org.deeplearning4j.nn.conf.layers.Layer;

/**
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 04-12-2019
 * Time: 16:45
 */
public class LayerWrapper {

    public LayerType type = null;
    public long numNodes = 1;
    public DenseLayerParams paramsDense = new DenseLayerParams();
    public OutputLayerParams paramsOutput = new OutputLayerParams();
    public ConvolutionLayerParams convolutionLayerParams = new ConvolutionLayerParams();
    public SubsamplingLayerParams subsamplingLayerParams = new SubsamplingLayerParams();


    public LayerWrapper() {
    }

    public LayerWrapper(LayerType type) {
        this.type = type;
    }

    public Layer generateLayer(long inputCount ) {
        if (type== LayerType.DENSE && paramsDense!=null) {
            return paramsDense.generateLayer(inputCount, numNodes);
        }
        if (type== LayerType.OUTPUT && paramsOutput!=null) {
            return paramsOutput.generateLayer(inputCount, numNodes);
        }
        if (type== LayerType.CONVOLUTION && convolutionLayerParams!=null) {
            return convolutionLayerParams.generateLayer(inputCount, numNodes);
        }
        if (type== LayerType.SUBSAMPLING && subsamplingLayerParams!=null) {
            return subsamplingLayerParams.generateLayer(inputCount, numNodes);
        }
        return null;
    }

    public String generateLayerCode(long inputCount ) {
        if (type== LayerType.DENSE && paramsDense!=null) {
            return paramsDense.generateLayerCode(inputCount, numNodes);
        }
        if (type== LayerType.OUTPUT && paramsOutput!=null) {
            return paramsOutput.generateLayerCode(inputCount, numNodes);
        }
        if (type== LayerType.CONVOLUTION && convolutionLayerParams!=null) {
            // TODO return convolutionLayerParams.generateLayerCode(inputCount, numNodes);
            return "";
        }
        if (type== LayerType.SUBSAMPLING && subsamplingLayerParams!=null) {
            // TODO return subsamplingLayerParams.generateLayerCode(inputCount, numNodes);
            return "";
        }
        return "";
    }

    public enum LayerType {
        DENSE,
        OUTPUT,
        CONVOLUTION,
        SUBSAMPLING;
    }
}
