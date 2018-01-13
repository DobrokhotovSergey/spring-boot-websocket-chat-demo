package com.example.websocketdemo.controller;

import com.example.websocketdemo.domain.ConnectInfo;
import com.example.websocketdemo.domain.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Iterator;
import java.util.Set;

import static com.example.websocketdemo.controller.GameController.lobbys;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if(username != null) {
            String roomName = null;
            ConnectInfo infos = null;
            logger.info("User Disconnected : " + username);
            for(Room room: lobbys.values()){
                Set<ConnectInfo> setInfo = room.getAllPlayers();
                Iterator<ConnectInfo> i = setInfo.iterator();
                while (i.hasNext()) {
                    ConnectInfo connectInfo = i.next();
                    if( connectInfo.getSender().equals(username)){
                        ConnectInfo info = new ConnectInfo();
                        info.setType(ConnectInfo.ConnectingType.LEAVE);
                        info.setSender(username);
                        roomName = room.getName();
                        infos = info;
                        messagingTemplate.convertAndSend("/topic/public/"+  roomName, infos);
                        i.remove();
                        if(room.getAllPlayers().size()==0){
                            lobbys.remove(room.getOwner().getSender());
                        }

                    }
                }
            }
        }
    }
}
