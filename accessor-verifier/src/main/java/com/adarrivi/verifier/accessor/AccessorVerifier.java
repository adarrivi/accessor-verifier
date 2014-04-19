package com.adarrivi.verifier.accessor;

public class AccessorVerifier {

    private static final FieldAccessorFactory FIELD_ACCESSOR_FACTORY = new FieldAccessorFactory();

    private AccessorVerifier() {
        // This class is not intended to be instantiated
    }

    public static AccessorVerifierBuilder forClass(Class<?> aClass) {
        return new AccessorVerifierBuilder(new ClassMemberFinder(aClass), FIELD_ACCESSOR_FACTORY);
    }

}
