package com.jbaysolutions.ailabs.builder.testing;


/**
 * (c) JBay Solutions 2010-2020 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 26-12-2019
 * Time: 23:03
 */
public class NNRecordReaderWrapper {

    public RecordReaderType type;
    public CSVRecordReaderParams csvRecordReaderParams = new CSVRecordReaderParams();

    public enum RecordReaderType {
        CSV_RECORD_READER, CSV_REGEX_RECORD_READER
    }
}
