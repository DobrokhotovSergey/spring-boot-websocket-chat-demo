package com.example.websocketdemo.controller;

import lombok.Data;

import java.security.Principal;

/**
 * Created by dn280489dsa on 12.01.18.
 */
@Data
public final class User implements Principal {

    private final String name;
}