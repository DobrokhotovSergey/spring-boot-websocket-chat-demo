package com.example.websocketdemo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Card implements Cloneable{
    private int id;
    private String name;
    private String description;
    private boolean open;
}
