package com.jbaysolutions.ailabs.builder.testing.local;

import com.jbaysolutions.ailabs.builder.testing.RecordReaderWrapper;

/**
 * (c) JBay Solutions 2010-2020 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 26-12-2019
 * Time: 23:11
 */
public class CSVRecordReaderParams extends RecordReaderWrapper {

    public CSVRecordReaderParams() {
        this.type = RecordReaderType.CSV_RECORD_READER;
    }

    public int skipLines = 0;
    public char delimiter = ',';
    public char quote = '\"';


}
