package com.adarrivi.verifier.accessor;

public class AccessorVerifier {

    private static final FieldAccessorFactory FIELD_ACCESSOR_FACTORY = new FieldAccessorFactory();

    public static AccessorVerifierBuilder forClass(Class<?> aClass) {
        return new AccessorVerifierBuilder(new ClassMemberFinder(aClass), FIELD_ACCESSOR_FACTORY);
    }

}
