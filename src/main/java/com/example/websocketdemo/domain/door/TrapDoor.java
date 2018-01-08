package com.example.websocketdemo.domain.door;

import com.example.websocketdemo.domain.Trap;

public class TrapDoor extends AbstractDoor{
    private Trap trap;

    @Override
    void initialize(Object object) {
        this.trap = (Trap) object;
    }
}
