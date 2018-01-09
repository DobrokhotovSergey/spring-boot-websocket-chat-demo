package com.example.websocketdemo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Monster extends Card implements Door{
    private int level;
    private int truesure;
    private String immorality;

    public Monster(int id, String name,  int level,String description, int truesure, String immorality, boolean open) {
        super(id, name, description, open);
        this.level = level;
        this.truesure = truesure;
        this.immorality = immorality;
    }
}
