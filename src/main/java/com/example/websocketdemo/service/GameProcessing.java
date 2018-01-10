package com.example.websocketdemo.service;

import com.example.websocketdemo.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.example.websocketdemo.domain.InitializeCards.doors;

@Slf4j
@Service
public class GameProcessing extends AbstractGameProcessing {

    private static final int NUMBER_DOOR_CARD_ON_START =4;

    public GameField start(List<String> list){


        initializeCard();
        List<Player> playerList = initializePlayers(list);

        GameField gameField = new GameField(playerList, doors, null);

        return gameField;
    }




    @Override
    void initializeCard() {
        Collections.shuffle(doors);
    }



    @Override
    List<Player> initializePlayers(List<String> playerList) {
        List<Player> players = new ArrayList<>();

        //Каждый игрок получает по 4 карты двери
        for(int i=0;i<playerList.size();i++){
            List<Card> cardList = new ArrayList<>();
            for(int j =0; j < NUMBER_DOOR_CARD_ON_START; j++){
                cardList.add(doors.get(0));
                doors.remove(0);
            }

            players.add(new Player(playerList.get(i), 1, false, cardList, null));
        }


        return players;

//        В самом начале игроки определяют, кто будет ходить первым.
//                Каждый игрок получает по 4 карты двери и 4 карты сокровищ.
//        Перед ходом первого игрока все могут сыграть необходимые им карты (например шмотки, "получи уровень") и игра начинается.
//        (Проклятия нельзя играть до начала хода первого игрока.)

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
