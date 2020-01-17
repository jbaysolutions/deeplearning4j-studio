package com.jbaysolutions.ailabs.builder.testing;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.jbaysolutions.ailabs.builder.testing.local.LocalTrainingStrategyWrapper;
import com.jbaysolutions.ailabs.builder.testing.spark.SparkTrainingStrategyWrapper;

/**
 * (c) JBay Solutions 2010-2020 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 27-12-2019
 * Time: 13:24
 */
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "serializableHelperData")
@JsonSubTypes({
  @JsonSubTypes.Type(value = LocalTrainingStrategyWrapper.class, name = "LOCAL"),
  @JsonSubTypes.Type(value = SparkTrainingStrategyWrapper.class, name = "SPARK"),
})
public abstract class TrainingStrategyWrapper {

    public TrainingType trainingType = null;

    public static Object generate(TrainingType type) {
        if ( type== TrainingType.LOCAL)
            return new LocalTrainingStrategyWrapper();
        if ( type== TrainingType.SPARK)
            return new SparkTrainingStrategyWrapper();

        throw new RuntimeException("Not Implemented");
    }

    public enum TrainingType {
        LOCAL, SPARK;
    }
}
