package com.jbaysolutions.ailabs.builder.testing.local.split;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 15-01-2020
 * Time: 14:48
 */
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "serializableHelperData")
@JsonSubTypes({
  @JsonSubTypes.Type(value = FileSplitWrapper.class, name = "FILE_SPLIT"),
})
public abstract class InputSplitWrapper {

    public InputSplitType type;

    public static Object generate(InputSplitType inputSplitType) {
        if (inputSplitType == InputSplitType.FILE_SPLIT)
            return new FileSplitWrapper();

        throw new RuntimeException("not implemented yet");
    }

    public enum InputSplitType {
        FILE_SPLIT
    }
}
