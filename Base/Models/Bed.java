package com.techupstudio.Base.Models;

public class Bed extends Utility {

    public Bed(){ super("bed"); }

    public Bed(Object identifier) {
        super("bed", identifier);
    }

    public void setOccupant(String occupant) {
        addInfo("occupant", occupant);
    }

    public String getOccupant() {
        return getInfo("occupant").toString();
    }

    public void setRoom(String room_id) {
        addInfo("fk_room_id", room_id);
    }

    public String getRoom() {
        return getInfo("fk_room_id").toString();
    }

    @Override
    public String toString() {
        return "Bed<"+super.getObjectID() + ": " + getOccupant()+">";
    }
}
