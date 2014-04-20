package com.adarrivi.verifier.sampleclass;

public class GenericClass extends AbstractGeneric<String, Integer, ChildStub> {

    public GenericClass(ChildStub mAttribute) {
        super(mAttribute);
    }

    @Override
    public ChildStub getAttributeM() {
        return attributeM;
    }

    @Override
    public void setAttributeM(ChildStub attributeM) {
        this.attributeM = attributeM;

    }

}
