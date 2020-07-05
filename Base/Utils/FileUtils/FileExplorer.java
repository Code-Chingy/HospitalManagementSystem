package com.techupstudio.Base.Utils.FileUtils;

import com.techupstudio.Base.Utils.GeneralUtils.Collections;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileExplorer {

    private File HOME;
    private File CURRENTFILE;
    private File FILE_TO_CUT;
    private File FILE_TO_COPY;
    private Collections.Stack<File> FORWARDSTACK;
    private Collections.Stack<File> BACKWARDSTACK;

    FileExplorer(File directory){ CURRENTFILE = directory; initializeFields(); }

    public FileExplorer(String directoryPath){
        if (directoryPath.equals("")){
            CURRENTFILE = new File(Paths.get("").toAbsolutePath().toString());
        }
        else {
            CURRENTFILE = new File(directoryPath);
        }
        initializeFields();
    }

    FileExplorer(FileExplorer fileExplorer) { CURRENTFILE = fileExplorer.getCurrentFile(); initializeFields(); }

    private void initializeFields(){
        FORWARDSTACK = new Collections.Stack<>();
        BACKWARDSTACK = new Collections.Stack<>();
        HOME = CURRENTFILE;
    }

    public void setHome(File file){ HOME = file; }
    public void setHome(String filePath){ HOME = new File(filePath); }
    public void setHome(FileExplorer file){ HOME = file.getCurrentFile(); }

    public FileExplorer createNewFolder(String childName){
        FileManager.createDirectory(CURRENTFILE, childName);
        return this;
    }

    public FileExplorer createNewFile(String fileName){
        FileManager.createFile(CURRENTFILE, fileName);
        return this;
    }

    public FileExplorer renameFile(String oldName, String newName) {
        if (getFile(oldName).exists()){
            getFile(oldName).renameTo(new File(CURRENTFILE, newName));
        }
        return this;
    }

    public FileExplorer deleteFile (String childName){
        if (getFile(childName).exists()){
            getFile(childName).delete();
        }
        return this;
    }

    public FileExplorer copy (String fileName){
        FILE_TO_COPY = getFile(fileName);
        FILE_TO_CUT = null;
        return this;
    }

    public FileExplorer copyCurrentFile (){
        FILE_TO_COPY = getCurrentFile();
        FILE_TO_CUT = null;
        return this;
    }

    public FileExplorer cut (String fileName){
        FILE_TO_CUT = getFile(fileName);
        FILE_TO_COPY = null;
        return this;
    }

    public FileExplorer cutCurrentFile (){
        FILE_TO_CUT = getCurrentFile();
        FILE_TO_COPY = null;
        return this;
    }

    public FileExplorer paste(){
        if (getCurrentFile().isDirectory()){
            if (FILE_TO_COPY != null) { FileManager.copy(FILE_TO_COPY, getCurrentFile()); }
            else if (FILE_TO_CUT != null){ FileManager.cut(FILE_TO_CUT, getCurrentFile()); }
        }
        return this;
    }

    public FileExplorer pasteWithNewName(String newName){
        if (getCurrentFile().isDirectory()){
            if (FILE_TO_COPY != null) { FileManager.copy(FILE_TO_COPY, getCurrentFile(), newName); }
            else if (FILE_TO_CUT != null){ FileManager.cut(FILE_TO_CUT, getCurrentFile(), newName); }
        }
        return this;
    }

    public FileExplorer pasteIn(String folderName){
        pasteIn(getFile(folderName));
        return this;
    }

    public FileExplorer pasteWithNewNameIn(String folderName, String newName){
        pasteWithNewNameIn(getFile(folderName),newName);
        return this;
    }

    public FileExplorer pasteIn(File directory){
        if (directory.isDirectory()){
            if (FILE_TO_COPY != null) { FileManager.copy(FILE_TO_COPY, directory); }
            else if (FILE_TO_CUT != null){ FileManager.cut(FILE_TO_CUT, directory); }
        }
        return this;
    }

    public FileExplorer pasteWithNewNameIn(File directory, String newName){
        if (directory.isDirectory()){
            if (FILE_TO_COPY != null) { FileManager.copy(FILE_TO_COPY, directory, newName); }
            else if (FILE_TO_CUT != null){ FileManager.cut(FILE_TO_CUT,directory, newName); }
        }
        return this;
    }

//            public void renameSelf(String newName){ CURRENTFILE.renameTo(new File(CURRENTFILE.getParentFile(), newName)); }
//
//            public void deleteSelf(){ CURRENTFILE.delete(); }

    public boolean isFile(){ return CURRENTFILE.isFile(); }

    public boolean isFolder(){ return CURRENTFILE.isFile(); }

    public URI getCurrentFileURI(){ return CURRENTFILE.toURI(); }

    public ReadWritableFile openCurrentFileAsReadWritableFile(){
        if (isFile()){
            try {
                return new ReadWritableFile(getCurrentFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public ReadWritableFile openReadWritableFile(String fileName){
        if (getFile(fileName).isFile()){
            try {
                return new ReadWritableFile(getFile(fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getCurrentPath(){ return CURRENTFILE.getAbsolutePath(); }

    public String makeFilePath(String newPathName){ return getCurrentPath()+"/"+newPathName; }

    public String[] getFileNames(){ return CURRENTFILE.list(); }

    public File[] getFileItems() { return CURRENTFILE.listFiles(); }

    public FileExplorer openFolder(String folderName){
        if (getFile(folderName).exists()) {
            if (getFile(folderName).isDirectory()) {
                return new FileExplorer(getFile(folderName));
            }
            try {
                throw new FileExplorer.FileNotDirectoryException();
            } catch (FileExplorer.FileNotDirectoryException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                throw new FileNotFoundException();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public FileExplorer exploreFile(String fileName){
        if (getFile(fileName).exists()){
            BACKWARDSTACK.push(getCurrentFile());
            CURRENTFILE = getFile(fileName);
        }
        else{
            try {
                throw new FileNotFoundException();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    public FileExplorer goBack(){
        if (BACKWARDSTACK.isEmpty()){ return null; }
        if (BACKWARDSTACK.peekTop().exists()) {
            FORWARDSTACK.push(getCurrentFile());
            CURRENTFILE = BACKWARDSTACK.pop();
        }
        else {
            try {
                throw new FileNotFoundException();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    public FileExplorer goForward(){
        if (FORWARDSTACK.isEmpty()){ return null; }
        if (FORWARDSTACK.peekTop().exists()) {
            BACKWARDSTACK.push(getCurrentFile());
            CURRENTFILE = FORWARDSTACK.pop();
        }
        else {
            try {
                throw new FileNotFoundException();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    public FileExplorer goHome(){
        if (HOME.exists()) {
            if (CURRENTFILE != HOME) {
                BACKWARDSTACK.push(getCurrentFile());
                BACKWARDSTACK.push(getCurrentFile());
                CURRENTFILE = HOME;
            }
        }
        else {
            try {
                throw new FileNotFoundException();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    public File[] searchFolderWithPattern(String pattern){
        List<File> matchesList = new ArrayList<>();
        for (File file : getFileItems()){
            String name = file.getName();
            if (name.toLowerCase().contains(pattern.toLowerCase())){
                matchesList.add(file);
            }
        }
        File[] matchesArray = new File[matchesList.size()];
        matchesList.toArray(matchesArray);
        return matchesArray;
    }

    public FileExplorer getBackwardFile(){ return new FileExplorer(BACKWARDSTACK.peekTop()); }

    public FileExplorer getForwardFile(){ return new FileExplorer(FORWARDSTACK.peekTop()); }

    public File getCurrentFile(){ return CURRENTFILE; }

    public File getFile(String fileName){return new File(CURRENTFILE, fileName); }

    public FileExplorer ls(){ if (CURRENTFILE.isDirectory()) { FileManager.listDir(CURRENTFILE); } return this; }

    public FileExplorer clone(){ return new FileExplorer(this); }

    public void forEachFile(FileExplorer.FileProcess fileProcess) {
        for (File file: getFileItems()){
            fileProcess.process(file);
        }
    }

    public interface FileProcess{ void process(File file); }

    private class FileNotDirectoryException extends Exception { FileNotDirectoryException(){} }
}