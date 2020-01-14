package com.jbaysolutions.ailabs.testing.readers;

import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.writable.Writable;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 02-12-2019
 * Time: 15:54
 */
public class MyCSVRecordReader extends CSVRecordReader {

    public MyCSVRecordReader(int skipNumLines) {
        super(skipNumLines);
    }

    public MyCSVRecordReader(char delimiter) {
        super(delimiter);
    }

    public MyCSVRecordReader(int skipNumLines, char delimiter) {
        super(skipNumLines, delimiter);
    }

    public MyCSVRecordReader(int skipNumLines, char delimiter, char quote) {
        super(skipNumLines, delimiter, quote);
    }

    public MyCSVRecordReader() {
        super();
    }

    Vector<Integer> ignoreCols = new Vector<Integer>();

    public void addIgnoreCol(int... index) {
        for ( int i : index)
            ignoreCols.add(i);
    }

    @Override
    public List<Writable> next() {
        List<Writable> temp = super.next();
        List<Writable> result = new ArrayList<Writable>();
        for (int index  = 0 ; index < temp.size() ; index++) {
            if (!ignoreCols.contains(index)) {
                result.add(temp.get(index));
            }
        }
        return result;
    }
}
