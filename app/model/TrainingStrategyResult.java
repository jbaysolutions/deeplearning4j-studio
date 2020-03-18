package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import io.ebean.Finder;
import io.ebean.Model;

import javax.persistence.*;
import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 27-01-2020
 * Time: 13:58
 */
@Entity
public class TrainingStrategyResult  extends Model {

    @Id
    public long trainingStrategyResultId;

    public int version;

    public Date executionDate;

    public JsonNode rawStrategy;

    public int bestEpoch;

    public double bestScore;

    @Lob
    public File savedfile;

    @ManyToOne
    public NNTrainingStrategy strategy;

    @JsonIgnore
    @OneToMany(mappedBy = "training", orphanRemoval = true)
    public List<EpochResult> epochResults;

    private static final Finder<Long, TrainingStrategyResult> find = new Finder<>(TrainingStrategyResult.class);

    public static TrainingStrategyResult createStrategyResult(
            NNTrainingStrategy strategy,
            int version,
            JsonNode rawStrategy
    ) {
        TrainingStrategyResult result = new TrainingStrategyResult();
        result.strategy = strategy;
        result.version = version;
        result.executionDate = new Date();
        result.rawStrategy = rawStrategy;
        result.save();
        result.refresh();
        return result;
    }

    public static List<TrainingStrategyResult> findStrategyResultsById(long strategyId) {
        List<TrainingStrategyResult> results = find.query().where().eq("strategy.strategyId", strategyId).findList();
        return results;
    }

    public static TrainingStrategyResult findSpecificResult(long resultId) {
        return find.byId(resultId);
    }
}
