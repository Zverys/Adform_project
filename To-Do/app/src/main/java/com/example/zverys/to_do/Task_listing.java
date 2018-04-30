package com.example.zverys.to_do;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Task_listing extends AppCompatActivity {
    TaskCreateActivity Object = new TaskCreateActivity();
    ArrayList<Task> tasks1 = Object.getTasks();

    @Override
    protected void onCreate(Bundle savedInstanceState) {////////////////////////////////padaryti kad atsinaujintu sukurus nauja uzduoti nes db nerodo nauju.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_listing);


        tasks1.add(new Task("Padaryti algoritmu nd","Ieina pasiruosimas, inzinerinio projekto parasymas", "virtuve", new Date(118, 1, 1)));
        tasks1.add(new Task("Isplakti kiausini","su sakute", "virtuve", new Date(117, 11, 11)));
        tasks1.add(new Task("Nupirkti kiausini","su pinigais", "virtuve", new Date(118, 5, 2)));
        tasks1.add(new Task("Rasti kiausini","su akimmmmmmmmm", "virtuve", new Date(117, 0, 27)));
        ListView lv = (ListView) findViewById(R.id.listview);

        lv.setAdapter(new MyListAdapter(this, R.layout.layout, tasks1));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Toast.makeText(Task_listing.this, "List item was clicked at " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public class MyListAdapter extends ArrayAdapter<Task> {
        private int layout;

        public MyListAdapter(@NonNull Context context, int resource, @NonNull List<Task> objects) {
            super(context, resource, objects);
            layout = resource;
            notifyDataSetChanged();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder mainViewholder = null;
            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.list_item_thumb);
                viewHolder.title = (TextView) convertView.findViewById(R.id.list_item_text);
                viewHolder.description = (TextView) convertView.findViewById(R.id.textView_description);
                viewHolder.date = (TextView) convertView.findViewById(R.id.texView_atliktiIKI);
                viewHolder.category = (TextView) convertView.findViewById(R.id.textView_Category);
                viewHolder.button = (Button) convertView.findViewById(R.id.list_item_button);
                viewHolder.button.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Toast.makeText(getContext(), "Button was clicked for list item " + getItem(position).name, Toast.LENGTH_SHORT).show();
                    }
                });
                convertView.setTag(viewHolder);
                viewHolder.title.setText(getItem(position).name);
                viewHolder.description.setText(getItem(position).desciption);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
                viewHolder.date.setText("Date: " + formatter.format(getItem(position).date));
                viewHolder.category.setText("Category: " + getItem(position).category);
            }
            else{
                mainViewholder = (ViewHolder) convertView.getTag();
                mainViewholder.title.setText(getItem(position).name);//.setText( "testinggg");//getItem(position).name);
                mainViewholder.description.setText(getItem(position).desciption);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
                mainViewholder.date.setText("Date: " + formatter.format(getItem(position).date));
                mainViewholder.category.setText("Category: " + getItem(position).category);
            }

            return convertView;
        }
    }
    public static class ViewHolder {
        ImageView thumbnail;
        TextView title;
        Button button;
        TextView description;
        TextView date;
        TextView category;
    }
}
