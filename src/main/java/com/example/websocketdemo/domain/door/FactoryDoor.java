package com.example.websocketdemo.domain.door;

import com.example.websocketdemo.domain.Monster;
import com.example.websocketdemo.domain.Trap;

public class FactoryDoor {

    public AbstractDoor getDoor(Object  object){
        AbstractDoor door = null;

        if(object instanceof Monster){
            door = new MonsterDoor();
        }else if(object instanceof Trap){
            door = new TrapDoor();
        }

        return  door;
    }

}
