package com.example.kuba.chatapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kuba.chatapp.Interfaces.OnNavigationCollapseListener;
import com.example.kuba.chatapp.Interfaces.OnTopReachedListener;
import com.example.kuba.chatapp.R;
import com.example.kuba.chatapp.Utilities.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**
 * Created by Kuba on 06.07.2018.
 */



public class ThreadMessagesAdapter extends RecyclerView.Adapter {

private ArrayList<Message> messages;
private FirebaseUser user;

OnTopReachedListener onTopReachedListener;


    @Override
    public int getItemCount() {/*
        int size=0;
        if(messages !=null)
            size= messages.size();*/
        return messages.size();
    }


    public ThreadMessagesAdapter(ArrayList<Message> arrayList) {
        messages = arrayList;
        user = FirebaseAuth.getInstance().getCurrentUser();
    }


    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);

        if (message.getSender().equals(user.getUid().toString())) {
            return 1;
        } else {
            return 2;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == 1) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_sent_item, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == 2) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_received_item, parent, false);
            return new ReceivedMessageHolder(view);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);

        switch (holder.getItemViewType()) {
            case 1:
                ((SentMessageHolder) holder).bind(message);
                break;
            case 2:
                ((ReceivedMessageHolder) holder).bind(message);
        }
        if (position == messages.size() - 1){
            onTopReachedListener.onTopReached(position);
        }
    }


    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        //TextView messageText, timeText, nameText;
        private TextView mItemContent, mItemSendTime;

        ReceivedMessageHolder(View itemView) {
            super(itemView);
            mItemContent = itemView.findViewById(R.id.content);
            mItemSendTime = itemView.findViewById(R.id.send_time);

        }

        void bind(Message message) {
            mItemContent.setText(message.getContent());
            mItemSendTime.setText(message.getSendTime());

        }
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        //TextView messageText, timeText, nameText;
        private TextView mItemContent, mItemSendTime;

        SentMessageHolder(View itemView) {
            super(itemView);
            mItemContent = itemView.findViewById(R.id.content);
            mItemSendTime = itemView.findViewById(R.id.send_time);

        }

        void bind(Message message) {
            mItemContent.setText(message.getContent());
            mItemSendTime.setText(message.getSendTime());

        }
    }

    public void removeItemFromList(ArrayList<Message> mCustomObjects, View view){
        /*
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("notes file", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson2 = new Gson();
        String json2 = gson2.toJson(messages);
        editor.putString("notes list", json2);
        editor.apply();
        notifyDataSetChanged();
        */

    }


    public void setOnTopReachedListener(OnTopReachedListener onTopReachedListener){

        this.onTopReachedListener = onTopReachedListener;
    }










}






//last adapter

/*
public class ThreadMessagesAdapter extends RecyclerView.Adapter<ThreadMessagesAdapter.ListViewHolder> {

public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
    private TextView mItemContent, mItemSendTime;
    //private TextView mItemDate;
    public Context context;


    public ListViewHolder(View itemView, Context context) {
        super(itemView);

        this.context=context;
        mItemContent = itemView.findViewById(R.id.content);
        mItemSendTime = itemView.findViewById(R.id.send_time);
        //mItemDate = itemView.findViewById(R.id.date);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view)
            {
                messages.remove(getAdapterPosition());
                removeItemFromList(messages, view);
                Toast.makeText(view.getContext(), "Usunięto notatkę", Toast.LENGTH_SHORT).show();

                return true;
            }
        });
    }

    public void onClick(View view) {

        Note note =messages.get(getAdapterPosition());
        Intent intent = new Intent(view.getContext(), NoteReview.class);
        Bundle extras = new Bundle();
        extras.putString("text", note.getText());
        extras.putInt("position", getAdapterPosition());
        intent.putExtras(extras);
        view.getContext().startActivity(intent);
        ((Activity)view.getContext()).finish();


    }

    public boolean onLongClick(View view) {
        return true;
    }


}



    @Override
    public int getItemCount() {
        int size=0;
        if(messages !=null)
            size= messages.size();
        return messages.size();
    }


    public ThreadMessagesAdapter(ArrayList<Message> arrayList) {
        messages = arrayList;
        user = FirebaseAuth.getInstance().getCurrentUser();
    }


    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        return new ListViewHolder(view, view.getContext());
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        if(messages !=null) {
            Message message = messages.get(position);
            holder.mItemContent.setText(message.getContent());
            holder.mItemSendTime.setText(message.getSendTime());
        }
        if (position == messages.size() - 1){
            onTopReachedListener.onTopReached(position);
        }
    }


    public void removeItemFromList(ArrayList<Message> mCustomObjects, View view){

        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("notes file", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson2 = new Gson();
        String json2 = gson2.toJson(messages);
        editor.putString("notes list", json2);
        editor.apply();
        notifyDataSetChanged();


    }


    public void setOnTopReachedListener(OnTopReachedListener onTopReachedListener){

        this.onTopReachedListener = onTopReachedListener;
    }
*/


