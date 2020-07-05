package com.techupstudio;

import com.techupstudio.Base.HospitalDatabaseManager.Models;
import com.techupstudio.Base.Models.Bed;
import com.techupstudio.Base.Models.Hospital;
import com.techupstudio.Base.Models.Staff;
import com.techupstudio.Base.Models.Ward;

import java.io.IOException;
import java.util.Date;


public class Test {

    private static Hospital ugHospital;

    interface Operation{ void run();}

    private static void loopOperation(Operation operation, int number){
        for (int i=0;i<number;i++){
            operation.run();
        }
    }

    private static void prepareUgHospital(){

        ugHospital = new Hospital("src/com/techupstudio/Base/Data/test_ug_hospital_database.db") {

            @Override
            public void prepareHospital() {

                //adding uid formats
                addUIDFormatForObjectType("staff", "S_", "", 10499);
                addUIDFormatForObjectType("patient", "P_", "", 10499);
                addUIDFormatForObjectType("visitor", "V_", "", 10499);
                addUIDFormatForObjectType("utility", "U_", "", 0);
                addUIDFormatForObjectType("room", "R_", "", 0);

                // building wards
                Ward maleWard = new Ward("maleWard");
                Ward femaleWard = new Ward("femaleWard");
                Ward childWard = new Ward("childWard");
                Ward surgicalWard = new Ward("surgicalWard");

                //adding ten beds for each ward
                loopOperation(() -> {
                    maleWard.addBed(new Bed());
                    femaleWard.addBed(new Bed());
                    childWard.addBed(new Bed());
                    surgicalWard.addBed(new Bed());
                }, 10);

                // Adding wards
                addWard(maleWard);
                addWard(femaleWard);
                addWard(childWard);
                addWard(surgicalWard);

                // TODO: build staffs
            }
        };

        // TODO: create Hospital Settings
        //adding Database ObjectIDFormats

    }


    public static Hospital getUGHospital(){
        return ugHospital;
    }

    public static void main(String[] var0) throws IOException {
        prepareUgHospital();

        Staff staff = new Staff();
        staff.setFirstName("Bernard");staff.setMiddleName("Azumah");staff.setLastName("Atinga");
        staff.setDateOfBirth(new Date());staff.setContact("0553567950");staff.setJobTitle("doctor");
        staff.setGender("male");staff.addInfo("salary", 2350);
        staff.setObjectID(getUGHospital().nextUIDForObjectType(staff.getType()));
        getUGHospital().addStaff(staff);

        staff.setProperty("salary", 3000);
        getUGHospital().getDataBase().updatePersonInformation(staff, Models.STAFF.INFO_JOB_TITLE);

    }

}
