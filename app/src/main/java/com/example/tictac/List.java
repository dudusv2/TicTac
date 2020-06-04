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

public class List extends AppCompatActivity {

    DatabaseReference reff;
    ListView myListView;
    ArrayList<String> myArrayList = new ArrayList<>();
    ArrayList<Score> myActiveGames = new ArrayList<>();
    ArrayAdapter<String> myArrayAdapter;
    private ValueEventListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        myListView = findViewById(R.id.ListView);
        reff = FirebaseDatabase.getInstance().getReference().child(MainActivity.user);
        myArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myArrayList);
        myListView.setAdapter(myArrayAdapter);
        myListView.setClickable(true);
//        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Object o = myListView.getItemAtPosition(position);
//                myActiveGames.get(position).setActive(false);
//                myActiveGames.get(position).setPlayer2(MainActivity.user);
//                myActiveGames.get(position).setTurn(new Random().nextBoolean());
//                myActiveGames.get(position).setPlayers(true);
//                reff.child(myActiveGames.get(position).getName()).setValue(myActiveGames.get(position));
//
//                Intent newActivity = new Intent(Multiplayer.this, NewActivity.class);
//                newActivity.putExtra("id", myActiveGames.get(position).getName());
//                newActivity.putExtra("multiplayer", true);
//                List.this.startActivity(newActivity);
//                finish();
//
//            }
//        });
        listener = reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                myArrayList.clear();
                myActiveGames.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Log.e("Multiplayer", postSnapshot.toString());
                    Score post = postSnapshot.getValue(Score.class);
                    String myChildValues = post.getScore();
                    myArrayList.add(myChildValues);
                    myActiveGames.add(post);
                    myArrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

//        FloatingActionButton btnAddGame = findViewById(R.id.btn_addGame);
//        btnAddGame.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                Intent addGameIntent = new Intent(List.this, AddGameActivity.class);
//                List.this.startActivity(addGameIntent);
//                finish();
//            }
//        });
    }

}
