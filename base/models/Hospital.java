package com.techupstudio.school_management_system.base.models;

import com.techupstudio.school_management_system.base.database_manager.HospitalDatabase;
import com.techupstudio.school_management_system.base.database_manager.Models;

import java.util.Map;

public abstract class Hospital {
    /*
           //Health Records records day of entry to hospital and what health problem
           //what medical responses were given
           //drugs? | made inpatient? | injection? | surgery? | lab test? | talk session? etc

           //Settings -
               Visiting period
               PaymentStyle -- before admit as inpatient and after halfway through treatment

            //ToDo  build login system...to add staff

        */

    private HospitalDatabase DB;

    public Hospital(String db_path) {
        DB = new HospitalDatabase(db_path);
        prepareHospital();
    }

    public HospitalDatabase getDataBase() {
        return DB;
    }

    protected void addUIDFormatForObjectType(String object_type, String prefix, String postfix, int startIDNumber) {
        getDataBase().addUIDFormatForObjectType(object_type, prefix, postfix, startIDNumber);
    }

    public Map<String, Object> getUIDElementsForObjectType(String object_type) {
        return getDataBase().getUIDElementsForObjectType(object_type);
    }

    public String nextUIDForObjectType(String object_type) {
        return getDataBase().nextUIDForObjectType(object_type);
    }

    public abstract void prepareHospital();

    public void addStaff(Staff staff) {
        getDataBase().insertPerson(staff);
    }

    public void addWard(Room room) {
        getDataBase().insertRoom(room);
    }

    public void addPatient(Patient patient, Room room) {
        getDataBase().insertPerson(patient);
    }

    public void addVisitor(Visitor visitor) {
        getDataBase().insertPerson(visitor);
    }

    public void addInPatient(Patient patient, Room room) {
        getDataBase().updatePersonProperties(patient, Models.PERSON.TYPE);
        getDataBase().getAvailableRooms(); // arg ward name
    }

    public void addPatientRecord() {
    }

    public void removeWard(Ward ward) {
        getDataBase().removeRoom(ward);
    }

    public void removeStaff(Staff staff) {
        getDataBase().removePerson(staff);
    }

    public void removeVisitor(Visitor visitor) {
    }

    public void removeInPatient(Patient patient) {
    }

    public void releaseInPatient(Patient patient) {
    }

    public void addInPatientHealthReport(Patient patient) {
    }

    public void addInPatientProgressReport(Patient patient) {
    }

    public void clearInPatientProgressReport(Patient... patient) {
    }

    public void getInPatientProgressReport(Patient... patient) {
    }

    public void setPatientBed() {
    }

    public void setPatientRoom() {
    }

    public void getOccupiedBeds() {
    }

    public void getAvailableBeds() {
    }

    public void getOccupiedRooms() {
    }

    public void getAvailableRooms() {
    }

    public void getPerson(String... ids) {
    }

    public void getStaffs(String... ids) {
    }

    public void getPatients(String... ids) {
    }

    public void getWards(String... ids) {
    }

    public void getRooms(String... ids) {
    }

    public void getUtilities(String... ids) {
    }

    public void getVisitors(String... ids) {
    }

    public void getPatientRecords(String... ids) {
    }

    public void findPerson(String id) {
    }

    public void findStaff(String id) {
    }

    public void findPatient(String id) {
    }

    public void findVisitor(String id) {
    }

    public void findWard(String id) {
    }

    public void findRoom(String id) {
    }

    public void findUtility(String id) {
    }

    public void existPerson(String id) {
    }

    public void existStaff(String id) {
    }

    public void existPatient(String id) {
    }

    public void existVisitor(String id) {
    }

    public void existWard(String id) {
    }

    public void existRoom(String id) {
    }

    public void existUtility(String ids) {
    }

}
