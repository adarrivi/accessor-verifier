package com.adarrivi.verifier.sampleclass;

public class SimpleClass {
    private int value;
    private String name;

    public String getName() { // <-- Verified OK
        return name;
    }

    public void setName(String name) { // <-- Verified OK
        this.name = name;
    }

    public void increaseValue(int increment) { // <-- Ignored, not standard
                                               // Accessor
        value += increment;
    }

    public int getValue() { // <-- Verified OK
        return value;
    }

}
