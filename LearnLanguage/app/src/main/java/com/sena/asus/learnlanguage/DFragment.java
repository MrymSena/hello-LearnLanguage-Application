package com.sena.asus.learnlanguage;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Asus on 17.01.2018.
 */

public class DFragment extends DialogFragment {

    private String type=null;
    private Button btn_saveWord, btn_cancel;


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
                rootView = inflater.inflate(R.layout.dialogfragment, container,
                    false);
                getDialog().setTitle("Yeni Kelime Ekle");

                btn_saveWord=(Button)rootView.findViewById(R.id.btn_saveWord);
                btn_cancel=(Button)rootView.findViewById(R.id.btn_cancel);


                btn_saveWord.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!isWordExist());
                            saveWord();
                    }
                });




            }else{
                if(type=="delete")
                getDialog().setTitle("Sil");
            }
            // Do something else
            return rootView;
        }


        private boolean isWordExist(){
            return false;
        }

        private void saveWord(){

        }
}
