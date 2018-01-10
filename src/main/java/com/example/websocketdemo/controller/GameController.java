package com.example.websocketdemo.controller;

import com.example.websocketdemo.domain.GameField;
import com.example.websocketdemo.domain.Move;
import com.example.websocketdemo.domain.Player;
import com.example.websocketdemo.model.ChatMessage;
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

    private Set<String> players = new HashSet<>();


//    @MessageMapping("/chat.sendMessage")
//    @SendTo("/topic/public")
//    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
//        return chatMessage;
//    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        players.add(chatMessage.getSender());
        return chatMessage;
    }

    @MessageMapping("/startGame")
    @SendTo("/topic/public/start")
    public GameField start(){
        System.out.println(players);
        return gameProcessing.start(new ArrayList<>(players));
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
