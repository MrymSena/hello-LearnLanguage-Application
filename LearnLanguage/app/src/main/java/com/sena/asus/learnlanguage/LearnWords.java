package com.sena.asus.learnlanguage;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class LearnWords extends AppCompatActivity {


    ArrayList<String> array;
    private ArrayList<String> definitions;


    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;
    private boolean mIsBackVisible = false;
    private View mCardFrontLayout;
    private View mCardBackLayout;

    private String currentWord ="";
    private TextView tvFront,tvBack;
   private HashMap<String, String> formList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_words);

        formList = new HashMap<>();

        readAll();

        findViews();
        loadAnimations();
        changeCameraDistance();


    }


    private void readAll(){

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
        array = new ArrayList<>(formList.keySet());

    }

    private void changeCameraDistance() {
        int distance = 8000;
        float scale = getResources().getDisplayMetrics().density * distance;
        mCardFrontLayout.setCameraDistance(scale);
        mCardBackLayout.setCameraDistance(scale);
    }

    private void loadAnimations() {

        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.anim.out_animation);
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.anim.in_animation);

    }

    private void findViews() {
        mCardBackLayout = findViewById(R.id.card_back);
        mCardFrontLayout = findViewById(R.id.card_front);
        tvFront= (TextView) findViewById(R.id.tv_frontWord);
        tvBack= (TextView) findViewById(R.id.tv_backAnswer);


        Collections.shuffle(array);
        String word = array.get(0);
        tvFront.setText(word);
        String answer=formList.get(array.get(0));
        tvBack.setText(answer);

    }

    public void flipCard(View view) {


        if (!mIsBackVisible) {
            Collections.shuffle(array);
            String word = array.get(0);
            tvFront.setText(word);
            mSetRightOut.setTarget(mCardFrontLayout);
            mSetLeftIn.setTarget(mCardBackLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = true;
        } else{
            String answer=formList.get(array.get(0));
            tvBack.setText(answer);
            mSetRightOut.setTarget(mCardBackLayout);
            mSetLeftIn.setTarget(mCardFrontLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = false;
        }
    }

}

