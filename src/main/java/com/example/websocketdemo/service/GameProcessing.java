package com.example.websocketdemo.service;

import com.example.websocketdemo.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class GameProcessing extends AbstractGameProcessing {

    private static final int NUMBER_DOOR_CARD_ON_START =4;
    private static final int NUMBER_TREASURE_CARD_ON_START =4;
    private List<Door> doorsInGames = new ArrayList<>();
    private List<Treasure> treasureInGames = new ArrayList<>();



    public GameField start(Set<ConnectInfo> list){

        doorsInGames = new ArrayList<>(new InitializeCards().doors);
        treasureInGames =  new ArrayList<>(new InitializeCards().treasures);

        initializeCard();
        List<Player> playerList = initializePlayers(new ArrayList<ConnectInfo>(list));

        GameField gameField = new GameField(playerList, doorsInGames, treasureInGames);

        return gameField;
    }




    @Override
    void initializeCard() {
        Collections.shuffle(treasureInGames);
        Collections.shuffle(doorsInGames);
    }



    @Override
    List<Player> initializePlayers(List<ConnectInfo> playerList) {
        List<Player> players = new ArrayList<>();

        //Каждый игрок получает по 4 карты двери
        for(int i=0;i<playerList.size();i++){
            List<Door> listDoors = new ArrayList<>();

            List<Treasure> listTreasures = new ArrayList<>();
            for(int j =0; j < NUMBER_DOOR_CARD_ON_START; j++){
                listDoors.add(doorsInGames.get(0));
                doorsInGames.remove(0);
            }
            for(int j =0; j < NUMBER_TREASURE_CARD_ON_START; j++){
                listTreasures.add(treasureInGames.get(0));
                treasureInGames.remove(0);
            }

            players.add(new Player(playerList.get(i).getSender(), 1, false, listDoors, listTreasures, null));
        }


        return players;

//        В самом начале игроки определяют, кто будет ходить первым.
//                Каждый игрок получает по 4 карты двери и 4 карты сокровищ.
//        Перед ходом первого игрока все м  огут сыграть необходимые им карты (например шмотки, "получи уровень") и игра начинается.
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
