package com.kevinm416;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(as = ImmutableMyClass.class)
@JsonDeserialize(as = ImmutableMyClass.class)
public interface MyClass {

    String getBar();

}
