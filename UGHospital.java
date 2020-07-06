package com.techupstudio.school_management_system;


import com.techupstudio.school_management_system.base.models.Hospital;

public class UGHospital extends Hospital {


    public UGHospital() {
        super("ug_hospital_database.db");
    }

    private static void loopOperation(Operation operation, int number) {
        for (int i = 0; i < number; i++) {
            operation.run();
        }
    }

    @Override
    public void prepareHospital() {
        // TODO: create Hospital Settings
        // TODO: build wards -- add utilities (bed, ward-room, etc)
        // TODO: build staffs
        // TODO: add wards and staffs
    }

    interface Operation {
        void run();
    }

}
