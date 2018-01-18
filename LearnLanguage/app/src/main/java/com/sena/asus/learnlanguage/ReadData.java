package com.sena.asus.learnlanguage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
    private static ReadData readData;
    final String FILEPATH="/download/lwmypersonaldictlw.txt";

    File root = android.os.Environment.getExternalStorageDirectory();
    private String dictFilePath = root.getAbsolutePath()+ FILEPATH;

    private ReadData(){
    }

    public static ReadData getInstance(){   //Singleton pattern
        if(readData == null){
            readData= new ReadData();
            readData.readAllData();
        }
        return readData;
    }

    void readAllData(){
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

    public boolean isWordExist(String word) {
        // String word = editText_SearchView.getText().toString();
        int c = 0;
        readAllData();
        for (int i = 0; i < mydata.size(); i++) {
            //msj.setText(dict.get(i));
            if (word.equals(mydata.get(i))) {
              //  showWordinLayout(mydata.get(i), formList.get(arrayData.get(i)));
                c = 1;
                return true;
            }
        }
        if (c == 0) {
            return false;
            //      textView_english.setText(word);
            //      textView_turkish.setText(getString(R.string.there_is_no_word));
        }
        return false;
    }

    public String saveWord(String english_word,String turkish_word){
        if(!isWordExist(english_word)){
            try {

                try {
                    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(dictFilePath,true)));
                    out.println(english_word+"\t"+turkish_word);
                    out.flush();
                    out.close();

                }
                catch (IOException e) {
                    return "exception";
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "exception";
            }
            return "true";
        }
        return "alreadyExist";
    }

    public void deleteWord(String english_word){
        if(!isWordExist(english_word)){
            try{
                removeLine(english_word);
            }catch (Exception e){

            }
        }
    }


    private void removeLine(String Line) throws IOException{
        File temp = new File(dictFilePath);
        BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
        Scanner scanner = new Scanner(new File(dictFilePath));
        String removeID = Line;
        String currentLine;
        while(scanner.hasNext()){
            currentLine = scanner.nextLine();
            String[] words = currentLine.split("\t");
            //formList.put(words[0],words[1]);
            if(words[0].equals(removeID)){
                currentLine = "";
            }
            bw.write(currentLine + System.getProperty("line.separator"));

        }
        bw.close();

    }


    public String getMeaning(String word){
        return formList.get(word);
    }

    public HashMap getFormList(){
        return formList;
    }

    public ArrayList getMydata(){
        return mydata;
    }

}
