package com.example.kuba.chatapp.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.kuba.chatapp.R;
import com.example.kuba.chatapp.Threads;

import java.util.ArrayList;

/**
 * Created by Kuba on 23.06.2018.
 */

public class ThreadsFragment extends Fragment {

    private ArrayList<Threads> threadsList;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_threads, container, false);

        Bundle b = getArguments();
        threadsList  = b.getParcelableArrayList("threadsList");
       /* for(Threads listObject : threadsList) {
            Threads th = listObject;
        }
       */

        ListView yourListView = view.findViewById(R.id.threads_list);

        ArrayList<Threads> lista = new ArrayList();
        Threads t1 = new Threads("ciuchy");
        lista.add(t1);
        Threads t2 = new Threads("samochody");
        lista.add(t2);

// get data from the table by the ListAdapter
        ThreadAdapter adapter = new ThreadAdapter(view.getContext(), threadsList);

        yourListView.setAdapter(adapter);


        return view;
    }






}