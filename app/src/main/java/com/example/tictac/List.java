package com.example.tictac;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class List extends AppCompatActivity {

    DatabaseReference reff;
    ListView myListView;
    ArrayList<String> myArrayList = new ArrayList<>();
    ArrayList<String> myArrayList2 = new ArrayList<>();
    ArrayList<Score> myActiveGames = new ArrayList<>();
    ArrayAdapter<String> myArrayAdapter;
    private ValueEventListener listener;
    Map<String, Integer> pairs = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        myListView = findViewById(R.id.ListView);
        reff = FirebaseDatabase.getInstance().getReference().child(MainActivity.user);
        myArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myArrayList2);
        myListView.setAdapter(myArrayAdapter);
        myListView.setClickable(true);

        listener = reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                myArrayList.clear();
                myActiveGames.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Log.e("Multiplayer", postSnapshot.toString());
                    Score post = postSnapshot.getValue(Score.class);
                    String myChildValues = post.getScore();

                    if (pairs.containsKey(post.getPlayer1()) )
                        pairs.put(post.getPlayer1(), pairs.get(post.getPlayer1()) + post.getScore1());
                    else
                        pairs.put(post.getPlayer1(),post.getScore1());
                    if (pairs.containsKey(post.getPlayer2()) )
                        pairs.put(post.getPlayer2(), pairs.get(post.getPlayer2()) + post.getScore2());
                    else
                        pairs.put(post.getPlayer2(),post.getScore2());

                    myArrayList.add(myChildValues);
                    myActiveGames.add(post);
                    myArrayAdapter.notifyDataSetChanged();
                }
                for (String i : pairs.keySet()) {
                    myArrayList2.add(i + " Wynik: " + pairs.get(i));
                    myArrayAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }



}
