package com.example.websocketdemo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    private String name;
    private ConnectInfo owner;
    private Set<ConnectInfo> allPlayers;
    private boolean start;
}
