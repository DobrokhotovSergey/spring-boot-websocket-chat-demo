package com.example.websocketdemo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Treasure extends Card{
    private int id;

    public Treasure(int id, String name, String description, boolean open) {
        super(id, name, description, open);
        this.id = id;
    }

}
