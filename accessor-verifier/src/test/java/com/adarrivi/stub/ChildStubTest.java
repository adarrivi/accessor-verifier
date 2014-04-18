package com.adarrivi.stub;

import org.junit.Test;

import com.adarrivi.verifier.accessor.ClassFieldAccessor;
import com.adarrivi.verifier.accessor.ClassFieldsConfig;
import com.adarrivi.verifier.accessor.ClassMemberFinder;
import com.adarrivi.verifier.accessor.FieldAccessorFactory;

public class ChildStubTest {

    @Test
    public void testDirectAccessors() {
        ClassFieldAccessor accessor = new ClassFieldAccessor(new ClassMemberFinder(ChildStub.class), new ClassFieldsConfig(),
                new FieldAccessorFactory());
        accessor.verify(new ChildStub());
    }
}
