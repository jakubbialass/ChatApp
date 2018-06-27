package com.example.kuba.chatapp.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.kuba.chatapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.reflect.TypeToken;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kuba on 25.06.2018.
 */

public class AddFavourites extends Fragment {

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    private Button addButton;
    private EditText editFav;

    private DocumentReference mFavouriteRef = FirebaseFirestore.getInstance().document("user/"/*+mAuth.getUid()*/ + "favourites");


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        addButton = view.findViewById(R.id.add_button);
        editFav = view.findViewById(R.id.edit_fav);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addFavouriteThread();
            }
        });




        return view;
    }


    private void addFavouriteThread()
    {
        String name = editFav.getText().toString();

        ArrayList<String> favouritesList;
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("favourites-threads", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("favourites-threads", null);
        Type type= new TypeToken<ArrayList<String>>() {}.getType();
        favouritesList = gson.fromJson(json, type);
        if(favouritesList==null)
            favouritesList = new ArrayList<>();
        favouritesList.add(name);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json2 = gson.toJson(favouritesList);
        editor.putString("favourites-threads", json2);
        editor.apply();

        Map<String, Object> data = new HashMap<>();
        data.put("favourites_threads", name);
        db.collection("users").document(mAuth.getUid()).collection("favourites").document()
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }


}
