package com.jbaysolutions.ailabs.builder.testing.local;

import com.jbaysolutions.ailabs.builder.testing.TrainingStrategyWrapper;
import com.jbaysolutions.ailabs.builder.testing.local.recordreader.RecordReaderWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 17-01-2020
 * Time: 14:46
 */
public class LocalTrainingStrategyWrapper extends TrainingStrategyWrapper {

    public List<RecordReaderWrapper> recordReader = new ArrayList<>();

    public LocalTrainingStrategyWrapper() {
        this.trainingType = TrainingType.LOCAL;
    }
}
