package controllers.request;

import com.jbaysolutions.ailabs.builder.testing.TrainingStrategyWrapper;
import play.mvc.QueryStringBindable;

import java.util.Map;
import java.util.Optional;

/**
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 12-12-2019
 * Time: 18:29
 */
public class CreateTrainingRequest implements QueryStringBindable<CreateTrainingRequest> {

    public long modelId;
    public String name;
    public TrainingStrategyWrapper.TrainingType type;

    @Override
    public Optional<CreateTrainingRequest> bind(String key, Map<String, String[]> parameters) {
        this.name = parameters.get("name")[0];
        this.modelId = Long.valueOf(parameters.get("modelId")[0]);
        this.type = TrainingStrategyWrapper.TrainingType.valueOf(parameters.get("type")[0]);
        return Optional.of(this);
    }

    @Override
    public String unbind(String key) {
        return null;
    }

    @Override
    public String javascriptUnbind() {
        return null;
    }

}
