package com.techupstudio.school_management_system.base.sqlite_database;


import java.util.HashMap;


public class ContentValues extends HashMap<String, Object> {


    public Object put(String key, Object value) {
        if (value.toString().contains(" ")){
            if (!value.toString().isEmpty() &&
                    value.toString().charAt(0) == value.toString().charAt(value.toString().length()-1)){
                if (value.toString().charAt(0) == '\'' || value.toString().charAt(0) == '"') {
                    return super.put(key, value);
                }
            }
            return super.put(key, ("\""+value+"\""));
        }
        else if (value instanceof String){
            if (!value.toString().isEmpty() &&
                    value.toString().charAt(0) == value.toString().charAt(value.toString().length()-1)){
                if (value.toString().charAt(0) == '\'' || value.toString().charAt(0) == '"') {
                    return super.put(key, value);
                }
            }
            return super.put(key, ("\""+value+"\""));
        }
        return super.put(key, value);
    }

    public Object get(Object key) {
        Object obj = super.get(key);
        if (obj.toString().contains(" ") || obj instanceof String){
            if (obj.toString().charAt(0) != obj.toString().charAt(obj.toString().length()-1)){
                return "\""+obj+"\"";
            }
        }
        return obj;
    }
}
