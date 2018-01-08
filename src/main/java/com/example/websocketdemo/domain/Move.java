package com.example.websocketdemo.domain;

import lombok.Data;

import java.util.List;

@Data
public class Move {
    private Player player;
    private Monster monster;
    private List<Player> players;
}
