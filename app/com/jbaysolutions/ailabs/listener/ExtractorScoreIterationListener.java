package com.jbaysolutions.ailabs.listener;

import lombok.extern.slf4j.Slf4j;
import org.deeplearning4j.nn.api.Model;
import org.deeplearning4j.optimize.api.BaseTrainingListener;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * Based on  :
 * Score iteration listener. Reports the score (value of the loss function )of the network during training every
 * N iterations
 *
 * @author Adam Gibson
 *
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 05-12-2019
 * Time: 10:44
 */
@Slf4j
public class ExtractorScoreIterationListener  extends BaseTrainingListener implements Serializable {

    private int printIterations = 10;
    private int storeIterations = 10;
    private Hashtable<Integer, Double> scoreData = new Hashtable<>();

    /**
     * @param printIterations    frequency with which to print scores (i.e., every printIterations parameter updates)
     * @param storeIterations    frequency with which to store scores (i.e., every storeIterations parameter updates)
     */                                                                                               
    public ExtractorScoreIterationListener(int printIterations, int storeIterations) {
        this.printIterations = printIterations;
        this.storeIterations = storeIterations;
    }

    /**
     * Default constructor:
     *  - printing every 10 iterations
     *  - storing every 10 iterations
     */
    public ExtractorScoreIterationListener() {}

    @Override
    public void iterationDone(Model model, int iteration, int epoch) {
        if (printIterations <= 0)
            printIterations = 1;
        Double score = null;
        if (iteration % printIterations == 0) {
            score = model.score();
            log.info("Score at iteration {} is {}", iteration, score);
        }

        if (iteration % storeIterations == 0) {
            if (score== null)
                score = model.score();
            scoreData.put(iteration, score);
        }
    }

    public Hashtable<Integer, Double> getScoreData() {
        return scoreData;
    }

    @Override
    public String toString(){
        return "ScoreIterationListener(" + printIterations + ")";
    }
}
