package com.adarrivi.verifier.sampleclass;

public abstract class AbstractGeneric<T, K, M> {

    private T attributeT;
    private K attributeK;
    protected M attributeM;

    protected AbstractGeneric(M attributeM) {
        this.attributeM = attributeM;
    }

    public T getAttributeT() {
        return attributeT;
    }

    public void setAttributeT(T attributeT) {
        this.attributeT = attributeT;
    }

    public K getAttributeK() {
        return attributeK;
    }

    public void setAttributeK(K attributeK) {
        this.attributeK = attributeK;
    }

    public abstract M getAttributeM();

    public abstract void setAttributeM(M attributeM);

}
