package com.techupstudio.Base.Models;

import com.techupstudio.Base.HospitalDatabaseManager.Models;


import java.util.HashMap;
import java.util.Map;

public class Utility {

    private Map<String, Object> PROPERTIES;
    private Map<String, Object> INFO;

    Utility(String type) {
        this.PROPERTIES = new HashMap<>();
        this.INFO = new HashMap<>();
        setType(type);
    }

    public boolean hasProperty(String object_id) {
        return getProperties().containsKey(object_id);
    }

    public boolean hasInfo(String object_id) {
        return getAllInfo().containsKey(object_id);
    }

    Utility(String type, Object identifier){}

    public void setObjectID(String objectID) {
        setProperty(Models.UTILITIES.OBJECT_ID, objectID);
    }

    public void setOwnerType(String owner_type) {
        setProperty(Models.UTILITIES.OWNER_TYPE, owner_type);
    }

    public void setOwnerID(String owner_id) {
        setProperty(Models.UTILITIES.OWNER_ID, owner_id);
    }

    public void setType(String type) {
        setProperty(Models.UTILITIES.OBJECT_TYPE, type);
    }

    public void addInfo(String key, Object value){
        INFO.put(key, value);
    }

    public void setProperty(String key, Object value){ PROPERTIES.put(key, value); }

    public Object getInfo(String key){
        return INFO.get(key);
    }

    public Object getProperty(String key){
        return PROPERTIES.get(key);
    }

    public Map<String, Object> getProperties(){
        return PROPERTIES;
    }

    public Map<String, Object> getAllInfo(){
        return INFO;
    }

    public String getType() { return getProperty(Models.UTILITIES.OBJECT_TYPE).toString(); }

    public String getOwnerType() {
        return getProperty(Models.UTILITIES.OWNER_TYPE).toString();
    }

    public String getOwnerID() {
        return getProperty(Models.UTILITIES.OWNER_ID).toString();
    }

    //TODO: FUTURE - getOwner - using owner type and id

    public Object getObjectID() {
        return getProperty(Models.UTILITIES.OBJECT_ID).toString();
    }

    @Override
    public String toString() {
        return "Utility<"+getType()+": "+getObjectID()+">";
    }
}
