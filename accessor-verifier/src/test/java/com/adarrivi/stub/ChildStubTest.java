package com.adarrivi.stub;

import org.junit.Test;

import com.adarrivi.verifier.AccessorConfiguration;
import com.adarrivi.verifier.ClassMethodAccessor;
import com.adarrivi.verifier.accessor.AccessorFactory;

public class ChildStubTest {

    @Test
    public void testDirectAccessors() {
        ClassMethodAccessor accessor = new ClassMethodAccessor(ChildStub.class, new AccessorConfiguration(), new AccessorFactory());
        accessor.addInstantiatedValue(int.class, 10);
        accessor.verify(new ChildStub());
    }

}
