package com.example.zverys.to_do;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class CategoryCreateActivity extends AppCompatActivity {

    private EditText categoryTitle;
    private RadioButton privateRB, publicRB;
    private Button createBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_create);

        categoryTitle=(EditText) findViewById(R.id.categoryTitle);
        privateRB=(RadioButton) findViewById(R.id.radioButton2);
        publicRB = (RadioButton) findViewById(R.id.radioButton3);
        createBtn=(Button) findViewById(R.id.button);


        categoryTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                createBtn.setEnabled(!categoryTitle.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //Check if the radiobutton Team is checked

        if(publicRB.isChecked()){
            //Go to another window to add more people to the project
        }

    }
}
