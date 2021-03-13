package com.mohamed.dependencyClasses;


import com.mohamed.annotations.Inject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class E {

    private int id;
    private String msg;

    @Inject
    private D d;


    @Override
    public String toString(){
        return "E";
    }
}
