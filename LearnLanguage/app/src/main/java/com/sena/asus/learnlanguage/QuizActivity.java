package com.sena.asus.learnlanguage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class QuizActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private int MULTIPLE_CHOICE_COUNT = 5;

    private Context mContext;

    private HashMap<String, String> formList;
    ArrayList<String> array;
    ListView list;
    RelativeLayout scoreLayout;
    TextView score;
    Button mainpage, quiz;
    TextView point;

    private ArrayAdapter<String> adapter;
    private ArrayList<String> definitions;
    private ArrayList<String> QuizWords;
    private String currentWord = "";
    private float totalPoint=0;
    private int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        point = (TextView)findViewById(R.id.Point);


        formList = new HashMap<>();
        readAll();


        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(this);
        QuizWords=new ArrayList<>(array);

        generateRandom();

    }

    private void readAll(){ File root = android.os.Environment.getExternalStorageDirectory();
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
        array = new ArrayList<>(formList.keySet());

    }

    private void generateRandom() {


        //shuffle array pick one
        Collections.shuffle(QuizWords);
        String word = QuizWords.get(0);

        //ask question
        TextView the_word = (TextView) findViewById(R.id.the_word);
        the_word.setText(word);
        currentWord = word;

        definitions.clear();
        int number=-1;

        for(int x=0; x<array.size(); x++){
            if(QuizWords.get(0).equals(array.get(x))){
                number=x;
                break;
            }
        }
        definitions.add(formList.get(word));

        for (int i = 0; i < MULTIPLE_CHOICE_COUNT-1 ; i++) {
            if (number == i) {
                    definitions.add(formList.get(array.get(5)));
            } else {
                definitions.add(formList.get(array.get(i)));
            }
        }
        Collections.shuffle(definitions);
        adapter = new ArrayAdapter<String>(
                this,
                R.layout.list_layout,
                R.id.content,
                definitions
        );

        list.setAdapter(adapter);
        QuizWords.remove(word);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int index, long id) {
        //Toast.makeText(this, list.getItemAtPosition(index).toString(),Toast.LENGTH_SHORT).show();
        //array.remove(index);
        //adapter.notifyDataSetChanged();
        count++;
        if (formList.get(currentWord).equals(list.getItemAtPosition(index).toString())) {
            point.setTextColor(Color.GREEN);
            point.setText("+1");
            point.animate().translationX(400).withLayer();
            totalPoint=totalPoint+1;

        } else {
            totalPoint = (float) (totalPoint-0.5);
            point.setTextColor(Color.RED);
            point.setText("-0.5");
            point.animate().translationX(-400).withLayer();
        }

        generateRandom();
        if(count==5){
            QuizWords=new ArrayList<>(array);
            scoreLayout=(RelativeLayout)findViewById(R.id.score_layout);
            setContentView(R.layout.score_layout);
            score= (TextView) findViewById(R.id.ScoreResult);
            String result=Float.toString( totalPoint);
            score.setText(result.toString());

            mainpage=(Button)findViewById(R.id.button_Anasayfa);
            quiz=(Button)findViewById(R.id.button_Tekrar_Quiz_Yap);

            if(totalPoint>0) {
                try {
                    Glide.with(this).load(R.drawable.success1).into((ImageView) findViewById(R.id.imageView_Success));
                    Toast.makeText(this, "You got it!!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                } }else{
                    try {
                        Glide.with(this).load(R.drawable.sad).into((ImageView) findViewById(R.id.imageView_Success));
                        Toast.makeText(this, "Study more!!", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
            mainpage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentMainPage= new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intentMainPage);
                }
            });

            quiz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentQuiz= new Intent(getApplicationContext(),QuizActivity.class);
                    startActivity(intentQuiz);
                }
            });
        }

    }



}