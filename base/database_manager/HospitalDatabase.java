package com.techupstudio.school_management_system.base.database_manager;

import com.techupstudio.school_management_system.base.models.*;
import com.techupstudio.school_management_system.base.sqlite_database.ContentValues;
import com.techupstudio.school_management_system.base.sqlite_database.SQLDatabase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HospitalDatabase extends SQLDatabase {

    public HospitalDatabase(String db_path) {
        super(db_path);
        createDatabaseTables();
    }

    private void log(String tag, String message) {
        Logger.getLogger("Hospital Database")
                .log(Level.INFO, (tag + ": " + message));
    }

    private void createDatabaseTables() {
        //TODO :  tables : STAFF | PATIENTS | VISITORS |  UTILITIES| ROOM | OBJECT_INFO
        //TODO : | PATIENTS_HEALTH_RECORDS | INPATIENTS_RECORDS | SETTINGS/PREFERENCES

        //PERSONS - STAFF | PATIENT | VISITOR
        try {
            execSQL().withTable("PERSONS").create(
                    Models.PERSON.OBJECT_ID + " TEXT UNIQUE PRIMARY KEY",
                    Models.PERSON.TYPE + " TEXT NOT NULL",
                    Models.PERSON.FIRSTNAME + " TEXT NOT NULL",
                    Models.PERSON.MIDDLENAME + " TEXT",
                    Models.PERSON.LASTNAME + " TEXT NOT NULL",
                    Models.PERSON.ADDRESS + " TEXT",
                    Models.PERSON.CONTACT + " VARCHAR(10) NOT NULL",
                    Models.PERSON.GENDER + " VARCHAR(6) NOT NULL",
                    Models.PERSON.DATEOFBIRTH + " DATETIME NOT NULL"
            ).commit();
        } catch (SQLException e) {
            log("Error", e.getMessage());
        }

        //TODO: log created table persons

        //UTILITIES - BED | WARD-ROOM | OTHER
        try {
            execSQL().withTable("UTILITIES").create(
                    Models.UTILITIES.OBJECT_ID + " TEXT UNIQUE PRIMARY KEY",
                    Models.UTILITIES.OBJECT_TYPE + " TEXT NOT NULL",  // bed, ward-room, other
                    Models.UTILITIES.OWNER_TYPE + " TEXT NOT NULL", // room | ward-room
                    Models.UTILITIES.OWNER_ID + " TEXT NOT NULL" //object_id
            ).commit();
        } catch (SQLException e) {
            log("Error", e.getMessage());
        }

        //TODO: log created table utilities

        //OBJECT INFO - KEY | VALUE
        try {
            execSQL().withTable("OBJECT_INFO").create(
                    Models.OBJECT_INFO.ID + " INTEGER PRIMARY KEY AUTOINCREMENT",
                    Models.OBJECT_INFO.OBJECT_ID + " TEXT NOT NULL",
                    Models.OBJECT_INFO.OBJECT_TYPE + " TEXT NOT NULL", // person (staff, patient, visitor) | room | utility (bed, ward-room, other)
                    Models.OBJECT_INFO.KEY + " TEXT NOT NULL",
                    Models.OBJECT_INFO.VALUE + " TEXT"
            ).commit();
        } catch (SQLException e) {
            log("Error", e.getMessage());
        }

        //TODO: log created table object_info

        //UTILITIES - WARD | WARD-ROOM
        try {
            execSQL().withTable("ROOMS").create(
                    Models.ROOM.OBJECT_ID + " TEXT PRIMARY KEY",
                    Models.ROOM.TYPE + " TEXT NOT NULL", //ward | ward-room
                    Models.ROOM.NAME + " TEXT",
                    Models.ROOM.PARENT + " TEXT" //id of parent room
            ).commit();
        } catch (SQLException e) {
            log("Error", e.getMessage());
        }

        //TODO: log created table object_info

        //UTILITIES - PATIENTS_HEALTH_RECORDS
        try {
            execSQL().withTable("PATIENTS_HEALTH_RECORDS").create(
                    Models.PATIENT.HEALTH_RECORDS.ID + " INTEGER PRIMARY KEY AUTOINCREMENT",
                    Models.PATIENT.HEALTH_RECORDS.PATIENT_ID + " TEXT NOT NULL",
                    Models.PATIENT.HEALTH_RECORDS.CHECK_IN_TIMESTAMP + " DATETIME DEFAULT DATETIME",
                    Models.PATIENT.HEALTH_RECORDS.CHECK_OUT_TIMESTAMP + " DATETIME",
                    Models.PATIENT.HEALTH_RECORDS.HEALTH_PROBLEM + " TEXT",
                    Models.PATIENT.HEALTH_RECORDS.HEALTH_SOLUTION + " TEXT",
                    Models.PATIENT.HEALTH_RECORDS.TREATMENT_RESPONSE + " TEXT"
            ).commit();
        } catch (SQLException e) {
            log("Error", e.getMessage());
        }

        //TODO: log created table patient_health_records

        //UTILITIES - PATIENTS_HEALTH_REPORTS
        try {
            execSQL().withTable("INPATIENTS_PROGRESS_REPORTS").create(
                    Models.INPATIENT.PROGRESS_REPORT.ID + " INTEGER PRIMARY KEY AUTOINCREMENT",
                    Models.INPATIENT.PROGRESS_REPORT.PATIENT_ID + " TEXT NOT NULL",
                    Models.INPATIENT.PROGRESS_REPORT.HEALTH_PERSONNEL_TYPE + " TEXT NOT NULL",
                    Models.INPATIENT.PROGRESS_REPORT.HEALTH_PERSONNEL_ID + " TEXT NOT NULL",
                    Models.INPATIENT.PROGRESS_REPORT.TREATMENT_ACTIVITY + " TEXT",
                    Models.INPATIENT.PROGRESS_REPORT.TREATMENT_RESPONSE + " TEXT",
                    Models.INPATIENT.PROGRESS_REPORT.CHECK_IN_TIMESTAMP + " DATETIME DEFAULT DATETIME",
                    Models.INPATIENT.PROGRESS_REPORT.CHECK_OUT_TIMESTAMP + " DATETIME",
                    Models.INPATIENT.PROGRESS_REPORT.NOTES + " TEXT"
            ).commit();
        } catch (SQLException e) {
            log("Error", e.getMessage());
        }

        //TODO: log created table inpatient_progress_reports


        //OBJECT_IDS - ROOM | UTILITY | STAFF | PATIENT | VISITOR
        try {
            execSQL().withTable("OBJECT_UID_FORMATS").create(
                    Models.OBJECT_UID.TYPE + " TEXT PRIMARY KEY",
                    Models.OBJECT_UID.UID_PREFIX + " TEXT NOT NULL",
                    Models.OBJECT_UID.UID_POSTFIX + " TEXT NOT NULL",
                    Models.OBJECT_UID.UID_CURRENT_COUNT + " INTEGER NOT NULL"
            ).commit();
        } catch (SQLException e) {
            log("Error", e.getMessage());
        }

        //TODO: log created table inpatient_progress_reports

    }

    private boolean validatePerson(Person person) {
        if (person instanceof Staff) {
            return (person.hasProperty(Models.STAFF.FIRSTNAME) && person.hasProperty(Models.STAFF.LASTNAME) &&
                    person.hasProperty(Models.STAFF.CONTACT) && person.hasProperty(Models.STAFF.GENDER) &&
                    person.hasInfo(Models.STAFF.INFO_JOB_TITLE) && person.hasProperty(Models.STAFF.OBJECT_ID));
        } else if (person instanceof Patient) {
            return (person.hasProperty(Models.PATIENT.FIRSTNAME) && person.hasProperty(Models.PATIENT.LASTNAME) &&
                    person.hasProperty(Models.PATIENT.CONTACT) && person.hasProperty(Models.PATIENT.GENDER) &&
                    person.hasProperty(Models.PATIENT.OBJECT_ID));
        } else if (person instanceof Visitor) {
            return (person.hasProperty(Models.VISITOR.FIRSTNAME) && person.hasProperty(Models.VISITOR.LASTNAME) &&
                    person.hasProperty(Models.VISITOR.CONTACT) && person.hasProperty(Models.VISITOR.GENDER) &&
                    person.hasProperty(Models.VISITOR.OBJECT_ID) && person.hasProperty(Models.VISITOR.INFO_RELATION) &&
                    person.hasProperty(Models.VISITOR.INFO_VISIT_REASON) &&
                    person.hasProperty(Models.VISITOR.INFO_FOREIGN_KEY_PATIENT_VISITING));
        }
        return false;
    }

    public void validateRoom(String... ids) {
    }

    public void addUIDFormatForObjectType(String object_type, String prefix, String postfix, int startIDNumber) {
        if (object_type != null && startIDNumber >= 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(Models.OBJECT_UID.TYPE, object_type.toLowerCase());
            contentValues.put(Models.OBJECT_UID.UID_PREFIX, prefix);
            contentValues.put(Models.OBJECT_UID.UID_POSTFIX, postfix);
            contentValues.put(Models.OBJECT_UID.UID_CURRENT_COUNT, startIDNumber);
            try {
                execSQL().withTable("OBJECT_UID_FORMATS").insert(contentValues).commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String nextUIDForObjectType(String object_type) {
        Map<String, Object> nextUIDElements = getUIDElementsForObjectType(object_type);
        String UIDPrefix = nextUIDElements.get("prefix").toString();
        Integer currentUIDNumber = (Integer) nextUIDElements.get("current_count");
        String UIDPostfix = nextUIDElements.get("postfix").toString();
        //TODO: update current_uid_count
        ContentValues contentValues = new ContentValues();
        contentValues.put(Models.OBJECT_UID.UID_CURRENT_COUNT, (currentUIDNumber + 1));
        try {
            execSQL().withTable("OBJECT_UID_FORMATS").update(contentValues)
                    .where(Models.OBJECT_UID.TYPE + " == '" + object_type.toLowerCase() + "'")
                    .commit();
            return "" + ((UIDPrefix != null) ? UIDPrefix : "") + (currentUIDNumber + 1) + ((UIDPostfix != null) ? UIDPostfix : "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, Object> getUIDElementsForObjectType(String object_type) {
        try {
            ResultSet result = execSQL().withTable("OBJECT_UID_FORMATS")
                    .select().where(Models.OBJECT_UID.TYPE + " == '" + object_type.toLowerCase() + "'").
                            getResult().all().get();

            if (result.next()) {
                String prefix = result.getString(Models.OBJECT_UID.UID_PREFIX);
                String postfix = result.getString(Models.OBJECT_UID.UID_POSTFIX);
                Integer count = result.getInt(Models.OBJECT_UID.UID_CURRENT_COUNT);
                Map<String, Object> retList = new HashMap<>();
                retList.put("prefix", prefix);
                retList.put("current_count", count);
                retList.put("postfix", postfix);
                System.out.println(retList);
                return retList;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void insertPerson(Person person) {

        if (validatePerson(person)) {
            ContentValues values = new ContentValues();
            values.put(Models.PERSON.OBJECT_ID, person.getObjectID());
            values.put(Models.PERSON.FIRSTNAME, person.getFirstName());
            values.put(Models.PERSON.LASTNAME, person.getLastName());
            values.put(Models.PERSON.GENDER, person.getGender());
            values.put(Models.PERSON.DATEOFBIRTH, person.getDateOfBirth());
            values.put(Models.PERSON.CONTACT, person.getContact());
            values.put(Models.PERSON.TYPE, person.getType());

            //optional fields
            if (person.getMiddleName() != null) {
                values.put(Models.PERSON.MIDDLENAME, person.getMiddleName());
            }
            if (person.getAddress() != null) {
                values.put(Models.PERSON.ADDRESS, person.getAddress());
            }

            try {
                if (execSQL().withTable("PERSONS").insert(values).commit().isSuccessful()) {
                    if (person.getProperties().size() > 0) {
                        Map<String, Object> info = person.getAllInfo();
                        for (String key : info.keySet()) {
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(Models.OBJECT_INFO.OBJECT_ID, person.getObjectID());
                            contentValues.put(Models.OBJECT_INFO.OBJECT_TYPE, person.getType());
                            contentValues.put(Models.OBJECT_INFO.KEY, key);
                            contentValues.put(Models.OBJECT_INFO.VALUE, info.get(key));

                            //update if key already exist else create new entry
                            execSQL().withTable("OBJECT_INFO").insert(contentValues).commit();

                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updatePersonProperties(Person person, String... fields) {
        //update if key already exist else create new entry

        if (validatePerson(person)) {
            Map<String, Object> x = person.getProperties();
            ContentValues values = new ContentValues();
            for (String field : fields) {
                if (person.hasProperty(field)) {
                    values.put(field, person.getProperty(field));
                } else {
                    //
                }
            }
        }


//        if (Integer.valueOf(execSQL().withTable("OBJECT_INFO").select().where(
//                models.OBJECT_INFO.OBJECT_ID + " == " + person.getObjectID()
//                        + " AND " +
//                        models.OBJECT_INFO.KEY + " == " + key)
//                .getResult().all().count().get(0).toString()) > 1){
//            execSQL().withTable("OBJECT_INFO").update(contentValues).where(
//                    models.OBJECT_INFO.OBJECT_ID + " == " + person.getObjectID()
//                            + " AND " +
//                            models.OBJECT_INFO.KEY + " == " + key
//            ).commit();
//        }
//        else{
//            execSQL().withTable("OBJECT_INFO").insert(contentValues).commit();
//        }
    }

    public void updatePersonInformation(Person person, String... fields) {
    }

    public void updatePersonProperties(Person person, ContentValues contentValues) {
    }

    public void updatePersonInformation(Person person, ContentValues contentValues) {
    }

    public void removePerson(Person person) {
    }

    public void getPatients(String... ids) {
    }

    public void getVisitors(String... ids) {
    }

    public void getStaffs(String... ids) {
    }

    public void findPerson(Person person) {
    }

    public void findStaff(Person person) {
    }

    public void findPatient(Person person) {
    }

    public void findVisitor(Person person) {
    }

    public void insertRoom(Room room) {
    }

    public void updateRoomProperties(Room room, String... fields) {
    }

    public void updateRoomInformation(Room room, String... fields) {
    }

    public void updateRoomProperties(Room room, ContentValues contentValues) {
    }

    public void updateRoomInformation(Room room, ContentValues contentValues) {
    }

    public void removeRoom(Room room) {
    }

    public void getRooms() {
    }

    public void getAvailableRooms() {
    }

    public void getOccuppiedRooms() {
    }

    public void insertUtilty(Utility room) {
    }

    public void updateUtiltyInformation(Utility room, String... fields) {
    }

    public void updateUtiltyProperties(Utility room, String... fields) {
    }

    public void updateUtiltyInformation(Utility room, ContentValues contentValues) {
    }

    public void updateUtiltyProperties(Utility room, ContentValues contentValues) {
    }

    public void removeUtilty(Utility room) {
    }

    public void setRoomUtilities(List<Utility> utilities) {
    }

    public void getRoomUtilities(Person person) {
    }

    public void insertInfo() {
    }

    public void updateInfo() {
    }

    public void removeInfo() {
    }
}
