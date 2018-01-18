package com.sena.asus.learnlanguage;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Asus on 18.01.2018.
 */

public class DFragment_Delete extends DialogFragment {

    private Button btn_delete, btn_cancel2;
    private EditText editText_word;
    ReadData readData=ReadData.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialogfragment_delete, container, false);
        getDialog().setTitle("Yeni Kelime Ekle");

        btn_delete=(Button)rootView.findViewById(R.id.btn_deleteWord);
        btn_cancel2=(Button)rootView.findViewById(R.id.btn_cancel2);
        editText_word=(EditText)rootView.findViewById(R.id.editText_word);


        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String word=editText_word.getText().toString();
                        readData.deleteWord(word);
                Toast.makeText(getActivity(), "Kelime başarıyla silinmiştir.", Toast.LENGTH_SHORT).show();
            }
        });





        return rootView;
    }
}
