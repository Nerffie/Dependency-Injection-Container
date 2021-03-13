package com.mohamed.dependencyClasses;

import com.mohamed.annotations.Inject;
import com.mohamed.annotations.Value;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class D {

    private int id;
    private String msg;

    @Inject
    private B b;

    @Inject
    private C c;

    @Value(key="db_path")
    private String injectedString;

    @Value(key="number_of_servers")
    private int numberOfServers;


    @Override
    public String toString() {
        return "D";
    }
}
