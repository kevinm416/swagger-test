package com.kevinm416;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImmutableMyClass implements MyClass {

    private final String bar;

    public ImmutableMyClass(String bar) {
        this.bar = bar;
    }

    @JsonProperty("bar")
    public String getBar() {
        return null;
    }
}
