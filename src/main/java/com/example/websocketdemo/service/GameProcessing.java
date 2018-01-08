package com.example.websocketdemo.service;

import com.example.websocketdemo.domain.Card;
import com.example.websocketdemo.domain.Monster;
import com.example.websocketdemo.domain.Move;
import com.example.websocketdemo.domain.Player;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

import static com.example.websocketdemo.domain.InitializeCards.doors;

@Slf4j
public class GameProcessing extends AbstractGameProcessing {

    public static void main(String[] args) {
        GameProcessing game = new GameProcessing();
        game.initializeCard();
        for(Card card: doors){
            System.out.println(card);
        }

    }


    @Override
    void initializeCard() {
        Collections.shuffle(doors);
    }

    @Override
    public void step(Move move) {
        Player player = move.getPlayer();
        Monster monster = move.getMonster();
        log.info(player+" is moved ");

        if(player.getLevel() > monster.getLevel()){
            log.info(player.getName() +" is kill monster "+monster.getName());
        }else{
            log.info(player.getName() + " is die... other logic");
        }


    }
}
