package com.jbaysolutions.ailabs.testing;

import com.jbaysolutions.ailabs.testing.readers.MyCSVRecordReader;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.BackpropType;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.SplitTestAndTrain;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerMinMaxScaler;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize;
import org.nd4j.linalg.learning.config.RmsProp;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 02-12-2019
 * Time: 12:56
 */
public class ANNTesting {


    public static void main(String[] args) throws IOException, InterruptedException {

        int BATCH_READING = 5;
        int FEATURES_COUNT = 8;
        int CLASSES_COUNT = 1;
        Random randomGenerator = new Random();
        long seed = 2417297719580880954l;//randomGenerator.nextLong();

        System.out.println("Seed : " +seed);
        System.out.println("Batch Reading : " +BATCH_READING);

        /* TRYING ANOTHER STRATEGY !!!! */
        // ORIGINALDATASETWORKER(BATCH_READING);

        try (RecordReader recordReader = new CSVRecordReader(1, ',')) {
            recordReader.initialize(new FileSplit(new File("src/main/resources/Churn_Modelling_v2.csv")));
            DataSetIterator iterator = new RecordReaderDataSetIterator(
              recordReader, 20000, FEATURES_COUNT, CLASSES_COUNT);
            DataSet allData = iterator.next();
            allData.shuffle(42);

            DataNormalization normalizer = new NormalizerStandardize();
            normalizer.fit(allData);
            normalizer.transform(allData);

            SplitTestAndTrain testAndTrain = allData.splitTestAndTrain(0.80);
            DataSet trainingData = testAndTrain.getTrain();
            DataSet testData = testAndTrain.getTest();



            /* GENERATE THE ANN */
            MultiLayerNetwork ann  = new MultiLayerNetwork(generateNNConfig(seed));
            ann.init();

            //ann.addListeners(new EvaluativeListener(TESTEiter,99, InvocationType.EPOCH_END));
            int epochSize = 5000;
            for ( int i = 0 ; i < epochSize ; i++) {
                ann.fit(
                        trainingData
                );
                INDArray output = ann.output(testData.getFeatures());
                Evaluation eval = new Evaluation(CLASSES_COUNT);
                eval.eval(testData.getLabels(), output);

                System.out.println("Epoch " + (i+1) + " - Accuracy : " + eval.accuracy());
            }




            INDArray output = ann.output(testData.getFeatures());
            Evaluation eval = new Evaluation(CLASSES_COUNT);
            eval.eval(testData.getLabels(), output);


            System.out.println(eval.stats());

            INDArray tt = testData.getFeatures();
            normalizer.revertFeatures(tt);
            normalizer.revertLabels(output);

            System.out.println("FEATURES " + tt);
            System.out.println("LABELS " + testData.getLabels());
            System.out.println("OUT" + output);


/*            DataSet predition = trainingData.get(20);
            ArrayList<String> labels= new ArrayList<String>();
            labels.add("0");
            labels.add("1");
            predition.setLabelNames(labels);
            List<String> results = ann.predict();
            for (String res : results)
                System.out.println("RES : " + res ) ;*/

            /*Evaluation eval = ann.evaluate(TESTEiter);
            System.out.println("Accuracy : " + eval.accuracy());
            System.out.println("Precision : " + eval.precision());
            System.out.println("Recall : " + eval.stats());

            TESTEiter.reset();
            DataSet ds = TESTEiter.next(40);
            ArrayList<String> labels= new ArrayList<String>();
            labels.add("FICA");
            labels.add("SAI");
            ds.setLabelNames(labels);
            List<String> results =  ann.predict(ds);

            for (String res : results)
                System.out.println("RES : " + res ) ;*/
        }
    }

    public static void ORIGINALDATASETWORKER(int BATCH_READING) throws IOException, InterruptedException {
        /* ALL RECRDS FOR TRAINING */

        MyCSVRecordReader rr = new MyCSVRecordReader(10);
        rr.initialize(new FileSplit(new File("src/main/resources/Churn_Modelling_v2.csv")));

        /* Generating the scaler for Data Feeding */

        NormalizerMinMaxScaler scaler = new NormalizerMinMaxScaler();
        DataSetIterator iterScaler = new RecordReaderDataSetIterator.Builder(rr, 1)
                .regression(8)
                .build();
        scaler.fit(iterScaler);

        /* Iterator for Training */

        DataSetIterator iter = new RecordReaderDataSetIterator.Builder(rr, BATCH_READING)
                .regression(8)
                .preProcessor(scaler)
                .build();

        /* Iterator for Testing and validation */

        MyCSVRecordReader rrTEST = new MyCSVRecordReader(1);
        rrTEST.initialize(new FileSplit(new File("src/main/resources/Churn_Modelling_v2_test.csv")));

        DataSetIterator TESTEiter = new RecordReaderDataSetIterator.Builder(rrTEST, 1)
                .regression(8)
                .preProcessor(scaler)
                .build();

    }


    public static MultiLayerConfiguration generateNNConfig(long seed) {
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .seed(seed)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .updater(new RmsProp())
                .l2(1e-4)
            .list()
            .layer(0,
                    new DenseLayer.Builder()
                            .nIn(8)
                            .nOut(5)
                            .dropOut(0.9)
                            .weightInit(WeightInit.UNIFORM)
                            .activation(Activation.RELU)
                            .build()
            )
            .layer(1,
                    new DenseLayer.Builder()
                            .nIn(5)
                            .nOut(5)
                            .dropOut(0.9)
                            .weightInit(WeightInit.UNIFORM)
                            .activation(Activation.RELU)
                            .build()
            )
            .layer(2,
                    new OutputLayer.Builder()
                            .lossFunction(LossFunctions.LossFunction.XENT)
                            .weightInit(WeightInit.UNIFORM)
                            .activation(Activation.SIGMOID)
                            .nIn(5).nOut(1).build()
            ).backpropType(BackpropType.Standard)
                .build();
        return conf;
    }
    


}
