package com.techupstudio.Base.Models;

public class Patient extends Person {

    Patient() {
        this.setType(this.getClass().getSimpleName().toLowerCase());
    }

    @Override
    public String toString() {
        return "Patient<"+getObjectID()+">";
    }
}
