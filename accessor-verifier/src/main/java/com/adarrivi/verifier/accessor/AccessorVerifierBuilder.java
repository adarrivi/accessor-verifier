package com.adarrivi.verifier.accessor;

public class AccessorVerifierBuilder {

    private ClassFieldsConfig config = new ClassFieldsConfig();
    private ClassMemberFinder classMemberFinder;
    private FieldAccessorFactory fieldAccessorFactory;
    private Object classInstance;

    AccessorVerifierBuilder(ClassMemberFinder classMemberFinder, FieldAccessorFactory fieldAccessorFactory) {
        this.classMemberFinder = classMemberFinder;
        this.fieldAccessorFactory = fieldAccessorFactory;
    }

    public AccessorVerifierBuilder givenClassInstance(Object givenInstance) {
        this.classInstance = givenInstance;
        return this;
    }

    public AccessorVerifierBuilder givenFieldInstances(Object... fieldInstances) {
        for (Object fieldInstance : fieldInstances) {
            config.addFieldInstance(fieldInstance);
        }
        return this;
    }

    public void verifyDirectAccessors() {
        setClassInstanceIfNotInstantiated();
        ClassFieldAccessor accessor = new ClassFieldAccessor(classMemberFinder, config, fieldAccessorFactory);
        accessor.verify(classInstance);
    }

    private void setClassInstanceIfNotInstantiated() {
        if (classInstance == null) {
            try {
                classInstance = classMemberFinder.getGivenClass().newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new AssertionError("Cannot instantiate the class under test: " + classMemberFinder.getGivenClass().getName()
                        + "; provide manually an instance with the method givenClassInstance", e);
            }
        }
    }
}
