package com.mohamed.dependencyInjection;

import com.mohamed.annotations.Inject;
import com.mohamed.annotations.Value;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class Container {

    private Map<Class<?>, Object> dependencies;
    private Map<Class<?>, Class<?>> implementations;
    private Map<String, Object> namedDependencies;

    public Container() {
        dependencies = new HashMap<Class<?>, Object>();
        implementations = new HashMap<Class<?>, Class<?>>();
        namedDependencies = new HashMap<String, Object>();
    }

    public void registerSimpleDependency(Class<?> dependencyCLass) {
        dependencies.put(dependencyCLass, null);
    }

    public void registerAbstractDependency(Class<?> abstractDependencyClass, Class<?> implementationDependencyClass) {
        dependencies.put(abstractDependencyClass, null);
        dependencies.put(implementationDependencyClass,null);
        implementations.put(abstractDependencyClass, implementationDependencyClass);
    }

    public void registerNamedDependency(String name, Object o) {
        namedDependencies.put(name, o);
    }

    public <T> T getInstance(Class<T> className,Class<?>... previousClassNames) throws Exception {
        System.out.println("GETTING INSTANCE OF " + className.toString());
        if (!dependencies.containsKey(className))
            throw new Exception("This dependency doesn't exist. The container can't inject it because it hasn't been registered.");


        if(Arrays.stream(previousClassNames).anyMatch(cn->className.equals(cn)))
            throw new Exception("Cycle detected");

        // If the dependency is already instanciated, return the instance
        if (dependencies.get(className) != null) {
            return (T) dependencies.get(className);
        }

        // If the class is abstract, we should instanciate the implementation of it
        if(Modifier.isAbstract(className.getModifiers())){
            if(!implementations.containsKey(className))
                throw new Exception("There is no implementation known to the container for this interface");
            List<Class<?>> newPreviousClassNames = new ArrayList<>(Arrays.asList(previousClassNames));
            newPreviousClassNames.add(className);
            T implementationInstance;
            implementationInstance = (T)getInstance(implementations.get(className),newPreviousClassNames.toArray(previousClassNames));
        //(T) implementations.get(className).getDeclaredConstructor().newInstance();
            dependencies.put(className,implementationInstance);
            dependencies.put(implementations.get(className),implementationInstance);
            return implementationInstance;

        }
        T instance;
        instance = className.getDeclaredConstructor().newInstance();

        // Loop through the fields and see if I need to inject an object
        for (Field field : className.getDeclaredFields()) {
            if (field.isAnnotationPresent(Inject.class)) {
                field.setAccessible(true);
                List<Class<?>> newPreviousClassNames = new ArrayList<>(Arrays.asList(previousClassNames));
                newPreviousClassNames.add(className);
                field.set(instance,this.getInstance(field.getType(),newPreviousClassNames.toArray(previousClassNames)));
            }
            if (field.isAnnotationPresent(Value.class)) {
                field.setAccessible(true);
                if (!this.namedDependencies.containsKey(field.getAnnotation(Value.class).key()))
                    throw new Exception("This binding value doesn't exist. The container can't inject it because it hasn't been registered");

                field.set(instance, this.namedDependencies.get(field.getAnnotation(Value.class).key())
                );
            }
        }

        dependencies.put(className, instance);
        return (T) instance;

    }


}
