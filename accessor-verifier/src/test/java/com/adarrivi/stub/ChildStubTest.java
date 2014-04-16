package com.adarrivi.stub;

import org.junit.Test;

import com.adarrivi.ClassMethodAccessor;

public class ChildStubTest {

    @Test
    public void testDirectAccessors() {
        ClassMethodAccessor accessor = new ClassMethodAccessor(ChildStub.class);
        accessor.addInstantiatedValue(int.class, 10);
        accessor.verify(new ChildStub());
    }

}
