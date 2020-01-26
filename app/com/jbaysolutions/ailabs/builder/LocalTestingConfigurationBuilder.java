package com.jbaysolutions.ailabs.builder;

import com.jbaysolutions.ailabs.builder.bundle.EarlyStoppingTrainerBundle;
import com.jbaysolutions.ailabs.builder.testing.TrainingStrategyWrapper;
import com.jbaysolutions.ailabs.builder.testing.general.iterator.DataIteratorWrapper;
import com.jbaysolutions.ailabs.builder.testing.general.iterator.RecordReaderDataSetIteratorWrapper;
import com.jbaysolutions.ailabs.builder.testing.local.LocalTrainingStrategyWrapper;
import com.jbaysolutions.ailabs.builder.testing.local.recordreader.CSVRecordReaderWrapper;
import com.jbaysolutions.ailabs.builder.testing.local.recordreader.RecordReaderWrapper;
import com.jbaysolutions.ailabs.builder.testing.local.split.FileSplitWrapper;
import com.jbaysolutions.ailabs.builder.testing.local.split.InputSplitWrapper;
import com.jbaysolutions.ailabs.builder.testing.local.trainer.EarlyStoppingTrainerWrapper;
import com.jbaysolutions.ailabs.builder.testing.local.trainer.helper.*;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.earlystopping.EarlyStoppingConfiguration;
import org.deeplearning4j.earlystopping.scorecalc.ClassificationScoreCalculator;
import org.deeplearning4j.earlystopping.scorecalc.ScoreCalculator;
import org.deeplearning4j.earlystopping.termination.*;
import org.deeplearning4j.earlystopping.trainer.EarlyStoppingTrainer;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 23-01-2020
 * Time: 14:44
 */
public class LocalTestingConfigurationBuilder {

