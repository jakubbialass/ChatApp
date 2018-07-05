package com.example.kuba.chatapp.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kuba.chatapp.R;

/**
 * Created by Kuba on 04.07.2018.
 */

public class ThreadChatFragment extends Fragment {


    private TextView title;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_threadchat, container, false);

        String stringTitle;
        Bundle b = getArguments();
        stringTitle  = b.getString("stringTitle");


        title=view.findViewById(R.id.title);
        title.setText(stringTitle);



        return view;
    }


}
