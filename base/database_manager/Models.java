package com.techupstudio.school_management_system.base.database_manager;

public class Models {


    private Models() {
    }

    public static class PERSON {
        public static final String TYPE = "type";
        public static final String OBJECT_ID = "object_uid";
        public static final String FIRSTNAME = "first_name";
        public static final String MIDDLENAME = "middle_name";
        public static final String LASTNAME = "last_name";
        public static final String ADDRESS = "address";
        public static final String CONTACT = "contact";
        public static final String GENDER = "gender";
        public static final String DATEOFBIRTH = "dob";
    }

    public static class STAFF extends PERSON {
        public static final String INFO_JOB_TITLE = "job_title";
    }

    public static class PATIENT extends PERSON {
        public static final String INFO_CHECK_IN_TIMESTAMP = "check_in_timestamp";
        public static final String INFO_CHECK_OUT_TIMESTAMP = "check_out_timestamp";
        public static final String INFO_FOREIGN_KEY_HEALTH_RECORDS = "fk_health_records";

        public static class HEALTH_RECORDS {
            public static final String ID = "id";
            public static final String PATIENT_ID = "fk_patient_uid";
            public static final String CHECK_IN_TIMESTAMP = "check_in_timestamp";
            public static final String CHECK_OUT_TIMESTAMP = "check_out_timestamp";
            public static final String HEALTH_PROBLEM = "problem";
            public static final String HEALTH_SOLUTION = "solution";
            public static final String TREATMENT_RESPONSE = "response";
        }
    }

    public static class INPATIENT extends PATIENT {

        public static final String INFO_CHECK_IN_TIMESTAMP = "check_in_timestamp";
        public static final String INFO_CHECK_OUT_TIMESTAMP = "check_out_timestamp";
        public static final String INFO_FOREIGN_KEY_HEALTH_RECORDS = "fk_health_records";
        public static final String INFO_FOREIGN_KEY_INPATIENT_RECORDS = "fk_inpatient_records";

        public static class PROGRESS_REPORT {
            public static final String ID = "id";
            public static final String PATIENT_ID = "fk_patient_uid";
            public static final String CHECK_IN_TIMESTAMP = "check_in_timestamp";
            public static final String CHECK_OUT_TIMESTAMP = "check_out_timestamp";
            public static final String HEALTH_PERSONNEL_TYPE = "personnel_type";
            public static final String HEALTH_PERSONNEL_ID = "personnel_id";
            public static final String TREATMENT_ACTIVITY = "activity";
            public static final String TREATMENT_RESPONSE = "response";
            public static final String NOTES = "notes";
        }

    }

    public static class VISITOR extends PERSON {
        public static final String INFO_RELATION = "relation";
        public static final String INFO_VISIT_REASON = "visit_reason";
        public static final String INFO_CHECK_IN_TIMESTAMP = "check_in_timestamp";
        public static final String INFO_CHECK_OUT_TIMESTAMP = "check_out_timestamp";
        public static final String INFO_FOREIGN_KEY_PATIENT_VISITING = "fk_patient_visiting";
    }

    public static class UTILITIES {
        public static final String OBJECT_ID = "object_uid";
        public static final String OWNER_TYPE = "owner_type";
        public static final String OWNER_ID = "owner_uid";
        public static final String OBJECT_TYPE = "type";

        public static class DEFAULTS {
            public static class BED_INFO {
                public static final String OCCUPANT = "occupant";
                public static final String IS_OCCUPIED = "is_occupied";
                public static final String ROOM_ID = "fk_room_uid";
            }

            public static class WARDROOM_INFO {
                public static final String ROOM_ID = "fk_room_uid";
            }
        }

    }


    public static class ROOM {
        public static final String OBJECT_ID = "uid";
        public static final String TYPE = "type";
        public static final String NAME = "name";
        public static final String PARENT = "fk_parent_room_id";

        public static final String INFO_USED_AS = "used_as";
    }

    public static class OBJECT_INFO {
        public static final String ID = "id";
        public static final String OBJECT_TYPE = "object_type";
        public static final String OBJECT_ID = "object_uid";
        public static final String KEY = "key";
        public static final String VALUE = "value";
    }

    public static class OBJECT_UID {
        public static final String TYPE = "object_type";
        public static final String UID_PREFIX = "uid_prefix";
        public static final String UID_POSTFIX = "uid_postfix";
        public static final String UID_CURRENT_COUNT = "uid_current_count";
    }


}
