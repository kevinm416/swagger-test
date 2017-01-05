package com.kevinm416.package1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MyClass {

    private final String bar;

    @JsonCreator
    public MyClass(@JsonProperty("bar") String bar) {
        this.bar = bar;
    }

    public String getBar() {
        return bar;
    }
}
