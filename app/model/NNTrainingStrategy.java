package model;

import com.fasterxml.jackson.databind.JsonNode;
import com.jbaysolutions.ailabs.builder.testing.TrainingStrategyWrapper;
import controllers.request.CreateModelRequest;
import io.ebean.Finder;
import io.ebean.Model;
import play.libs.Json;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.List;

/**
 * (c) JBay Solutions 2010-2012 All rights reserved.
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

    public Date creationDate;

    public JsonNode rawStrategy;

    public TrainingStatus status = TrainingStatus.DRAFT;

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
        model.rawStrategy = Json.toJson(new TrainingStrategyWrapper());

        model.save();
        return model.strategyId;
    }

    public static NNTrainingStrategy findStrategyById(long id) {
        return find.byId(id);
    }

    public static NNTrainingStrategy updateWithRawData(long id, JsonNode rawData) {
        NNTrainingStrategy model = find.byId(id);
        if (model == null)
            return null;

        model.rawStrategy = rawData;

        model.update();
        model.refresh();
        return model;
    }

    public static List<NNTrainingStrategy> findAllStrategiesForModel(long modelId) {
        return find.query().where().eq("model.modelId", modelId).findList();
    }

    public static void deleteStrategyById(long id) {
        find.deleteById(id);
    }

    public enum TrainingStatus {
        DRAFT, EXECUTED
    }

}
