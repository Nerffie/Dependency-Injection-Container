package com.mohamed.dependencyAbstractClasses;

import com.mohamed.annotations.Inject;
import com.mohamed.dependencyClasses.A;
import com.mohamed.dependencyClasses.D;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImplementationOne extends AbstractClass{

    @Inject
    private A a;

    @Inject
    private D d;

    @Override
    public void methodOne() {
        System.out.println("Method One");
    }

    @Override
    public void methodTwo() {
        System.out.println("Method Two");
    }
}
