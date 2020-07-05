package com.techupstudio.school_management_system.base.models;


public class Staff extends Person {

    public Staff() {
        this.setType(this.getClass().getSimpleName().toLowerCase());
    }

    public void setJobTitle(String jobTitle) {
        addInfo("job_title", jobTitle);
    }

    public String getJobTitle() {
        return getInfo("job_title").toString();
    }

    @Override
    public String toString() {
        return "Staff<"+getObjectID()+">";
    }
}
