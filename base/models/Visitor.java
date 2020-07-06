package com.techupstudio.school_management_system.base.models;


public class Visitor extends Person {

    public Visitor() {
        this.setType(this.getClass().getSimpleName().toLowerCase());
    }

    public void setVisitingPatient(Patient patient) {
        addInfo("patient_id", patient.getObjectID());
    }

    public String getVisitingPatientID() {
        return getInfo("patient_id").toString();
    }

    public void setVisitingPatientID(String patient_id) {
        addInfo("patient_id", patient_id);
    }

    public String getVisitReason() {
        return getInfo("visit_reason").toString();
    }

    public void setVisitReason(String visitReason) {
        addInfo("visit_reason", visitReason);
    }

    public String getRelation() {
        return getInfo("relation").toString();
    }

    public void setRelation(String relation) {
        addInfo("relation", relation);
    }
}
