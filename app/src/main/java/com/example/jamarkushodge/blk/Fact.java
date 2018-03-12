package com.example.jamarkushodge.blk;

/**
 * Created by jamarkushodge on 3/11/18.
 */

public class Fact {
    private String value;


    Fact(){
        value = " ";
    }

    public Fact(String id, String value){
        this.value = value;
    }



    String getValue() {
        return value;
    }

    void setValue(String value) {
        this.value = value;
    }


}
