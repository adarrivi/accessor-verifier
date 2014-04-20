package com.adarrivi.verifier;

import org.junit.Test;

import com.adarrivi.verifier.accessor.AccessorVerifier;
import com.adarrivi.verifier.sampleclass.ChildStub;
import com.adarrivi.verifier.sampleclass.GenericClass;

public class GenericClassTest {

    @Test
    public void verifyDirectAccessors() {
        ChildStub childStub = new ChildStub();
        AccessorVerifier.givenClass(GenericClass.class).givenClassInstance(new GenericClass(childStub)).verifyDirectAccessors();
    }
}
