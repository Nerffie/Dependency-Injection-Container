package com.mohamed.dependencyAbstractClasses;

import com.mohamed.annotations.Inject;
import com.mohamed.annotations.Value;
import com.mohamed.dependencyClasses.A;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImplementationTwo implements MyInterface{

    @Inject
    private A a;

    @Value(key="my_name")
    private String myName;

    @Override
    public void myMethod() {
        System.out.println("My Method");
    }
}
