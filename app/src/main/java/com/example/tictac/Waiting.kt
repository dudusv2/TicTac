package com.example.tictac

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class Waiting : AppCompatActivity() {

    var reff: DatabaseReference? = null
    private var gameName: String = ""
    private lateinit var post: Game
    private lateinit var context:Context
    private var init:Boolean = false
    lateinit var addValueEventListener:ValueEventListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waiting)
        context = this
        gameName = intent.getStringExtra("id") as String
        Log.e("Multiplayer", "Gra $gameName")
        reff = FirebaseDatabase.getInstance().reference.child("Game").child(gameName)
        addValueEventListener = reff!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                post = snapshot.getValue(Game::class.java)!!
                Log.e("Multiplayer", "Gracz drugi "+post.player2)
                if(!post.players){
                    finish()
                }
                if (post.player2 !== null && !init) {
                    init = true
                    Log.e("Multiplayer", "Zaczynamy !")
                    val intent = Intent(context, NewActivity::class.java)
                    intent.putExtra("id", gameName)
                    startActivity(intent)
                    finish()
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("Multiplayer", "Usunięto")
            }
        })

    }

    override fun onStop() {
        super.onStop()
        if (reff != null) {
            reff!!.removeEventListener(addValueEventListener)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        finish()
    }

    override fun onRestart() {
        super.onRestart()
    }

}
