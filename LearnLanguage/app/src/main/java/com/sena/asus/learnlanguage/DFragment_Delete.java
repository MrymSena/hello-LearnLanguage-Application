package com.sena.asus.learnlanguage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sena.asus.learnlanguage.Database.DatabaseHandler;
import com.sena.asus.learnlanguage.Database.Word;

import org.apache.commons.lang3.StringUtils;

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
        getDialog().setTitle("Kelime Sil");

        btn_delete=(Button)rootView.findViewById(R.id.btn_deleteWord);
        btn_cancel2=(Button)rootView.findViewById(R.id.btn_cancel2);
        editText_word=(EditText)rootView.findViewById(R.id.editText_word);


        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String e_word=editText_word.getText().toString();
                e_word= StringUtils.capitalize(e_word.toLowerCase());

                final DatabaseHandler databaseHandler= new DatabaseHandler(getActivity());
                boolean isExist=databaseHandler.isWordExist(e_word);

                try {
                    if (isExist) {
                        final Word word=databaseHandler.getWord(e_word);

                        //Alert Dialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage(word.getEnglish_word()+"\n"+word.getTurkish_word());
                        builder.setTitle("Kelime silinecek emin misin?");
                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                databaseHandler.deleteWord(word.getEnglish_word());
                                Toast.makeText(getActivity(), "Kelime başarıyla silinmiştir.", Toast.LENGTH_SHORT).show();
                                editText_word.setText("");
                            }
                        });

                        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                getDialog().dismiss();
                            }
                        });

                        AlertDialog dialog=builder.create();
                        dialog.show();
                        //end Alert Dialog

                    }else{
                        Toast.makeText(getActivity(), "Kelime veritabanınızda bulunmamaktadır.", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e) {
                    Toast.makeText(getActivity(), "Bir hata oluştu!", Toast.LENGTH_SHORT).show();

                }
            }
        });

        btn_cancel2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                getDialog().dismiss();
            }
        });

        return rootView;
    }

}
