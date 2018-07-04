package com.example.kuba.chatapp.Fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.kuba.chatapp.R;
import com.example.kuba.chatapp.Threads;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kuba on 29.06.2018.
 */

public class ThreadAdapter extends ArrayAdapter<Threads>  {

    private TextView title;
    private Button button;
    private ArrayList<Threads> threadsList;


    public ThreadAdapter(Context context, ArrayList<Threads> threadsList) {
        super(context, R.layout.thread_row, threadsList);
        this.threadsList=threadsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Threads th = getItem(position);

        View view = convertView;
        if (view == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.thread_row, null);
        }

        button= view.findViewById(R.id.button);
        button.setText(th.getText());



        return view;
    }

}
