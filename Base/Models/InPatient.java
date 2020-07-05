package com.techupstudio.Base.Models;

public class InPatient extends Person {

    @Override
    public String toString() {
        return "Patient<"+getObjectID()+">";
    }
}
