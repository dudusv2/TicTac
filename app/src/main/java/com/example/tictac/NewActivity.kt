package com.example.tictac


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import java.util.*
import kotlin.math.max
import kotlin.random.Random


@Suppress(
        "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS",
        "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS"
)
class NewActivity : AppCompatActivity() {

    private var buttons = arrayOfNulls<Button>(100)
    private var turn = true
   // private var roundCount = 0
    private var p1Score = 0
    private var p2Score = 0
    private var p1Name = "Player 1"
    private var p2Name = "Player 2"
    private var multiplayer = false
    private lateinit var tvp1: TextView
    private lateinit var tvp2: TextView
    private var gameName: String = ""
    private var reff: DatabaseReference? = null
    private lateinit var post: Game
    lateinit var addValueEventListener:ValueEventListener
    private lateinit var context:Context
    private lateinit var buttonReset:Button
    private var maxId:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)

        context = this
        gameName = (if (savedInstanceState == null) {
            val extras = intent.extras
            extras?.getString("id")
        } else {
            savedInstanceState?.getString("id") ?: true
        }).toString()
        multiplayer = intent.getBooleanExtra("multiplayer", false)

        if(multiplayer)
            Log.e("Multiplayer", "Palyer 2")
        else
            Log.e("Multiplayer", "Player 1")

        reff = FirebaseDatabase.getInstance().reference
        if (reff != null)
            reff!!.child(MainActivity.user).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                        var score =  Score()
                        score.score1 = p1Score
                        score.score2 = p2Score
                        score.name = gameName
                        score.player1 = p1Name
                        score.player2 = p2Name
                        Toast.makeText(context,"ID "+maxId,Toast.LENGTH_LONG).show()
                        Log.e("Multiplayer", maxId.toString())
                        maxId = snapshot.childrenCount
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("Multiplayer", "Wyjście z gry")
                }
            })
        addValueEventListener = reff!!.child("Game").child(gameName).addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                    post = snapshot.getValue(Game::class.java)!!
                    turn = post.turn
                    if (!post.players) {
                        post.players2 = false
                        reff!!.child("Game").child(gameName).setValue(post)
                        Toast.makeText(context, "Przeciwnk opóścił grę", Toast.LENGTH_LONG).show()
//                        var score =  Score()
//                        score.score1 = p1Score
//                        score.score2 = p2Score
//                        score.name = gameName
//                        score.player1 = p1Name
//                        score.player2 = p2Name
//                        score.time =  Calendar.getInstance().getTime()
//                        Toast.makeText(context,"ID "+maxId,Toast.LENGTH_LONG).show()
//                        Log.e("Multiplayer", maxId.toString())
//                        reff!!.child(MainActivity.user).child("1").setValue(score)
                        finish()
                    }
                    p1Name = post.player1
                    p2Name = post.player2
                    tvp1.text = "$p1Name: $p1Score"
                    tvp2.text = "$p2Name: $p2Score"
                    if (post.board >= 0) {
                        if (turn) {
                            buttons[post.board]!!.text = "X"
                            buttons[post.board]!!.isActivated = true
                            buttons[post.board]!!.isEnabled = true
                            tvp1.isEnabled = false
                            tvp2.isEnabled = true
                            Log.e("Multiplayer", "Ustawiam X")
                        } else {
                            buttons[post.board]!!.text = "O"
                            buttons[post.board]!!.isActivated = false
                            buttons[post.board]!!.isEnabled = false
                            tvp1.isEnabled = true
                            tvp2.isEnabled = false
                            Log.e("Multiplayer", "Ustawiam O")
                        }
                    }
                    Log.e("Multiplayer", "odebrałem dane " + post.board)
                    if (checkForWin()) {
                        if (turn) {
                            player1Wins()
                        } else {
                            player2Wins()
                        }
                    } else if (post.roundCount == 99) {
                        draw()
                    }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("Multiplayer", "Wyjście z gry")
            }
        })
        Toast.makeText(this, "Zaczynamy !!!", Toast.LENGTH_SHORT).show()
        tvp1 = findViewById(R.id.text_view_p1)
        tvp2 = findViewById(R.id.text_view_p2)

        supportActionBar!!.hide()
        for (x in 0..99) {
            val btn = "button_$x"
            val resID = resources.getIdentifier(btn, "id", packageName)
            buttons[x] = findViewById(resID)
            buttons[x]!!.setOnClickListener {
                if ((buttons[x] as Button).text.toString() != "") return@setOnClickListener
                if (multiplayer == turn) {
                    if (turn) {
                        post.board = x
                        post.roundCount= post.roundCount+1
                        buttonReset.text = if(post.roundCount>1) "Poddaj się" else "Kończę grę"

                    } else {
                        post.board = x
                        post.roundCount= post.roundCount+1
                        buttonReset.text = if(post.roundCount>1) "Poddaj się" else "Kończę grę"
                    }
                    if (checkForWin()) {
                        if (turn) {
                            player1Wins()
                        } else {
                            player2Wins()
                        }
                    } else if (post.roundCount == 99) {
                        draw()
                    } else {
                        switchTurn()
                    }
                }
            }
        }
        buttonReset = findViewById<Button>(R.id.button_reset)
        buttonReset.setOnClickListener {
            post.players=false
            reff?.child("Game")?.child(gameName)?.setValue(post)
//            var score =  Score()
//            score.score1 = p1Score
//            score.score2 = p2Score
//            score.name = gameName
//            score.player1 = p1Name
//            score.player2 = p2Name
//            score.time =  Calendar.getInstance().getTime();
//            reff!!.child(MainActivity.user).child("jol").setValue(score)
            //reff!!.removeEventListener(addValueEventListener)
            Log.e("Multiplayer", "poddaję się")
            finish()
        }
        buttonReset.text = "Wyjdź"
    }

    private fun checkForWin(): Boolean = (checkHorizontal() || checkVertical() || checkLeftDiagonally() || checkRightDiagonally())

    private fun checkVertical(): Boolean {
        for (x in 4..94 step 10) {
            // Czy dwa środkowe mają takie same niepuste znaki
            if (buttons[x]!!.text.toString() != "" && buttons[x]!!.text.toString() == buttons[x + 1]!!.text.toString() &&
                    ((buttons[x - 3]!!.text.toString() == buttons[x]!!.text.toString() &&
                            buttons[x - 2]!!.text.toString() == buttons[x]!!.text.toString() &&
                            buttons[x - 1]!!.text.toString() == buttons[x]!!.text.toString() &&
                            buttons[x + 1]!!.text.toString() == buttons[x]!!.text.toString()) ||
                            (buttons[x - 2]!!.text.toString() == buttons[x]!!.text.toString() &&
                                    buttons[x - 1]!!.text.toString() == buttons[x]!!.text.toString() &&
                                    buttons[x + 1]!!.text.toString() == buttons[x]!!.text.toString() &&
                                    buttons[x + 2]!!.text.toString() == buttons[x]!!.text.toString()) ||
                            (buttons[x - 1]!!.text.toString() == buttons[x]!!.text.toString() &&
                                    buttons[x + 1]!!.text.toString() == buttons[x]!!.text.toString() &&
                                    buttons[x + 2]!!.text.toString() == buttons[x]!!.text.toString() &&
                                    buttons[x + 3]!!.text.toString() == buttons[x]!!.text.toString()) ||
                            (buttons[x + 1]!!.text.toString() == buttons[x]!!.text.toString() &&
                                    buttons[x + 2]!!.text.toString() == buttons[x]!!.text.toString() &&
                                    buttons[x + 3]!!.text.toString() == buttons[x]!!.text.toString() &&
                                    buttons[x + 4]!!.text.toString() == buttons[x]!!.text.toString()))
            ) return true
            // Jeśli lewy środek jest zaznaczony i jest różny od prawgo środka
            if (buttons[x]!!.text.toString() != "" &&
                    buttons[x - 4]!!.text.toString() == buttons[x]!!.text.toString() &&
                    buttons[x - 3]!!.text.toString() == buttons[x]!!.text.toString() &&
                    buttons[x - 2]!!.text.toString() == buttons[x]!!.text.toString() &&
                    buttons[x - 1]!!.text.toString() == buttons[x]!!.text.toString()
            ) return true
            // Jeśli prawy środek jest zaznaczony i jest różny od lewego środka
            if (buttons[x + 1]!!.text.toString() != "" &&
                    buttons[x + 5]!!.text.toString() == buttons[x + 1]!!.text.toString() &&
                    buttons[x + 4]!!.text.toString() == buttons[x + 1]!!.text.toString() &&
                    buttons[x + 3]!!.text.toString() == buttons[x + 1]!!.text.toString() &&
                    buttons[x + 2]!!.text.toString() == buttons[x + 1]!!.text.toString()
            )
                return true
        }
        return false
    }

    private fun checkHorizontal(): Boolean {
        for (x in 40..49) {
            // Czy dwa środkowe mają takie same niepuste znaki
            if (buttons[x]!!.text.toString() != "" && buttons[x]!!.text.toString() == buttons[x + 10]!!.text.toString() &&
                    (
                            (buttons[x - 30]!!.text.toString() == buttons[x]!!.text.toString() &&
                                    buttons[x - 20]!!.text.toString() == buttons[x]!!.text.toString() &&
                                    buttons[x - 10]!!.text.toString() == buttons[x]!!.text.toString() &&
                                    buttons[x + 10]!!.text.toString() == buttons[x]!!.text.toString()) ||
                                    (buttons[x - 20]!!.text.toString() == buttons[x]!!.text.toString() &&
                                            buttons[x - 10]!!.text.toString() == buttons[x]!!.text.toString() &&
                                            buttons[x + 10]!!.text.toString() == buttons[x]!!.text.toString() &&
                                            buttons[x + 20]!!.text.toString() == buttons[x]!!.text.toString()) ||
                                    (buttons[x - 10]!!.text.toString() == buttons[x]!!.text.toString() &&
                                            buttons[x + 10]!!.text.toString() == buttons[x]!!.text.toString() &&
                                            buttons[x + 20]!!.text.toString() == buttons[x]!!.text.toString() &&
                                            buttons[x + 30]!!.text.toString() == buttons[x]!!.text.toString()) ||
                                    (buttons[x + 10]!!.text.toString() == buttons[x]!!.text.toString() &&
                                            buttons[x + 20]!!.text.toString() == buttons[x]!!.text.toString() &&
                                            buttons[x + 30]!!.text.toString() == buttons[x]!!.text.toString() &&
                                            buttons[x + 40]!!.text.toString() == buttons[x]!!.text.toString())
                            )
            ) return true

            // Jeśli górny środek jest zaznaczony i jest różny od dolnego środka
            if (buttons[x]!!.text.toString() != "" &&
                    buttons[x - 40]!!.text.toString() == buttons[x]!!.text.toString() &&
                    buttons[x - 30]!!.text.toString() == buttons[x]!!.text.toString() &&
                    buttons[x - 20]!!.text.toString() == buttons[x]!!.text.toString() &&
                    buttons[x - 10]!!.text.toString() == buttons[x]!!.text.toString()
            ) return true

            // Jeśli dolny środek jest zaznaczony i jest różny od górnego środka
            if (buttons[x + 10]!!.text.toString() != "" &&
                    buttons[x + 50]!!.text.toString() == buttons[x + 10]!!.text.toString() &&
                    buttons[x + 40]!!.text.toString() == buttons[x + 10]!!.text.toString() &&
                    buttons[x + 30]!!.text.toString() == buttons[x + 10]!!.text.toString() &&
                    buttons[x + 20]!!.text.toString() == buttons[x + 10]!!.text.toString()
            ) return true
        }
        return false
    }

    private fun checkLeftDiagonally(): Boolean {
        for (x in 4..9) for (y in 0..50 step 10) if (buttons[y + x]!!.text.toString() != "" &&
                buttons[y + x + 9]!!.text.toString() == buttons[x + y]!!.text.toString() &&
                buttons[y + x + 18]!!.text.toString() == buttons[x + y]!!.text.toString() &&
                buttons[y + x + 27]!!.text.toString() == buttons[x + y]!!.text.toString() &&
                buttons[y + x + 36]!!.text.toString() == buttons[x + y]!!.text.toString()
        )
            return true
        return false
    }

    private fun checkRightDiagonally(): Boolean {
        for (x in 0..5) for (y in 0..50 step 10) if (buttons[y + x]!!.text.toString() != "" &&
                buttons[y + x + 11]!!.text.toString() == buttons[x + y]!!.text.toString() &&
                buttons[y + x + 22]!!.text.toString() == buttons[x + y]!!.text.toString() &&
                buttons[y + x + 33]!!.text.toString() == buttons[x + y]!!.text.toString() &&
                buttons[y + x + 44]!!.text.toString() == buttons[x + y]!!.text.toString()
        )
            return true
        return false
    }

    @SuppressLint("SetTextI18n")
    private fun updatePointsText() {
        tvp1.text = "$p1Name: $p1Score"
        tvp2.text = "$p2Name: $p2Score"
    }

    private fun resetGame() {
        p1Score = 0
        p2Score = 0
        updatePointsText()
        resetBoard()
    }

    private fun resetBoard() {
        for (i in 0..99) {
            buttons[i]!!.text = ""
            buttons[i]!!.isActivated = false
            buttons[i]!!.isEnabled = true
        }
        tvp1.isEnabled = true
        tvp2.isEnabled = true
        post.roundCount = 0
        buttonReset.text = "Kończę grę"
        post.board = -1
        switchTurn()
    }

    private fun switchTurn() {
        turn = !turn
        post.turn = turn
        reff?.child("Game")?.child(gameName)?.setValue(post)
    }

    private fun player1Wins() {
        p1Score++
        Toast.makeText(this, "$p1Name wins!", Toast.LENGTH_SHORT).show()
        updatePointsText()
        resetBoard()
    }

    private fun player2Wins() {
        p2Score++
        Toast.makeText(this, "$p2Name wins!", Toast.LENGTH_SHORT).show()
        updatePointsText()
        resetBoard()
    }

    private fun draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show()
        resetBoard()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState!!.putBoolean("turn", turn)
        outState.putString("id", gameName)
        outState.putBoolean("multiplayer", multiplayer)
    }

    override fun onStop() {
        super.onStop()
        var score =  Score()
        score.score1 = p1Score
        score.score2 = p2Score
        score.name = gameName
        score.player1 = p1Name
        score.player2 = p2Name
        score.time =  Calendar.getInstance().getTime();
        reff!!.child(MainActivity.user).child(maxId.toString()).setValue(score)
        post.players=false
        if (reff != null) {
            reff?.child("Game")?.child(gameName)?.setValue(post)
            //reff!!.removeEventListener(addValueEventListener);
        }
        finish()

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
