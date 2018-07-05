package com.example.kuba.chatapp.Utilities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.kuba.chatapp.Fragments.MessagesFragment;
import com.example.kuba.chatapp.Fragments.ProfileFragment;
import com.example.kuba.chatapp.Fragments.ThreadsFragment;
import com.example.kuba.chatapp.R;
import com.example.kuba.chatapp.Threads;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * Created by Kuba on 23.06.2018.
 */

public class MainActivity extends AppCompatActivity {

    BottomNavigationView nav;

    Fragment profileFragment, messagesFragment, threadsFragment;
    String selectedFragment;

    ArrayList<Threads> threadsList;
    private long mLastClickTime = System.currentTimeMillis();
    private static final long CLICK_TIME_INTERVAL = 0;

    private boolean busy=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        threadsList = new ArrayList<>();


        profileFragment = new ProfileFragment();
        messagesFragment = new MessagesFragment();
        threadsFragment = new ThreadsFragment();
        selectedFragment="threadsFragment";

        feedThreadsList(threadsList);



        nav = findViewById(R.id.bottom_navigation);
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                long now = System.currentTimeMillis();
                if (now - mLastClickTime < CLICK_TIME_INTERVAL) {
                    return false;
                }
                mLastClickTime = now;
                switch (item.getItemId()) {
                    case R.id.action_profile:
                        if(selectedFragment!="profileFragment") {
                            selectedFragment="profileFragment";
                            //profileFragment= new ProfileFragment();
                            ArrayList<String> favouritesListFromFirestore1 = new ArrayList<>();
                            ArrayList<String> favouritesListFromFirestore2 = new ArrayList<>();
                            feedFavouritesList(favouritesListFromFirestore1, favouritesListFromFirestore2);
                        }
                        break;
                    case R.id.action_messages:
                        if(selectedFragment!="messagesFragment") {
                            messagesFragment= new MessagesFragment();
                            selectedFragment = "messagesFragment";
                            replaceFragment(messagesFragment);
                        }
                        break;
                    case R.id.action_threads:
                        if(selectedFragment!="threadsFragment") {
                            //threadsFragment= new ThreadsFragment();
                            selectedFragment="threadsFragment";
                            feedThreadsList(threadsList);
                            //selectedFragment=threadsFragment;
                        }
                        break;
                }

                return true;
            }
        });




    }





    public void feedFavouritesList(final ArrayList<String> first_column, final ArrayList<String> second_column)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        db.collection("users").document(mAuth.getUid()).collection("favourites")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        boolean toFirst = true;
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                if (toFirst == true) {
                                    first_column.add(snapshot.getString("favourites_threads"));
                                    toFirst = false;
                                } else {
                                    second_column.add(snapshot.getString("favourites_threads"));
                                    toFirst = true;
                                }
                            }

                            Bundle args = new Bundle();
                            args.putStringArrayList("favourites 1", first_column);
                            args.putStringArrayList("favourites 2", second_column);
                            profileFragment= new ProfileFragment();
                            profileFragment.setArguments(args);
                            if(selectedFragment=="profileFragment")
                                replaceFragment(profileFragment);
                            //Log.d("lista nr 1 ", first_column.toString());
                            //Log.d("lista nr 2 ", second_column.toString());
                        }
                    }
                });
    }



    public void feedThreadsList(final ArrayList<Threads> threadsList)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if(threadsList.size()<1) {
            Log.d("Wszedlem do ", "IF");
            db.collection("threads")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                    Threads th = new Threads(snapshot.getString("title"));
                                    threadsList.add(th);
                                }

                                Bundle args = new Bundle();
                                args.putParcelableArrayList("threadsList", threadsList);
                                threadsFragment= new ThreadsFragment();
                                threadsFragment.setArguments(args);
                                replaceFragment(threadsFragment);
                                //Log.d("lista nr 1 ", threadsList.get(3).getText());

                            }
                        }
                    });
        }
        else{
            Bundle args = new Bundle();
            args.putParcelableArrayList("threadsList", threadsList);
            threadsFragment= new ThreadsFragment();
            threadsFragment.setArguments(args);
            replaceFragment(threadsFragment);
            Log.d("Wszedlem do ", "ELSE");
        }
    }



    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_layout, fragment, selectedFragment);
        fragmentTransaction.commit();
    }



}
