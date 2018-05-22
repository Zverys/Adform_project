package com.example.zverys.to_do;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Parser extends AsyncTask<Void,Integer,Integer> {

    Context c;
    ListView lv;
    String data;

    //ArrayList<String> players=new ArrayList<>();
    ArrayList<Task> tasks = new ArrayList<>();
    ProgressDialog pd;

    public Parser(Context c, String data, ListView lv) {
        this.c = c;
        this.data = data;
        this.lv = lv;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd=new ProgressDialog(c);
        pd.setTitle("Parser");
        pd.setMessage("Parsing ....Please wait");
        pd.show();
    }

    @Override
    protected Integer doInBackground(Void... params) {

        return this.parse();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);

        if(integer == 1)
        {
            //ADAPTER
            //ArrayAdapter<String> adapter=new ArrayAdapter<String>(c,android.R.layout.simple_list_item_1,players);

            //ADAPT TO LISTVIEW
            lv.setAdapter(new MyListAdapter(c, R.layout.layout,tasks));

            //LISTENET
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                    //Toast.makeText(this, "List item was clicked at " + position, Toast.LENGTH_SHORT).show();
                    //Snackbar.make(view,tasks.get(position),Snackbar.LENGTH_SHORT).show();;//////////////////////////uncm
                }
            });

        }else
        {
           Toast.makeText(c,"unable to parse",Toast.LENGTH_SHORT).show();
        }

        pd.dismiss();
    }

    //PARSE RECEIVED DATA
    private int parse()
    {
        try
        {
            //ADD THAT DATA TO JSON ARRAY FIRST
            JSONArray ja=new JSONArray(data);

            //CREATE JO OBJ TO HOLD A SINGLE ITEM
            JSONObject jo= null;

            //players.clear();
            tasks.clear();
            //LOOP THRU ARRAY
            for(int i=0;i<ja.length();i++)
            {
                jo=ja.getJSONObject(i);
                //RETRIOEVE NAME
                String name=jo.getString("pavadinimas");

                //ADD IT TO OUR ARRAYLIST
                //players.add(name);
               //players.add(jo.getString("aprasymas"));
                //players.add(jo.getString("kategorija"));
                //players.add(jo.getString("data"));
                String dataa = jo.getString("data");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
                Date reformattedStr =  new Date();//new Date(33,11,15);
                try {
                    reformattedStr = formatter.parse(dataa);
                    //reformattedStr = formatter.format(reformattedStr);

                } catch (Exception ex ){
                    System.out.println(ex);
                }
                Date a = new Date(reformattedStr.getYear(), reformattedStr.getMonth(), reformattedStr.getDay());
                tasks.add(new Task(name, jo.getString("aprasymas"), jo.getString("kategorija"), a));
            }

            return 1;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
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
                        Intent inent = new Intent(c, TaskCreateActivity.class);
                        c.startActivity(inent);
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
    public class ViewHolder {
        ImageView thumbnail;
        TextView title;
        Button button;
        TextView description;
        TextView date;
        TextView category;
    }
}
