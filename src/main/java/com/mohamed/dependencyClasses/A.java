package com.mohamed.dependencyClasses;

import com.mohamed.annotations.Inject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class A {

    private int id;
    private String msg;


    @Override
    public String toString(){
        return "A";
    }
}
