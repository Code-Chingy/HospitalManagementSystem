package com.techupstudio;

import com.techupstudio.Base.Models.Hospital;

public class UGHospital extends Hospital {


    public UGHospital() {
        super("ug_hospital_database.db");
    }

    @Override
    public void prepareHospital() {
        // TODO: create Hospital Settings
        // TODO: build wards -- add utilities (bed, ward-room, etc)
        // TODO: build staffs
        // TODO: add wards and staffs
    }

    interface Operation{ void run();}

    private static void loopOperation(Operation operation, int number){
        for (int i=0;i<number;i++){
            operation.run();
        }
    }

}
