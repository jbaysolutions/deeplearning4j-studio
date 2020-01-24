package com.jbaysolutions.framework.websockets;

import akka.actor.*;
import com.jbaysolutions.framework.websockets.request.StartTrainingMessage;

/**
 * (c) JBay Solutions 2010-2012 All rights reserved.
 * <p>
 * User: RuiP
 * Date: 24-01-2020
 * Time: 14:29
 */
public class WebSocketActor extends AbstractActor {

    public static Props props(ActorRef out) {
        return Props.create(WebSocketActor.class, out);
    }

    private final ActorRef out;
    private final WebSocketMessageHandler messageHandler;

    public WebSocketActor(ActorRef out) {
        this.out = out;
        this.messageHandler = new WebSocketMessageHandler(out);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(StartTrainingMessage.class, message -> messageHandler.handleMessage(message))
                .build();
    }

    @Override
    public void postStop() throws Exception {
        
    }

}