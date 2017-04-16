package com.sena.asus.learnlanguage;

/**
 * Created by Lincoln on 18/05/16.
 */
public class Menu {
    private String name;
    private int numOfSongs;
    private int thumbnail;

    public Menu() {
    }

    public Menu(String name, int thumbnail) {
        this.name = name;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}