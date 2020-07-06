package com.techupstudio.school_management_system.base.models;

public class Bed extends Utility {

    public Bed() {
        super("bed");
    }

    public Bed(Object identifier) {
        super("bed", identifier);
    }

    public String getOccupant() {
        return getInfo("occupant").toString();
    }

    public void setOccupant(String occupant) {
        addInfo("occupant", occupant);
    }

    public String getRoom() {
        return getInfo("fk_room_id").toString();
    }

    public void setRoom(String room_id) {
        addInfo("fk_room_id", room_id);
    }

    @Override
    public String toString() {
        return "Bed<" + super.getObjectID() + ": " + getOccupant() + ">";
    }
}
