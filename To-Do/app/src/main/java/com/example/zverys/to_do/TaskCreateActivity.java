package com.example.zverys.to_do;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.Console;

public class TaskCreateActivity extends AppCompatActivity {

    private EditText uzduotiesPavadinimas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_create);
        //get the spinner from the xml.
        Spinner dropdown = (Spinner) findViewById(R.id.kategorijos);
        //create a list of items for the spinner.
        String[] items = new String[]{"choose category", "Summer", "place holder"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        Button create = (Button) findViewById(R.id.Create);
        this.uzduotiesPavadinimas = (EditText)findViewById(R.id.taskName);


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent permesti = new Intent(TaskCreateActivity.this, TaskCreateActivity.class);
                //startActivity(permesti);
                final String uzduotis = uzduotiesPavadinimas.getText().toString();
                Toast.makeText(TaskCreateActivity.this, uzduotis, Toast.LENGTH_LONG).show();
            }
        });
    }
}
