package com.jbaysolutions.ailabs.testing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jbaysolutions.ailabs.builder.MultiLayerConfigurationBuilder;
import com.jbaysolutions.ailabs.builder.nnwrapper.LayerWrapper;
import com.jbaysolutions.ailabs.builder.nnwrapper.MultiLayerWrapper;
import com.jbaysolutions.ailabs.builder.nnwrapper.layer.DenseLayerParams;
import com.jbaysolutions.ailabs.builder.nnwrapper.layer.OutputLayerParams;
import com.jbaysolutions.ailabs.builder.nnwrapper.updater.NesterovsWrapper;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import play.libs.Json;

/**
 * (c) JBay Solutions 2010-2020 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 04-12-2019
 * Time: 18:01
 */
public class WrapperTest {


    public static void main(String[] args) throws JsonProcessingException {

        MultiLayerWrapper mlw = new MultiLayerWrapper();
        mlw.optimizationAlgo = OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT;
        mlw.randomSeed = false;
        mlw.seed = 123;
        mlw.defaultUpdater = MultiLayerWrapper.WrapperUpdater.NESTEROVS;
        mlw.isNesterovsWrapper = new NesterovsWrapper();
        mlw.isNesterovsWrapper.learningRate = 0.005;
        mlw.isNesterovsWrapper.momentum = 0.9;
        mlw.inputNodes = 2 ;

        LayerWrapper layerWrapper = new LayerWrapper();
        layerWrapper.type = LayerWrapper.LayerType.DENSE;
        layerWrapper.numNodes = 20;
        layerWrapper.paramsDense = new DenseLayerParams();
        layerWrapper.paramsDense.weightInit =  WeightInit.XAVIER;
        layerWrapper.paramsDense.activation = Activation.RELU;

        mlw.layers.add(
                layerWrapper
        );

        mlw.outputLayer = new LayerWrapper();
        mlw.outputLayer.type = LayerWrapper.LayerType.OUTPUT;
        mlw.outputLayer.numNodes= 2;
        mlw.outputLayer.paramsOutput = new OutputLayerParams();
        mlw.outputLayer.paramsOutput.lossFunction = LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD;
        mlw.outputLayer.paramsOutput.weightInit = WeightInit.XAVIER;
        mlw.outputLayer.paramsOutput.activation = Activation.SOFTMAX;

        System.out.println(
                Json.stringify(Json.toJson(mlw))
        );

        MultiLayerConfiguration conf = MultiLayerConfigurationBuilder.buildConfiguration(mlw);
    }

}
