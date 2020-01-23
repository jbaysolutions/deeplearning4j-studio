package com.jbaysolutions.ailabs.builder.testing.general.iterator;

/**
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 23-01-2020
 * Time: 10:54
 */
public class RecordReaderDataSetIteratorWrapper extends DataIteratorWrapper{

    public Integer batchSize;
    public Integer classificationLabelIndex;
    public Integer classificationNumClasses;

    public Integer regressionLabelIndexFrom;
    public Integer regressionLabelIndexTo;

    public String recordReader; 

    public RecordReaderDataSetIteratorWrapper() {
        this.type = DataIteratorType.RECORD_READER_DATA_ITERATOR;
    }
}
