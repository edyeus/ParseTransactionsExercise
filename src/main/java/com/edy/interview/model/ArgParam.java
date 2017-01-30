package com.edy.interview.model;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

public class ArgParam {

    private Object args;

    /**
     * Class to represent request to get all transaction end point
     * @param args, argument payload
     */
    @JsonCreator
    public ArgParam(@JsonProperty("args") Object args){
        this.args = args;
    }

    public Object getArgs() {
        return args;
    }

    public void setArgs(Object args) {
        this.args = args;
    }
}
