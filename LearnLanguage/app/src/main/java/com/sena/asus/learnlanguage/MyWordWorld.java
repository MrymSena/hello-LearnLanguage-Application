package com.sena.asus.learnlanguage;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sena.asus.learnlanguage.Database.DatabaseHandler;
import com.sena.asus.learnlanguage.Database.Word;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;


public class MyWordWorld extends AppCompatActivity {

    private Button btn_search, btn_addWord, btn_deleteWord;
    private EditText editText_SearchView;
    private HashMap<String, String> formList;
    private ArrayList<String> arrayData;
    private TextView textView_english, textView_turkish;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_word_world);

        editText_SearchView = (EditText) findViewById(R.id.editText_SearchView);


        textView_english = (TextView) findViewById(R.id.textView_english);
        textView_turkish = (TextView) findViewById(R.id.textView_turkish);

        btn_search = (Button) findViewById(R.id.btn_search);
        btn_addWord = (Button) findViewById(R.id.btn_addWord);
        btn_deleteWord = (Button) findViewById(R.id.btn_deleteWord);

        //ReadData readData = new ReadData();
        //readData.readAllData();
        //formList = readData.getFormList();
        //arrayData = readData.getMydata();

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String word = editText_SearchView.getText().toString();
                word= StringUtils.capitalize(word.toLowerCase());

                DatabaseHandler db= new DatabaseHandler(getApplication());
                Word words=db.getWord(word);
                if(words!=null) {
                    showWordinLayout(words.getEnglish_word(), words.getTurkish_word());
                }else{
                    textView_english.setText(word);
                    textView_turkish.setText(getString(R.string.there_is_no_word));
                }
              /*  ReadData readData = ReadData.getInstance();
                if (readData.isWordExist(word)) {
                    showWordinLayout(word,readData.getMeaning(word));
                } else {
                    textView_english.setText(word);
                    textView_turkish.setText(getString(R.string.there_is_no_word));
                }  */
            }
        });

        btn_addWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle= new Bundle();
                DFragment_Add dialogFragment= new DFragment_Add();
               // bundle.putString("type","add");
               // dialogFragment.setArguments(bundle);
                dialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
                dialogFragment.show(getSupportFragmentManager(),"df");
            }
        });


        btn_deleteWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DFragment_Delete dFragment_delete= new DFragment_Delete();
                dFragment_delete.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
                dFragment_delete.show(getSupportFragmentManager(),"df");
            }
        });

    }

    @SuppressLint("WrongConstant")
    public void showWordinLayout(String englishWord, String turkishWord) {
        textView_english.setText(englishWord);
        textView_turkish.setText(turkishWord);


    }
}