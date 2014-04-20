package com.adarrivi.verifier.sampleclass;

import java.util.List;

public class GenericClassComposition {

    private List<GenericClass> genericClasses;

    private GenericClass genericClass;

    @SuppressWarnings("unused")
    private String other;

    public GenericClassComposition(String other) {
        this.other = other;
    }

    public List<GenericClass> getGenericClasses() {
        return genericClasses;
    }

    public void setGenericClasses(List<GenericClass> genericClasses) {
        this.genericClasses = genericClasses;
    }

    public GenericClass getGenericClass() {
        return genericClass;
    }

    public void setGenericClass(GenericClass genericClass) {
        this.genericClass = genericClass;
    }

    public void modifyOther() {
        other = "modify";
    }

}