    public static EarlyStoppingTrainerBundle buildConfiguration(LocalTrainingStrategyWrapper def, MultiLayerConfiguration nn) throws BadConfigurationException {

        Hashtable<String, RecordReader> recordReaderList = new Hashtable<>();

        for (RecordReaderWrapper rrw : def.recordReader) {
            RecordReader item = null;
            if (
                    rrw.type == RecordReaderWrapper.RecordReaderType.CSV_RECORD_READER
                ) {
                CSVRecordReaderWrapper wrapper = (CSVRecordReaderWrapper)rrw;
                item = new CSVRecordReader(
                        wrapper.skipLines,
                        wrapper.delimiter,
                        wrapper.quote
                );

                if (wrapper.inputSplit != null && wrapper.inputSplit.type == InputSplitWrapper.InputSplitType.FILE_SPLIT) {

                    FileSplitWrapper splitter = (FileSplitWrapper)wrapper.inputSplit;
                    try {
                        item.initialize(
                                new FileSplit(
                                        new File(splitter.rootDir),
                                        null, //splitter.allowFormat.toArray(new String[splitter.allowFormat.size()]),
                                        splitter.recursive
                                )
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new BadConfigurationException(e);
                    }
                }
            }


            if (item!=null) {
                recordReaderList.put(rrw.name, item);
            } else {
                throw new BadConfigurationException("Unknown type of Record Reader : " + rrw.type);
            }
        }

        /* Working on the Data Sets now*/

        DataSetIterator trainingDataSetIterator = null;
        DataSetIterator testingDataSetIterator = null;

        if (def.trainingDataIterator != null && def.trainingDataIterator.type == DataIteratorWrapper.DataIteratorType.RECORD_READER_DATA_ITERATOR) {
            RecordReaderDataSetIteratorWrapper item = (RecordReaderDataSetIteratorWrapper)def.trainingDataIterator;

            trainingDataSetIterator = generateDataSetIterator(recordReaderList, item);
        }

        if (trainingDataSetIterator==null) {
            throw new BadConfigurationException("No Training DataSet Iterator defined");
        }

        if (def.testingDataIterator != null && def.testingDataIterator.type == DataIteratorWrapper.DataIteratorType.RECORD_READER_DATA_ITERATOR) {
            RecordReaderDataSetIteratorWrapper item = (RecordReaderDataSetIteratorWrapper)def.testingDataIterator;

            testingDataSetIterator = generateDataSetIterator(recordReaderList, item);
        }

        if (testingDataSetIterator==null) {
            throw new BadConfigurationException("No Testing DataSet Iterator defined");
        }

        DataNormalization normalizer = new NormalizerStandardize();
        normalizer.fit(trainingDataSetIterator);
        trainingDataSetIterator.setPreProcessor(normalizer);
        testingDataSetIterator.setPreProcessor(normalizer);
        
        /* Working on Trainer Config now */

        EarlyStoppingTrainerWrapper item = def.earlyStoppingTrainer;

        EarlyStoppingConfiguration.Builder<MultiLayerNetwork> trainerConfiguration = new EarlyStoppingConfiguration.Builder<MultiLayerNetwork>();
        trainerConfiguration.evaluateEveryNEpochs(item.evalutateEveryNEpochs);

        if (item.terminationConditionList.size() > 0) {
            trainerConfiguration.epochTerminationConditions(
                    generateEpochTerminationConditions(item.terminationConditionList)
            );
        }

        if (item.iterationTerminationConditionList.size() > 0) {
            trainerConfiguration.iterationTerminationConditions(
                    generateIterationTerminationConditions(item.iterationTerminationConditionList)
            );
        }

        trainerConfiguration.scoreCalculator(
                generateScoreCalculator(item.scoreCalculator, testingDataSetIterator)
        );

        EarlyStoppingConfiguration conf = trainerConfiguration.build();

        EarlyStoppingTrainer eet = new EarlyStoppingTrainer(
                conf, nn, trainingDataSetIterator
        );

        return new EarlyStoppingTrainerBundle(eet, testingDataSetIterator);
    }

    private static ScoreCalculator generateScoreCalculator(ScoreCalculatorWrapper scoreCalculatorWrapper, DataSetIterator testingDataSetIterator) {
        ScoreCalculator item = null;
        if (scoreCalculatorWrapper.type == ScoreCalculatorWrapper.ScoreCalculatorType.CLASSIFICATION_SCORE) {
            ClassificationScoreCalculatorWrapper wrapper = (ClassificationScoreCalculatorWrapper)scoreCalculatorWrapper;
            item = new ClassificationScoreCalculator(
                    Evaluation.Metric.valueOf(wrapper.metric.name()),
                    testingDataSetIterator
            );
        }
        return item;
    }

    private static IterationTerminationCondition[] generateIterationTerminationConditions(List<IterationTerminationConditionWrapper> iterationTerminationConditionList) {
        List<IterationTerminationCondition> list = new ArrayList<>();
        for (IterationTerminationConditionWrapper wrapper : iterationTerminationConditionList) {
            if (wrapper.type == IterationTerminationConditionWrapper.IterationTerminationConditionType.MAX_SCORE) {
                MaxScoreIterationTerminationConditionWrapper item = (MaxScoreIterationTerminationConditionWrapper )wrapper;
                list.add(
                        new MaxScoreIterationTerminationCondition(
                                item.score
                        )
                );
            }
        }
        return list.toArray(new IterationTerminationCondition[list.size()]);
    }

    private static List<EpochTerminationCondition> generateEpochTerminationConditions(List<EpochTerminationConditionWrapper> terminationConditionList) {
        List<EpochTerminationCondition> conditions = new ArrayList<>();
        for ( EpochTerminationConditionWrapper wrapper : terminationConditionList) {
            if (wrapper.type == EpochTerminationConditionWrapper.EpochTerminationConditionType.MAX_EPOCHS) {
                MaxEpochsTerminationConditionWrapper item = (MaxEpochsTerminationConditionWrapper )wrapper;
                conditions.add(
                        new MaxEpochsTerminationCondition(
                                item.maxEpochs
                        )
                );
            } else if (wrapper.type == EpochTerminationConditionWrapper.EpochTerminationConditionType.SCORE_IMPROVEMENT) {
                ScoreImprovementEpochsTerminationConditionWrapper item = (ScoreImprovementEpochsTerminationConditionWrapper )wrapper;
                conditions.add(
                        new ScoreImprovementEpochTerminationCondition(
                                item.maxEpochsWithNoImprovement,
                                item.minImprovement
                        )
                );
            } else if (wrapper.type == EpochTerminationConditionWrapper.EpochTerminationConditionType.BEST_SCORE) {
                BestScoreEpochsTerminationConditionWrapper item = (BestScoreEpochsTerminationConditionWrapper )wrapper;
                conditions.add(
                        new BestScoreEpochTerminationCondition(
                                item.bestExpectedScore
                        )
                );
            }
        }
        return conditions;
    }

    private static DataSetIterator generateDataSetIterator(Hashtable<String, RecordReader> recordReaderList, RecordReaderDataSetIteratorWrapper item) throws BadConfigurationException {
        DataSetIterator trainingDataSetIterator;
        RecordReader recordReaderToUse = recordReaderList.get(item.recordReader);
        if (recordReaderToUse==null) {
            throw new BadConfigurationException("No Record Reader correctly defined for the DataSet Iterator:" + item.recordReader);
        }
        if (item.batchSize == null) {
            throw new BadConfigurationException("No Batch Size defined for the DataSet Iterator:" + item.recordReader);
        }

        RecordReaderDataSetIterator.Builder builder = new RecordReaderDataSetIterator
                .Builder(recordReaderToUse, item.batchSize);

        if (item.classificationLabelIndex != null && item.classificationNumClasses!= null) {
            builder.classification(
                    item.classificationLabelIndex,
                    item.classificationNumClasses
            );
        }

        if (item.regressionLabelIndexFrom != null && item.regressionLabelIndexTo!= null) {
            builder.regression(
                    item.regressionLabelIndexFrom ,
                    item.regressionLabelIndexTo
            );
        } else if (item.regressionLabelIndexFrom != null ) {
            builder.regression(
                    item.regressionLabelIndexFrom
            );
        } else if (item.regressionLabelIndexTo!= null ) {
            builder.regression(
                    item.regressionLabelIndexTo
            );
        }
        trainingDataSetIterator = builder.build();
        return trainingDataSetIterator;
    }


}
