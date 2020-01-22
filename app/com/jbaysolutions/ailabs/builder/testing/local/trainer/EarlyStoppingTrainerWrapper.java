package com.jbaysolutions.ailabs.builder.testing.local.trainer;

import com.jbaysolutions.ailabs.builder.testing.local.trainer.helper.EpochTerminationConditionWrapper;
import com.jbaysolutions.ailabs.builder.testing.local.trainer.helper.IterationTerminationConditionWrapper;
import com.jbaysolutions.ailabs.builder.testing.local.trainer.helper.ScoreCalculatorWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 21-01-2020
 * Time: 14:54
 */
public class EarlyStoppingTrainerWrapper {

    public int evalutateEveryNEpochs = 1;

    public List<EpochTerminationConditionWrapper> terminationConditionList = new ArrayList<>();

    public List<IterationTerminationConditionWrapper> iterationTerminationConditionList = new ArrayList<>();

    public ScoreCalculatorWrapper scoreCalculator = null;

}
