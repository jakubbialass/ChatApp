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
import android.widget.TextView;
import android.widget.Toast;

import com.example.kuba.chatapp.Fragments.ThreadChatFragment;
import com.example.kuba.chatapp.R;
import com.example.kuba.chatapp.Threads;
import com.example.kuba.chatapp.Utilities.Message;

import java.util.ArrayList;

/**
 * Created by Kuba on 06.07.2018.
 */

public class ThreadMessagesAdapter extends RecyclerView.Adapter<ThreadMessagesAdapter.ListViewHolder> {

private ArrayList<Message> mCustomObjects;


public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
    private TextView mItemContent;
    //private TextView mItemDate;
    public Context context;

    public ListViewHolder(View itemView, Context context) {
        super(itemView);

        this.context=context;
        mItemContent = itemView.findViewById(R.id.content);
        //mItemDate = itemView.findViewById(R.id.date);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view)
            {/*
                mCustomObjects.remove(getAdapterPosition());
                removeItemFromList(mCustomObjects, view);
                Toast.makeText(view.getContext(), "Usunięto notatkę", Toast.LENGTH_SHORT).show();
                */
                return true;
            }
        });
    }

    public void onClick(View view) {
        /*
        Note note =mCustomObjects.get(getAdapterPosition());
        Intent intent = new Intent(view.getContext(), NoteReview.class);
        Bundle extras = new Bundle();
        extras.putString("text", note.getText());
        extras.putInt("position", getAdapterPosition());
        intent.putExtras(extras);
        view.getContext().startActivity(intent);
        ((Activity)view.getContext()).finish();
        */

    }

    public boolean onLongClick(View view) {
        return true;
    }


}





    @Override
    public int getItemCount() {
        int size=0;
        if(mCustomObjects!=null)
            size=mCustomObjects.size();
        return size;
    }


    public ThreadMessagesAdapter(ArrayList<Message> arrayList) {
        mCustomObjects = arrayList;
    }


    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        return new ListViewHolder(view, view.getContext());
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        if(mCustomObjects!=null) {
            Message message = mCustomObjects.get(position);
            holder.mItemContent.setText(message.getContent());
        }
    }


    public void removeItemFromList(ArrayList<Message> mCustomObjects, View view){
/*
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("notes file", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson2 = new Gson();
        String json2 = gson2.toJson(mCustomObjects);
        editor.putString("notes list", json2);
        editor.apply();
        notifyDataSetChanged();
        */

    }


}