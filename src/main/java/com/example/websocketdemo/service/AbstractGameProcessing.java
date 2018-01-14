package com.example.websocketdemo.service;

import com.example.websocketdemo.domain.ConnectInfo;
import com.example.websocketdemo.domain.Move;
import com.example.websocketdemo.domain.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class AbstractGameProcessing {

    abstract void initializeCard();
    abstract void step(Move move);

    abstract List<Player> initializePlayers(List<ConnectInfo> playerList);


}
