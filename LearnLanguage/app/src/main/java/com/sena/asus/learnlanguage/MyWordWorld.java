package com.sena.asus.learnlanguage;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MyWordWorld extends AppCompatActivity {

    private Button btn_search, btn_addWord, btn_deleteWord;
    private EditText editText_SearchView;
    private HashMap<String, String> formList;
    private ArrayList<String> arrayData;
    private LinearLayout linearLayout;
    private TextView textView_englisg, textView_turkish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_word_world);

        editText_SearchView = (EditText) findViewById(R.id.editText_SearchView);

        linearLayout=(LinearLayout)findViewById(R.id.layout_words);
        textView_englisg = (TextView) findViewById(R.id.textView_english);
        textView_turkish = (TextView) findViewById(R.id.textView_turkish);

        btn_search = (Button) findViewById(R.id.btn_search);
        btn_addWord = (Button) findViewById(R.id.btn_addWord);
        btn_deleteWord = (Button) findViewById(R.id.btn_deleteWord);

        ReadData readData = new ReadData();
        readData.readAllData();
        formList = readData.getFormList();
        arrayData = readData.getMydata();

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplication(),"tıklandı", Toast.LENGTH_LONG).show();
                String word = editText_SearchView.getText().toString();

                for (int i = 0; i < arrayData.size(); i++) {
                    //msj.setText(dict.get(i));
                    if (word.equals(arrayData.get(i))) {
                       showWordinLayout(arrayData.get(i),formList.get(arrayData.get(i)));
                        break;
                    }
                }
            }
        });

    }


    @SuppressLint("WrongConstant")
    public void showWordinLayout(String englishWord, String turkishWord) {
        textView_englisg.setText(englishWord);
        textView_turkish.setText(turkishWord);
        linearLayout.setVisibility(1);


    }
}