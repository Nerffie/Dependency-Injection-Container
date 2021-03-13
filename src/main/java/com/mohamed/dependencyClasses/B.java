package com.mohamed.dependencyClasses;

import com.mohamed.annotations.Inject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class B {
    private int id;
    private String msg;

    @Inject
    private A a;

    @Override
    public String toString(){
        return "B";
    }
}
