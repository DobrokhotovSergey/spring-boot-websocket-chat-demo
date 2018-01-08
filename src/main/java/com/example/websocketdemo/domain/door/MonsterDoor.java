package com.example.websocketdemo.domain.door;

import com.example.websocketdemo.domain.Monster;

public class MonsterDoor extends AbstractDoor {

    private Monster monster;

    @Override
    void initialize(Object object) {
        this.monster = (Monster) object;
    }
}
