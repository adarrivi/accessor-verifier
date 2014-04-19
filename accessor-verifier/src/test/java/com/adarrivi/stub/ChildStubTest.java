package com.adarrivi.stub;

import org.junit.Test;

import com.adarrivi.verifier.accessor.AccessorVerifier;

public class ChildStubTest {

    @Test
    public void testDirectAccessors() {
        AccessorVerifier.forClass(ChildStub.class).verifyDirectAccessorsOfInstance(new ChildStub());
    }
}
