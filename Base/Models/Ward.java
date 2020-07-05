package com.techupstudio.Base.Models;

public class Ward extends Room {

    public Ward(String name){
        super(name);
        this.setType(
                this.getClass()
                        .getSimpleName()
                        .toLowerCase()
        );
    }

    public void addBed(Bed bed){
        //TODO : validate bed
        addUtility(bed);
    }

    public void getBeds(){
        //TODO: get beds in this room and inner rooms
    }

    public void getAvailableBeds(){
        //TODO: check beds in this room and inner rooms
    }

    public void getOccupiedBeds(){
        //TODO: check beds in this room and inner rooms
    }

    @Override
    public String toString() {
        return "Ward<"+getObjectID()+">";
    }
}
