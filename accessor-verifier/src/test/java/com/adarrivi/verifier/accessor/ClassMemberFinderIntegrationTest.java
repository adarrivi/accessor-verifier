package com.adarrivi.verifier.accessor;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.adarrivi.verifier.sampleclass.AssortedFieldClass;
import com.adarrivi.verifier.sampleclass.NoFieldsClass;

public class ClassMemberFinderIntegrationTest {

    private ClassMemberFinder victim;

    private Class<?> inputClass;
    private Field inputField;
    private Map<Field, PropertyDescriptor> outputFields;
    private Method outputMethod;

    @Test
    public void getFields_NoFields_ReturnsEmptyCollection() {
        givenClass(NoFieldsClass.class);
        givenVictim();
        whenGetClassMemberMap();
        thenFieldsShouldBeEmpty();
    }

    private void givenClass(Class<?> givenClass) {
        inputClass = givenClass;
    }

    private void givenVictim() {
        victim = new ClassMemberFinder(inputClass);
        victim.findMembers();
    }

    private void whenGetClassMemberMap() {
        outputFields = victim.getClassMemberMap();
    }

    private void thenFieldsShouldBeEmpty() {
        Assert.assertTrue(outputFields.isEmpty());
    }

    @Test
    public void getClassMemberMap_ReturnsOnlyVaildFields() {
        givenClass(AssortedFieldClass.class);
        givenVictim();
        whenGetClassMemberMap();
        thenNumberOfFieldsShouldBe(1);
    }

    private void thenNumberOfFieldsShouldBe(int expectedNumber) {
        Assert.assertEquals(expectedNumber, outputFields.size());
    }

    @Test
    public void findStandardSetterFromField_NoValidSetter_ReturnsNull() {
        givenClass(AssortedFieldClass.class);
        givenVictim();
        givenField("invalidSetter");
        whenFindStandardSetterFromField();
        thenOutputMethodShouldBeNull();
    }

    private void givenField(String fieldName) {
        try {
            inputField = inputClass.getDeclaredField(fieldName);
            inputField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new AssertionError(e);
        }
    }

    private void whenFindStandardSetterFromField() {
        outputMethod = victim.findStandardSetterFromField(inputField);
    }

    private void thenOutputMethodShouldBeNull() {
        Assert.assertNull(outputMethod);
    }

    @Test
    public void findStandardGetterFromField_NoValidGetter_ReturnsNull() {
        givenClass(AssortedFieldClass.class);
        givenVictim();
        givenField("invalidGetter");
        whenFindStandardGetterFromField();
        thenOutputMethodShouldBeNull();
    }

    private void whenFindStandardGetterFromField() {
        outputMethod = victim.findStandardGetterFromField(inputField);
    }

    @Test
    public void findStandardGetterFromField_ReturnsGetter() {
        givenClass(AssortedFieldClass.class);
        givenVictim();
        givenField("validAccessors");
        whenFindStandardGetterFromField();
        thenMethodShouldBeReturned();
    }

    private void thenMethodShouldBeReturned() {
        Assert.assertNotNull(outputMethod);
    }

    @Test
    public void findStandardSetterFromField_ReturnsGetter() {
        givenClass(AssortedFieldClass.class);
        givenVictim();
        givenField("validAccessors");
        whenFindStandardSetterFromField();
        thenMethodShouldBeReturned();
    }

}
