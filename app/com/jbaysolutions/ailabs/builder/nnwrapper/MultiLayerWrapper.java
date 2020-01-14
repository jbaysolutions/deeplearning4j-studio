package com.jbaysolutions.ailabs.builder.nnwrapper;

import com.jbaysolutions.ailabs.builder.nnwrapper.updater.AdamWrapper;
import com.jbaysolutions.ailabs.builder.nnwrapper.updater.NesterovsWrapper;
import com.jbaysolutions.ailabs.builder.nnwrapper.updater.SgdWrapper;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 04-12-2019
 * Time: 16:02
 */
public class MultiLayerWrapper {

    public boolean randomSeed = true;
    public long seed = new Random().nextLong();
    public OptimizationAlgorithm optimizationAlgo = OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT;

    public WrapperUpdater defaultUpdater = WrapperUpdater.ADAM;
    public AdamWrapper isAdamWrapper = new AdamWrapper();
    public NesterovsWrapper isNesterovsWrapper = new NesterovsWrapper();
    public SgdWrapper isSgdWrapper = new SgdWrapper();

    public int inputNodes = 1;

    public List<LayerWrapper> layers = new ArrayList<>();
    public LayerWrapper outputLayer = new LayerWrapper(LayerWrapper.LayerType.OUTPUT);

    public enum WrapperUpdater {
        ADAM,
        NESTEROVS,
        SGD,
        // NOT IMPLEMENTED YET ON BUILDER!
        // ADA_DELTA, ADA_GRAD,  ADA_MAX, AMS_GRAD, NADAM, NO_OP, RMS_PROP
        ;
    }
}
