package com.techupstudio.school_management_system.base.models;

import com.techupstudio.school_management_system.base.database_manager.Models;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public abstract class Person {

    private Map<String, Object> PROPERTIES;
    private Map<String, Object> INFO;

    public Person() {
        //TODO : generate object OBJECT_ID
        PROPERTIES = new HashMap<>();
        INFO = new HashMap<>();
        setProperty(Models.PERSON.TYPE, this.getClass().getSimpleName().toLowerCase());
    }

    public boolean hasProperty(String object_id) {
        return getProperties().containsKey(object_id);
    }

    public boolean hasInfo(String object_id) {
        return getAllInfo().containsKey(object_id);
    }

    public String getFirstName() {
        return getProperty(Models.PERSON.FIRSTNAME).toString();
    }

    public void setFirstName(String firstName) {
        setProperty(Models.PERSON.FIRSTNAME, firstName);
    }

    public String getMiddleName() {
        return getProperty(Models.PERSON.MIDDLENAME).toString();
    }

    public void setMiddleName(String middleName) {
        setProperty(Models.PERSON.MIDDLENAME, middleName);
    }

    public String getLastName() {
        return getProperty(Models.PERSON.LASTNAME).toString();
    }

    public void setLastName(String lastName) {
        setProperty(Models.PERSON.LASTNAME, lastName);
    }

    public String getFullName() {
        return ((getLastName() != null) ? getLastName() : "") + " "
                + ((getFirstName() != null) ? getFirstName() : "") + " "
                + ((getMiddleName() != null) ? getMiddleName() : "");
    }

    public String getAddress() {
        return getProperty(Models.PERSON.FIRSTNAME).toString();
    }

    public void setAddress(String address) {
        setProperty(Models.PERSON.ADDRESS, address);
    }

    public String getGender() {
        return getProperty(Models.PERSON.GENDER).toString();
    }

    public void setGender(String gender) {
        setProperty(Models.PERSON.GENDER, gender);
    }

    public String getContact() {
        return getProperty(Models.PERSON.CONTACT).toString();
    }

    public void setContact(String contact) {
        setProperty(Models.PERSON.CONTACT, contact);
    }

    public void setProperty(String key, Object value) {
        PROPERTIES.put(key, value);
    }

    public String getObjectID() {
        return getProperty(Models.PERSON.OBJECT_ID).toString();
    }

    public void setObjectID(String object_id) {
        setProperty(Models.PERSON.OBJECT_ID, object_id);
    }

//    public Integer getAge() {
//        return (new Date() - new Date(dob)) / 356;
//    }

    public Date getDateOfBirth() {
        return (Date) getProperty(Models.PERSON.DATEOFBIRTH);
    }

    public void setDateOfBirth(Date date_of_birth) {
        setProperty(Models.PERSON.DATEOFBIRTH, date_of_birth);
    }

    public String getType() {
        return getProperty(Models.PERSON.TYPE).toString();
    }

    public void setType(String type) {
        setProperty(Models.PERSON.TYPE, type);
    }

    public Map<String, Object> getProperties() {
        return PROPERTIES;
    }

    public Object getProperty(String key) {
        return PROPERTIES.get(key);
    }

    public Map<String, Object> getAllInfo() {
        return INFO;
    }

    public Object getInfo(String key) {
        return INFO.get(key);
    }

    public Object addInfo(String key, Object value) {
        return INFO.put(key, value);
    }

    @Override
    public String toString() {
        return "Person<" + getObjectID() + ">";
    }

}
