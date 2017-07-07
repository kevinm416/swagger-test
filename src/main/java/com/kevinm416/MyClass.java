package com.kevinm416;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Value.Immutable
@Value.Style(allParameters = true)
@JsonSerialize(as = ImmutableMyClass.class)
@JsonDeserialize(as = ImmutableMyClass.class)
public interface MyClass {

    String getFoo();

}
