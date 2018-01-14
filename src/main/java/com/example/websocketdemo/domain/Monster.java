package com.example.websocketdemo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Monster extends Door{
    private int level;
    private int truesure;
    private String immorality;

    public Monster(int id, String name, String description, boolean open, int level, int truesure, String immorality) {
        super(id, name, description, open);
        this.level = level;
        this.truesure = truesure;
        this.immorality = immorality;
    }
}
