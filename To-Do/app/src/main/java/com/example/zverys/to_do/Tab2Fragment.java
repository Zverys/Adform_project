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


public class Tab2Fragment extends Fragment {
    private static final String TAG = "Tab2Fragment";
    //private Button btnTEST;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2_fragment,container,false);
        final ListView lv= (ListView) view.findViewById(R.id.tab2_listview);
        final Downloader d=new Downloader(this.getContext(),getString(R.string.URL_Get_Tasks),lv);
        d.execute();

        return view;
    }
}

