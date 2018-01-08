package com.example.websocketdemo.service;

import com.example.websocketdemo.domain.Move;

public abstract class AbstractGameProcessing {

    abstract void initializeCard();
    abstract void step(Move move);

}
