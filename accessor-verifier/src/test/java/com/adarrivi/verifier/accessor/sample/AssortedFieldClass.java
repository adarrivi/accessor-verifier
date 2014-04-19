package com.adarrivi.verifier.accessor.sample;

public class AssortedFieldClass {

    private String validAccessors;
    private String invalidGetter;
    @SuppressWarnings("unused")
    private String invalidSetter;

    public String getValidAccessors() {
        return validAccessors;
    }

    public void setValidAccessors(String validAccessors) {
        this.validAccessors = validAccessors;
    }

    public String getNoValidInvalidGetter() {
        return invalidGetter;
    }

    public void setNoValidInvalidSetter(String invalidSetter) {
        this.invalidSetter = invalidSetter;
    }

}
