package com.jbaysolutions.ailabs.builder.testing;

import com.jbaysolutions.ailabs.builder.testing.local.CSVRecordReaderParams;

/**
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 14-01-2020
 * Time: 19:31
 */
public abstract class RecordReaderWrapper {

    public RecordReaderType type;

    public static Object generate(RecordReaderType recordReaderType) {
        if (recordReaderType == RecordReaderType.CSV_RECORD_READER) {
            return new CSVRecordReaderParams();
        }
        if (recordReaderType == RecordReaderType.CSV_REGEX_RECORD_READER) {
            // TODO
        }
        throw new RuntimeException("Not Implemented Yet!");
    }

    public enum RecordReaderType {
        CSV_RECORD_READER,
        CSV_REGEX_RECORD_READER
    }
}
