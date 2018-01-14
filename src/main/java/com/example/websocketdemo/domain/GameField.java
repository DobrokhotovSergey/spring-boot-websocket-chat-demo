package com.example.websocketdemo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class GameField {

    private List<Player> players;
    private List<Door> monsters;
    private List<Treasure> truesures;


}
