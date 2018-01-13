package com.example.websocketdemo.controller;

import com.example.websocketdemo.domain.ConnectInfo;
import com.example.websocketdemo.domain.GameField;
import com.example.websocketdemo.domain.Player;
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class GameController {

    @Autowired
    private GameProcessing gameProcessing;

    private SimpMessagingTemplate template;

    public static Map<String, Room> lobbys = new ConcurrentHashMap<>();

    @MessageMapping("/chat.addUser")
    public void addUser(@Payload ConnectInfo info, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", info.getSender());
    }

    @Autowired
    public GameController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/getrooms/{selectedLobby}")
    public void getAllRoom(@DestinationVariable("selectedLobby") String selectedLobby, User user){
        this.template.convertAndSendToUser(user.getName(), "/topic/getrooms/"+user, lobbys.values());
    }

    @MessageMapping("/create/{room}")
    public void createRoom(@DestinationVariable("room") String room, @Payload ConnectInfo owner, User user) throws Exception {
        Room r = new Room(room, owner,  new HashSet<>(Arrays.asList(owner)), false);

        lobbys.put(user.getName(), r);
        System.out.println(lobbys);
        this.template.convertAndSendToUser(user.getName(), "/topic/create/"+room, r);
    }

    @MessageMapping("/room/{room}")
    public void connectToRoom(@DestinationVariable("room") String room, @Payload ConnectInfo info, User user) throws Exception {
        lobbys.get(room).getAllPlayers().add(info);
        this.template.convertAndSend("/topic/room/"+room, lobbys.get(room));
    }

    @MessageMapping("/ready/{room}")
    public void ready(@DestinationVariable("room") String room, User user){
        String owner = null;
        for(Room rooms:lobbys.values()){
            if(rooms.getName().equals(room)){
                for(ConnectInfo connectInfo: rooms.getAllPlayers()){
                    if (connectInfo.getSender().equals(user.getName())){
                        connectInfo.setReady(true);
                        owner = rooms.getOwner().getSender();
                    }
                }
            }
        }
        boolean start = true;
        for(Room room1: lobbys.values()){
            if(room1.getName().equals(room)){
                for(ConnectInfo connectInfo: room1.getAllPlayers()){
                    if(!connectInfo.isReady()){
                        start = false;
                    }
                }
            }
        }
        Room r = lobbys.get(owner);
        r.setStart(start);
        this.template.convertAndSend("/topic/ready/"+room, r);
    }

    @MessageMapping("/startGame")
    @SendTo("/topic/public/start")
    public GameField start(){
        return null;
//        return new GameProcessing().start(new ArrayList<>(players));
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
