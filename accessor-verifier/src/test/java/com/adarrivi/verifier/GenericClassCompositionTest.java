package com.adarrivi.verifier;

import org.junit.Test;

import com.adarrivi.verifier.accessor.AccessorVerifier;
import com.adarrivi.verifier.sampleclass.ChildStub;
import com.adarrivi.verifier.sampleclass.GenericClass;
import com.adarrivi.verifier.sampleclass.GenericClassComposition;

public class GenericClassCompositionTest {

    @Test
    public void verifyAccessors() {
        GenericClassComposition testClassInstance = new GenericClassComposition("other");
        GenericClass genericClassAttributeInstance = new GenericClass(new ChildStub());
        AccessorVerifier.givenClass(GenericClassComposition.class).givenClassInstance(testClassInstance)
                .givenFieldInstances(genericClassAttributeInstance).verifyDirectAccessors();
    }
}
