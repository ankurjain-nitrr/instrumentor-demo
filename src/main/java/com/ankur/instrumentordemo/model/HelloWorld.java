package com.ankur.instrumentordemo.model;

import lombok.Getter;

@Getter
public class HelloWorld {

    private final String greeting;
    public HelloWorld(String name) {
        this.greeting = "Hello " + name + "!";
    }

}
