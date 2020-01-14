package com.jbaysolutions.ailabs.testing;

import org.deeplearning4j.datasets.iterator.impl.MnistDataSetIterator;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.deeplearning4j.nn.conf.layers.ConvolutionLayer;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.conf.layers.SubsamplingLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.learning.config.Nesterovs;
import org.nd4j.linalg.learning.regularization.L2Regularization;
import org.nd4j.linalg.learning.regularization.Regularization;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 05-12-2019
 * Time: 8:28
 */
public class CNNTesting {

    private static final Logger log = LoggerFactory
            .getLogger(CNNTesting.class);

    public static void main(String[] args) throws Exception {
        int nChannels = 1; // Number of input channels
        int outputNum = 10; // The number of possible outcomes
        int batchSize = 64; // Test batch size
        int nEpochs = 1; // Number of training epochs
        int iterations = 1; // Number of training iterations
        int seed = 123; //
    /*
    Create an iterator using the batch size for one iteration
    */
        log.info("Load data....");
        DataSetIterator mnistTrain = new MnistDataSetIterator(batchSize, 5000, false, true, true, 12345);
        DataSetIterator mnistTest = new MnistDataSetIterator(batchSize, 1000, false, true, true, 12345);
    /*
    Construct the neural network
    */

        List<Regularization> totReg = new ArrayList<>();
        Regularization regularization = new L2Regularization(0.0005);

        totReg.add(regularization);

        Nesterovs updater = new Nesterovs();
        updater.setLearningRate(0.01);
        updater.setMomentum(0.9);


        log.info("Build model....");
        int layerNumber = 0 ;
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .seed(seed)
                .regularization(totReg)
                /*
                Uncomment the following for learning decay and bias
                */
                //.learningRate(.01)//.biasLearningRate(0.02)
                //.learningRateDecayPolicy(LearningRatePolicy.Inverse)
                //.lrPolicyDecayRate(0.001).lrPolicyPower(0.75)
                .weightInit(WeightInit.XAVIER)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .updater(updater)//.momentum(0.9)
                .list()
                .layer(layerNumber++, new ConvolutionLayer.Builder(5, 5)
                        //nIn and nOut specify depth. nIn here is the nChannels and
                        //nOut is the number of filters to be applied
                        .nIn(nChannels)
                        .stride(1, 1)
                        .nOut(20)
                        .activation(Activation.IDENTITY)
                        .build())
                .layer(layerNumber++, new SubsamplingLayer
                        .Builder(SubsamplingLayer.PoolingType.MAX)
                        .kernelSize(2, 2)
                        .stride(2, 2)
                        .build())
                .layer(layerNumber++, new ConvolutionLayer.Builder(5, 5)
                        //Note that nIn need not be specified in later layers
                        .stride(1, 1)
                        .nOut(50)
                        .activation(Activation.IDENTITY)
                        .build())
                .layer(layerNumber++, new SubsamplingLayer.Builder(SubsamplingLayer
                        .PoolingType.MAX)
                        .kernelSize(2, 2)
                        .stride(2, 2)
                        .build())
                .layer(layerNumber++, new DenseLayer.Builder().activation(Activation.RELU)
                        .nOut(500).build())
                .layer(layerNumber++, new OutputLayer
                        .Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        .nOut(outputNum)
                        .activation(Activation.SOFTMAX)
                        .build())
                .setInputType(InputType.convolutionalFlat(28, 28, 1)) //See note below
                //.backprop(true)
                //.pretrain(false)

                .build();



            /*
            Regarding the .setInputType(InputType.convolutionalFlat(28,28,1)) line: This
            does a few things.
            (a) It adds preprocessors, which handle things like the transition between
            the convolutional/subsampling layers and the dense layer
            (b) Does some additional configuration validation
            (c) Where necessary, sets the nIn (number of input neurons, or input depth in
            the case of CNNs) values for each layer based on the size of the previous
            layer (but it won't override values manually set by the user)
            InputTypes can be used with other layer types too (RNNs, MLPs etc) not
            just CNNs.
            For normal images (when using ImageRecordReader) use
            InputType.convolutional(height,width,depth).
            MNIST record reader is a special case, that outputs 28x28 pixel grayscale
            (nChannels=1) images, in a "flattened" row vector format (i.e., 1x784 vectors),
            hence the "convolutionalFlat" input type used here.
            */
        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.init();
        log.info("Train model....");
        model.setListeners(new ScoreIterationListener(1));
        for (int i = 0; i < nEpochs; i++) {
            model.fit(mnistTrain);
            log.info("*** Completed epoch {} ***", i);
            log.info("Evaluate model....");
            Evaluation eval = new Evaluation(outputNum);
            while (mnistTest.hasNext()) {
                DataSet ds = mnistTest.next();
                INDArray output = model.output(ds.getFeatures(), false);
                eval.eval(ds.getLabels(), output);
            }
            log.info(eval.stats());
            mnistTest.reset();
        }
        log.info("****************Example finished********************");
    }
}