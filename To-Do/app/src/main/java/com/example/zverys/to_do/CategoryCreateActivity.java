package com.example.zverys.to_do;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class CategoryCreateActivity extends AppCompatActivity {

    private EditText categoryTitle, categoryDsc;
    private Button createBtn;
    List<Category> Categories=new ArrayList<Category>();
    ListView categoriesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_create);

        categoryTitle=(EditText) findViewById(R.id.categoryTitle);
        categoryDsc=(EditText) findViewById(R.id.catedoryDescription);
        createBtn=(Button) findViewById(R.id.button);
        categoriesListView=(ListView) findViewById(R.id.ListView);
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("creator");
        tabSpec.setContent(R.id.creatorTab);
        tabSpec.setIndicator("Creator");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("list");
        tabSpec.setContent(R.id.categoriesList);
        tabSpec.setIndicator("List");
        tabHost.addTab(tabSpec);


        final Button createBtn = (Button) findViewById(R.id.button);
        createBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                addCategory(categoryTitle.getText().toString(), categoryDsc.getText().toString());
                populateList();
                ArrayList<String> list = new ArrayList<>();
                list.add(categoryTitle.getText().toString());
                list.add(categoryDsc.getText().toString());
                CreateCategoryToDB(list);
                Toast.makeText(getApplicationContext(), categoryTitle.getText().toString()+" has been added to your Categories", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CategoryCreateActivity.this, MainActivity.class);
                startActivity(intent);
            }
                                     });
        /*
        createBtn.setOnClickListener((view) -> {
                addCategory(categoryTitle.getText().toString(), categoryDsc.getText().toString());
                populateList();
                Toast.makeText(getApplicationContext(), categoryTitle.getText().toString()+" has been added to your Categories", Toast.LENGTH_SHORT).show();
            //public void onClick(View view) {
            //    Intent inent = new Intent(getActivity(), Tab3Fragment.class);
             //   startActivity(inent);
            //}
        });*/



        categoryTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                createBtn.setEnabled(!categoryTitle.getText().toString().trim().isEmpty());/////////////////////////////////////////////////////////
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void populateList(){
        ArrayAdapter<Category> adapter = new CategoriesListAdapter();
        categoriesListView.setAdapter(adapter);
    }

    private void addCategory (String name, String description){
        Categories.add(new Category(name, description));
    }

    private class CategoriesListAdapter extends ArrayAdapter<Category>{
        public CategoriesListAdapter(){
            super (CategoryCreateActivity.this, R.layout.listview_item, Categories);
        }

        @Override
        public View getView (int position, View view, ViewGroup parent){
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.listview_item, parent, false);

            Category currentCategory = Categories.get(position);

            TextView title=(TextView) view.findViewById(R.id.categoryName);
            title.setText(currentCategory.getTitle());

            TextView description=(TextView) view.findViewById(R.id.categoryDesc);
            description.setText(currentCategory.getDescript());

            return view;
        }

    }
    public void CreateCategoryToDB(ArrayList list) {

        class CreateTask extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            DB database = new DB();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(CategoryCreateActivity.this, getString(R.string.Login_please_wait), null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                if (s.equals(Integer.toString(HttpsURLConnection.HTTP_OK))) {
                    Intent intent = new Intent(CategoryCreateActivity.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(CategoryCreateActivity.this, "pavyko", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(CategoryCreateActivity.this, "nepavyko", Toast.LENGTH_LONG).show();
                }
                loading.dismiss();
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<>();
                data.put("action", "category_create");
                data.put("category", params[0]);
                data.put("description", params[1]);
                data.put("iduser", StaticItems.UserName);

                String result = database.sendPostRequest(getString(R.string.URL_DATABASE), data);

                return result;
            }
        }

        CreateTask tasks = new CreateTask();
        tasks.execute(list.get(0).toString(), list.get(1).toString());
    }
}
