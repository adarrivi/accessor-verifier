package com.adarrivi.verifier;

import org.junit.Test;

import com.adarrivi.verifier.accessor.AccessorVerifier;
import com.adarrivi.verifier.sampleclass.ChildStub;

public class ChildStubTest {

    @Test
    public void testDirectAccessors() {
        AccessorVerifier.givenClass(ChildStub.class).verifyDirectAccessors();
    }
}
