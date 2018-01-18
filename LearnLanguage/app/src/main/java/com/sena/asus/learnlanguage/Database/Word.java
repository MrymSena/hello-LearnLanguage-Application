package com.sena.asus.learnlanguage.Database;

/**
 * Created by Asus on 18.01.2018.
 */

public class Word{

    //private variables
    int id;
    String english_word;
    String turkish_word;

    // Empty constructor
    public Word(){

    }
    // constructor
    public Word(int id, String english_word, String turkish_word){
        this.id = id;
        this.english_word = english_word;
        this.turkish_word = turkish_word;
    }

    // constructor
    public Word(String english_word, String turkish_word){
        this.english_word = english_word;
        this.turkish_word = turkish_word;
    }
    // getting ID
    public int getID(){
        return this.id;
    }

    // setting id
    public void setID(int id){
        this.id = id;
    }

    // getting english word
    public String getEnglish_word(){
        return this.english_word;
    }

    // setting english word
    public void setEnglish_word(String english_word){
        this.english_word = english_word;
    }

    // getting turkish word
    public String getTurkish_word(){
        return this.turkish_word;
    }

    // setting turkish word
    public void setTurkish_word(String turkish_word){
        this.turkish_word = turkish_word;
    }
}