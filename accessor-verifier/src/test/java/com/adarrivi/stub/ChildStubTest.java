package com.adarrivi.stub;

import org.junit.Test;

import com.adarrivi.accessor.AccessorConfiguration;
import com.adarrivi.accessor.ClassMethodAccessor;

public class ChildStubTest {

    @Test
    public void testDirectAccessors() {
        ClassMethodAccessor accessor = new ClassMethodAccessor(ChildStub.class, new AccessorConfiguration());
        accessor.addInstantiatedValue(int.class, 10);
        accessor.verify(new ChildStub());
    }

}
