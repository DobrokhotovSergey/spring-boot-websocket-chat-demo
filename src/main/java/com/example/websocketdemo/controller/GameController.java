package com.example.websocketdemo.controller;

import com.example.websocketdemo.domain.ConnectInfo;
import com.example.websocketdemo.domain.GameField;
import com.example.websocketdemo.domain.Room;
import com.example.websocketdemo.service.GameProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class GameController {

    @Autowired
    private GameProcessing gameProcessing;

    private SimpMessagingTemplate template;

    public static Set<String> players = new HashSet<>();
    private Set<Room> lobbys = new HashSet<>();

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ConnectInfo addUser(@Payload ConnectInfo info, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", info.getSender());
        return info;
    }

    @Autowired
    public GameController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/getrooms/{user}")
    public void getAllRoom(@DestinationVariable("user") String user){
        this.template.convertAndSend("/topic/getrooms/"+user, lobbys);
    }

    @MessageMapping("/create/{room}")
    public void createRoom(@DestinationVariable("room") String room, @Payload ConnectInfo info) throws Exception {
        Room r = new Room(room, info.getSender());
        players.add(info.getSender());
        info.setAllPlayers(players);
        if(!lobbys.contains(r)){
            lobbys.add(r);
        }else{
            System.out.println("lobbys contains this room!");
        }
        this.template.convertAndSend("/topic/create/"+room, info);
    }

    @MessageMapping("/room/{room}")
    public void connectToRoom(@DestinationVariable("room") String room, @Payload ConnectInfo info) throws Exception {
        System.out.println(room);
        System.out.println(info);
        players.add(info.getSender());
        info.setAllPlayers(players);
        this.template.convertAndSend("/topic/room/"+room, info);
    }

    @MessageMapping("/startGame")
    @SendTo("/topic/public/start")
    public GameField start(){
        return new GameProcessing().start(new ArrayList<>(players));
    }

//    @MessageMapping("/move")
//    @SendTo("/topic/public")
//    public Move move(@Payload Move move){
//       for(int i=0; i<players.size();i++){
//           if(players.get(i).getName().equals(move.getPlayer().getName())){
//               players.get(i).setMoving(false);
//               if(i+1!=players.size()){
//                   players.get(i+1).setMoving(true);
//               }else {
//                   players.get(0).setMoving(true);
//               }
//
//           }
//       }
//        System.out.println(players);
//        move.setPlayers(players);
//        return move;
//    }
}
