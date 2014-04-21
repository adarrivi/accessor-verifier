# Accessor Verifier

Small utility library to **automatically** Unit Test _Getter and Setter's_ behavior in Java POJOs.

### Why to do such a thing?
To be able to Unit Test their _**logic**_, but without the pain of the repetitive task.

### But there is no logic in the  Getters and Setters!!
Well, there at least there is one:  Getters and Setters (from now on **_Accessors_**) should set and get **_directly_** the attribute. From my point of view, this means:
* After invoking the setter, the parameter used in the method should be **_equals_** to the _class_ attribute where should be setted on.
* The return value of the getter should be _**equals**_ to the _class_ attribute

So that is mainly what is verified with this library.

A very important note here:
The library will look **only** for _standard_ Accessors ([Oracle JavaBean standard](http://www.oracle.com/technetwork/java/javase/documentation/spec-136004.html)). It is not mandatory to define them, but if done with the _standard naming_, they should access the attribute _**directly**_ and **_without modifying it_**

### How is it going to help me?
If you have the necessity to verify that there is not **_extra logic_** in your Accessors, and you don't want to write lots of Unit Tests doing so. Also when you modify your class attributes (naming, types, etc...) your test will stay **unaffected**!

Besides, if you are a _TDD-100%-test-coverage_ guy, you will definitively end with those stupid arguments about testing the Accessors, because you will do it in a few lines for the whole project ;)

## Usage Examples

### Simple Class
The following test:
```java
public class SimpleClassTest {
    @Test
    public void verifyDirectAccessors() {
        AccessorVerifier.givenClass(SimpleClass.class).verifyDirectAccessors();
    }
}
```
Will run successfully for the given class:

```java
public class SimpleClass {
    private int value;
    private String name;

    public String getName() { // <-- Verified OK
        return name;
    }

    public void setName(String name) { // <-- Verified OK
        this.name = name;
    }

    public void increaseValue(int increment) { // <-- Ignored, not standard Accessor
        value += increment;
    }

    public int getValue() { // <-- Verified OK
        return value;
    }
}
```

But it will fail if we modify the class as follows:
```java
    public void setName(String name) { // <-- Error: the value setted is not equals!
        this.name = name + "helloWorld";
    }
```

### Victim class with no default constructor
The Accessor-Verifier will try to instantiate your class under test with the **_default constructor_**. But if it doesn't exist or is not accessible, then you can create the instance yourself and pass it into the test with _givenClassInstance_:

```java
AccessorVerifier.givenClass(NoDefaultConstructorClass.class).givenClassInstance(new NoDefaultConstructorClass("helloWorld")).verifyDirectAccessors();
```

### Class attributes with no default constructors
Also your class under might have attributes with no default constructor too. No problems! There is a way to specify them too with _givenClassInstance_:

```java
MyAttributeClass1 attribute1 = new MyAttributeClass1("helloWorld");
MyAttributeClass2 attribute2 = new MyAttributeClass2(2234454);
AccessorVerifier.givenClass(MyAttributeContainerClass.class).givenFieldInstances(attribute1, attribute2).verifyDirectAccessors();
```

### Scan all classes for a given package
Accessor-Verifier doesn't support directly this functionality (I didn't want to add any extra library to this one).
But you can use it in combination with [Reflections Library](http://code.google.com/p/reflections/) to do it:

```java
Reflections reflections = new Reflections("my.project.prefix");
Set<Class<?>> allClasses = reflections.getSubTypesOf(Object.class);
for (Class<?> givenClass : allClasses){
    AccessorVerifier.givenClass(givenClass).verifyDirectAccessors();
}
```

More examples (generics, polymorphism, etc...) are available in the test folder.
