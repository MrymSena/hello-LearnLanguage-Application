package com.sena.asus.learnlanguage;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Asus on 17.01.2018.
 */

public class DFragment extends DialogFragment {

    private String type=null;
    private Button btn_saveWord, btn_cancel;
    private EditText editText_englishWord, editText_turkishWord;
    private HashMap<String, String> allWords=new HashMap<String, String>();
    ArrayList<String> englishwords;
    private ReadData readData =ReadData.getInstance();

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            type = bundle.getString("type");

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = null;

        Bundle bundle = this.getArguments();

        if(bundle !=null){
            type=bundle.getString("type");
        }

        if(type=="add") {
            rootView = inflater.inflate(R.layout.dialogfragment, container, false);
            getDialog().setTitle("Yeni Kelime Ekle");

            btn_saveWord=(Button)rootView.findViewById(R.id.btn_saveWord);
            btn_cancel=(Button)rootView.findViewById(R.id.btn_cancel);

            editText_englishWord=(EditText)rootView.findViewById(R.id.editTexr_englishWord);
            editText_turkishWord=(EditText)rootView.findViewById(R.id.editText_turkishWord);


            btn_saveWord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String english_Word=editText_englishWord.getText().toString();
                    String turkish_Word=editText_turkishWord.getText().toString();
                    int a=0;
                    int b=0;


                        if(!english_Word.isEmpty() && !turkish_Word.isEmpty()){
                            for(int i=0; i<english_Word.length(); i++) {
                                if (Character.isDigit(english_Word.charAt(i))) {
                                    Toast.makeText(getActivity(), "Lütfen rakam girmeyiniz", Toast.LENGTH_SHORT).show();
                                    a=1;
                                    break;
                                }

                                for (int k = 0; k < turkish_Word.length(); k++) {
                                    if (Character.isDigit(turkish_Word.charAt(k))) {
                                        Toast.makeText(getActivity(), "Lütfen rakam girmeyiniz", Toast.LENGTH_SHORT).show();
                                        b=1;
                                        break;
                                    }
                                }
                            }
                            if(a==0 && b==0) {
                                saveWord(english_Word, turkish_Word);
                                editText_englishWord.setText("");
                                editText_turkishWord.setText("");
                            }

                        }else{
                            Toast.makeText(getActivity(),"Opps! Neyi kaydedicez anlamadık. İki kısmı da doldurdun mu?",Toast.LENGTH_LONG).show();
                        }

                    }
            });

        }else{
            if(type=="delete")
                getDialog().setTitle("Sil");
            }
            // Do something else
            return rootView;
        }



        private void saveWord(String english_Word, String turkish_Word){

            String isSaved=readData.saveWord(english_Word,turkish_Word);

            if(isSaved.equals("true")){
                Toast.makeText(getActivity(),"Kelime başarıyla kaydedildi.", Toast.LENGTH_SHORT).show();
            }else if(isSaved.equals("alreadyExist")){
                Toast.makeText(getActivity(),"Kelime zaten veritabanınızda bulunmaktadır",Toast.LENGTH_SHORT).show();
            }else if(isSaved.equals("exception")){
                Toast.makeText(getActivity(),"Bir hatayla karşılaşıldı, tekrar deneyiniz!", Toast.LENGTH_SHORT).show();
            }
        }
}
