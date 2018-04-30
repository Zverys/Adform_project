package com.example.zverys.to_do;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.Console;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TaskCreateActivity extends AppCompatActivity {

    private EditText taskName;
    private EditText taskDescriptionprasymas;
    private DatePicker datePick;
    private ArrayList<Task> tasks = new ArrayList<Task>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //tasks.add(new Task("uzduotis","aaaaaa", "virtuve", new Date(2011-11-11)));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_create);
        //get the spinner from the xml.
        final Spinner dropdown = (Spinner) findViewById(R.id.kategorijos);
        //create a list of items for the spinner.
        String[] items = new String[]{"choose category", "Summer", "place holder"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
        final ListView lv = (ListView) findViewById(R.id.listview);
        Button create = (Button) findViewById(R.id.Create);
        this.taskName = (EditText)findViewById(R.id.taskName);
        this.taskDescriptionprasymas = (EditText)findViewById(R.id.taskDescription);
        this.datePick = (DatePicker)findViewById(R.id.datePicker);


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String task = taskName.getText().toString();
                final String description = taskDescriptionprasymas.getText().toString();
                final String category = dropdown.getSelectedItem().toString();
                final Date date = getDateFromDatePicker(datePick);
                
                if (task.equals("") || description.equals("")){
                    String error1 = "Neivesta uzduotis arba aprasymas ";

                    Toast.makeText(TaskCreateActivity.this, error1, Toast.LENGTH_LONG).show();
                }
                else{
                    tasks.add(new Task(task, description, category, new Date(2018-11-27)));
                    Toast.makeText(TaskCreateActivity.this, task + "\n" + description + "\n" + category + "\n" + date + "\n" + tasks.size(), Toast.LENGTH_LONG).show();
                    //Intent toTaskView = new Intent(TaskCreateActivity.this, Task_listing.class);
                    //startActivity(toTaskView);

                    //lv.setAdapter(new Task_listing.MyListAdapter(this, R.layout.layout, tasks));
                }
                //    tasks.add(new Task("", "", "", new Date(2018-11-27)));}
                //Toast.makeText(TaskCreateActivity.this, task + "\n" + description + "\n" + category + "\n" + date, Toast.LENGTH_LONG).show();

            }
        });}

        public static java.util.Date getDateFromDatePicker(DatePicker datePicker ){
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth();
            int year =  datePicker.getYear();

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);

            return calendar.getTime();
        }
        public ArrayList<Task> getTasks(){
            return tasks;
        }
    }
