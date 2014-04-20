package com.adarrivi.verifier;

import org.junit.Test;

import com.adarrivi.verifier.accessor.AccessorVerifier;
import com.adarrivi.verifier.sampleclass.SimpleClass;

public class SimpleClassTest {

    @Test
    public void verifyDirectAccessors() {
        AccessorVerifier.givenClass(SimpleClass.class).verifyDirectAccessors();
    }
}
