package com.example.kuba.chatapp.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.kuba.chatapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Kuba on 23.06.2018.
 */

public class ProfileFragment extends Fragment{

    FirebaseFirestore db;
    FirebaseAuth mAuth;
    CircleImageView avatar;

    private DocumentReference mFavouriteRef= FirebaseFirestore.getInstance().document("user/"/*+mAuth.getUid()*/+"favourites");

    ListView favouritesList1, favouritesList2;

    ArrayList<String> favouritesListFromFirestore1, favouritesListFromFirestore2;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Bundle b = getArguments();
        favouritesListFromFirestore1 = b.getStringArrayList("favourites 1");
        favouritesListFromFirestore2 = b.getStringArrayList("favourites 2");

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        avatar = view.findViewById(R.id.circle_avatar);
        //avatar.setImageResource(user.getPhotoUrl());
        /////////////////////
        FloatingActionButton butt = view.findViewById(R.id.add_favourites);
        butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment addFavourites = new AddFavourites();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.animator.fade_in,
                        android.R.animator.fade_in);
                fragmentTransaction.replace(R.id.fragment_layout, addFavourites);
                fragmentTransaction.commit();
            }
        });
        favouritesList1 = view.findViewById(R.id.favourites_list1);
        favouritesList2 = view.findViewById(R.id.favourites_list2);

        db = FirebaseFirestore.getInstance();


        ArrayAdapter<String> adapter1 = new ArrayAdapter(view.getContext(),
                android.R.layout.simple_list_item_1, favouritesListFromFirestore1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter(view.getContext(),
                android.R.layout.simple_list_item_1, favouritesListFromFirestore2);
        adapter1.notifyDataSetChanged();
        adapter2.notifyDataSetChanged();
        favouritesList1.setAdapter(adapter1);
        favouritesList2.setAdapter(adapter2);


        favouritesList1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return (motionEvent.getAction()==MotionEvent.ACTION_MOVE);
            }
        });
        favouritesList2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return (motionEvent.getAction()==MotionEvent.ACTION_MOVE);
            }
        });

        setListViewHeightBasedOnChildren(favouritesList1);
        setListViewHeightBasedOnChildren(favouritesList2);



        return view;
    }




    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, RelativeLayout.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }




}
