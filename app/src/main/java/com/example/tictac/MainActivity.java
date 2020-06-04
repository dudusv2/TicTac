package com.example.tictac;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;

public class MainActivity extends AppCompatActivity {

//    RecyclerView mRecyclerView;
//    MyAdapter mAdapter;
//    private ArrayList<ListPetsQuery.Item> mPets;
//    private final String TAG = MainActivity.class.getSimpleName();

     static String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //supportActionBar.hide()
        getSupportActionBar().hide();

        CognitoUserPool userpool = new CognitoUserPool(MainActivity.this, new AWSConfiguration(MainActivity.this));
        user = userpool.getCurrentUser().getUserId();
        Log.e("mój tag", user);
        setContentView(R.layout.activity_main);
        Button btn_1 = findViewById(R.id.btn);
        Button btn_2 = findViewById(R.id.btn_2);
        Button btn_3 = findViewById(R.id.btn_3);
        Button btn_4 = findViewById(R.id.btn_4);

        btn_1.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         Intent intent =new Intent(MainActivity.this, Multiplayer.class);
                                         startActivity(intent);
                                     }
                                 }
        );
        btn_2.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         Intent intent =new Intent(MainActivity.this, NewActivity2.class);
                                         startActivity(intent);
                                     }
                                 }
        );
        btn_3.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         //Toast.makeText(MainActivity.this, "jolll", Toast.LENGTH_SHORT).show();
                                         Intent intent =new Intent(MainActivity.this, List.class);
                                         startActivity(intent);
                                     }
                                 }
        );
        btn_4.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         finish();
                                         System.exit(0);
                                     }
                                 }
        );


        }
}
