package com.example.websocketdemo.service;

import com.example.websocketdemo.domain.Move;
import com.example.websocketdemo.domain.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGameProcessing {

    abstract void initializeCard();
    abstract void step(Move move);

    abstract List<Player> initializePlayers(List<String> playerList);


}
