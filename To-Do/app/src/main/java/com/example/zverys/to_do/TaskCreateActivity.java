package com.example.zverys.to_do;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
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
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class TaskCreateActivity extends AppCompatActivity {

    private EditText taskName;
    private EditText taskDescriptionprasymas;
    private DatePicker datePick;
    private ArrayList<Task> tasks = new ArrayList<Task>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                    CreateTaskToDB(new Task(task, description, category, date));

                    Toast.makeText(TaskCreateActivity.this, task + "\n" + description + "\n" + category + "\n" + date + "\n", Toast.LENGTH_LONG).show();
                }
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
    public void CreateTaskToDB(final Task task) {

        class CreateTask extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            DB database = new DB();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TaskCreateActivity.this, getString(R.string.Login_please_wait), null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                if (s.equals(Integer.toString(HttpsURLConnection.HTTP_OK))) {
                    Intent intent = new Intent(TaskCreateActivity.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(TaskCreateActivity.this, "Task added", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(TaskCreateActivity.this, "Task didn't add", Toast.LENGTH_LONG).show();
                }
                loading.dismiss();
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                data.put("action", "task_create");
                data.put("pavadinimas", params[0]);
                data.put("aprasymas", params[1]);
                data.put("kategorija", params[2]);
                data.put("data", params[3]);
                data.put("userid", UserName.UserName);
                String result = database.sendPostRequest(getString(R.string.URL_DATABASE), data);
                return result;
            }
        }

        CreateTask tasks = new CreateTask();
        tasks.execute(task.name, task.desciption, task.category, task.date.toString());
    }
    }
