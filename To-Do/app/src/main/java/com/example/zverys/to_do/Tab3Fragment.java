package com.example.zverys.to_do;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;


public class Tab3Fragment extends Fragment {
    private static final String TAG = "Tab3Fragment";
    private Button btnTEST;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab3_fragment,container,false);
        final ListView lv1= (ListView) view.findViewById(R.id.lv1);
        final CategoriesDownloader d1=new CategoriesDownloader(this.getContext(),getString(R.string.URL_Get_Categories),lv1);
        d1.execute();

        return view;
    }
}

