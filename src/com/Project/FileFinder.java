package com.Project;

import java.io.File;

public class FileFinder {
    private String[] files;
    public  void fileFinder() {
        int m = 0;
        File folder = new File("./");
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                if(file.getName().endsWith(".txt")){
                    m++;
                };
            }
        }

        String files[] = new String[m];
        int n = 0;
        for (File file : listOfFiles) {
            if (file.isFile()) {
                if(file.getName().endsWith(".txt")){
                    files[n] = file.getName().replaceFirst("[.][^.]+$", "");
                    n++;
                }
            }
        }
        this.files = files;
    }

    public String[] getFiles() {
        fileFinder();
        return files;
    }
}
