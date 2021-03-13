package com.mohamed.dependencyAbstractClasses;

import com.mohamed.annotations.Inject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BundleOfAbstractDependencies {

    @Inject
    private AbstractClass abstractClass;

    @Inject
    private MyInterface myInterface;
}
