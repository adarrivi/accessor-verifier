package com.adarrivi.verifier.accessor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AccessorTest {

    private static final String[] NON_INSTANTIABLE = { "nonInstantiable", "getNonInstantiable", "setNonInstantiable" };
    private static final String[] NO_CONSTRUCTOR_ACCESS = { "noAccessConstructor", "getNoAccessConstructor", "setNoAccessConstructor" };
    private static final String[] NO_ACCESS_METHOD = { "noAccessMethods", "getNoAccessMethods", "setNoAccessMethods" };
    private static final String[] METHOD_THROWS_EX = { "throwsException", "getThrowsException", "setThrowsException" };
    private static final String[] NO_DIRECT_ACCESSORS = { "noDirectAccessors", "getNoDirectAccessors", "setNoDirectAccessors" };
    private static final String[] DIRECT_ACCESSORS = { "directAccess", "getDirectAccess", "setDirectAccess" };

    @Rule
    public ExpectedException thrown = ExpectedException.none().handleAssertionErrors();

    private Method getter;
    private Method setter;
    private Field field;
    private Accessor victim;

    @Test
    public void findValueInstance_NonInstantiable_ThrowsAssertionErr() {
        thrown.expect(AssertionError.class);
        thrown.expectMessage(InstantiationException.class.getName());
        givenAccessors(NON_INSTANTIABLE);
        givenVictim();
        whenFindValueInstance();
    }

    private void givenAccessors(String[] accessorNames) {
        try {
            field = AccessorStub.class.getDeclaredField(accessorNames[0]);
            field.setAccessible(true);
            getter = AccessorStub.class.getDeclaredMethod(accessorNames[1], (Class<?>[]) null);
            setter = AccessorStub.class.getDeclaredMethod(accessorNames[2], field.getType());
        } catch (NoSuchFieldException | SecurityException | NoSuchMethodException e) {
            throw new AssertionError(e);
        }
    }

    private void givenVictim() {
        victim = new Accessor(field, getter, setter);
    }

    private void whenFindValueInstance() {
        victim.findValueInstance();
    }

    @Test
    public void findValueInstance_NotAccessible_ThrowsAssertionErr() {
        thrown.expect(AssertionError.class);
        thrown.expectMessage(IllegalAccessException.class.getName());
        givenAccessors(NO_CONSTRUCTOR_ACCESS);
        givenVictim();
        whenFindValueInstance();
    }

    @Test
    public void verifyAccessors_NotAccessible_ThrowsAssertionErr() {
        thrown.expect(AssertionError.class);
        thrown.expectMessage(IllegalAccessException.class.getName());
        givenAccessors(NO_ACCESS_METHOD);
        givenVictim();
        givenFindValueInstance();
        whenVerifyAccessors();
    }

    private void givenFindValueInstance() {
        victim.findValueInstance();
    }

    private void whenVerifyAccessors() {
        victim.verifyAccessors(new AccessorStub());
    }

    @Test
    public void verifyAccessors_MethodThrowsEx_ThrowsAssertionErr() {
        thrown.expect(AssertionError.class);
        thrown.expectMessage(InvocationTargetException.class.getName());
        givenAccessors(METHOD_THROWS_EX);
        givenVictim();
        givenFindValueInstance();
        whenVerifyAccessors();
    }

    @Test
    public void verifyAccessors_NoDirectAccessors_ThrowsAssertionErr() {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("expected:");
        givenAccessors(NO_DIRECT_ACCESSORS);
        givenVictim();
        givenFindValueInstance();
        whenVerifyAccessors();
    }

    @Test
    public void verifyAccessors_DirectAccessor() {
        givenAccessors(DIRECT_ACCESSORS);
        givenVictim();
        givenFindValueInstance();
        whenVerifyAccessors();
    }

}
