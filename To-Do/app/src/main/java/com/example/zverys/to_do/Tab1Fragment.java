package com.example.zverys.to_do;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class Tab1Fragment extends Fragment {
    private static final String TAG = "Tab1Fragment";
    private Button btnTEST;
    private Button btnTEST2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_fragment,container,false);
        btnTEST = (Button) view.findViewById(R.id.btnTEST);
        btnTEST2 = (Button) view.findViewById(R.id.btnTEST2);
        TextView Json = (TextView) view.findViewById(R.id.textas);

        btnTEST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inent = new Intent(getActivity(), TaskCreateActivity.class);
                startActivity(inent);
            }
        });
        btnTEST2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inent = new Intent(getActivity(), CategoryCreateActivity.class);
                startActivity(inent);
            }
        });

        return view;
    }
    }

