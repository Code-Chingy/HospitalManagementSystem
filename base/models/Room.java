package com.techupstudio.school_management_system.base.models;

import com.techupstudio.school_management_system.base.database_manager.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Room {

    private Map<String, Object> PROPERTIES;
    private Map<String, Object> INFO;
    private List<Utility> UTILITIES;
    private List<Room> INNER_ROOMS;

    public Room(String name) {
        //TODO : auto generate OBJECT_ID
        this.UTILITIES = new ArrayList<>();
        this.INNER_ROOMS = new ArrayList<>();
        this.PROPERTIES = new HashMap<>();
        this.INFO = new HashMap<>();
        setType(this.getClass().getSimpleName().toLowerCase());
        setProperty(Models.ROOM.PARENT, "NULL");
        setRoomName(name);
    }

    public boolean hasProperty(String object_id) {
        return getProperties().containsKey(object_id);
    }

    public boolean hasInfo(String object_id) {
        return getAllInfo().containsKey(object_id);
    }

    public void setType(String type) {
        setProperty(Models.ROOM.TYPE, type);
    }

    public void setParent(Room room) {
        setProperty(Models.ROOM.PARENT, room.getObjectID());
    }

    public String getParent(Room room) {
        return getProperty(Models.ROOM.PARENT).toString();
    }

    public boolean hasParent(Room room) {
        return getProperty(Models.ROOM.PARENT).toString() != "NULL";
    }

    public String getType() {
        return getProperty(Models.ROOM.TYPE).toString();
    }

    public void addInfo(String key, Object value){
        this.INFO.put(key, value);
    }

    public Object getInfo(String key){
        return this.INFO.get(key);
    }

    public Map<String, Object> getAllInfo(){
        return this.INFO;
    }

    public void setProperty(String key, Object value){
        this.PROPERTIES.put(key, value);
    }

    public Object getProperty(String key){
        return this.PROPERTIES.get(key);
    }

    public Map<String, Object> getProperties(){
        return this.PROPERTIES;
    }

    public List<Utility> getUtilities() {
        return this.UTILITIES;
    }

    public Utility getUtilityWithID(Object object_id) {
        for (Utility utility: UTILITIES){
            if (utility.getObjectID().toString().equals(object_id.toString())){
                return utility;
            }
        }
        return null;
    }

    public void addInnerRoom(Room room){
        this.INNER_ROOMS.add(room);
    }
    public boolean hasInnerRooms(){
        return this.INNER_ROOMS.size() > 0;
    }
    public List<Room> getInnerRooms(){
        return this.INNER_ROOMS;
    }

    public String getRoomName() {
        return getProperty(Models.ROOM.NAME).toString();
    }

    public void setRoomName(String room_name) {
        setProperty(Models.ROOM.NAME, room_name);
    }

    public void addUtility(Utility utility) {
        this.UTILITIES.add(utility);
    }

    public String getObjectID() {
        return getProperty(Models.ROOM.OBJECT_ID).toString();
    }

    public void setObjectID(String object_id) {
        setProperty(Models.ROOM.OBJECT_ID, object_id);
    }

    @Override
    public String toString() {
        return "Room<"+getObjectID()+">";
    }
}
