package com.example.websocketdemo.controller;

import com.example.websocketdemo.domain.Move;
import com.example.websocketdemo.domain.Player;
import com.example.websocketdemo.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rajeevkumarsingh on 24/07/17.
 */
@Controller
public class ChatController {
    private List<Player> players = new ArrayList<Player>(Arrays.asList(
            new Player("a",0,true),
            new Player("b",0,false)
            ));


//    @MessageMapping("/chat.sendMessage")
//    @SendTo("/topic/public")
//    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
//        return chatMessage;
//    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    @MessageMapping("/move")
    @SendTo("/topic/public")
    public Move move(@Payload Move move){

        System.out.println(players.stream().
                filter(p -> p.getName().equals(move.getPlayer().getName())).
                findFirst());
       for(int i=0; i<players.size();i++){
           if(players.get(i).getName().equals(move.getPlayer().getName())){
               players.get(i).setMoving(false);
               if(i+1!=players.size()){
                   players.get(i+1).setMoving(true);
               }else {
                   players.get(0).setMoving(true);
               }

           }
       }
        System.out.println(players);
        move.setPlayers(players);
        return move;
    }
}
