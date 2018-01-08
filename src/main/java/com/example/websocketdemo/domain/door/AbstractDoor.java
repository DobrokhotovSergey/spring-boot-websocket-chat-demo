package com.example.websocketdemo.domain.door;

import lombok.Data;

@Data
public abstract class AbstractDoor {
    abstract void initialize(Object object);
}
