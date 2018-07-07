package com.example.kuba.chatapp.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.kuba.chatapp.Adapters.ThreadMessagesAdapter;
import com.example.kuba.chatapp.R;
import com.example.kuba.chatapp.Threads;
import com.example.kuba.chatapp.Utilities.Message;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kuba on 04.07.2018.
 */

public class ThreadChatFragment extends Fragment {


    //Firestore
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser user;
    private String stringTitle;

    private Date lastMessageDate;

    private TextView title, content;
    private Button send;
    private ThreadMessagesAdapter adapter;
    private ArrayList<Message> messages;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_threadchat, container, false);

        Bundle b = getArguments();
        stringTitle  = b.getString("stringTitle");
        Log.d("stringtitle2", stringTitle.toString() );
        //title=view.findViewById(R.id.title);
        //title.setText(stringTitle);

        lastMessageDate = Calendar.getInstance().getTime();

        //Firestore
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();


        messages = new ArrayList<>();

        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        adapter = new ThreadMessagesAdapter(messages);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
/*
        Message message = new Message("raz", "Jakub", null, null, null);
        Message message2 = new Message("dwa", "Jakub", null, null, null);
        Message message3 = new Message("trzy", "Jakub", null, null, null);
        messages.add(message);
        messages.add(message2);
        messages.add(message3);
*/

        adapter.notifyDataSetChanged();

        content = view.findViewById(R.id.edit);
        send = view.findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
                //Log.d("dasd", messages.toString());
            }
        });

        feedMessagesList();


        return view;
    }



    public void feedMessagesList() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        db.collection("threads").document(stringTitle).collection("messages").whereLessThan("sendDate", lastMessageDate).orderBy("sendDate").limit(20)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                lastMessageDate=snapshot.getDate("sendDate");
                                if(lastMessageDate!=null)
                                    Log.d("lastMessageDate", lastMessageDate.toString());
                                Log.d("tutaj31", snapshot.getId() + " => " + snapshot.getData());

                                //Message message = new Message(snapshot.getString("content"), snapshot.getString("sender"), stringTitle, snapshot.getString("senderNickname"), lastMessageDate);
                                Message message = snapshot.toObject(Message.class);//toObject(Message.class);
                                messages.add(message);
                                Log.d("pobrana4",message.getContent().toString());
                             //   Log.d("contenti",snapshot.getString("content").toString());
                                    //first_column.add(snapshot.getString("favourites_threads"));
                            }
                            adapter.notifyDataSetChanged();
                           // Log.d("pobrana4",message.getContent().toString());
                           // lastMessageDate=Calendar.getInstance().getTime();
                            //Log.d("lista nr 1 ", first_column.toString());
                        }
                    }
                });


    }

    //Message message = new Message(content.getText().toString(), user.getUid(), stringTitle, user.getDisplayName(), Calendar.getInstance().getTime());

    public void sendMessage() {
        Message message = new Message(content.getText().toString(), user.getUid().toString(), stringTitle, user.getDisplayName().toString(), Calendar.getInstance().getTime());
        //
        messages.add(message);
        adapter.notifyDataSetChanged();
        //
        //Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Message> data = new HashMap<>();
        Log.d("przed", " data");
        data.put("messagenus", message);
        Log.d("po", " data");
        Log.d("stringtitle", stringTitle.toString());

        db.collection("threads").document(stringTitle).collection("messages")
                .add(message)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }


}
