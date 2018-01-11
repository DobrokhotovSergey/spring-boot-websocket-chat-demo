package com.example.websocketdemo.controller;

import com.example.websocketdemo.domain.ConnectInfo;
import com.example.websocketdemo.domain.GameField;
import com.example.websocketdemo.service.GameProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class GameController {

    @Autowired
    private GameProcessing gameProcessing;

    public static Set<String> players = new HashSet<>();

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ConnectInfo addUser(@Payload ConnectInfo info, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", info.getSender());
        players.add(info.getSender());
        info.setAllPlayers(players);
        return info;
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
