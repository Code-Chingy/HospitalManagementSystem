package com.techupstudio.school_management_system.base.models;

public class Patient extends Person {

    Patient() {
        this.setType(this.getClass().getSimpleName().toLowerCase());
    }

    @Override
    public String toString() {
        return "Patient<"+getObjectID()+">";
    }
}
