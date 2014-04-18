package com.adarrivi.accessor;

import com.adarrivi.accessor.sample.NoAccessibleConstructorClass;

class AccessorStub {

    private int nonInstantiable;
    private NoAccessibleConstructorClass noAccessConstructor;
    private String noAccessMethods;
    @SuppressWarnings("unused")
    private String throwsException;
    private String noDirectAccessors;
    private String directAccess;

    int getNonInstantiable() {
        return nonInstantiable;
    }

    void setNonInstantiable(int nonInstantiable) {
        this.nonInstantiable = nonInstantiable;
    }

    NoAccessibleConstructorClass getNoAccessConstructor() {
        return noAccessConstructor;
    }

    void setNoAccessConstructor(NoAccessibleConstructorClass noAccessConstructor) {
        this.noAccessConstructor = noAccessConstructor;
    }

    @SuppressWarnings("unused")
    private String getNoAccessMethods() {
        return noAccessMethods;
    }

    @SuppressWarnings("unused")
    private void setNoAccessMethods(String noAccessMethods) {
        this.noAccessMethods = noAccessMethods;
    }

    String getThrowsException() {
        throw new UnsupportedOperationException();
    }

    void setThrowsException(@SuppressWarnings("unused") String throwsException) {
        throw new UnsupportedOperationException();
    }

    String getNoDirectAccessors() {
        return noDirectAccessors + "getter";
    }

    void setNoDirectAccessors(String noDirectAccessors) {
        this.noDirectAccessors = noDirectAccessors + "setter";
    }

    String getDirectAccess() {
        return directAccess;
    }

    void setDirectAccess(String directAccess) {
        this.directAccess = directAccess;
    }

}
