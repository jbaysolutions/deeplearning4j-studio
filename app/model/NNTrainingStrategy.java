package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.jbaysolutions.ailabs.builder.testing.TrainingStrategyWrapper;
import controllers.request.CreateModelRequest;
import io.ebean.Ebean;
import io.ebean.Finder;
import io.ebean.Model;
import play.libs.Json;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

/**
 * (c) JBay Solutions 2010-2020 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 12-12-2019
 * Time: 10:34
 */
@Entity
public class NNTrainingStrategy extends Model {

    @Id
    public long strategyId;

    public String name;

    public int version;

    public Date creationDate;

    public JsonNode rawStrategy;

    public TrainingStatus status = TrainingStatus.DRAFT;

    @JsonIgnore
    @OneToMany(mappedBy = "strategy", orphanRemoval = true)
    public List<TrainingStrategyResult> results;

    @ManyToOne
    public NNModel model;

    private static final Finder<Long, NNTrainingStrategy> find = new Finder<>(NNTrainingStrategy.class);

    public static List<NNTrainingStrategy> findAllModels() {
        return find.all();
    }

    public static long createNewModel(CreateModelRequest request) {
        NNTrainingStrategy model = new NNTrainingStrategy();
        model.name = request.name;
        model.creationDate = new Date();
        model.rawStrategy = null;

        model.save();
        return model.strategyId;
    }

    public static NNTrainingStrategy findStrategyById(long id) {
        return find.byId(id);
    }

    public static NNTrainingStrategy updateWithRawData(long id, JsonNode rawData) {
        NNTrainingStrategy strategy = find.byId(id);
        if (strategy == null)
            return null;

        strategy.rawStrategy = rawData;

        strategy.update();
        strategy.refresh();
        return strategy;
    }

    public static List<NNTrainingStrategy> findAllStrategiesForModel(long modelId) {
        return find.query().where().eq("model.modelId", modelId).findList();
    }

    public static void deleteStrategyById(long id) {
        find.deleteById(id);
    }

    public static void updateToExecuted(long id) {
        find.update().set("status", TrainingStatus.EXECUTED).where().eq("strategyId", id);
    }

    public static void updateToExecuted(NNTrainingStrategy strategy) {
        strategy.status = TrainingStatus.EXECUTED;
        strategy.update();
    }

    public enum TrainingStatus {
        DRAFT, EXECUTED
    }

}
