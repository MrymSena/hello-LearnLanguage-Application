package com.sena.asus.learnlanguage;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sena.asus.learnlanguage.Database.DatabaseHandler;
import com.sena.asus.learnlanguage.Database.Word;

import org.apache.commons.lang3.StringUtils;

import java.util.List;


public class DFragment_Add extends DialogFragment {


    private Button btn_saveWord, btn_cancel;
    private EditText editText_englishWord, editText_turkishWord;
    private ReadData readData = ReadData.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialogfragment_add, container, false);
        getDialog().setTitle("Yeni Kelime Ekle");

        btn_saveWord = (Button) rootView.findViewById(R.id.btn_saveWord);
        btn_cancel = (Button) rootView.findViewById(R.id.btn_cancel);

        editText_englishWord = (EditText) rootView.findViewById(R.id.editText_englishWord);
        editText_turkishWord = (EditText) rootView.findViewById(R.id.editText_turkishWord);


        btn_saveWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String english_Word = editText_englishWord.getText().toString();
                String turkish_Word = editText_turkishWord.getText().toString();
                english_Word=StringUtils.capitalize(english_Word.toLowerCase());
                turkish_Word=StringUtils.capitalize(turkish_Word.toLowerCase());
                int a = 0;
                int b = 0;



                if (!english_Word.isEmpty() && !turkish_Word.isEmpty()) {
                    for (int i = 0; i < english_Word.length(); i++) {
                        if (Character.isDigit(english_Word.charAt(i))) {
                            Toast.makeText(getActivity(), "Lütfen rakam girmeyiniz", Toast.LENGTH_SHORT).show();
                            a = 1;
                            break;
                        }

                        for (int k = 0; k < turkish_Word.length(); k++) {
                            if (Character.isDigit(turkish_Word.charAt(k))) {
                                Toast.makeText(getActivity(), "Lütfen rakam girmeyiniz", Toast.LENGTH_SHORT).show();
                                b = 1;
                                break;
                            }
                        }
                    }
                    if (a == 0 && b == 0) {
                        saveWord(english_Word, turkish_Word);
                        editText_englishWord.setText("");
                        editText_turkishWord.setText("");
                    }

                } else {
                    Toast.makeText(getActivity(), "Opps! Neyi kaydedicez anlamadık. İki kısmı da doldurdun mu?", Toast.LENGTH_LONG).show();
                }

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        return rootView;
    }

    private void saveWord(String english_Word, String turkish_Word) {

       // String isSaved = readData.saveWord(english_Word, turkish_Word);
        DatabaseHandler databaseHandler= new DatabaseHandler(getActivity());
        String isSaved = databaseHandler.addWord(new Word(english_Word, turkish_Word));
        Log.d("Reading: ", "Reading all words..");
        List<Word> contacts = databaseHandler.getAllWords();

        for (Word cn : contacts) {
            String log = "Id: " + cn.getID() + " ,English: " + cn.getEnglish_word() + " ,Turkish: " + cn.getTurkish_word();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }

        if (isSaved.equals("true")) {
            Toast.makeText(getActivity(), "Kelime başarıyla kaydedildi.", Toast.LENGTH_SHORT).show();
        } else if (isSaved.equals("alreadyExist")) {
            Toast.makeText(getActivity(), "Kelime zaten veritabanınızda bulunmaktadır", Toast.LENGTH_SHORT).show();
        } else if (isSaved.equals("exception")) {
            Toast.makeText(getActivity(), "Bir hatayla karşılaşıldı, tekrar deneyiniz!", Toast.LENGTH_SHORT).show();
        }
    }

}