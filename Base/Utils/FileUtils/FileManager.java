package com.techupstudio.Base.Utils.FileUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static com.techupstudio.Base.Utils.GeneralUtils.Funcs.println;


public class FileManager {

    private FileManager(){}

    public static void createDirectory(File parent, String childName){
        File temp = new File(parent, childName);
        if (!temp.exists()){ temp.mkdirs(); }
    }

    public static File getDirectory(File parent, String childName){
        File folder = new File(parent, childName);
        return folder;
    }

    public static void createFile(File parent, String fileName){
        File temp = new File(parent, fileName);
        if (!temp.exists()){
            try {
                temp.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static File getFile(File parent, String fileName){
        File temp = new File(parent, fileName);
        if (temp.exists()){
            return temp;
        }
        return null;
    }

    public static void listDir(File file) {
        String[] paths;
        try{
            paths = file.list();
            for(String path : paths)
            {
                System.out.println(path);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void makeDir(String path){ new File(path).mkdirs(); }

    public static void write(File file, String line){

        FileWriter WRITER = null;

        if (file.canWrite()){
            try {
                WRITER = new FileWriter(file);
                WRITER.write(line);
                WRITER.flush();
                WRITER.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeAppend(File file, String lines){

        FileWriter WRITER = null;

        if (file.canWrite()){
            try {
                WRITER = new FileWriter(file);
                WRITER.write(read(file)+"\n"+lines);
                WRITER.flush();
                WRITER.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String read(File file) {
        String retStr = "";

        FileReader READER = null;

        if (file.canRead()){
            try {
                READER = new FileReader(file);
                while (true){
                    int c = READER.read();
                    if (c != -1){
                        retStr += (char)c;
                    }
                    else{ break; }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        try {
            READER.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retStr;
    }

    public static Map<Integer, String> readEnumerate(File file) {

        Map<Integer, String> lines = new HashMap<>();

        String retStr = "";

        FileReader READER = null;

        if (file.canRead()){
            try {
                READER = new FileReader(file);
                int i = 1;
                while (true){
                    int c = READER.read();
                    if (c != -1){
                        if (c == 10){
                            if (!retStr.strip().isEmpty()){
                                lines.put(i, retStr);
                                retStr = "";i++;
                            }
                        }
                        else{
                            retStr += (char)c;
                        }
                    }
                    else{
                        if (!retStr.strip().isEmpty()){
                            lines.put(i, retStr);
                        }
                        break;
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                READER.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return lines;
    }

    public static void writeUTF(File file, String lines) {
        if (file.canWrite()) {
            DataOutputStream dataOut = null;
            try {
                dataOut = new DataOutputStream(new FileOutputStream(file.getAbsolutePath()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            try {
                dataOut.writeUTF(lines);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String readUTF(File file) {
        String retStr = "";

        if (file.canRead()) {
            DataInputStream dataIn = null;
            try {
                dataIn = new DataInputStream(new FileInputStream(file.getAbsolutePath()));
                while (dataIn.available() > 0) {
                    String c = dataIn.readUTF();
                    retStr += c;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return retStr;
    }

    public static void copy(File file, File newLocation){ copy(file, newLocation, ""); }

    public static void copy(File file, File newLocation, String newName){
        if (file.exists()){
            if (file.isFile()){  copyFile(file, newLocation, newName); }
            else{ copyDirectory(file, newLocation, newName); }
        }
    }

    private static void copyFile(File file, File newLocation, String newName){
        try {
            if (file.isFile()) {
                if (newLocation.exists() && newLocation.isDirectory()) {
                    File copiedFile = getCopiedFile(file, newLocation, newName);
                    copiedFile.createNewFile();
                    FileInputStream inputStream = new FileInputStream(file);
                    FileOutputStream fileOutputStream = new FileOutputStream(copiedFile);
                    while (inputStream.available() != 0){
                        fileOutputStream.write(inputStream.read());
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void copyDirectory(File file, File newLocation, String newName){
        try {
            if (file.isDirectory()) {

                if (newLocation.exists() && newLocation.isDirectory()) {

                    File copiedDirectory = getCopiedFile(file, newLocation, newName);

                    if (!copiedDirectory.exists()) { copiedDirectory.mkdirs(); }

                    for (File subfile : file.listFiles()) {
                        if (subfile.isFile()) {
                            copyFile(subfile, copiedDirectory, "");
                        }
                        else{
                            copyDirectory(subfile, copiedDirectory, "");
                        }
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String renameToCopiedFile(String name){
        if (!name.contains(".")){ return name+"_copy"; }
        else if (name.lastIndexOf(".") == 0){ return name+"_copy"; }
        String nameOnly = name.substring(0,name.lastIndexOf("."));
        String newName = nameOnly+"_copy"+name.substring(name.lastIndexOf("."));
        return newName;
    }

    public static boolean renameFile(File file, String name){
        if (file.exists()){
            String pathName = file.getAbsolutePath()
                    .substring(0, file.getAbsolutePath().lastIndexOf(file.getName())) + name;
            return file.renameTo(new File(pathName));
        }
        return false;
    }

    public static String getExtension(String name){
        if (!name.contains(".")){ return ""; }
        else if (name.lastIndexOf(".") == 0){ return ""; }
        return name.substring(name.lastIndexOf("."));
    }

    private static File getCopiedFile(File file, File newLocation, String newName) {

        File copiedFile;

        if (newName.strip().isEmpty()){
            if (getDirectory(newLocation, file.getName()).exists()) {
                copiedFile = getDirectory(newLocation, renameToCopiedFile(file.getName()));
                while(copiedFile.exists()){
                    copiedFile = getDirectory(newLocation, renameToCopiedFile(copiedFile.getName()));
                }
            }
            else{
                copiedFile = getDirectory(newLocation, file.getName());
            }
        }
        else {

            if (!getExtension(newName).equals(getExtension(file.getName()))){
                newName+= getExtension(file.getName());
            }

            if (getDirectory(newLocation, newName).exists()) {
                copiedFile = getDirectory(newLocation, renameToCopiedFile(file.getName()));
            }
            else{
                copiedFile = getDirectory(newLocation, newName);
            }
        }
        return copiedFile;
    }

    public static void cut(File file, File newLocation){
        copy(file, newLocation); file.delete();
    }

    public static void cut(File file, File newLocation, String newName){
        copy(file, newLocation, newName); file.delete();
    }

    public static String getEnviron(String name){ return System.getenv(name); }

    public static void listEnviron(){ System.getenv().forEach((k,v) -> println("KEY : "+k, "\tVALUE : "+v)); }

}