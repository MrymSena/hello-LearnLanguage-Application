package com.sena.asus.learnlanguage;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.sena.asus.learnlanguage.Database.DatabaseHandler;
import com.sena.asus.learnlanguage.Database.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class LearnWords extends AppCompatActivity{


    private ArrayList<String> arrayData;
    private ArrayList<String> definitions;
    List<Word> wordList = new ArrayList<Word>();

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

       /*ReadData readData= ReadData.getInstance();
       formList=readData.getFormList();
       arrayData=readData.getMydata();
        */
        DatabaseHandler db= new DatabaseHandler(this);
        wordList=db.getAllWords();


        findViews();
        loadAnimations();
        changeCameraDistance();


    }


  /*  private void readAll(){

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
*/
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


        Collections.shuffle(wordList);
        String word = wordList.get(0).getEnglish_word();
        tvFront.setText(word);
        String answer=wordList.get(0).getTurkish_word();
        tvBack.setText(answer);

    }

    public void flipCard(View view) {


        if (!mIsBackVisible) {
            Collections.shuffle(wordList);
            String word = wordList.get(0).getEnglish_word();
            tvFront.setText(word);
            mSetRightOut.setTarget(mCardFrontLayout);
            mSetLeftIn.setTarget(mCardBackLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = true;
        } else{
            String answer=wordList.get(0).getTurkish_word();
            tvBack.setText(answer);
            mSetRightOut.setTarget(mCardBackLayout);
            mSetLeftIn.setTarget(mCardFrontLayout);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = false;
        }
    }

}


