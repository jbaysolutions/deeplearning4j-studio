package com.jbaysolutions.ailabs.builder.testing.general.iterator;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 22-01-2020
 * Time: 19:54
 */
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "serializableHelperData")
@JsonSubTypes({
  @JsonSubTypes.Type(value = RecordReaderDataSetIteratorWrapper.class, name = "RECORD_READER_DATA_ITERATOR"),
})
public abstract class DataIteratorWrapper {

    public DataIteratorType type;

    public static Object generate(DataIteratorType inputSplitType) {
        if (inputSplitType == DataIteratorType.RECORD_READER_DATA_ITERATOR)
            return new RecordReaderDataSetIteratorWrapper();

        throw new RuntimeException("not implemented yet");
    }

    public enum DataIteratorType {
        RECORD_READER_DATA_ITERATOR;
    }
}