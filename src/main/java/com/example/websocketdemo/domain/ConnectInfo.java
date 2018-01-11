package com.example.websocketdemo.domain;


import lombok.Data;

import java.util.Set;

@Data
public class ConnectInfo {
    private ConnectingType type;
    private String content;
    private String sender;
    private Set<String> allPlayers;

    public enum ConnectingType {
        JOIN,
        LEAVE
    }
}
