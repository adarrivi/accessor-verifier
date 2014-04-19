package com.adarrivi.verifier.accessor;

public class AccessorVerifierBuilder {

    private ClassFieldsConfig config = new ClassFieldsConfig();
    private ClassMemberFinder classMemberFinder;
    private FieldAccessorFactory fieldAccessorFactory;

    AccessorVerifierBuilder(ClassMemberFinder classMemberFinder, FieldAccessorFactory fieldAccessorFactory) {
        this.classMemberFinder = classMemberFinder;
        this.fieldAccessorFactory = fieldAccessorFactory;
    }

    public AccessorVerifierBuilder addNoDefaultConstructorInstance(Object... givenInstances) {
        for (Object givenInstance : givenInstances) {
            config.addNoDefaultConstructorInstance(givenInstance);
        }
        return this;
    }

    public void verifyDirectAccessorsOfInstance(Object victimInstance) {
        ClassFieldAccessor accessor = new ClassFieldAccessor(classMemberFinder, config, fieldAccessorFactory);
        accessor.verify(victimInstance);
    }
}
