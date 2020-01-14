package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.jbaysolutions.ailabs.builder.nnwrapper.MultiLayerWrapper;
import controllers.request.CreateModelRequest;
import io.ebean.Ebean;
import io.ebean.Finder;
import io.ebean.Model;
import play.libs.Json;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
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
public class NNModel extends Model {

    @Id
    public long modelId;

    public String name;

    public Status status = Status.DRAFT;

    public Date creationDate;

    public NNType modelType = NNType.MULTILAYER;

    public JsonNode rawModel;

    @JsonIgnore
    @OneToMany(mappedBy = "model", orphanRemoval = true)
    public List<NNTrainingStrategy> strategies;

    private static final Finder<Long, NNModel> find = new Finder<>(NNModel.class);

    public static List<NNModel> findAllModels() {
        return find.all();
    }

    public static long createNewModel(CreateModelRequest request) {
        NNModel model = new NNModel();
        model.name = request.name;
        model.creationDate = new Date();
        model.modelType = NNType.valueOf(request.type);

        if (model.modelType == NNType.MULTILAYER) {
            model.rawModel = Json.toJson(new MultiLayerWrapper());
        }

        model.save();
        return model.modelId;
    }

    public static NNModel findModelById(long id) {
        return find.byId(id);
    }

    public static NNModel updateWithRawData(long id, JsonNode rawData) {
        NNModel model = find.byId(id);
        if (model == null)
            return null;

        model.rawModel = rawData;

        if (model.status==Status.DRAFT)
            model.status = Status.COMPILED;

        model.update();
        model.refresh();
        return model;
    }

    public static void deleteModelById(long id) {
        Ebean.beginTransaction();
        find.deleteById(id);
        Ebean.commitTransaction();
    }

    public enum Status {
        DRAFT, COMPILED,
        RUNNING, STOPPED, STARTING, STOPPING
    }

    public enum NNType {
        MULTILAYER,
    }
}
