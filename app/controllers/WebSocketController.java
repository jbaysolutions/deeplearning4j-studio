package controllers;

import com.jbaysolutions.framework.websockets.WebSocketActor;
import com.jbaysolutions.framework.websockets.request.WSBaseMessage;
import lombok.extern.slf4j.Slf4j;
import play.libs.streams.ActorFlow;
import play.mvc.*;
import akka.actor.*;
import akka.stream.*;

import javax.inject.Inject;
@Slf4j
/**
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 24-01-2020
 * Time: 14:27
 */
public class WebSocketController extends Controller {

    private final ActorSystem actorSystem;
    private final Materializer materializer;

    @Inject
    public WebSocketController(ActorSystem actorSystem, Materializer materializer) {
        this.actorSystem = actorSystem;
        this.materializer = materializer;
    }


    public WebSocket socket() {
        log.debug("Websocket Connection called");
        return WebSocket
                .json(WSBaseMessage.class).accept(
                request -> ActorFlow.actorRef(WebSocketActor::props, actorSystem, materializer));
    }


}
