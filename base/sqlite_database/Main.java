package com.techupstudio.school_management_system.base.sqlite_database;

import java.sql.SQLException;


public class Main {

    public static void main(String[] var0) throws SQLException {

        SQLDatabase db = new SQLDatabase("hospital_database.db");

//        SQLCommandBuilder.Query.Commit commit = db.execSQL().withTable("staffs").drop().commit();
//        if (commit.isSuccessful()){
//            System.out.println(commit.getUpdateCount());
//        }

//        ContentValues values = new ContentValues();
//
//        values.put("age", new Random().nextInt(70));
//        values.put("name", "'someone"+ new Random().nextInt()+"'");
//        values.put("job_title", "'nurse'");
//        values.put("email", "'chris@gmail.com'");
//
//        SQLCommandBuilder.Query.Commit commit = db.execSQL().withTable("staffs").insert(values).commit();
//        if (commit.isSuccessful()){
//            System.out.println("success "+commit.getUpdateCount());
//        }

        SQLCommandBuilder.Query.Result.Paginater result = db.execSQL().withTable("staffs")
                .select("job_title", "COUNT(*) as count")
                .groupBy("job_title").orderBy("count")
                .getResult().paginate(10).get();

        while (result.hasNextBatch()) {
            System.out.println(result.getNextBatch());

//            System.out.println("age = "+result.getInt("age"));
//            System.out.println("name = "+result.getString("name"));
//            System.out.println("email = "+result.getString("email"));
//            System.out.println("job_title = "+result.getString("job_title"));
//
//            System.out.println("job_title = "+result.getString("job_title"));
//            System.out.println("count = "+result.getString("count"));

        }


/*

//TODO: future

//        PaginaterBuilder paginater = db.execSQL().withTable("staffs")
//                .select().getResult().paginate(5).get();
//
//        while(paginater.hasNextGroup()){
//            ResultData resultDataGroup = paginater.getNextGroup();
//            //Do stuff with dataStuff
//        }

*/
    }

}
