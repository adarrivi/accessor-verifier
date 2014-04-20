package com.adarrivi.verifier.accessor;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractMap.SimpleEntry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FieldAccessorIntegrationTest {

    private static final String[] NON_INSTANTIABLE = { "nonInstantiable", "getNonInstantiable", "setNonInstantiable" };
    private static final String[] NO_CONSTRUCTOR_ACCESS = { "noAccessConstructor", "getNoAccessConstructor", "setNoAccessConstructor" };
    private static final String[] NO_ACCESS_METHOD = { "noAccessMethods", "getNoAccessMethods", "setNoAccessMethods" };
    private static final String[] METHOD_THROWS_EX = { "throwsException", "getThrowsException", "setThrowsException" };
    private static final String[] NO_DIRECT_ACCESSORS = { "noDirectAccessors", "getNoDirectAccessors", "setNoDirectAccessors" };
    private static final String[] DIRECT_ACCESSORS = { "directAccess", "getDirectAccess", "setDirectAccess" };

    @Rule
    public ExpectedException thrown = ExpectedException.none().handleAssertionErrors();
    @Mock
    private PropertyDescriptor propertyDescriptor;
    private Field field;
    private FieldAccessor victim;

    @Test
    public void findValueInstance_NonInstantiable_ThrowsAssertionErr() {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("givenFieldInstances");
        givenAccessors(NON_INSTANTIABLE);
        givenVictim();
        whenFindValueInstance();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void givenAccessors(String[] accessorNames) {
        try {
            field = FieldAccessorStub.class.getDeclaredField(accessorNames[0]);
            field.setAccessible(true);
            Method getter = FieldAccessorStub.class.getDeclaredMethod(accessorNames[1], (Class<?>[]) null);
            Method setter = FieldAccessorStub.class.getDeclaredMethod(accessorNames[2], field.getType());
            Mockito.when(propertyDescriptor.getReadMethod()).thenReturn(getter);
            Mockito.when(propertyDescriptor.getWriteMethod()).thenReturn(setter);
            Mockito.when(propertyDescriptor.getPropertyType()).thenReturn((Class) field.getType());
        } catch (NoSuchFieldException | SecurityException | NoSuchMethodException e) {
            throw new AssertionError(e);
        }
    }

    private void givenVictim() {
        victim = new FieldAccessor(new SimpleEntry<>(field, propertyDescriptor));
    }

    private void whenFindValueInstance() {
        victim.findValueInstance();
    }

    @Test
    public void findValueInstance_NotAccessible_ThrowsAssertionErr() {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("givenFieldInstances");
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
        victim.verify(new FieldAccessorStub());
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
        thrown.expectMessage("Expected:");
        givenAccessors(NO_DIRECT_ACCESSORS);
        givenVictim();
        givenFindValueInstance();
        whenVerifyAccessors();
    }

    @Test
    public void verifyAccessors_DirectAccessors_OK() {
        givenAccessors(DIRECT_ACCESSORS);
        givenVictim();
        givenFindValueInstance();
        whenVerifyAccessors();
    }

    @Test
    public void verifyAccessors_NullGetter_NullSetter_OK() {
        givenAccessors(DIRECT_ACCESSORS);
        givenNullGetter();
        givenNullSetter();
        givenVictim();
        givenFindValueInstance();
        whenVerifyAccessors();
    }

    private void givenNullGetter() {
        Mockito.when(propertyDescriptor.getReadMethod()).thenReturn(null);
    }

    private void givenNullSetter() {
        Mockito.when(propertyDescriptor.getWriteMethod()).thenReturn(null);
    }

    @Test
    public void verifyAccessors_NullGetter_OK() {
        givenAccessors(DIRECT_ACCESSORS);
        givenNullGetter();
        givenVictim();
        givenFindValueInstance();
        whenVerifyAccessors();
    }

    @Test
    public void verifyAccessors_NullSetter_OK() {
        givenAccessors(DIRECT_ACCESSORS);
        givenNullSetter();
        givenVictim();
        givenFindValueInstance();
        whenVerifyAccessors();
    }

}
