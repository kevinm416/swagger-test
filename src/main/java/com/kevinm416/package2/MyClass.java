package com.kevinm416.package2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MyClass {

    private final int foo;

    @JsonCreator
    public MyClass(@JsonProperty("foo") int foo) {
        this.foo = foo;
    }

    public int getFoo() {
        return foo;
    }
}
