package com.sena.asus.learnlanguage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Asus on 16.01.2018.
 */

public class ReadData {

    private HashMap<String, String> formList=new HashMap<String, String>();
    ArrayList<String> mydata;
    private ArrayList<String> definitions;


    void readAllData(){

        File root = android.os.Environment.getExternalStorageDirectory();
        String dictFilePath = root.getAbsolutePath()+ "/download/dic.txt";

        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(dictFilePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while(scanner.hasNext()){
            String line = scanner.nextLine();
            String[] words = line.split("\t");
            formList.put(words[0],words[1]);
        }
        scanner.close();

        definitions = new ArrayList<>();
        mydata = new ArrayList<>(formList.keySet());

    }

    public HashMap getFormList(){
        return formList;
    }

    public ArrayList getMydata(){
        return mydata;
    }

}
