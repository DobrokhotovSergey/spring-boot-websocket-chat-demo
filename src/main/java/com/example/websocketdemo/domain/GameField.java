package com.example.websocketdemo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GameField {

    private List<Player> players;
    private List<Card> monsters;
    private List<Truesure> truesures;


}
