package com.example.kuba.chatapp.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.kuba.chatapp.Adapters.ThreadAdapter;
import com.example.kuba.chatapp.Interfaces.OnNavigationCollapseListener;
import com.example.kuba.chatapp.R;
import com.example.kuba.chatapp.Threads;

import java.util.ArrayList;

/**
 * Created by Kuba on 23.06.2018.
 */

public class ThreadsFragment extends Fragment {

    private ArrayList<Threads> threadsList;
    OnNavigationCollapseListener onNavigationCollapseListener;
    OnCollapseListener onCollapseListener;
    private boolean isBack=false;


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

        adapter.setOnNavigationCollapseListener(new OnNavigationCollapseListener() {
            @Override
            public void onCollapseChange(boolean collapse) {
                if(collapse) {
                    onCollapseListener.onCollapse(true);
                }
                else
                    onCollapseListener.onCollapse(false);
            }
        });

        getFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if(getFragmentManager()!=null) {
                    if (getFragmentManager().getBackStackEntryCount() == 0 && isBack == true)
                        onCollapseListener.onCollapse(false);
                    Log.d("isBack", Integer.toString(getFragmentManager().getBackStackEntryCount()));
                }
            }
        });

        isBack=true;
        return view;
    }



    public interface OnCollapseListener {
        public void onCollapse(boolean collapse);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            onCollapseListener = (OnCollapseListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }








}