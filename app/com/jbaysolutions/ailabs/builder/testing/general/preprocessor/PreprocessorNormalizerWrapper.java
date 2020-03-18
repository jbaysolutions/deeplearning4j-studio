package com.jbaysolutions.ailabs.builder.testing.general.preprocessor;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 18-03-2020
 * Time: 15:02
 */
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "serializableHelperData")
@JsonSubTypes({
  @JsonSubTypes.Type(value = NormalizerStandardizeWrapper.class, name = "NORMALIZER_STANDARDIZE"),
})
public abstract class PreprocessorNormalizerWrapper {

    public PreprocessorNormalizerType type;

    public static Object generate(PreprocessorNormalizerType preprocessorType) {
        if (preprocessorType == PreprocessorNormalizerType.NORMALIZER_STANDARDIZE) {
            return new NormalizerStandardizeWrapper();
        }
        
        throw new RuntimeException("Not Implemented Yet!");
    }


    public enum PreprocessorNormalizerType {
        NORMALIZER_STANDARDIZE;
    }
}
