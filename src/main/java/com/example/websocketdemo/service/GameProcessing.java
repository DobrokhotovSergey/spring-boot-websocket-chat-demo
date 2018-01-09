package com.example.websocketdemo.service;

import com.example.websocketdemo.domain.Card;
import com.example.websocketdemo.domain.Monster;
import com.example.websocketdemo.domain.Move;
import com.example.websocketdemo.domain.Player;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.example.websocketdemo.domain.InitializeCards.doors;

@Slf4j
public class GameProcessing extends AbstractGameProcessing {

    private static final int NUMBER_DOOR_CARD_ON_START =4;



    public static void main(String[] args) {
        List<String> stringList = new ArrayList<>(Arrays.asList("Sergey","Anastasia","player1", "player2"));
        GameProcessing game = new GameProcessing();
        game.initializeCard();
        game.initializePlayers(stringList).forEach(s-> {
            System.out.println(s);
        });

    }


    @Override
    void initializeCard() {
        Collections.shuffle(doors);
    }



    @Override
    List<Player> initializePlayers(List<String> playerList) {
        List<Player> players = new ArrayList<>();

        for(int i=0;i<playerList.size();i++){
            List<Card> cardList = new ArrayList<>();
            for(int j =0; j < NUMBER_DOOR_CARD_ON_START; j++){
                cardList.add(doors.get(i+j));
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
