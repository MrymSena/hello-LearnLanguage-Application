package com.sena.asus.learnlanguage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class AddNewWord extends AppCompatActivity {

    EditText kelime, anlam;
    Button ekleButon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_word);

        try {
            Glide.with(this).load(R.drawable.addnewwords).into((ImageView) findViewById(R.id.imageView_addnewwords));
        } catch (Exception e) {
            e.printStackTrace();
        }

        kelime = (EditText) findViewById(R.id.editText_Kelime);
        anlam = (EditText) findViewById(R.id.editText_Anlam);
        ekleButon = (Button) findViewById(R.id.button_KelimeyiEkle);


        ekleButon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String english = kelime.getText().toString();
                String turkish = anlam.getText().toString();
                try {

                    File root = android.os.Environment.getExternalStorageDirectory();
                    String dictFilePath = root.getAbsolutePath()+"/download/lwmypersonaldictlw.txt";
                    try {
                        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(dictFilePath,true)));
                        out.println(english+"\t"+turkish);
                        out.flush();
                        out.close();

                    }
                    catch (IOException e) {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Toast.makeText(getApplicationContext(), "Kelime eklendi", Toast.LENGTH_SHORT).show();
                kelime.setText("");
                anlam.setText("");

            }
        });

    }







}
