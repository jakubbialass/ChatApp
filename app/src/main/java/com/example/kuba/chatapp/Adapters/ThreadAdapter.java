package com.example.kuba.chatapp.Adapters;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kuba.chatapp.Fragments.ThreadChatFragment;
import com.example.kuba.chatapp.R;
import com.example.kuba.chatapp.Threads;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.Duration;

/**
 * Created by Kuba on 29.06.2018.
 */

public class ThreadAdapter extends ArrayAdapter<Threads> implements View.OnClickListener{

    private TextView title;
    //private Button button;
    private ArrayList<Threads> threadsList;
    private String stringTitle;

    private static class ViewHolder {
        Button button;
    }


    public ThreadAdapter(Context context, ArrayList<Threads> threadsList) {
        super(context, R.layout.thread_row, threadsList);
        this.threadsList=threadsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Threads th = getItem(position);

        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            convertView = vi.inflate(R.layout.thread_row, null);
            viewHolder.button= convertView.findViewById(R.id.button);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.button.setText(th.getText());
        viewHolder.button.setOnClickListener(this);
        viewHolder.button.setTag(position);

        return convertView;
    }

    @Override
    public void onClick(View view) {
        int position = (Integer) view.getTag();
        Threads th = getItem(position);

        //Toast.makeText(getContext(), position, Toast.LENGTH_SHORT).show();
        //Log.d("pozycja", "pozucja");

        switch (view.getId()) {
            case R.id.button:
                Fragment chatFragment = new ThreadChatFragment();
                Bundle args = new Bundle();
                stringTitle=th.getText();
                args.putString("stringTitle", stringTitle);
                chatFragment.setArguments(args);
                addFragment(chatFragment);

                Log.d("pozycja", Integer.toString(position) );
                Log.d("stringtitle1", stringTitle.toString() );
                break;
        }
    }






    public void addFragment(Fragment fragment){
        FragmentManager fragmentManager = ((Activity) getContext()).getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_layout, fragment, stringTitle).addToBackStack("threadsFragment");
        fragmentTransaction.commit();
    }
}
