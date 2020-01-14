package controllers.request;

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
public class CreateModelRequest implements QueryStringBindable<CreateModelRequest> {

    public String name;
    public String type;

    @Override
    public Optional<CreateModelRequest> bind(String key, Map<String, String[]> parameters) {
        this.name = parameters.get("name")[0];
        this.type = parameters.get("type")[0];
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
