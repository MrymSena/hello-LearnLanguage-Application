package com.sena.asus.learnlanguage.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asus on 18.01.2018.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static DatabaseHandler databaseHandler;
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "wordsManager";

    // Contacts table name
    private static final String TABLE_WORDS = "words";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_EWORD = "english_word";
    private static final String KEY_TWORD = "turkish_word";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

   /* public static DatabaseHandler getInstance(){   //Singleton pattern
        if(databaseHandler == null){
            databaseHandler= new DatabaseHandler(this);
        }
        return databaseHandler;
    }*/

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_WORDS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_EWORD + " TEXT,"
                + KEY_TWORD + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORDS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new word
    public String addWord(Word word) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EWORD, word.getEnglish_word()); // Word English word
        values.put(KEY_TWORD, word.getTurkish_word()); // Word Turkish word

        Word english=getWord(word.getEnglish_word());
        if(english==null) {
            try {
                db.insert(TABLE_WORDS, null, values);
                db.close();
                return "true";
            } catch (Exception e) {
                db.close();
                return "exception";
            }
        }
        else{
            db.close();
            return "alreadyExist";
        }
        // Closing database connection
    }

    // Getting single contact
    public Word getWord(String key_EWORD) {
        SQLiteDatabase db = this.getReadableDatabase();
        Word word=null;
        Cursor cursor = db.query(TABLE_WORDS, new String[] { KEY_ID,
                        KEY_EWORD, KEY_TWORD }, KEY_EWORD + "=?",
                new String[] { String.valueOf(key_EWORD) }, null, null, null, null);
        if (cursor != null)
            if(cursor.moveToFirst()) {
                word = new Word(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2));
            }
        return word;
    }

    // Getting All Contacts
    public List<Word> getAllWords() {
        List<Word> contactList = new ArrayList<Word>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_WORDS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Word contact = new Word();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setEnglish_word(cursor.getString(1));
                contact.setTurkish_word(cursor.getString(2));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // Updating single contact
    public int updateWord(Word contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EWORD, contact.getEnglish_word());
        values.put(KEY_TWORD, contact.getTurkish_word());

        // updating row
        return db.update(TABLE_WORDS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }

    // Deleting single contact
    public void deleteWord(Word word) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WORDS, KEY_ID + " = ?",
                new String[] { String.valueOf(word.getID()) });
        db.close();
    }


    // Getting contacts Count
    public int getWordsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_WORDS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}

